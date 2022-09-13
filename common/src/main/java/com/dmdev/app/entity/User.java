package com.dmdev.app.entity;

public record User(Long id, String name) {

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
