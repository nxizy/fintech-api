package br.com.fintech.API.infra.utils;

public class SanitizerUtils {

    public static boolean isCpfValid(String cpf) {
        if (cpf == null || !cpf.matches("\\d{11}")) return false;

        // Rejeita CPFs com todos os dígitos iguais (ex: 00000000000)
        if (cpf.chars().distinct().count() == 1) return false;

        // 1º dígito verificador
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += (cpf.charAt(i) - '0') * (10 - i);
        }
        int digit1 = 11 - (sum % 11);
        digit1 = (digit1 > 9) ? 0 : digit1;

        // 2º dígito verificador
        sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += (cpf.charAt(i) - '0') * (11 - i);
        }
        int digit2 = 11 - (sum % 11);
        digit2 = (digit2 > 9) ? 0 : digit2;

        // Verifica se os dígitos batem
        return cpf.charAt(9) - '0' == digit1 &&
                cpf.charAt(10) - '0' == digit2;
    }

    public static String sanitize(String value) {
        return value == null ? null : value.replaceAll("\\D", "");
    }
}
