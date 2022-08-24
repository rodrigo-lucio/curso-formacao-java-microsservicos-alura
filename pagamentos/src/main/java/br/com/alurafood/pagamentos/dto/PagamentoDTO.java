package br.com.alurafood.pagamentos.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;

import br.com.alurafood.pagamentos.domain.model.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
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
    private LocalDateTime dataHoraCriacao;
    private LocalDateTime dataHoraAtualizacao;
}
