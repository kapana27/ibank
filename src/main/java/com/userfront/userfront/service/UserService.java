package com.userfront.userfront.service;

import com.userfront.userfront.domain.User;
import com.userfront.userfront.domain.security.UserRole;

import java.util.Set;

public interface UserService {

     void save(User user);

     User findByUsername(String username);

     User findByEmail(String email);

     boolean checkUserExists(String username, String email);

     boolean checkEmailExists(String email);

     boolean checkUsernameExists(String username);

     User createUser(User user, Set<UserRole> userRoles);


    User saveUser(User user);
}
