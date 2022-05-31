package com.example.webservice.Repositories;

import com.example.webservice.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByLogin(String login);

    Optional<User> findByLoginAndEmail(String login, String email);

    @Query(value = "SELECT user_id, email FROM users",nativeQuery = true)
    List<Object[]> findAllWithIdAndEmail();
}
