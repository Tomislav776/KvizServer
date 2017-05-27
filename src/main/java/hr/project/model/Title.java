package hr.project.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "title")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Title implements Serializable {

    @Id
    @Column(name= "id")
    @GeneratedValue
    private Integer id;

    @Column(name= "name")
    private String name;

    @Column(name= "points")
    private Integer points;

    @OneToMany(mappedBy="title")
    @JsonManagedReference
    private List<User> users;
}
