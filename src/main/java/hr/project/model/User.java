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

    @Column(name= "image")
    private String image;

    @Column(name= "semester")
    private Integer semester;

    @Column(name= "role_id")
    private Integer role_id;

    @Column(name= "title_id")
    private Integer title_id;

    @Column(name= "course_id")
    private Integer course_id;

    @OneToMany(mappedBy="user")
    private List<Statistic> statistics;

    @OneToMany(mappedBy="user1")
    private List<Game> game1;

    @OneToMany(mappedBy="user2")
    private List<Game> game2;

    @ManyToOne//(fetch = FetchType.LAZY)
    //@JsonIgnore
    @JoinColumn(name="role_id", insertable = false, updatable = false)
    private Role role;

    @ManyToOne//(fetch = FetchType.LAZY)
    //@JsonIgnore
    @JoinColumn(name="title_id", insertable = false, updatable = false)
    private Title title;

    @ManyToOne//(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name="course_id", insertable = false, updatable = false)
    private Course course;
}
