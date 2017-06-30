package hr.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MultiplayerAnswer {

    private String username;
    private String answer;
    private String examId;

}
