package com.usercase.api.domain.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "contas_a_pagar")
public class Conta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotNull
	@Column(name = "data_vencimento")
	private LocalDate dueDate;
	
	@NotNull
	@Column(name = "data_pagamento")
	private LocalDate payDay;
	
	@NotNull
	@Column(name = "valor")
	private Double value;
	
	@NotBlank
	@Column(name = "descricao")
	private String description;
	
	@Column(name = "situacao")
	private Boolean status;
	
	@PrePersist
	public void prePersist() {
		if(getId() == null) {
			setStatus(false);
		}
	}
}
