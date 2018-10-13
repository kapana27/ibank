package com.userfront.userfront.Dao;

import com.userfront.userfront.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserDao extends CrudRepository<User,Long> {

    User findByUsername(String username);
    User findByEmail(String email);

}
