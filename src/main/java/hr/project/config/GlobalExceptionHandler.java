package hr.project.config;

import org.springframework.http.HttpStatus;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    public GlobalExceptionHandler() {
        super();
    }

    // 400 BAD REQUEST

    // 401 UNAUTHORIZED

    // 403 FORBIDDEN

    // 404 NOT FOUND
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler()
    public String handleNotFound(ModelMap model, final RuntimeException ex, final WebRequest request) {
        logger.error("Status code 500: ", ex);
        model.addAttribute("exception", ex);
        model.addAttribute("request", request);
        return "error";
    }

    // 409 CONFLICT

    // 500 INTERNAL SERVER ERROR
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = { NullPointerException.class })
    public String handleInternal(ModelMap model, final RuntimeException ex, final WebRequest request) {
        logger.error("Status code 500: ", ex);
        model.addAttribute("exception", ex);
        model.addAttribute("request", request);
        return "error";
    }
}
