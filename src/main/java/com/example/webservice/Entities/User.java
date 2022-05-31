package com.example.webservice.Entities;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long userId;
    private String login;
    private String email;


    @JsonIgnoreProperties("users")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "LectureReservations",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "lecture_id")
    )
    private Set<Lecture> reservations = new HashSet<>();

    public void addReservation(Lecture lecture){
        this.reservations.add(lecture);
        lecture.getUsers().add(this);
    }
    public void removeReservation(Lecture lecture){
        this.reservations.remove(lecture);
        lecture.getUsers().remove(this);
    }
}
