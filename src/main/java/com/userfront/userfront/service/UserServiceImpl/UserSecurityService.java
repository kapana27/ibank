package com.userfront.userfront.service.UserServiceImpl;

import com.userfront.userfront.dao.UserDao;
import com.userfront.userfront.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserSecurityService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userDao.findByUsername(username);
        if(user == null){
            log.warn("Username {} not found",username);
            throw new UsernameNotFoundException("Username "+username+" not found");
        }


        return user;
    }
}
