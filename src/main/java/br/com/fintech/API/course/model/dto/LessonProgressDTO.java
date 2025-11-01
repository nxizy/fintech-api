package br.com.fintech.API.course.model.dto;

public record LessonProgressDTO(
        Integer currentTime,
        Boolean completed
) {}
