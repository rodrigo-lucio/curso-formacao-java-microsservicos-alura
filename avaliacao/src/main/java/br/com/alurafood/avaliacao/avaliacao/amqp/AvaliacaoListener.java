package br.com.alurafood.avaliacao.avaliacao.amqp;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import br.com.alurafood.avaliacao.avaliacao.dto.PagamentoDTO;

@Component
public class AvaliacaoListener {

    @RabbitListener(queues = PagamentoEventsConstants.QUEUE_PAGAMENTO_EVENTS)
    public void recebeMensagem(@Payload PagamentoDTO pagamento) {
        System.out.println(PagamentoEventsConstants.QUEUE_PAGAMENTO_EVENTS + pagamento.toString());
    }
}
