package com.lulo.lulobank.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Account {
	private Long id;
	@JsonProperty("active-card")
	private boolean activeCard; 
	@JsonProperty("available-limit")
	private Integer availableLimit;
	

}
