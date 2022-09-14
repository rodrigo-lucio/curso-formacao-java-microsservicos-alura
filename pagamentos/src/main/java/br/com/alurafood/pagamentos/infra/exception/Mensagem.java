package br.com.alurafood.pagamentos.infra.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Mensagem {

	private String mensagem;
	private String erro;

}