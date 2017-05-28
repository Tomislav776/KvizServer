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
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User implements Serializable{

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

    @Column(name= "role_id")
    private String role_id;

    @Column(name= "title_id")
    private String title_id;

    @Column(name= "course_id")
    private String course_id;

    @OneToMany(mappedBy="user")
    private List<Statistic> statistics;

    @OneToMany(mappedBy="user1")
    private List<Game> game1;

    @OneToMany(mappedBy="user2")
    private List<Game> game2;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="role_id", insertable = false, updatable = false)
    private Role role;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="title_id", insertable = false, updatable = false)
    private Title title;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="course_id", insertable = false, updatable = false)
    private Course course;
}
