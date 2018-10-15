package com.userfront.userfront.dao;

import com.userfront.userfront.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserDao extends CrudRepository<User,Long> {


    @Query(value = "select t from User t where t.firstName = :username")
    User findByUsername(@Param("username") String username);


    List<User> findAll();

    User findByEmail(String email);

}
