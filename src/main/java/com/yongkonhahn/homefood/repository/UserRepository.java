package com.yongkonhahn.homefood.repository;

import com.yongkonhahn.homefood.dto.UserDTO;
import com.yongkonhahn.homefood.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);

    UserDTO findUserDTOByEmail(String email);

    User findByEmailAndPassword(String email, String password);
}