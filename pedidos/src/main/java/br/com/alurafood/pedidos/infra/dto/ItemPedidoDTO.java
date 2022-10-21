package br.com.alurafood.pedidos.infra.dto;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemPedidoDTO {

    @JsonIgnore
    private UUID id;
    private Integer quantidade;
    private String descricao;
}
