package br.com.fintech.API.user.model.enums;

import br.com.fintech.API.assets.model.enums.AssetType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum InvestorLevel {
    INICIANTE("iniciante"),
    MODERADO("moderado"),
    AVANCADO("avancado"),
    PROFISSIONAL("profissional");

    private String value;

    InvestorLevel(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static InvestorLevel fromValue(String value) {
        for (InvestorLevel level : InvestorLevel.values()) {
            if (level.value.equalsIgnoreCase(value)) {
                return level;
            }
        }
        throw new IllegalArgumentException("Invalid investor level: " + value);
    }
}
