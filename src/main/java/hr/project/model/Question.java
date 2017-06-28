package hr.project.model;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "question")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Question implements Serializable {

    @Id
    @Column(name= "id")
    @GeneratedValue
    private Integer id;

    @Column(name= "question")
    private String question;

    @Column(name= "exam_id")
    private Integer exam_id;

    @Column(name= "verified", nullable = false)
    private boolean verified;

    @OneToMany(mappedBy="question", cascade = CascadeType.ALL) //fetch = FetchType.LAZY
    private List<Report> reports;

    @OneToMany(mappedBy="question", cascade = CascadeType.ALL)//fetch = FetchType.LAZY,
    private List<Answer> answers;

    @ManyToOne//(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name="exam_id", insertable = false, updatable = false)
    private Exam exam;

    public Question(String question, Integer exam_id) {
        this.question = question;
        this.exam_id = exam_id;
    }


}
