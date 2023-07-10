package com.security2.studentlogin.service;
import com.security2.studentlogin.dto.*;
import com.security2.studentlogin.model.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDTO userDto);
    User findUserByEmail(String email);
    List<UserDTO> findAllUsers();
}
