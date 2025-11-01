package br.com.fintech.API.course.controller;

import br.com.fintech.API.account.model.Account;
import br.com.fintech.API.course.model.dto.CourseDetailDTO;
import br.com.fintech.API.course.model.dto.CourseListDTO;
import br.com.fintech.API.course.model.dto.LessonProgressDTO;
import br.com.fintech.API.course.model.dto.ProgressUpdateDTO;
import br.com.fintech.API.course.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<List<CourseListDTO>> getAllCourses() {
        List<CourseListDTO> courses = courseService.getAllCourses();
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDetailDTO> getCourseDetails(
            @PathVariable UUID id,
            @AuthenticationPrincipal Account account
    ) {
        String accountId = (account != null) ? account.getId() : null;

        CourseDetailDTO courseDetails = courseService.getCourseDetails(id, accountId);
        return ResponseEntity.ok(courseDetails);
    }

    @PutMapping("/accounts/{accountId}/courses/{courseId}/lessons/{lessonId}/progress")
    public ResponseEntity<LessonProgressDTO> updateLessonProgress(
            @PathVariable String accountId,
            @PathVariable UUID courseId,
            @PathVariable Long lessonId,
            @Valid @RequestBody ProgressUpdateDTO progressUpdate
    ) {
        LessonProgressDTO updatedProgress = courseService.updateLessonProgress(accountId, courseId, lessonId, progressUpdate);
        return ResponseEntity.ok(updatedProgress);
    }
}