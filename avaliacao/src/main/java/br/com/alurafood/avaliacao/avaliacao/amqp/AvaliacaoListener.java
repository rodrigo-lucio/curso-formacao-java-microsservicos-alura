package br.com.alurafood.avaliacao.avaliacao.amqp;

import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import br.com.alurafood.avaliacao.avaliacao.dto.PagamentoDTO;

@Component
public class AvaliacaoListener {

    @RabbitListener(queues = PagamentoEventsConstants.QUEUE_PAGAMENTO_EVENTS) // concurrency = "100-100" = lê de 100 em 100
    public void recebeMensagem(@Payload PagamentoDTO pagamento) {
        System.out.println("lendo pagamento events");
        if(!StringUtils.isNumeric(pagamento.getNumero())) {
            throw new RuntimeException("Número do cartão inválido");
        }
        System.out.println(PagamentoEventsConstants.QUEUE_PAGAMENTO_EVENTS + pagamento.toString());
    }
}
