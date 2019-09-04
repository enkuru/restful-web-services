package com.enkuru.rest.webservices.restfulwebservices.controller;

import com.enkuru.rest.webservices.restfulwebservices.dao.UserDaoService;
import com.enkuru.rest.webservices.restfulwebservices.exception.AppException;
import com.enkuru.rest.webservices.restfulwebservices.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserDaoService userDaoService;

    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return userDaoService.findAll();
    }

    @PostMapping("/users")
    public ResponseEntity.BodyBuilder save(@RequestBody User user) {
        user = userDaoService.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();

        return ResponseEntity.created(location);
    }

    @GetMapping("/users/{id}")
    public User retrieveUser(@PathVariable int id) {
        User user = userDaoService.findOne(id);

        if (user == null) {
            throw new AppException("id:" + id);
        }

        return user;
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {
        User user = userDaoService.delete(id);

        if (user == null) {
            throw new AppException("id:" + id);
        }
    }
}
