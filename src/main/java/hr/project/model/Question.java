package hr.project.model;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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

    @OneToMany(mappedBy="question") //fetch = FetchType.LAZY
    private List<Report> reports;

    @OneToMany(mappedBy="question")//fetch = FetchType.LAZY,
    private List<Answer> answers;

    @ManyToOne//(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name="exam_id", insertable = false, updatable = false)
    private Exam exam;


}
