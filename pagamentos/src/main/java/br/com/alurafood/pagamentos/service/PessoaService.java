package br.com.alurafood.pagamentos.service;

import java.util.UUID;

import javax.persistence.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.alurafood.pagamentos.dto.PessoaDTO;
import br.com.alurafood.pagamentos.model.Pessoa;
import br.com.alurafood.pagamentos.repository.PessoaRepository;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Page<PessoaDTO> buscarTodos(Pageable pageable) {
        return pessoaRepository.findAll(pageable)
                .map(pagamento -> modelMapper.map(pagamento, PessoaDTO.class));
    }

    public PessoaDTO buscarPorId(UUID id) {
        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());
        return modelMapper.map(pessoa, PessoaDTO.class);
    }

    @Transactional
    public PessoaDTO criar(PessoaDTO dto) {
        Pessoa pessoa = modelMapper.map(dto, Pessoa.class);
        pessoaRepository.save(pessoa);
        return modelMapper.map(pessoa, PessoaDTO.class);
    }

    @Transactional
    public PessoaDTO atualizar(UUID id, PessoaDTO dto) {
        Pessoa pagamento = pessoaRepository.findById(id)
                .orElseThrow(() -> new EmptyResultDataAccessException("Recurso não não encontrado para o id " + id, 1));
        Pessoa pagamentoAtualizado = modelMapper.map(dto, Pessoa.class);
        BeanUtils.copyProperties(pagamentoAtualizado, pagamento, "id");
        pessoaRepository.saveAndFlush(pagamento);
        return modelMapper.map(pagamento, PessoaDTO.class);
    }

    @Transactional
    public void remover(UUID id) {
        pessoaRepository.deleteById(id);
    }
}
