package br.com.alurafood.pagamentos.domain.exception;

public class CpfCnpjInvalidoExcepton extends ServicoException {

	private static final long serialVersionUID = 1L;

	public CpfCnpjInvalidoExcepton(String mensagem) {
		super(mensagem);
	}
	
}
