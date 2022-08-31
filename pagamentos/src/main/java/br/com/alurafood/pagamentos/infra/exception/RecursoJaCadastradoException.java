package br.com.alurafood.pagamentos.infra.exception;

public class RecursoJaCadastradoException extends GlobalException {

	private static final long serialVersionUID = 1L;

	public RecursoJaCadastradoException(Mensagem mensagem) {
		super(mensagem);
	}
	
}
