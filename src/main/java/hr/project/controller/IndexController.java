package hr.project.controller;

import hr.project.formObjects.NewQuestion;
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

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index () {

        return new ModelAndView(INDEX_PAGE);
    }


    @PostMapping (value = "/new-question")
    public ModelAndView newQuestion(final NewQuestion newQuestion) {

        System.out.println();
        return new ModelAndView();
    }

}
