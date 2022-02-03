package com.lulo.lulobank.exception;

import java.util.List;

import com.lulo.lulobank.model.Account;
import com.lulo.lulobank.model.AccountRq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class ErrorResponse {

	private String message;
    private List<String> details;
}
