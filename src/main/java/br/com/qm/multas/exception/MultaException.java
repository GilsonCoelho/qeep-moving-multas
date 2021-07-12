package br.com.qm.multas.exception;

public class MultaException extends Exception{

	private static final long serialVersionUID = 353395560889389756L;
	
	private String menssagem;

	public MultaException(String menssagem) {
		
		this.menssagem = menssagem;
		
	}

	public String getMenssagem() {
		return menssagem;
	}	

}
