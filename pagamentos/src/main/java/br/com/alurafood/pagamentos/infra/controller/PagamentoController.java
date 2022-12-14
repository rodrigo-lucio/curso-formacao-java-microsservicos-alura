package br.com.alurafood.pagamentos.infra.controller;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.gson.Gson;

import br.com.alurafood.pagamentos.domain.service.PagamentoService;
import br.com.alurafood.pagamentos.infra.amqp.PagamentoEventsConstants;
import br.com.alurafood.pagamentos.infra.dto.PagamentoDTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping("pagamento")
public class PagamentoController {

    @Autowired
    public PagamentoService service;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping
    public Page<PagamentoDTO> listar(@PageableDefault Pageable paginacao){
        return service.buscarTodos(paginacao);
    }

    @GetMapping("/{id}")
    public PagamentoDTO buscarPorId(@PathVariable @NotNull UUID id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    public ResponseEntity<PagamentoDTO> criar(@RequestBody PagamentoDTO pagamentoDTO, UriComponentsBuilder uriBuilder) {
        PagamentoDTO pagamentoCriado = service.criar(pagamentoDTO);
        URI endereco = uriBuilder.path("/pagamento/{id}").buildAndExpand(pagamentoCriado.getId()).toUri();
        rabbitTemplate.convertAndSend(PagamentoEventsConstants.EXCHANGE_PAGAMENTO_EVENTS, "", pagamentoCriado);
        return ResponseEntity.created(endereco).body(pagamentoCriado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PagamentoDTO> atualizar(@PathVariable @NotNull UUID id, @Valid PagamentoDTO pagamentoDTO) {
        PagamentoDTO pagamentoAtualizado = service.atualizar(id, pagamentoDTO);
        return ResponseEntity.ok(pagamentoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable @NotNull UUID id) {
        service.remover(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/confirmar")
    @CircuitBreaker(name = "confirmarPagamentoAtualizaPedido", fallbackMethod = "pagamentoAutorizadoPedidoPendenteIntegracao")
    public ResponseEntity<Void> confirmarPagamento(@PathVariable @NotNull UUID id) {
        service.confirmarPagamento(id);
        return ResponseEntity.accepted().build();
    }

    public ResponseEntity<Void> pagamentoAutorizadoPedidoPendenteIntegracao(UUID id, Exception e) {
        service.confirmarPagamentoPedidoPendenteIntegracao(id);
        return ResponseEntity.accepted().build();
    }

}
