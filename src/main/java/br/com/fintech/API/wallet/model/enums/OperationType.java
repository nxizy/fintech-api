package br.com.fintech.API.wallet.model.enums;

import br.com.fintech.API.user.model.enums.InvestorLevel;
import br.com.fintech.API.wallet.model.Operation;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum OperationType {
    DEPOSIT("deposit"),
    WITHDRAW("withdraw");

    private String value;

    OperationType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static OperationType fromValue(String value) {
        for (OperationType type : OperationType.values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid operation type: " + value);
    }
}