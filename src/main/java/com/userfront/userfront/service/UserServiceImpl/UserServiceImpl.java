package com.userfront.userfront.service.UserServiceImpl;

import com.userfront.userfront.Dao.RoleDao;
import com.userfront.userfront.Dao.UserDao;
import com.userfront.userfront.domain.User;
import com.userfront.userfront.domain.security.UserRole;
import com.userfront.userfront.service.AccountServce;
import com.userfront.userfront.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Slf4j
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AccountServce accountService;

    public void save(User user){
        userDao.save(user);
    }

    public User findByUsername(String username){
        return userDao.findByUsername(username);
    }

    public User findByEmail(String email){
        return userDao.findByEmail(email);
    }
    public boolean checkUserExists(String username, String email){
        
        if(checkUsernameExists(username) || checkEmailExists(email)){
            return true;
        }else{
            return false;
        }
    }
    public boolean checkEmailExists(String email) {
        if(findByEmail(email) != null){
            return true;
        }
        return false;
    }

    public boolean checkUsernameExists(String username) {

        if(findByUsername(username) != null){
            return true;
        }
        return false;
    }


    @Override
    public User createUser(User user, Set<UserRole> userRoles) {
        User localUser = userDao.findByUsername(user.getUsername());

        if(localUser != null){
            log.info("User with username {} already exist. nothing will be done", user.getUsername());
        }else{
            String encryptedPassword = passwordEncoder.encode(user.getPassword());

            user.setPassword(encryptedPassword);

            for(UserRole ur : userRoles){
                roleDao.save(ur.getRole());
            }

            user.getUserRoles(accountService.createPrimaryAccount());
            user.getUserRoles(accountService.createSavingsAccount());

            localUser = userDao.save(user);
        }
        return localUser;
    }


}
