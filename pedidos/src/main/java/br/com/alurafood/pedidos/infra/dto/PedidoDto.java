package br.com.alurafood.pedidos.infra.dto;

import br.com.alurafood.pedidos.domain.entity.Status;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDto {

    private UUID id;
    private LocalDateTime dataHora;
    private Status status;
    private List<ItemPedidoDto> itens = new ArrayList<>();

}