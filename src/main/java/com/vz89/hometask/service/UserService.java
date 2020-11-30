package com.vz89.hometask.service;


import com.vz89.hometask.model.User;

import java.util.List;

public interface UserService {

    List<User> getUsers();

    void create(User user);
}
