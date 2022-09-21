package br.com.alurafood.pagamentos.domain.entity;

import java.util.Objects;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import br.com.alurafood.pagamentos.domain.exception.CpfCnpjInvalidoExcepton;

public class CpfCnpj {

    private String cpfCnpjNumero;
    private static final int[] PESO_CPF = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
    private static final int[] PESO_CNPJ = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

    public static CpfCnpj of(String cpfCnpjNumero) {
        return new CpfCnpj(cpfCnpjNumero);
    }

    private CpfCnpj(String cpfCnpjNumero) {
        if (!isValido(cpfCnpjNumero)) {
            throw new CpfCnpjInvalidoExcepton("Campo CPF/CNPJ é inválido");
        }
        this.cpfCnpjNumero = cpfCnpjNumero;
    }

    public static boolean isValido(String cpfCnpj) {
        if (StringUtils.isBlank(cpfCnpj)) {
            throw new CpfCnpjInvalidoExcepton("Campo 'CPF/CNPJ' não deve estar em branco");
        }

        if (!Pattern.matches("\\d+", cpfCnpj)) {
            return false;
        }

        if ((cpfCnpj.length() != 11 && cpfCnpj.length() != 14)) {
            return false;
        }

        if (cpfCnpj.length() == 11) {
            return isValidCPF(cpfCnpj);
        }
        return isValidCNPJ(cpfCnpj);
    }

    private static int calcularDigito(String str, int[] peso) {
        int soma = 0;
        for (int indice = str.length() - 1, digito; indice >= 0; indice--) {
            digito = Integer.parseInt(str.substring(indice, indice + 1));
            soma += digito * peso[peso.length - str.length() + indice];
        }
        soma = 11 - soma % 11;
        return soma > 9 ? 0 : soma;
    }

    public static boolean isValidCPF(String cpf) {
        Integer digito1 = calcularDigito(cpf.substring(0, 9), PESO_CPF);
        Integer digito2 = calcularDigito(cpf.substring(0, 9) + digito1, PESO_CPF);
        return cpf.equals(cpf.substring(0, 9) + digito1 + digito2);
    }

    public static boolean isValidCNPJ(String cnpj) {
        Integer digito1 = calcularDigito(cnpj.substring(0, 12), PESO_CNPJ);
        Integer digito2 = calcularDigito(cnpj.substring(0, 12) + digito1, PESO_CNPJ);
        return cnpj.equals(cnpj.substring(0, 12) + digito1 + digito2);
    }

    @Override
    public String toString() {
        return cpfCnpjNumero;
    }

    public TipoPessoa getTipoPessoa() {
        return this.cpfCnpjNumero.length() == 11 ? TipoPessoa.FISICA : TipoPessoa.JURIDICA;
    }
}
