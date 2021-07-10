package engine.application.dto;

import java.util.List;

public class UserQuizAnswerReqDTO {

    private List<Integer> answer;

    public UserQuizAnswerReqDTO() {

    }

    public List<Integer> getAnswer() {
        return answer;
    }

    public void setAnswer(List<Integer> answer) {
        this.answer = answer;
    }

}
