package com.example.webservice.Entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="Lectures")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "lectureId")
public class Lecture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lecture_id")
    private Long lectureId;

    @ManyToMany(mappedBy = "reservations")
    private Set<User> users = new HashSet<>();

    private LocalDateTime localDateTime;
    private String lectureCategory;
    private String prelectionNumber;


    public void addUser(User user){
        this.users.add(user);
        user.getReservations().add(this);
    }
    public void removeUser(User user){
        this.users.remove(user);
        user.getReservations().remove(this);
    }
}
