package br.com.alurafood.pedidos.domain.exception;

public class ServicoException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String mensagem;

	public ServicoException(String mensagem) {
		super();
		this.mensagem = mensagem;
	}

	public String getMensagem() {
		return mensagem;
	}

	@Override
	public String toString() {
		return this.getClass().toString();
	}

}
