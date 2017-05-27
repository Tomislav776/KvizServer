package hr.project.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "answer")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Answer implements Serializable {

    @Id
    @Column(name= "id")
    @GeneratedValue
    private Integer id;

    @Column(name= "answer")
    private String answer;

    @Column(name= "correct")
    private boolean correct;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="question_id")
    private Question question;

}
