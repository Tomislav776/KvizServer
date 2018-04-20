package hr.project.model;

import com.fasterxml.jackson.annotation.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "answer")
@AllArgsConstructor
@NoArgsConstructor
public class Answer implements Serializable {

    @Id
    @Column(name= "id")
    @GeneratedValue
    private Integer id;

    @Column(name= "answer")
    private String answer;

    @Column(name= "correct")
    private boolean correct;

    /*@Column(name= "question_id")
    private Integer question_id;*/

    //@JsonBackReference(value = "question-answers")
    @ManyToOne//(fetch = FetchType.LAZY)
    @JoinColumn(name="question_id", insertable = true, updatable = true)
    private Question question;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
