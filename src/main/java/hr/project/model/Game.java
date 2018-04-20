package hr.project.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "game")
@AllArgsConstructor
@NoArgsConstructor
public class Game implements Serializable {

    @Id
    @Column(name= "id")
    @GeneratedValue
    private Integer id;

    @Column(name= "user1_points")
    private Integer user1_points;

    @Column(name= "user2_points")
    private Integer user2_points;

    @Column(name= "subject_id")
    private Integer subject_id;

    @Column(name= "user1_id")
    private Integer user1_id;

    @Column(name= "user2_id")
    private Integer user2_id;
/*
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name="subject_id", insertable = false, updatable = false)
    private Subject subject;
*/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user1_id", insertable = false, updatable = false)
    private User user1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user2_id", insertable = false, updatable = false)
    private User user2;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUser1_points() {
        return user1_points;
    }

    public void setUser1_points(Integer user1_points) {
        this.user1_points = user1_points;
    }

    public Integer getUser2_points() {
        return user2_points;
    }

    public void setUser2_points(Integer user2_points) {
        this.user2_points = user2_points;
    }

    public Integer getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(Integer subject_id) {
        this.subject_id = subject_id;
    }

    public Integer getUser1_id() {
        return user1_id;
    }

    public void setUser1_id(Integer user1_id) {
        this.user1_id = user1_id;
    }

    public Integer getUser2_id() {
        return user2_id;
    }

    public void setUser2_id(Integer user2_id) {
        this.user2_id = user2_id;
    }

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }
}
