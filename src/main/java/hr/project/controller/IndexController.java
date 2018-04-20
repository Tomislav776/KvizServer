package hr.project.controller;

import hr.project.formObjects.NewQuestion;
import hr.project.model.Answer;
import hr.project.model.Exam;
import hr.project.model.Question;
import hr.project.repository.AnswerRepository;
import hr.project.repository.ExamRepository;
import hr.project.repository.QuestionRepository;
import hr.project.repository.SubjectRepository;
import hr.project.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping (value = "/")
public class IndexController {

    private static final String INDEX_PAGE = "index";

    @Autowired
    CourseService courseService;

    @Autowired
    SubjectRepository subjectRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    ExamRepository examRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index (final ModelMap modelMap) {

        modelMap.addAttribute("courses", courseService.findAllCourses());
        modelMap.addAttribute("subjects", subjectRepository.findAll());
        modelMap.addAttribute("exams", examRepository.findAll());

        return new ModelAndView(INDEX_PAGE);
    }


    @PostMapping (value = "/new-question")
    public ModelAndView newQuestion(final NewQuestion question) {
        int c = 1;
        Question question1 = questionRepository.save(new Question(question.question, Integer.valueOf(question.exam)));

        for (Answer answer : question.answers) {
            answer.setQuestion(question1);
            if (c == Integer.valueOf(question.correct)) {
                answer.setCorrect(true);
            }
            answerRepository.save(answer);
            c++;
        }


        System.out.println();
        return new ModelAndView(INDEX_PAGE);
    }

}
