package br.com.alurafood.pagamentos.api.exception;

public class ServicoException extends GlobalException {

	private static final long serialVersionUID = 1L;

	public ServicoException(Mensagem mensagem) {
		super(mensagem);
	}

}
