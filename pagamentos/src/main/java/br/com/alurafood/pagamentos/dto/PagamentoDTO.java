package br.com.alurafood.pagamentos.dto;

import java.math.BigDecimal;
import java.util.UUID;

import br.com.alurafood.pagamentos.model.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagamentoDTO {

    private UUID id;
    private BigDecimal valor;
    private String nome;
    private String numero;
    private String expiracao;
    private String codigo;
    private Status status;
    private UUID pedidoId;
    private UUID formaPagamentoId;

}
