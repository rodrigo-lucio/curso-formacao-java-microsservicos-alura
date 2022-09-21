package br.com.alurafood.pagamentos.domain.entity;

import java.util.Objects;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class CpfCnpjConverter implements AttributeConverter<CpfCnpj, String> {

    @Override
    public String convertToDatabaseColumn(CpfCnpj cpfCnpj) {
        return Objects.isNull(cpfCnpj) ? null : cpfCnpj.toString();
    }

    @Override
    public CpfCnpj convertToEntityAttribute(String cpfCnpjNumero) {
        return Objects.isNull(cpfCnpjNumero) ? null : CpfCnpj.of(cpfCnpjNumero);
    }
}
