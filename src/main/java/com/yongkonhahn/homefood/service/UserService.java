package com.yongkonhahn.homefood.service;
import com.yongkonhahn.homefood.dto.UserDTO;
import com.yongkonhahn.homefood.model.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDTO userDto);
    User findUserByEmail(String email);

    UserDTO findUserDtoByEmail(String email);

    List<UserDTO> findAllUsers();
}
