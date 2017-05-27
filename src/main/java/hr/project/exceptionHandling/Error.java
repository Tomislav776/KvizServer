package hr.project.exceptionHandling;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Error {
    private int code;
    private String message;

}
