package engine.domain.entities.quiz;

import com.fasterxml.jackson.annotation.JsonProperty;
import engine.domain.entities.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "quizzes")
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long id;

    @Column
    @NotBlank
    private String title;

    @Column
    @NotBlank
    private String text;

    @ElementCollection
    @CollectionTable(name = "quiz_options", joinColumns = @JoinColumn(name = "quiz_id"))
    @Column(name = "options", nullable = false)
    @Size(min = 2)
    private List<QuizOption> quizOptions;

    @ManyToOne
    @JoinColumn(name = "creator")
    private User creator;

    public Quiz() {}

    public Quiz(User creator, String title, String text, List<QuizOption> quizOptions) {
        this.title = title;
        this.text = text;
        this.quizOptions = quizOptions;
        this.creator = creator;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<QuizOption> getOptions() {
        return quizOptions;
    }

    public void setOptions(List<QuizOption> quizOptions) {
        this.quizOptions = quizOptions;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }
}
