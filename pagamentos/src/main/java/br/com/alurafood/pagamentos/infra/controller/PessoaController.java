package br.com.alurafood.pagamentos.infra.controller;

import java.net.URI;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alurafood.pagamentos.infra.dto.PessoaDTO;
import br.com.alurafood.pagamentos.domain.service.PessoaService;

@RestController
@RequestMapping("pessoa")
public class PessoaController {

    @Autowired
    public PessoaService service;

    @GetMapping
    public Page<PessoaDTO> listar(@PageableDefault Pageable paginacao){
        return service.buscarTodos(paginacao);
    }

    @GetMapping("{id}")
    public PessoaDTO buscarPorId(@PathVariable @NotNull UUID id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    public ResponseEntity<PessoaDTO> criar(@RequestBody PessoaDTO pessoaDTO, UriComponentsBuilder uriBuilder) {
        PessoaDTO pessoaCriada = service.criar(pessoaDTO);
        URI endereco = uriBuilder.path("/pessoa/{id}").buildAndExpand(pessoaCriada.getId()).toUri();
        return ResponseEntity.created(endereco).body(pessoaCriada);
    }

    @PutMapping("{id}")
    public ResponseEntity<PessoaDTO> atualizar(@PathVariable @NotNull UUID id, @RequestBody PessoaDTO pessoaDTO) {
        PessoaDTO pessoaAtualizada = service.atualizar(id, pessoaDTO);
        return ResponseEntity.ok(pessoaAtualizada);
    }

    @PatchMapping("{id}")
    public ResponseEntity<PessoaDTO> atualizarParcialmente(@PathVariable @NotNull UUID id, @RequestBody PessoaDTO pessoaDTO) {
        PessoaDTO pessoaAtualizada = service.atualizarParcialmente(id, pessoaDTO);
        return ResponseEntity.ok(pessoaAtualizada);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<PessoaDTO> remover(@PathVariable @NotNull UUID id) {
        service.remover(id);
        return ResponseEntity.noContent().build();
    }

}
