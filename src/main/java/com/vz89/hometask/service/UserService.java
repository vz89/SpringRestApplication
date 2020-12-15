package com.vz89.hometask.service;


import com.vz89.hometask.dto.UserDTO;
import com.vz89.hometask.model.User;

import java.util.List;

public interface UserService {

    List<UserDTO> getUsers();

    void create(User user);

    boolean activate(User user, Integer code);

    void getNewActivationCode(User user);

    boolean update(Long id, User user);

    void delete(User user);

    User findByUserName(String username);

    UserDTO findById(Long id);

    boolean updateStatusOrRole(Long id, User user);
}
