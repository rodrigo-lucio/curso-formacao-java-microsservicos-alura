package br.com.alurafood.pagamentos.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;

import br.com.alurafood.pagamentos.model.TipoPessoa;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PessoaDTO {

    private UUID id;
    private String nome;
    private TipoPessoa tipoPessoa;
    private String cpfCnpj;
    private String email;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;

}
