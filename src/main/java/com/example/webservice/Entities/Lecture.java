package com.example.webservice.Entities;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="Lectures")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Lecture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lecture_id")
    private String lectureId;

    @ManyToMany(mappedBy = "reservations")
    private Set<User> users = new HashSet<>();

    public void addUser(User user){
        this.users.add(user);
    }
    public void removeUser(User user){
        this.users.remove(user);
    }
}
