package com.usercase.api.domain.service;

import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.usercase.api.domain.model.Conta;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.usercase.api.domain.repository.ContaRepository;
import com.usercase.api.infrastructure.exception.GenericException;
import com.opencsv.CSVReader;

@Service
public class ContaService {

	@Autowired
	private ContaRepository repository;
	
	public Conta create(Conta obj) {
		return repository.save(obj);
	}
	
	public Conta findById(Integer id) {
		return repository.findById(id).orElseThrow(() -> new EmptyResultDataAccessException(1));
	}
	
	public Conta update(Conta obj) {
		Conta objSaved = findById(obj.getId());
		
		BeanUtils.copyProperties(obj, objSaved, "id", "status");
		
		return repository.save(objSaved);
	}
	
	public void changeStatus(Integer id) {
		Conta objSaved = findById(id);
		Boolean status = !objSaved.getStatus();
		objSaved.setStatus(status);
		repository.save(objSaved);
	}
	
	public Page<Conta> search(String statusStr, String dueDateStr, String description, Pageable pageable) {
		
		LocalDate dueDate = LocalDate.parse(dueDateStr);
		Boolean status = Boolean.parseBoolean(statusStr);
		
		return repository.findByStatusAndDueDateAndDescriptionContaining(status, dueDate, description, pageable);
	}
	
	public Double countByPeriod(String startDateStr, String endDateStr) {
		if(!startDateStr.equals("") && !endDateStr.equals("")) {
			LocalDate startDate = LocalDate.parse(startDateStr);
			LocalDate endDate = LocalDate.parse(endDateStr);
			return repository.countByPeriod(startDate, endDate);
		} else {
			return 0.0;
		}		
	}
	
	public void delete(Integer id) {
		repository.deleteById(id);
	}
	
	public List<Conta> importCsv(MultipartFile file) {
        List<Conta> bills = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
            String[] headers = reader.readNext();
            Map<String, Integer> headerMap = mapHeaders(headers);

            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                Conta bill = new Conta();
                bill.setDueDate(LocalDate.parse(nextLine[headerMap.get("data_vencimento")], DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                bill.setPayDay(LocalDate.parse(nextLine[headerMap.get("data_pagamento")], DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                bill.setValue(Double.parseDouble(nextLine[headerMap.get("valor")]));
                bill.setDescription(nextLine[headerMap.get("descricao")]);
                bill.setStatus(Boolean.parseBoolean(nextLine[headerMap.get("situacao")]));
                bills.add(bill);
            }
        } catch (Exception e) {
        	e.printStackTrace();
        	throw new GenericException("Não foi possível salvar os dados em CSV");
		}
        return repository.saveAll(bills);
    }
	
	private Map<String, Integer> mapHeaders(String[] headers) {
        Map<String, Integer> headerMap = new HashMap<>();
        for (int i = 0; i < headers.length; i++) {
            headerMap.put(headers[i], i);
        }
        return headerMap;
    }
	
}
