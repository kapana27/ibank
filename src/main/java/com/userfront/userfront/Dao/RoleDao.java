package com.userfront.userfront.Dao;

import com.userfront.userfront.domain.security.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleDao extends CrudRepository<Role,Integer> {

    Role findByName(String name);
}
