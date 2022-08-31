package br.com.alurafood.pedidos.domain.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.alurafood.pedidos.infra.dto.PedidoDto;
import br.com.alurafood.pedidos.infra.dto.StatusDto;
import br.com.alurafood.pedidos.domain.entity.Pedido;
import br.com.alurafood.pedidos.domain.entity.Status;
import br.com.alurafood.pedidos.domain.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PedidoService {

    @Autowired
    private PedidoRepository repository;

    @Autowired
    private final ModelMapper modelMapper;

    public List<PedidoDto> obterTodos() {
        return repository.findAll().stream()
                .map(p -> modelMapper.map(p, PedidoDto.class))
                .collect(Collectors.toList());
    }

    public PedidoDto obterPorId(UUID id) {
        Pedido pedido = repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        return modelMapper.map(pedido, PedidoDto.class);
    }

    @Transactional
    public PedidoDto criarPedido(PedidoDto dto) {
        Pedido pedido = modelMapper.map(dto, Pedido.class);
        pedido.setDataHora(LocalDateTime.now());
        pedido.setStatus(Status.REALIZADO);
        pedido.getItens().forEach(item -> item.setPedido(pedido));
        Pedido salvo = repository.save(pedido);
        return modelMapper.map(salvo, PedidoDto.class);
    }

    @Transactional
    public PedidoDto atualizaStatus(UUID id, StatusDto dto) {
        Pedido pedido = repository.porIdComItens(id);
        if (pedido == null) {
            throw new EntityNotFoundException();
        }
        pedido.setStatus(dto.getStatus());
        repository.atualizaStatus(dto.getStatus(), pedido);
        return modelMapper.map(pedido, PedidoDto.class);
    }

    @Transactional
    public void aprovaPagamentoPedido(UUID id) {
        Pedido pedido = repository.porIdComItens(id);
        if (Objects.nonNull(pedido)) {
            throw new EntityNotFoundException();
        }
        pedido.setStatus(Status.PAGO);
        repository.atualizaStatus(Status.PAGO, pedido);
    }

    @Transactional
    public void remover(UUID id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException();
        }
        repository.deleteById(id);
    }
}
