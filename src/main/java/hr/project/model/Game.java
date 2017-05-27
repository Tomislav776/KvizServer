package hr.project.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "game")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Game {

    @Id
    @Column(name= "id")
    @GeneratedValue
    private Integer id;

    @Column(name= "score")
    private String score;

    @Column(name= "user1_points")
    private Integer user1_points;

    @Column(name= "user2_points")
    private Integer user2_points;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="subject_id")
    private Subject subject;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="user1_id")
    private User user1;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="user2_id")
    private User user2;
}
