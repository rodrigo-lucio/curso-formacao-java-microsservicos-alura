package br.com.alurafood.pagamentos.domain.service;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.alurafood.pagamentos.domain.entity.Pagamento;
import br.com.alurafood.pagamentos.domain.entity.Status;
import br.com.alurafood.pagamentos.domain.repository.PagamentoRepository;
import br.com.alurafood.pagamentos.infra.client.PedidoClient;
import br.com.alurafood.pagamentos.infra.dto.PagamentoDTO;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository repository;

    @Autowired
    private PedidoClient pedidoClient;

    @Autowired
    private ModelMapper modelMapper;

    public Page<PagamentoDTO> buscarTodos(Pageable pageable) {
        return repository.findAll(pageable)
                .map(pagamento -> modelMapper.map(pagamento, PagamentoDTO.class));
    }

    public PagamentoDTO buscarPorId(UUID id) {
        Pagamento pagamento = repository.findById(id)
                .orElseThrow(() -> new EmptyResultDataAccessException("Pedido não encontrado para o id " + id, 1));
        return modelMapper.map(pagamento, PagamentoDTO.class);
    }

    @Transactional
    public PagamentoDTO criar(PagamentoDTO dto) {
        Pagamento pagamento = modelMapper.map(dto, Pagamento.class);
        pagamento.setStatus(Status.CRIADO);
        repository.saveAndFlush(pagamento);
        return modelMapper.map(pagamento, PagamentoDTO.class);
    }

    @Transactional
    public PagamentoDTO atualizar(UUID id, PagamentoDTO dto) {
        Pagamento pagamento = repository.findById(id)
                .orElseThrow(() -> new EmptyResultDataAccessException("Recurso não encontrado para o id " + id, 1));
        Pagamento pagamentoAtualizado = modelMapper.map(dto, Pagamento.class);
        BeanUtils.copyProperties(pagamentoAtualizado, pagamento, "id");
        repository.saveAndFlush(pagamento);
        return modelMapper.map(pagamento, PagamentoDTO.class);
    }

    public void confirmarPagamento(UUID id) {
        Pagamento pagamento = repository.findById(id)
                .orElseThrow(() -> new EmptyResultDataAccessException("Recurso não encontrado para o id " + id, 1));
        pagamento.setStatus(Status.CONFIRMADO);
        pedidoClient.aprovaPagamento(pagamento.getPedidoId());
        repository.saveAndFlush(pagamento);
    }

    @Transactional
    public void remover(UUID id) {
        repository.deleteById(id);
    }

}
