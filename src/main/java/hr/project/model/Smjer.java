package hr.project.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Smjer {

    @Id
    @GeneratedValue
    private Integer id;
    private String naziv;

    public Smjer(){

    }

    public Smjer(Integer id, String naziv) {
        this.id = id;
        this.naziv = naziv;
    }
}
