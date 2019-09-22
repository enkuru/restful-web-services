package com.enkuru.rest.webservices.restfulwebservices.controller;

import com.enkuru.rest.webservices.restfulwebservices.exception.AppException;
import com.enkuru.rest.webservices.restfulwebservices.model.Post;
import com.enkuru.rest.webservices.restfulwebservices.model.User;
import com.enkuru.rest.webservices.restfulwebservices.repository.PostRepository;
import com.enkuru.rest.webservices.restfulwebservices.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController("/jpa")
@RequiredArgsConstructor
public class UserJPAController {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/users")
    public ResponseEntity.BodyBuilder saveUser(@Valid @RequestBody User user) {
        user = userRepository.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();

        return ResponseEntity.created(location);
    }

    @GetMapping("/users/{id}")
    public Resource<User> retrieveUser(@PathVariable int id) {
        User user = userRepository.getOne(id);

        if (user == null) {
            throw new AppException("id:" + id);
        }

        ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        Resource<User> resource = new Resource<>(user);
        resource.add(linkTo.withRel("all-users"));

        return resource;
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {
        userRepository.deleteById(id);
    }

    @GetMapping("/users/{id}/posts")
    public List<Post> retrieveUserPosts(@PathVariable int id) {
        User user = userRepository.getOne(id);

        if (user == null) {
            throw new AppException("id:" + id);
        }

        return user.getPosts();
    }

    @PostMapping("/users/{id}/posts")
    public ResponseEntity.BodyBuilder save(@PathVariable int id, @Valid @RequestBody Post post) {
        User user = userRepository.getOne(id);

        if (user == null) {
            throw new AppException("id:" + id);
        }

        post.setUser(user);
        post = postRepository.save(post);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(post.getId()).toUri();

        return ResponseEntity.created(location);
    }
}
