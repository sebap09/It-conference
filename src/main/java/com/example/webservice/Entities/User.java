package com.example.webservice.Entities;

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
    private Long userId;
    private String login;
    private String email;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "LectureReservations",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "lecture_id")
    )
    private Set<Lecture> reservations = new HashSet<>();

    public void addReservation(Lecture lecture){
        this.reservations.add(lecture);
    }
    public void removeReservation(Lecture lecture){
        this.reservations.remove(lecture);
    }
}
