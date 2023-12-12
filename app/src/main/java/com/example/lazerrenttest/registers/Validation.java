package com.example.lazerrenttest.registers;

public class Validation {

    public boolean validarTelefone(String telefone) {
        // Use uma expressão regular para verificar se o telefone está no formato correto
        String regex = "\\(\\d{2}\\)\\s\\d{4,5}-\\d{4}";

        if (telefone.matches(regex)) {
            return true; // O telefone está no formato correto
        } else {
            return false; // O telefone está em um formato incorreto
        }
    }

    public boolean validarCPF(String cpf) {
        // Remove caracteres não numéricos
        cpf = cpf.replaceAll("[^0-9]", "");

        // Verifica se o CPF tem 11 dígitos
        if (cpf.length() != 11) {
            return false;
        }

        // Calcula o dígito verificador
        // Implemente a lógica do cálculo do dígito verificador aqui

        // Verifique se o dígito verificador calculado é igual ao dígito verificador real
        // Implemente a lógica de verificação aqui

        return true; // Retorna true se o CPF for válido
    }
}
