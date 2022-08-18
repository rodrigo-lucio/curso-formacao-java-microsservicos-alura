package br.com.alurafood.pagamentos.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pagamento")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Pagamento {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false)
    private UUID id;

    @NotNull
    @Positive
    @Column(name = "valor")
    private BigDecimal valor;

    @NotBlank
    @Size(max = 100)
    @Column(name = "nome")
    private String nome;

    @NotBlank
    @Size(max = 19)
    @Column(name = "numero")
    private String numero;

    @NotBlank
    @Size(max = 7)
    @Column(name = "expiracao")
    private String expiracao;

    @NotBlank
    @Size(min = 3, max = 3)
    @Column(name = "codigo")
    private String codigo;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @NotNull
    @Column(name = "pedido_id")
    private UUID pedidoId;

    @NotNull
    @Column(name = "forma_pagamento_id")
    private UUID formaPagamentoId;

    @CreatedDate
    @Column(name = "criado_em", updatable = false)
    private LocalDateTime criadoEm;

    @LastModifiedDate
    @Column(name = "atualizado_em")
    private LocalDateTime atualizadoEm;

}
