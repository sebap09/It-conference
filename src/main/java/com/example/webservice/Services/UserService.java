package com.example.webservice.Services;

import com.example.webservice.Entities.User;
import com.example.webservice.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
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

    public Optional<User> getUserByLogin(String login){
       return userRepository.findByLogin(login);
    }

    public Optional<User> getUserByLoginAndEmail(String login, String email){
        return userRepository.findByLoginAndEmail(login,email);
    }

    public Optional<User> getUser(Long userId){
       return userRepository.findById(userId);
    }

    public List<Object[]> getUsers(){
       return userRepository.findAllWithIdAndEmail();
    }

    @Transactional
    public User editEmail(Long userId, User editedUser){
       User user=getUser(userId).orElseThrow(RuntimeException::new);
       user.setEmail(editedUser.getEmail());
       return user;

    }
}
