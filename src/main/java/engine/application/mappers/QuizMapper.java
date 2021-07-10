package engine.application.mappers;

import engine.application.dto.QuizCompletionDTO;
import engine.application.dto.QuizReqDTO;
import engine.application.dto.QuizRespDTO;
import engine.domain.entities.quiz.QuizCompletion;
import engine.domain.entities.quiz.QuizOption;
import engine.domain.entities.quiz.Quiz;
import engine.domain.entities.User;

import java.util.stream.Collectors;

public class QuizMapper {

    public static Quiz mapQuizDTOToQuizEntity(User user, QuizReqDTO quizReqDTO) {
        var quizOptionRows = quizReqDTO.getOptions().stream()
                                            .map(optionStr -> mapOptionStrToOptionEntity(quizReqDTO, optionStr))
                                            .collect(Collectors.toList());

        return new Quiz(user, quizReqDTO.getTitle(), quizReqDTO.getText(), quizOptionRows);
    }

    public static QuizOption mapOptionStrToOptionEntity(QuizReqDTO quizReq, String optionStr) {
        int indexOfOption = quizReq.getOptions().indexOf(optionStr);
        boolean isCorrectAnswer = quizReq.getAnswer() != null && quizReq.getAnswer().contains(indexOfOption);

        return new QuizOption(optionStr, indexOfOption, isCorrectAnswer);
    }

    public static QuizRespDTO mapQuizEntityToQuizRespDTO(Quiz quizRow) {
        var quizRowOptionsText = quizRow.getOptions().stream()
                .map(QuizOption::getText).collect(Collectors.toList());

        return new QuizRespDTO(quizRow.getId(), quizRow.getTitle(), quizRow.getText(), quizRowOptionsText);
    }

    public static QuizCompletionDTO mapQuizCompletionToQuizCompletionDTO(QuizCompletion quizCompletion) {
        return new QuizCompletionDTO(quizCompletion.getCompletedQuiz().getId(), quizCompletion.getCompletionDate());
    }

}
