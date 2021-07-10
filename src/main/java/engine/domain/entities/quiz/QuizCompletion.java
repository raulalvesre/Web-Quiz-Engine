package engine.domain.entities.quiz;

import com.fasterxml.jackson.annotation.JsonProperty;
import engine.domain.entities.User;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "QUIZ_COMPLETIONS")
public class QuizCompletion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "completed_quiz")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Quiz completedQuiz;

    @ManyToOne
    @JoinColumn(name = "who_completed")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User whoCompleted;

    @Column(name = "completion_date")
    private LocalDateTime completionDate;

    public QuizCompletion() {}

    public QuizCompletion(Quiz completedQuiz, User whoCompleted, LocalDateTime completionDate) {
        this.completedQuiz = completedQuiz;
        this.whoCompleted = whoCompleted;
        this.completionDate = completionDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Quiz getCompletedQuiz() {
        return completedQuiz;
    }

    public void setCompletedQuiz(Quiz completedQuiz) {
        this.completedQuiz = completedQuiz;
    }

    public User getWhoCompleted() {
        return whoCompleted;
    }

    public void setWhoCompleted(User whoCompleted) {
        this.whoCompleted = whoCompleted;
    }

    public LocalDateTime getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(LocalDateTime completionDate) {
        this.completionDate = completionDate;
    }
}
