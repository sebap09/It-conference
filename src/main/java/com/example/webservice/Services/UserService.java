package com.example.webservice.Services;

import com.example.webservice.Entities.User;
import com.example.webservice.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

   private final UserRepository userRepository;

   @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User addNewUser(User user){
       return userRepository.save(user);
    }

    public User getUserByLogin(String login){
       return userRepository.findByLogin(login).orElseThrow(RuntimeException::new);
    }

    public Optional<User> getUserByLoginAndEmail(String login, String email){
        return userRepository.findByLoginAndEmail(login,email);
    }
}
