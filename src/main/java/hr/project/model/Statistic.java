package hr.project.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "statistic")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Statistic implements Serializable {

    @Id
    @Column(name= "id")
    @GeneratedValue
    private Integer id;

    @Column(name= "points")
    private Integer points;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="subject_id")
    private Subject subject;
}
