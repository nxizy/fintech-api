package br.com.fintech.API.user.model.enums;

public enum InvestorLevel {
    INICIANTE("iniciante"),
    MODERADO("moderado"),
    AVANCADO("avancado"),
    PROFISSIONAL("profissional");

    private String level;

    InvestorLevel(String level) {
        this.level = level;
    }
}
