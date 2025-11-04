package br.com.fintech.API.course.model.enums;

import br.com.fintech.API.assets.model.enums.AssetType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum CourseLevel {
    BASICO("basico"),
    INTERMEDIARIO("intermediario"),
    AVANCADO("avancado");

    private String value;

    CourseLevel(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static CourseLevel fromValue(String value) {
        for (CourseLevel level : CourseLevel.values()) {
            if (level.value.equalsIgnoreCase(value)) {
                return level;
            }
        }
        throw new IllegalArgumentException("Invalid course level: " + value);
    }

}
