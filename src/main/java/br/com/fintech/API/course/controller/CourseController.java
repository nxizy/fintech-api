package br.com.fintech.API.course.controller;

import br.com.fintech.API.account.model.Account;
import br.com.fintech.API.course.model.Course;
import br.com.fintech.API.course.model.dto.CourseDetailDTO;
import br.com.fintech.API.course.model.dto.CourseResponse;
import br.com.fintech.API.course.model.dto.LessonProgressDTO;
import br.com.fintech.API.course.model.dto.ProgressUpdateDTO;
import br.com.fintech.API.course.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<Page<CourseResponse>> getAllCourses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sort
    ) {
        Pageable pageable;
        if (sort != null && !sort.isBlank()) {
            pageable = PageRequest.of(page, size, Sort.by(sort.split(",")));
        } else {
            pageable = PageRequest.of(page, size);
        }
        Page<CourseResponse> courses = courseService.getAllCourses(pageable)
                .map(Course::toCourseResponse);
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/{id}/accounts/{accountId}")
    public ResponseEntity<CourseDetailDTO> getCourseDetails(
            @PathVariable String id,
            @PathVariable String accountId
    ) {
        CourseDetailDTO courseDetails = courseService.getCourseDetails(id, accountId);
        return ResponseEntity.ok(courseDetails);
    }
}