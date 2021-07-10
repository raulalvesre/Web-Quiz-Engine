package engine.domain.entities.quiz;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Embeddable
public class QuizOption {

    @NotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private int position;

    @NotNull
    private String text;

    @NotNull
    private boolean isCorrectAnswer;

    public QuizOption() {}

    public QuizOption(String text, int position, boolean isCorrectAnswer) {
        this.text = text;
        this.position = position;
        this.isCorrectAnswer = isCorrectAnswer;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isCorrectAnswer() {return this.isCorrectAnswer;}

    public void setCorrectAnswer(boolean correctAnswer) {
        isCorrectAnswer = correctAnswer;
    }

    @Override
    public String toString() {
        return this.text;
    }

}
