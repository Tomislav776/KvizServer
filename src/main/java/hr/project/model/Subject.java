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

    @OneToMany(mappedBy="subject")
    @JsonManagedReference
    private List<Exam> exam;

    @OneToMany(mappedBy="subject")
    @JsonManagedReference
    private List<Game> Games;

    @OneToMany(mappedBy="subject")
    @JsonManagedReference
    private List<Statistic> statistics;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="course_id")
    private Course course;


}
