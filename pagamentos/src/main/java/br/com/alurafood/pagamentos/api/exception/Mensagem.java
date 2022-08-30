package br.com.alurafood.pagamentos.api.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Mensagem {

	private String mensagemUsuario;
	private String mensagemDesenvolvedor;

}