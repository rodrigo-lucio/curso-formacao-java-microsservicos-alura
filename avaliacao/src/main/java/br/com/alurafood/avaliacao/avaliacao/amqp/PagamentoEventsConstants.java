package br.com.alurafood.avaliacao.avaliacao.amqp;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PagamentoEventsConstants {

    public static final String EXCHANGE_PAGAMENTO_EVENTS = "pagamento.events";
    public static final String EXCHANGE_PAGAMENTO_EVENTS_DEAD_LETTER = "pagamento.events-dead-letter";
    public static final String QUEUE_PAGAMENTO_EVENTS = "pagamento.events.pagamento-criado.avaliacao";
    public static final String QUEUE_PAGAMENTO_EVENTS_DEAD_LETTER = "pagamento.events.pagamento-criado.avaliacao-dead-letter";

}
