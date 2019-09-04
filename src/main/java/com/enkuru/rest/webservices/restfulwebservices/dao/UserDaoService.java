package com.enkuru.rest.webservices.restfulwebservices.dao;

import com.enkuru.rest.webservices.restfulwebservices.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserDaoService {
    private static List<User> users = new ArrayList<>();

    static {
        users.add(new User(1, "Adam", new Date()));
        users.add(new User(2, "Eve", new Date()));
        users.add(new User(3, "Jack", new Date()));
    }

    public List<User> findAll() {
        return users;
    }

    public User save(User user) {
        if (user.getId() == null) user.setId(users.size() + 1);

        users.add(user);
        return user;
    }

    public User findOne(int id) {
        return users.stream()
                .filter(u -> u.getId().equals(id))
                .findAny()
                .orElse(null);
    }

    public User delete(int id) {
        User user = findOne(id);

        if (user == null) return null;

        users.remove(user);

        return user;
    }
}
