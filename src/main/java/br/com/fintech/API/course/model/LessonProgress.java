package br.com.fintech.API.course.model;

//import br.com.fintech.API.account.model.Account;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ACCOUNT_LESSON_PROGRESS",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"account_id", "lesson_id"})
    }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LessonProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lesson;

    @Column(name = "current_time_sec", nullable = false)
    private Integer currentTime;

    @Column(nullable = false)
    private boolean completed;

}
