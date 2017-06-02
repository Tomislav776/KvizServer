package hr.project.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column(name= "user_id")
    private Integer user_id;

    @Column(name= "subject_id")
    private Integer subject_id;

    @ManyToOne//(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name="user_id", insertable = false, updatable = false)
    private User user;

    @ManyToOne//(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name="subject_id", insertable = false, updatable = false)
    private Subject subject;
}
