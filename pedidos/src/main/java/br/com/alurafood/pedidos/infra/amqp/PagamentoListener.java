package br.com.alurafood.pedidos.infra.amqp;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListeners;
import org.springframework.stereotype.Component;

import br.com.alurafood.pedidos.infra.dto.PagamentoDTO;

@Component
public class PagamentoListener {

    @RabbitListener(queues = PagamentoEventsConstants.QUEUE_PAGAMENTO_EVENTS)
    public void recebeMensagem(PagamentoDTO pagamentoDTO) {
        System.out.println(PagamentoEventsConstants.QUEUE_PAGAMENTO_EVENTS + pagamentoDTO.toString());
    }

}
