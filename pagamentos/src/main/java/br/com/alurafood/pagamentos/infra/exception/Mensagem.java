package br.com.alurafood.pagamentos.infra.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Mensagem {

	private String mensagem;
	private String erro;

}