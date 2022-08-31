package br.com.alurafood.pedidos.infra.exception;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Erro {

	private List<Mensagem> camposInvalidos;
	
}