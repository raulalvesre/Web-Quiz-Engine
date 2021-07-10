package engine.application.services;

import engine.application.dto.*;
import engine.application.exceptions.QuizNotFoundException;
import engine.application.exceptions.UserDoesNotOwnQuizException;
import engine.application.exceptions.UserNotFoundException;
import engine.application.interfaces.QuizServiceInterface;
import engine.application.mappers.QuizMapper;
import engine.domain.entities.quiz.Quiz;
import engine.domain.entities.quiz.QuizCompletion;
import engine.domain.entities.quiz.QuizOption;
import engine.infrastructure.repositories.QuizCompletionRepository;
import engine.infrastructure.repositories.QuizRepository;
import engine.infrastructure.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuizService implements QuizServiceInterface {

    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private QuizCompletionRepository quizCompletionRepository;
    @Autowired
    private UserRepository userRepository;

    public Page<QuizRespDTO> getQuizPage(Integer pageNumber) {
        Pageable paging = PageRequest.of(pageNumber, 10, Sort.by("id"));

        Page<QuizRespDTO> pagedResult = quizRepository
                .findAll(paging)
                .map(QuizMapper::mapQuizEntityToQuizRespDTO);

        if (pagedResult.hasContent()) {
            return pagedResult;
        }

        return Page.empty();
    }

    public QuizRespDTO getQuiz(long id) throws QuizNotFoundException {
        var dbQuiz = quizRepository.findById(id).orElse(null);
        if (dbQuiz == null) {
            throw new QuizNotFoundException();
        }

        return QuizMapper.mapQuizEntityToQuizRespDTO(dbQuiz);
    }

    public QuizRespDTO createQuiz(QuizReqDTO quizReq, Principal principal) throws UserNotFoundException {
        var user = userRepository.findByEmail(principal.getName()).orElse(null);
        if (user == null) {
            throw new UserNotFoundException();
        }

        Quiz quiz = QuizMapper.mapQuizDTOToQuizEntity(user, quizReq);
        quizRepository.save(quiz);

        return QuizMapper.mapQuizEntityToQuizRespDTO(quiz);
    }

    public QuizRespDTO updateQuiz(long quizId, QuizReqDTO quizReq, Principal principal) throws UserDoesNotOwnQuizException, QuizNotFoundException, UserNotFoundException {
        var user = userRepository.findByEmail(principal.getName()).orElse(null);
        if (user == null) {
            throw new UserNotFoundException();
        }

        var dbQuiz = quizRepository.findById(quizId).orElse(null);
        if (dbQuiz == null)
            throw new QuizNotFoundException();

        if (!dbQuiz.getCreator().getEmail().equals(principal.getName()))
            throw new UserDoesNotOwnQuizException();

        updateDatabaseQuiz(dbQuiz, quizReq);
        quizRepository.save(dbQuiz);

        return QuizMapper.mapQuizEntityToQuizRespDTO(dbQuiz);
    }

    private void updateDatabaseQuiz(Quiz dbQuiz, QuizReqDTO newQuiz) {
        updateQuizTitle(dbQuiz, newQuiz);
        updateQuizText(dbQuiz, newQuiz);
        updateQuizOptions(dbQuiz, newQuiz);
    }

    private void updateQuizTitle(Quiz dbQuiz, QuizReqDTO newQuiz) {
        dbQuiz.setTitle(newQuiz.getTitle());
    }

    private void updateQuizText(Quiz dbQuiz, QuizReqDTO newQuiz) {
        dbQuiz.setText(newQuiz.getText());
    }

    private void updateQuizOptions(Quiz dbQuiz, QuizReqDTO newQuiz) {
        var newQuizOptions = newQuiz.getOptions().stream()
                .map(x -> QuizMapper.mapOptionStrToOptionEntity(newQuiz, x))
                .collect(Collectors.toList());

        dbQuiz.setOptions(newQuizOptions);
    }

    public FeedbackDTO solveQuiz(Long id, UserQuizAnswerReqDTO answer, Principal principal) throws QuizNotFoundException, UserNotFoundException {
        var user = userRepository.findByEmail(principal.getName()).orElse(null);
        if (user == null)
            throw new UserNotFoundException();

        var dbQuiz = quizRepository.findById(id).orElse(null);
        if (dbQuiz == null)
            throw new QuizNotFoundException();

        var quizAnswersPositions = getQuizCorrectAnswersPositions(dbQuiz);
        var userAnswers = answer.getAnswer() == null ? new ArrayList<Integer>() : answer.getAnswer().stream().sorted().collect(Collectors.toList());

        if (!userAnswers.equals(quizAnswersPositions))
            return new FeedbackDTO(false, "Wrong answer! Please, try again.");

        var quizCompletions = quizCompletionRepository.findUserCompletion(user.getId(), dbQuiz.getId());
        var wasQuizCompletedToday = quizCompletions.stream()
                .anyMatch(qC -> qC.getCompletionDate().toLocalDate().isEqual(LocalDate.now()));

        if (!wasQuizCompletedToday)
            quizCompletionRepository.save(new QuizCompletion(dbQuiz, user, LocalDateTime.now()));

        return new FeedbackDTO(true, "Congratulations, you're right!");
    }

    private List<Integer> getQuizCorrectAnswersPositions(Quiz quiz) {
        return quiz.getOptions().stream()
                .filter(QuizOption::isCorrectAnswer)
                .map(QuizOption::getPosition)
                .sorted()
                .collect(Collectors.toList());
    }

    public Page<QuizCompletionDTO> getCompletedQuizzes(Integer pageNumber, Principal principal) throws UserNotFoundException {
        var user = userRepository.findByEmail(principal.getName()).orElse(null);
        if (user == null) {
            throw new UserNotFoundException();
        }

        Pageable paging = PageRequest.of(pageNumber, 10, Sort.by("completion_date"));
        var userQuizCompletions = quizCompletionRepository
                .findAllUserCompletions(user.getId(), paging);

        if (userQuizCompletions.isEmpty()) {
            return Page.empty();
        }

        return userQuizCompletions.map(QuizMapper::mapQuizCompletionToQuizCompletionDTO);
    }

    public MessageDTO deleteQuiz(long id, Principal principal) throws UserDoesNotOwnQuizException, QuizNotFoundException {
        var dbQuiz = quizRepository.findById(id).orElse(null);
        if (dbQuiz == null) {
            throw new QuizNotFoundException();
        }

        if (!dbQuiz.getCreator().getEmail().equals(principal.getName())) {
            throw new UserDoesNotOwnQuizException();
        }

        quizRepository.deleteById(id);

        return new MessageDTO("Quiz deleted");
    }

}
