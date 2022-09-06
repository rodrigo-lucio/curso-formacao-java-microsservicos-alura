package br.com.alurafood.pagamentos.infra.client;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("pedido-service")
public interface PedidoClient {

    @RequestMapping(method = RequestMethod.PUT, value = "/pedido/{id}/pago")
    void aprovaPagamento(@PathVariable UUID id);

}
