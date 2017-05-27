package hr.project.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
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

    @OneToMany(mappedBy="question")
    @JsonManagedReference
    private List<Report> reports;

    @OneToMany(mappedBy="question")
    @JsonManagedReference
    private List<Answer> answers;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="exam_id")
    private Exam exam;
}
