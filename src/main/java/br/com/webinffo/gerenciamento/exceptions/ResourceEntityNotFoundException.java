package br.com.webinffo.gerenciamento.exceptions;

public class ResourceEntityNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public ResourceEntityNotFoundException (String msg) {
		super(msg);
	}
}
