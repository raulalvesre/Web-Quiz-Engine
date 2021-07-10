package engine.api.controllers;

import engine.application.dto.*;
import engine.application.exceptions.QuizNotFoundException;
import engine.application.exceptions.UserDoesNotOwnQuizException;
import engine.application.exceptions.UserNotFoundException;
import engine.application.interfaces.QuizServiceInterface;
import engine.application.services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
public class QuizController {

    @Autowired
    QuizServiceInterface quizService;

    @GetMapping("api/quizzes")
    public Page<QuizRespDTO> getQuizPage(@RequestParam(defaultValue = "0") Integer pageNumber, Principal principal) {
        return this.quizService.getQuizPage(pageNumber);
    }

    @GetMapping("api/quizzes/{id}")
    public QuizRespDTO getQuiz(@PathVariable long id, Principal principal) throws QuizNotFoundException {
        return this.quizService.getQuiz(id);
    }

    @PostMapping(value = "api/quizzes", consumes = "application/json")
    public QuizRespDTO createQuiz(@Valid @RequestBody QuizReqDTO quizReq, Principal principal) throws UserNotFoundException {
       return this.quizService.createQuiz(quizReq, principal);
    }

    @PutMapping(value = "api/quizzes/{id}", consumes = "application/json")
    public QuizRespDTO updateQuizz(@PathVariable long id, @Valid @RequestBody QuizReqDTO quizReq, Principal principal) throws UserNotFoundException, UserDoesNotOwnQuizException, QuizNotFoundException {
        return this.quizService.updateQuiz(id, quizReq, principal);
    }

    @PostMapping(value = "api/quizzes/{id}/solve", consumes = "application/json")
    public FeedbackDTO solveQuiz(@PathVariable long id, @RequestBody UserQuizAnswerReqDTO answer, Principal principal) throws QuizNotFoundException, UserNotFoundException {
        return this.quizService.solveQuiz(id, answer, principal);
    }

    @GetMapping("api/quizzes/completed")
    public Page<QuizCompletionDTO> getCompletedQuizzes(@RequestParam(defaultValue = "0") Integer pageNumber, Principal principal) throws UserNotFoundException {
        return this.quizService.getCompletedQuizzes(pageNumber, principal);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("api/quizzes/{id}")
    public MessageDTO deleteQuiz(@PathVariable long id, Principal principal) throws UserDoesNotOwnQuizException, QuizNotFoundException {
        return this.quizService.deleteQuiz(id, principal);
    }
}
