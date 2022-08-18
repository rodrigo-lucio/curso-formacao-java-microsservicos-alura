package br.com.alurafood.pagamentos.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.alurafood.pagamentos.model.Pessoa;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, UUID> {

}
