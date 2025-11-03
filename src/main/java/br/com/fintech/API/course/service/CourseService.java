package br.com.fintech.API.course.service;

import br.com.fintech.API.account.model.Account;
import br.com.fintech.API.account.repository.AccountRepository;
import br.com.fintech.API.course.model.Course;
import br.com.fintech.API.course.model.Lesson;
import br.com.fintech.API.course.model.LessonProgress;
import br.com.fintech.API.course.model.dto.CourseDetailDTO;
import br.com.fintech.API.course.model.dto.CourseListDTO;
import br.com.fintech.API.course.model.dto.LessonProgressDTO;
import br.com.fintech.API.course.model.dto.ProgressUpdateDTO;
import br.com.fintech.API.course.repository.CourseRepository;
import br.com.fintech.API.course.repository.LessonProgressRepository;
import br.com.fintech.API.course.repository.LessonRepository;
import br.com.fintech.API.infra.exceptions.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final LessonRepository lessonRepository;
    private final LessonProgressRepository lessonProgressRepository;
    private final AccountRepository accountRepository;

    public CourseService(CourseRepository courseRepository,
                         LessonRepository lessonRepository,
                         LessonProgressRepository lessonProgressRepository,
                         AccountRepository accountRepository) {
        this.courseRepository = courseRepository;
        this.lessonRepository = lessonRepository;
        this.lessonProgressRepository = lessonProgressRepository;
        this.accountRepository = accountRepository;
    }

    @Transactional(readOnly = true)
    public List<CourseListDTO> getAllCourses() {
        return courseRepository.findAll().stream()
                .map(course -> new CourseListDTO(
                        course.getId(),
                        course.getTitle(),
                        course.getSummary(),
                        course.getLevel().name(),
                        course.getThumbnail()
                ))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CourseDetailDTO getCourseDetails(UUID courseId, String accountId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NotFoundException("Curso não encontrado"));

        Map<Long, LessonProgress> progressMap = lessonProgressRepository
                .findByAccount_IdAndLesson_Course_Id(accountId, courseId)
                .stream()
                .collect(Collectors.toMap(progress -> progress.getLesson().getId(), Function.identity()));

        List<CourseDetailDTO.LessonDetailDTO> lessonDTOs = course.getLessons().stream()
                .map(lesson -> {
                    LessonProgress progress = progressMap.get(lesson.getId());

                    Integer currentTime = (progress != null) ? progress.getCurrentTime() : 0;
                    Boolean completed = progress != null && progress.isCompleted();

                    return new CourseDetailDTO.LessonDetailDTO(
                            lesson.getId(),
                            lesson.getTitle(),
                            lesson.getVideoUrl(),
                            lesson.getDuration(),
                            lesson.getLessonOrder(),
                            currentTime,
                            completed
                    );
                })
                .collect(Collectors.toList());

        return new CourseDetailDTO(
                course.getId(),
                course.getTitle(),
                course.getDescription(),
                course.getThumbnail(),
                course.getLevel().name(),
                lessonDTOs
        );
    }

    @Transactional
    public LessonProgressDTO updateLessonProgress(String accountId, UUID courseId, Long lessonId, ProgressUpdateDTO progressUpdate) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new NotFoundException("Conta não encontrada"));

        Lesson lesson = lessonRepository.findByIdAndCourse_Id(lessonId, courseId)
                .orElseThrow(() -> new NotFoundException("Aula ou Curso não encontrado"));

        LessonProgress lessonProgress = lessonProgressRepository
                .findByAccount_IdAndLesson_Id(accountId, lessonId)
                .orElse(new LessonProgress(account.getId(), lesson, 0, false));

        lessonProgress.setCurrentTime(progressUpdate.currentTime());
        lessonProgress.setCompleted(lesson.getDuration() != null && progressUpdate.currentTime() >= lesson.getDuration());

        LessonProgress savedProgress = lessonProgressRepository.save(lessonProgress);

        return new LessonProgressDTO(
                savedProgress.getCurrentTime(),
                savedProgress.isCompleted()
        );
    }
}