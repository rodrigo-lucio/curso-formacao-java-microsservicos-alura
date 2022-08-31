package br.com.alurafood.pedidos.infra.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Mensagem {

	private String mensagem;
	private String erro;

}