package br.com.alurafood.pedidos.infra.amqp;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import br.com.alurafood.pedidos.infra.dto.PagamentoDTO;

@Component
public class PagamentoListener {

    @RabbitListener(queues = "pagamento.concluido")
    public void recebeMensagem(PagamentoDTO pagamentoDTO) {
        System.out.printf("Recebeu " + pagamentoDTO.toString());
    }

}
