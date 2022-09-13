package com.dmdev.app.dao;

import com.dmdev.app.entity.User;

import java.util.List;
import java.util.Optional;

public class UserDao {

    private final List<User> users = List.of(new User(1L, "Vasya"));

    public Optional<User> getUserById(Long id) {
        return Optional.of(users.get(Math.toIntExact(id)));
    }
}
