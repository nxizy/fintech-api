package br.com.fintech.API.course.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record ProgressUpdateDTO(

        @NotNull(message = "O tempo atual (currentTime) não pode ser nulo.")
        @PositiveOrZero(message = "O tempo atual (currentTime) deve ser zero ou positivo.")
        Integer currentTime
) {
}