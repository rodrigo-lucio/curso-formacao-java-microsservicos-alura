package br.com.alurafood.pedidos.infra.dto;

import java.util.UUID;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemPedidoDto {

    private UUID id;
    private Integer quantidade;
    private String descricao;
}
