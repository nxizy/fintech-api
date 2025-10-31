package br.com.fintech.API.assets.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum AssetType {
    CRYPTO("crypto"),
    STOCK("stock"),
    CURRENCIES("currencies");

    private String value;

    private AssetType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static AssetType fromValue(String value) {
        for (AssetType type : AssetType.values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid asset type: " + value);
    }
}
