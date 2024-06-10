package com.usercase.api.infrastructure.rest;

import java.util.List;

import com.usercase.api.domain.model.Conta;
import com.usercase.api.domain.service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/conta")
public class ContaController {

	@Autowired
	private ContaService service;

	@PostMapping
	public ResponseEntity<Conta> create(@RequestBody @Valid Conta obj) {
		Conta objSaved = service.create(obj);
		return ResponseEntity.status(HttpStatus.CREATED).body(objSaved);
	}

	@PutMapping
	public ResponseEntity<Conta> update(@RequestBody @Valid Conta obj) {
		Conta objSaved = service.update(obj);
		return ResponseEntity.ok(objSaved);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Conta> findById(@PathVariable("id") Integer id) {
		Conta objSaved = service.findById(id);
		return ResponseEntity.ok(objSaved);
	}

	@PutMapping("/status/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void changeStatus(@PathVariable("id") Integer id) {
		service.changeStatus(id);
	}

	@GetMapping
	public Page<Conta> search(@RequestParam(defaultValue = "false") String status, String dueDate,
							  @RequestParam(defaultValue = "") String description, Pageable pageable) {
		return service.search(status, dueDate, description, pageable);
	}

	@GetMapping("/contar-por-periodo")
	public ResponseEntity<Double> countByPeriod(@RequestParam(defaultValue = "") String startDate,
			@RequestParam(defaultValue = "") String endDate) {
		Double value = service.countByPeriod(startDate, endDate);
		return ResponseEntity.ok(value);
	}
	
	@PostMapping("/csv")
	public List<Conta> importCsv(@RequestParam("arquivo") MultipartFile file) {
		return service.importCsv(file);
	}

}
