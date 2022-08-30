package br.com.alurafood.pagamentos.api.exception;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Erro {

	private List<Mensagem> mensagens;
	
}