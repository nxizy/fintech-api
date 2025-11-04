package br.com.fintech.API.course.service;

import br.com.fintech.API.account.model.Account;
import br.com.fintech.API.account.repository.AccountRepository;
import br.com.fintech.API.course.model.Course;
import br.com.fintech.API.course.model.Lesson;
import br.com.fintech.API.course.model.LessonProgress;
import br.com.fintech.API.course.model.dto.CourseDetailDTO;
import br.com.fintech.API.course.model.dto.LessonProgressDTO;
import br.com.fintech.API.course.model.dto.ProgressUpdateDTO;
import br.com.fintech.API.course.repository.CourseRepository;
import br.com.fintech.API.course.repository.LessonProgressRepository;
import br.com.fintech.API.course.repository.LessonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final LessonRepository lessonRepository;
    private final LessonProgressRepository lessonProgressRepository;
    private final AccountRepository accountRepository;


    @Transactional(readOnly = true)
    public Page<Course> getAllCourses(Pageable pageable) {
        return courseRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public CourseDetailDTO getCourseDetails(String courseId, String accountId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Curso não encontrado"));

        Map<String, LessonProgress> progressMap = lessonProgressRepository
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
    public LessonProgressDTO updateLessonProgress(String accountId, String courseId, String lessonId, ProgressUpdateDTO progressUpdate) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Conta não encontrada"));

        Lesson lesson = lessonRepository.findByIdAndCourse_IdOrderByLessonOrderAsc(lessonId, courseId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Aula ou Curso não encontrado"));

        LessonProgress lessonProgress = lessonProgressRepository
                .findByAccount_IdAndLesson_Id(accountId, lessonId)
                .orElse(new LessonProgress(account, lesson, 0, false));

        if(progressUpdate.getCurrentTime() > lesson.getDuration()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O valor de progresso do usuário não pode ser maior do que o valor da duração da aula");
        }

        lessonProgress.setCurrentTime(progressUpdate.getCurrentTime());
        lessonProgress.setCompleted(lesson.getDuration() != null && progressUpdate.getCurrentTime() >= lesson.getDuration());

        LessonProgress savedProgress = lessonProgressRepository.save(lessonProgress);

        return new LessonProgressDTO(
                savedProgress.getCurrentTime(),
                savedProgress.isCompleted()
        );
    }
}