package br.com.alurafood.pagamentos.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.alurafood.pagamentos.model.Pagamento;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, UUID> {

}
