package hr.project.model;

import com.fasterxml.jackson.annotation.*;
import lombok.AccessLevel;
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

   /* @Column(name= "question_id")
    private Integer question_id;*/

    //@JsonBackReference(value = "question-answers")
    @ManyToOne//(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name="question_id", insertable = true, updatable = true)
    private Question question;



}
