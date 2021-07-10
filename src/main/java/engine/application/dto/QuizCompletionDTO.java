package engine.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class QuizCompletionDTO {

    @JsonProperty("id")
    private long completedQuizId;
    @JsonProperty("completedAt")
    private LocalDateTime completionDate;

    public QuizCompletionDTO() {}

    public QuizCompletionDTO(long completedQuizId, LocalDateTime completionDate) {
        this.completedQuizId = completedQuizId;
        this.completionDate = completionDate;
    }

    public long getCompletedQuizId() {
        return completedQuizId;
    }

    public void setCompletedQuizId(long completedQuizId) {
        this.completedQuizId = completedQuizId;
    }

    public LocalDateTime getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(LocalDateTime completionDate) {
        this.completionDate = completionDate;
    }

}
