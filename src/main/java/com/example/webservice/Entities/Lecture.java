package com.example.webservice.Entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class Lecture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lecture_id")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long lectureId;

    @JsonIgnoreProperties("reservations")
    @ManyToMany(mappedBy = "reservations")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Set<User> users = new HashSet<>();

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime localDateTime;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String lectureCategory;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
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
