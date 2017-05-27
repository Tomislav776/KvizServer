package hr.project.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "report")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Report implements Serializable {

    @Id
    @Column(name= "id")
    @GeneratedValue
    private Integer id;

    @Column(name= "complaint")
    private String complaint;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="question_id")
    private Question question;
}
