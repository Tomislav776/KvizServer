package hr.project.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "statistic")
@AllArgsConstructor
@NoArgsConstructor
public class Statistic implements Serializable {

    @Id
    @Column(name= "id")
    @GeneratedValue
    private Integer id;

    @Column(name= "points")
    private Integer points;

    @Column(name= "user_id")
    private Integer user_id;

    @Column(name= "questions_user")
    private String questions_user;

    @Column(name= "subject_id")
    private Integer subject_id;

    @ManyToOne//(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", insertable = false, updatable = false)
    private User user;

    @ManyToOne//(fetch = FetchType.LAZY)
    @JoinColumn(name="subject_id", insertable = false, updatable = false)
    private Subject subject;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getQuestions_user() {
        return questions_user;
    }

    public void setQuestions_user(String questions_user) {
        this.questions_user = questions_user;
    }

    public Integer getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(Integer subject_id) {
        this.subject_id = subject_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}
