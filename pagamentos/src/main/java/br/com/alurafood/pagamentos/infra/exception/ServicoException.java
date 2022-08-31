package br.com.alurafood.pagamentos.infra.exception;

public class ServicoException extends GlobalException {

	private static final long serialVersionUID = 1L;

	public ServicoException(Mensagem mensagem) {
		super(mensagem);
	}

}
