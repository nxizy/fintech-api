package br.com.fintech.API.course.model.dto;

import br.com.fintech.API.course.model.enums.CourseLevel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "Course Response Object")
public class CourseResponse {

    @Schema(description = "course's id", example = "a12f5670-2d4e-11ef-bf4b-123456789abc")
    private String course_id;

    @Schema(description = "course's title", example = "Introdução aos Investimentos")
    private String title;

    @Schema(description = "course's summary", example = "Aprenda os fundamentos essenciais para começar a investir com segurança.")
    private String summary;

    @Schema(description = "course's level", example = "BASICO")
    private CourseLevel level;

    @Schema(description = "course's thumbnail", example = "https://cdn.borainvestir.b3.com.br/2024/04/17112249/fundos-de-investimentos-como-escolher.jpg")
    private String thumbnail;
}