package br.com.alurafood.pagamentos.infra.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import br.com.alurafood.pagamentos.domain.entity.TipoPessoa;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PessoaDTO {

    @JsonIgnore
    private UUID id;
    private String nome;
    private TipoPessoa tipoPessoa;
    private String cpfCnpj;
    private String email;
    private UUID pedidoId;

}
