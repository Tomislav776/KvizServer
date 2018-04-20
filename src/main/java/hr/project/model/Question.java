package hr.project.model;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "question")
@AllArgsConstructor
@NoArgsConstructor
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
    @JoinColumn(name="exam_id", insertable = false, updatable = false)
    private Exam exam;

    public Question(String question, Integer exam_id) {
        this.question = question;
        this.exam_id = exam_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Integer getExam_id() {
        return exam_id;
    }

    public void setExam_id(Integer exam_id) {
        this.exam_id = exam_id;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public List<Report> getReports() {
        return reports;
    }

    public void setReports(List<Report> reports) {
        this.reports = reports;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }
}
