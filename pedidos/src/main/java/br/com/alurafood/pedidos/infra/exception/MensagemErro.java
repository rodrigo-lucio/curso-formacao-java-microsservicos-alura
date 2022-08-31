package br.com.alurafood.pedidos.infra.exception;

public enum MensagemErro {
	
	CORPO_MENSAGEM_INVALIDO("Corpo da mensagem inválido"),
	CONTENT_TYPE_NAO_SUPORTADO("Tipo de mídia não suportado"),
	METODO_NAO_PERMITIDO("Método não permitido"),
	RECURSO_NAO_ENCONTRADO_PARA("Recurso não encontrado para %s"),
	PARAMETRO_INVALIDO("Parâmetro inválido na requisição");
	
	private String mensagem;
	
	private MensagemErro(String mensagem) {
		this.mensagem = mensagem;
	}

	public String toString() {
		return this.mensagem;
	}

}
