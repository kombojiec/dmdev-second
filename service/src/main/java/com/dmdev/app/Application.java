package com.dmdev.app;

import com.dmdev.app.service.UserService;

public class Application {

    public static void main(String[] args) {
        var userService = new UserService();
        System.out.println(userService.getUser());
    }

}
