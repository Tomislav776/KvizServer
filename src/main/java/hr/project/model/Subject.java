package hr.project.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="subject")
@AllArgsConstructor
@NoArgsConstructor
@Data
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
    @JsonIgnore
    private List<Statistic> statistics;

    @ManyToOne//(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name="course_id", insertable = false, updatable = false)
    private Course course;


}
