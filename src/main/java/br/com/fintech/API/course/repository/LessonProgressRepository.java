package br.com.fintech.API.course.repository;

import br.com.fintech.API.course.model.LessonProgress;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LessonProgressRepository extends JpaRepository<LessonProgress, String> {

    Optional<LessonProgress> findByAccount_IdAndLesson_Id(String accountId, String lessonId);
    List<LessonProgress> findByAccount_IdAndLesson_Course_Id(String accountId, String courseId);
}