package hr.project.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

    @Id
    @Column(name= "id")
    @GeneratedValue
    private Integer id;

    @Column(name= "name")
    private String name;

    @Column(name= "email")
    private String email;

    @Column(name= "password")
    private String password;

    @OneToMany(mappedBy="user")
    @JsonManagedReference
    private List<Statistic> statistics;

    @OneToMany(mappedBy="user1")
    @JsonManagedReference
    private List<Game> game1;

    @OneToMany(mappedBy="user2")
    @JsonManagedReference
    private List<Game> game2;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="role_id")
    private Role role;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="title_id")
    private Title title;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="course_id")
    private Course course;
}
