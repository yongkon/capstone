package com.yongkonhahn.homefood.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.yongkonhahn.homefood.service.UserService;
import com.yongkonhahn.homefood.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.yongkonhahn.homefood.model.User;
import com.yongkonhahn.homefood.model.Role;
import com.yongkonhahn.homefood.repository.RoleRepository;
import com.yongkonhahn.homefood.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    public static final String DB_URL = "jdbc:mysql://localhost:3306/capstonedb";
    public static final String DB_USERNAME = "root";
    public static final String DB_PASSWORD = "password";
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder)  {
        super();
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserServiceImpl() {
    }
    @Override
    public void saveUser(UserDTO userDTO) {
        User user = new User();

        user.setName(userDTO.getFirstName() + " " + userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        // Encrypt the password using Spring Security
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        Role role = roleRepository.findByName("ROLE_USER");
        if (role == null) {
            role = checkRoleExist();
        }
        user.setRoles(Arrays.asList(role));

        userRepository.save(user);
    }
    private Role checkRoleExist() {
        Role role = new Role();
        role.setName("ROLE_USER");
        return roleRepository.save(role);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserDTO findUserDtoByEmail(String email) {
        return null;
    }

    @Override
    public List<UserDTO> findAllUsers() {
        List<User>users = userRepository.findAll();
        return users.stream()
                .map((user) -> mapToUserDto(user))
                .collect(Collectors.toList());
    }
    private UserDTO mapToUserDto(User user) {
        UserDTO userDto = new UserDTO();
        String[] str = user.getName().split(" ");
        userDto.setFirstName(str[0]);
        userDto.setLastName(str[1]);
        userDto.setEmail(user.getEmail());
        return userDto;
    }
}
