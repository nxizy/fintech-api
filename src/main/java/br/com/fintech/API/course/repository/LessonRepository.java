package br.com.fintech.API.course.repository;

import br.com.fintech.API.course.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

    Optional<Lesson> findByIdAndCourse_Id(Long lessonId, UUID courseId);
}