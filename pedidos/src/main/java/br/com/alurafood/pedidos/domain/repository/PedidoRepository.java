package br.com.alurafood.pedidos.domain.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import br.com.alurafood.pedidos.domain.entity.Pedido;
import br.com.alurafood.pedidos.domain.entity.Status;

public interface PedidoRepository extends JpaRepository<Pedido, UUID> {

    @Modifying(clearAutomatically = true)
    @Query("update Pedido p set p.status = :status where p = :pedido")
    void atualizaStatus(Status status, Pedido pedido);

    @Query(value = "SELECT p from Pedido p LEFT JOIN FETCH p.itens where p.id = :id")
    Pedido porIdComItens(UUID id);

}
