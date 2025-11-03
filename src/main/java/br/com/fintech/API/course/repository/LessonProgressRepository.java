package br.com.fintech.API.course.repository;

import br.com.fintech.API.course.model.LessonProgress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LessonProgressRepository extends JpaRepository<LessonProgress, Long> {

    Optional<LessonProgress> findByAccount_IdAndLesson_Id(String accountId, Long lessonId);
    List<LessonProgress> findByAccount_IdAndLesson_Course_Id(String accountId, UUID courseId);
}