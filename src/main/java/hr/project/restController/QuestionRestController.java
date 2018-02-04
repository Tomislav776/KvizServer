package hr.project.restController;

import hr.project.exceptionHandling.Error;
import hr.project.exceptionHandling.ObjectNotFound;
import hr.project.model.Answer;
import hr.project.model.Question;
import hr.project.repository.AnswerRepository;
import hr.project.repository.QuestionRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionRestController {
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    @PersistenceContext
    private EntityManager em;

    private Session configurarSessao() {
        return  em.unwrap(org.hibernate.Session.class);
    }

    @Autowired
    QuestionRestController(QuestionRepository questionRepository, AnswerRepository answerRepository) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    @ExceptionHandler(ObjectNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error objectNotFound(ObjectNotFound e) {
        Integer id = e.getObjectId();
        return new Error(404, "Object [" + id + "] not found");
    }

    @RequestMapping(value="/all/{examId}", method=RequestMethod.GET)
    public ResponseEntity<List<Question>> findAll(@PathVariable Integer examId) {
        List<Question> questions = questionRepository.findAllByexam_id(examId);
        if (questions.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(questions);
    }
    

    @RequestMapping(value="/random/{examId}", method=RequestMethod.GET)
    public ResponseEntity<List<Question>> get10Random(@PathVariable Integer examId) {
        List questions = configurarSessao().createQuery("select o from Question o where o.exam_id = :examId  order by rand()")
                .setParameter("examId", examId)
                .setMaxResults(10)
                .list();

        if (questions.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(questions);
    }

    @RequestMapping(value="/reports", method=RequestMethod.GET)
    public ResponseEntity<List<Question>> getQuestionWhichHaveReports() {
        List questions = configurarSessao().createQuery("select q from Question q inner join q.reports r inner join q.answers a where r.resolved = 0")
                .list();

        if (questions.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<Question> listWithoutDuplicates = new ArrayList<Question>(new HashSet<>(questions));

        return ResponseEntity.ok(listWithoutDuplicates);
    }

    @RequestMapping(value="/toVerify", method=RequestMethod.GET)
    public ResponseEntity<List<Question>> getQuestionWhichNeedToBeVerified() {
        List questions = configurarSessao().createQuery("select q from Question q inner join q.answers a where q.verified = false")
                .list();

        if (questions.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<Question> listWithoutDuplicates = new ArrayList<Question>(new HashSet<>(questions));

        return ResponseEntity.ok(listWithoutDuplicates);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<Question> findById(@PathVariable Integer id) {
        Question question = questionRepository.findById(id);
        if (question == null) { throw new ObjectNotFound(id); }
        return ResponseEntity.ok(question);
    }

    @RequestMapping(method=RequestMethod.POST, consumes="application/json")
    public ResponseEntity<Question> saveQuestion(@RequestBody Question question, UriComponentsBuilder ucb) {
        HttpHeaders headers = new HttpHeaders();

        List<Answer> answers = question.getAnswers();
        String question_string = question.getQuestion();
        Integer exam_id = question.getExam_id();

        Question question1 = new Question(question_string, exam_id);

        try {
            question = questionRepository.save(question1);

            for (int i = 0; i < answers.size(); i++) {
                answers.get(i).setQuestion(question);
                answerRepository.save(answers.get(i));
            }

            URI locationUri = ucb.path("/question/").path(String.valueOf(question.getId())).build().toUri();
            headers.setLocation(locationUri);
            return new ResponseEntity<>(question, headers, HttpStatus.CREATED);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(question);
        }
    }

    @RequestMapping(path="/{id}",method=RequestMethod.PUT)
    public ResponseEntity<Question> update(@RequestBody Question question,@PathVariable Integer id)  {

        if (!(questionRepository.exists(id))){
            return ResponseEntity.notFound().build();
        }

        question.setId(id);
        questionRepository.save(question);
        return ResponseEntity.ok(question);
    }

    @RequestMapping(value="/{id}", method= RequestMethod.DELETE)
    public ResponseEntity<String> deleteById(@PathVariable Integer id) {
        try {
            questionRepository.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body("Item successfully deleted!");
        }
        catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error deleting the item:" + ex.toString());
        }
    }

}
