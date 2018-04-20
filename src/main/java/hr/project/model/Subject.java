package hr.project.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="subject")
@AllArgsConstructor
@NoArgsConstructor
public class Subject implements Serializable{

    @Id
    @Column(name= "id")
    @GeneratedValue
    private Integer id;

    @Column(name= "name")
    private String name;

    @Column(name= "semester")
    private Integer semester;

    @Column(name= "question_counter")
    private Integer question_counter;

    @OneToMany(mappedBy="subject")//, fetch = FetchType.LAZY)
    private List<Exam> exam;

 /*   @OneToMany(mappedBy="subject")//, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Game> Games;
*/
    @OneToMany(mappedBy="subject")//, fetch = FetchType.LAZY)
    private List<Statistic> statistics;

    @ManyToOne//(fetch = FetchType.LAZY)
    @JoinColumn(name="course_id", insertable = false, updatable = false)
    private Course course;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    public Integer getQuestion_counter() {
        return question_counter;
    }

    public void setQuestion_counter(Integer question_counter) {
        this.question_counter = question_counter;
    }

    public List<Exam> getExam() {
        return exam;
    }

    public void setExam(List<Exam> exam) {
        this.exam = exam;
    }

    public List<Statistic> getStatistics() {
        return statistics;
    }

    public void setStatistics(List<Statistic> statistics) {
        this.statistics = statistics;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
