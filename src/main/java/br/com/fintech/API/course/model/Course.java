package br.com.fintech.API.course.model;

import br.com.fintech.API.course.model.enums.CourseLevel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "COURSES")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "course_id")
    private UUID id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(length = 255)
    private String summary;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private CourseLevel level;

    @Column(name = "THUMBNAIL")
    private String thumbnail; // (caso haja capa para o curso, url da imagem)

    // relacionamento (curso = muitas aulas)
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Lesson> lessons;


}
