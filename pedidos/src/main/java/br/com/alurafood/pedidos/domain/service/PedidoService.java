package br.com.alurafood.pedidos.domain.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.alurafood.pedidos.domain.entity.Pedido;
import br.com.alurafood.pedidos.domain.entity.Status;
import br.com.alurafood.pedidos.domain.repository.PedidoRepository;
import br.com.alurafood.pedidos.infra.dto.PedidoDTO;
import br.com.alurafood.pedidos.infra.dto.StatusDTO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PedidoService {

    @Autowired
    private PedidoRepository repository;

    @Autowired
    private final ModelMapper modelMapper;

    public List<PedidoDTO> obterTodos() {
        return repository.findAll().stream()
                .map(p -> modelMapper.map(p, PedidoDTO.class))
                .collect(Collectors.toList());
    }

    public PedidoDTO obterPorId(UUID id) {
        Pedido pedido = repository.findById(id)
                .orElseThrow(() -> new EmptyResultDataAccessException("Pedido não encontrado para o id " + id, 1));
        return modelMapper.map(pedido, PedidoDTO.class);
    }

    @Transactional
    public PedidoDTO criarPedido(PedidoDTO dto) {
        Pedido pedido = modelMapper.map(dto, Pedido.class);
        pedido.setDataHora(LocalDateTime.now());
        pedido.setStatus(Status.REALIZADO);
        pedido.getItens().forEach(item -> item.setPedido(pedido));
        Pedido pedidoSalvo = repository.save(pedido);
        return modelMapper.map(pedidoSalvo, PedidoDTO.class);
    }

    @Transactional
    public PedidoDTO atualizaStatus(UUID id, StatusDTO dto) {
        Pedido pedido = repository.porIdComItens(id);
        if (Objects.isNull(pedido)) {
            throw new EmptyResultDataAccessException("Pedido não encontrado para o id " + id, 1);
        }
        pedido.setStatus(dto.getStatus());
        repository.atualizaStatus(dto.getStatus(), pedido);
        return modelMapper.map(pedido, PedidoDTO.class);
    }

    @Transactional
    public void aprovaPagamentoPedido(UUID id) {
        Pedido pedido = repository.porIdComItens(id);
        if (Objects.isNull(pedido)) {
            throw new EmptyResultDataAccessException("Pedido não encontrado para o id " + id, 1);
        }
        pedido.setStatus(Status.PAGO);
        repository.atualizaStatus(Status.PAGO, pedido);
    }

    @Transactional
    public void remover(UUID id) {
        if (!repository.existsById(id)) {
            throw new EmptyResultDataAccessException("Pedido não encontrado para o id " + id, 1);
        }
        repository.deleteById(id);
    }
}
