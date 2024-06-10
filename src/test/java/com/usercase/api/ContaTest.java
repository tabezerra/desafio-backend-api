package com.usercase.api;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import com.usercase.api.domain.model.Conta;
import com.usercase.api.domain.service.ContaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ContaTest {

	@Autowired
	private ContaService service;
	
	@Test
	public void contextLoads() {
		
		Conta obj = create();
		Conta objSaved = update(obj);
		delete(objSaved.getId());
		
	}
	
	private Conta create() {
		Conta obj = new Conta();
		obj.setDescription("Test");
		obj.setDueDate(LocalDate.now());
		obj.setPayDay(LocalDate.now());
		obj.setValue(200.0);
		
		Conta objSaved = service.create(obj);
		
		assertThat(obj).isEqualTo(objSaved);
		
		return objSaved;
	}
	
	private Conta update(Conta obj) {
		
		obj.setDescription("Test 2");
		obj.setDueDate(LocalDate.now());
		obj.setPayDay(LocalDate.now());
		obj.setValue(400.0);
		
		Conta objSaved = service.update(obj);
		
		assertThat(obj).isEqualTo(objSaved);
		
		return objSaved;
		
	}
	
	private void delete(Integer id) {
		service.delete(id);
	}
	
}
