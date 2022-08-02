package br.com.alurafood.pagamentos.dto;

import java.math.BigDecimal;
import java.util.UUID;

import br.com.alurafood.pagamentos.model.Status;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PagamentoDTO {

    private UUID id;
    private BigDecimal valor;
    private String nome;
    private Status status;
    private UUID pedidoId;
    private UUID formaPagamentoId;

}
