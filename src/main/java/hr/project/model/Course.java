package hr.project.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "course")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Course implements Serializable{

    @Id
    @Column(name= "id")
    @GeneratedValue
    private Integer id;

    @Column(name= "name")
    private String name;

    @OneToMany(mappedBy="course")
    @JsonManagedReference
    private List<Subject> subjects;

    @OneToMany(mappedBy="course")
    @JsonManagedReference
    private List<User> users;

}
