package br.com.alurafood.pagamentos.infra.exception;

public class GlobalException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private Mensagem mensagem;

	public GlobalException(Mensagem mensagem) {
		super();
		this.mensagem = mensagem;
	}

	public Mensagem getMensagem() {
		return mensagem;
	}

	@Override
	public String toString() {
		return this.getClass().toString();
	}

}
