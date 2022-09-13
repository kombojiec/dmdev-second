package com.dmdev.app.service;

import com.dmdev.app.dao.UserDao;
import com.dmdev.app.dto.UserDto;

import java.util.Optional;

public class UserService {

    private final UserDao userDao = new UserDao();

    public Optional<UserDto> getUser() {
        return userDao.getUserById(0L).map(UserDto::new);
    }

}
