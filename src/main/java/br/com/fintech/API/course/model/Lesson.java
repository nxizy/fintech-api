package br.com.fintech.API.course.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "LESSONS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "lesson_id")
    private String id;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(name = "video_url")
    private String videoUrl;

    @Column(name = "duration_in_sec",nullable = false)
    private Integer duration; // em segundos

    @Column(name = "lesson_order", nullable = false)
    private Integer lessonOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    @JsonBackReference // quebra loops infinitos
    private Course course;
}
