package com.usercase.api.domain.repository;

import java.time.LocalDate;

import com.usercase.api.domain.model.Conta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ContaRepository extends JpaRepository<Conta, Integer> {

	Page<Conta> findByStatusAndDueDateAndDescriptionContaining(Boolean status, LocalDate dueLocalDate, String description, Pageable pageable);
	
	@Query("SELECT SUM(b.value) FROM Conta b WHERE b.dueDate BETWEEN :startDate AND :endDate")
	Double countByPeriod(LocalDate startDate, LocalDate endDate);
	
}	
