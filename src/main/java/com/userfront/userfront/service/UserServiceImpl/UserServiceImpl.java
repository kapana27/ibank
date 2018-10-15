package com.userfront.userfront.service.UserServiceImpl;

import com.userfront.userfront.dao.RoleDao;
import com.userfront.userfront.dao.UserDao;
import com.userfront.userfront.domain.User;
import com.userfront.userfront.domain.security.UserRole;
import com.userfront.userfront.service.AccountService;
import com.userfront.userfront.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    private AccountService accountService;

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

            user.getUserRoles().addAll(userRoles);

            user.setPrimaryAccount(accountService.createPrimaryAccount());
            user.setSavingsAccount(accountService.createSavingsAccount());

            localUser = userDao.save(user);
        }
        return localUser;
    }

    public User saveUser (User user) {
        return userDao.save(user);
    }

    public List<User> findUserList() {
        return userDao.findAll();
    }

    public void enableUser (String username) {
        User user = findByUsername(username);
        user.setEnabled(true);
        userDao.save(user);
    }

    public void disableUser (String username) {
        User user = findByUsername(username);
        user.setEnabled(false);
        System.out.println(user.isEnabled());
        userDao.save(user);
        System.out.println(username + " is disabled.");
    }


}
