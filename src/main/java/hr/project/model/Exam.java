package hr.project.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "exam")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Exam implements Serializable{

    @Id
    @Column(name= "id")
    @GeneratedValue
    private Integer id;

    @Column(name= "name")
    private String name;

    @OneToMany(mappedBy="exam")
    @JsonManagedReference
    private List<Question> questions;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="subject_id")
    private Subject subject;
}
