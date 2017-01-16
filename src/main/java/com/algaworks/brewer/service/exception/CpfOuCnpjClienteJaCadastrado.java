package com.algaworks.brewer.service.exception;

public class CpfOuCnpjClienteJaCadastrado extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public CpfOuCnpjClienteJaCadastrado(String mensage) {
		super(mensage);
	}
}
