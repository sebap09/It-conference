package com.example.webservice.Controllers;

import com.example.webservice.Entities.Lecture;
import com.example.webservice.Entities.User;
import com.example.webservice.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // PUT http://localhost:8080/api/users?userId=
    @PutMapping(value = "/users")
    public ResponseEntity<User> editUser(@RequestParam final Long userId,
                                         @RequestBody final User editedUser) {
        try {
            User user = userService.editEmail(userId, editedUser);
            return ResponseEntity.ok().build();
        }catch (RuntimeException ex){
            return ResponseEntity.notFound().build();
        }

    }

    // GET http://localhost:8080/api/users
    @GetMapping(value = "/users")
    public ResponseEntity <List<Object[]>> getUsers(){
        return new ResponseEntity<>(userService.getUsers(),HttpStatus.OK);
    }
}
