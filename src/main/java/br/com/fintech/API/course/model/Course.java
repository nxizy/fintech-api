package br.com.fintech.API.course.model;

import br.com.fintech.API.course.model.dto.CourseResponse;
import br.com.fintech.API.course.model.enums.CourseLevel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "COURSES")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "course_id")
    private String id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(length = 255)
    private String summary;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "course_level",nullable = false)
    private CourseLevel level;

    @Column(name = "THUMBNAIL")
    private String thumbnail; // (caso haja capa para o curso, url da imagem)

    // relacionamento (curso = muitas aulas)
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Lesson> lessons;


    public CourseResponse toCourseResponse() {
        return CourseResponse.builder()
                .course_id(this.id)
                .title(this.title)
                .summary(this.summary)
                .level(this.level)
                .thumbnail(this.thumbnail)
                .build();
    }

}
