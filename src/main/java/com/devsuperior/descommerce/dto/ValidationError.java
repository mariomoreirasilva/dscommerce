package com.devsuperior.descommerce.dto;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ValidationError extends CustomError{
	
	private List<FieldMessage> erros = new ArrayList<>();

	public ValidationError(Instant timestamp, Integer status, String error, String path) {
		super(timestamp, status, error, path);
		
	}

	public List<FieldMessage> getErros() {
		return erros;
	}
	
	public void adicionaError(String field, String message) {
		
		erros.add(new FieldMessage(field, message));
		
	}

}
