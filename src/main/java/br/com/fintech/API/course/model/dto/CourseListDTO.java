package br.com.fintech.API.course.model.dto;

import java.util.UUID;

public record CourseListDTO(
        UUID course_id,
        String title,
        String summary,
        String level,
        String thumbnail
) {
}