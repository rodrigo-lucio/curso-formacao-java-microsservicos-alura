package br.com.alurafood.pagamentos.domain.service;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.alurafood.pagamentos.infra.dto.PessoaDTO;
import br.com.alurafood.pagamentos.domain.entity.Pessoa;
import br.com.alurafood.pagamentos.domain.repository.PessoaRepository;
import br.com.alurafood.pagamentos.shared.util.Utils;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Page<PessoaDTO> buscarTodos(Pageable pageable) {
        return pessoaRepository.findAll(pageable)
                .map(pessoa -> toPessoaDto(pessoa));
    }

    public PessoaDTO buscarPorId(UUID id) {
        Pessoa pessoa = buscarPessoa(id);
        return toPessoaDto(pessoa);
    }

    @Transactional
    public PessoaDTO criar(PessoaDTO dto) {
        Pessoa pessoa = toPessoaEntity(dto);
        pessoaRepository.saveAndFlush(pessoa);
        return toPessoaDto(pessoa);
    }

    @Transactional
    public PessoaDTO atualizar(UUID id, PessoaDTO dto) {
        Pessoa pessoa = buscarPessoa(id);
        Utils.copyProperties(toPessoaEntity(dto), pessoa, "id");
        pessoaRepository.saveAndFlush(pessoa);
        return toPessoaDto(pessoa);
    }

    @Transactional
    public PessoaDTO atualizarParcialmente(UUID id, PessoaDTO dto) {
        Pessoa pessoa = buscarPessoa(id);
        Utils.copyNonNullProperties(toPessoaEntity(dto), pessoa);
        pessoaRepository.saveAndFlush(pessoa);
        return toPessoaDto(pessoa);
    }

    @Transactional
    public void remover(UUID id) {
        pessoaRepository.deleteById(id);
    }
    
    private Pessoa buscarPessoa(UUID id) {
        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> new EmptyResultDataAccessException("Recurso não encontrado para o id " + id, 1));
        return pessoa;
    }

    private PessoaDTO toPessoaDto(Pessoa pessoa) {
        return modelMapper.map(pessoa, PessoaDTO.class);
    }

    private Pessoa toPessoaEntity(PessoaDTO dto) {
        return modelMapper.map(dto, Pessoa.class);
    }

}
