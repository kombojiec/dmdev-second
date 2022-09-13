package com.dmdev.app.dto;

import com.dmdev.app.entity.User;

public class UserDto {

    private final Long id;
    private String name;

    public UserDto(User user) {
        id = user.id();
        name = user.name();
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
