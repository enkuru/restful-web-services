package com.enkuru.rest.webservices.restfulwebservices.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import com.enkuru.rest.webservices.restfulwebservices.dao.UserDaoService;
import com.enkuru.rest.webservices.restfulwebservices.exception.AppException;
import com.enkuru.rest.webservices.restfulwebservices.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Locale;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserDaoService userDaoService;

    private final MessageSource messageSource;

    @GetMapping("/hello")
//    public String hello(@RequestHeader(name = "Accept-Language", required = false) Locale locale) { -- v1
    public String hello() {
        return messageSource.getMessage("good.morning.message", null, LocaleContextHolder.getLocale());
    }

    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return userDaoService.findAll();
    }

    @PostMapping("/users")
    public ResponseEntity.BodyBuilder save(@Valid @RequestBody User user) {
        user = userDaoService.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();

        return ResponseEntity.created(location);
    }

    @GetMapping("/users/{id}")
    public Resource<User> retrieveUser(@PathVariable int id) {
        User user = userDaoService.findOne(id);

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
        User user = userDaoService.delete(id);

        if (user == null) {
            throw new AppException("id:" + id);
        }
    }
}
