package hr.project.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
//@Builder
public class User implements Serializable{ //UserDetails

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
    @JoinColumn(name="role_id", insertable = false, updatable = false)
    private Role role;

    @ManyToOne//(fetch = FetchType.LAZY)
    @JoinColumn(name="title_id", insertable = false, updatable = false)
    private Title title;

    @ManyToOne//(fetch = FetchType.LAZY)
    @JoinColumn(name="course_id", insertable = false, updatable = false)
    private Course course;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    public Integer getRole_id() {
        return role_id;
    }

    public void setRole_id(Integer role_id) {
        this.role_id = role_id;
    }

    public Integer getTitle_id() {
        return title_id;
    }

    public void setTitle_id(Integer title_id) {
        this.title_id = title_id;
    }

    public Integer getCourse_id() {
        return course_id;
    }

    public void setCourse_id(Integer course_id) {
        this.course_id = course_id;
    }

    public List<Statistic> getStatistics() {
        return statistics;
    }

    public void setStatistics(List<Statistic> statistics) {
        this.statistics = statistics;
    }

    public List<Game> getGame1() {
        return game1;
    }

    public void setGame1(List<Game> game1) {
        this.game1 = game1;
    }

    public List<Game> getGame2() {
        return game2;
    }

    public void setGame2(List<Game> game2) {
        this.game2 = game2;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }


/*
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_" + this.role.getName()));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }*/
}
