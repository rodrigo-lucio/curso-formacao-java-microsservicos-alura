package br.com.alurafood.pagamentos.domain.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.alurafood.pagamentos.domain.entity.Pessoa;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, UUID> {

}
