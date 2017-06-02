package hr.project.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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

    @Column(name= "subject_id")
    private Integer subject_id;

    @OneToMany(mappedBy="exam")//, fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    private List<Question> questions;

    @ManyToOne// (fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name="subject_id", insertable = false, updatable = false)
    private Subject subject;

}
