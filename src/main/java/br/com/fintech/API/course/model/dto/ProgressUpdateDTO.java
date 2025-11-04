package br.com.fintech.API.course.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProgressUpdateDTO{

    @NotBlank
    @JsonProperty("current_time")
    private Integer currentTime;
}