package br.com.fintech.API.course.model.dto;

import java.util.List;
import java.util.UUID;

public record CourseDetailDTO(
        String course_id,
        String title,
        String description,
        String thumbnail,
        String level,
        List<LessonDetailDTO> lessons
) {

    public record LessonDetailDTO(
            String lesson_id,
            String title,
            String video_url,
            Integer duration,
            Integer lesson_order,
            Integer current_time,
            Boolean completed
    ) {
    }
}