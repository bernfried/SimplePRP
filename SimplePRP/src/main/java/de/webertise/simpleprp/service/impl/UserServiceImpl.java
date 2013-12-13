package de.webertise.simpleprp.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import de.webertise.simpleprp.model.User;
import de.webertise.simpleprp.model.dao.UserDao;
import de.webertise.simpleprp.service.UserService;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Override
    public User get(Long id) {
        return userDao.findOne(id);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void remove(Long id) {
        userDao.delete(id);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public User save(User user) {

        logger.debug("UserServiceImpl - save: plainPassword: " + user.getPlainPassword());

        // get existing user for salt string
        if (user.getId() == 0L || user.getPlainPassword() != null) {
            // create salt and password for inserted or updated user
            ShaPasswordEncoder pwdEnc = new ShaPasswordEncoder();
            user.setSalt(user.getUsername() + "-" + user.getEmail());
            user.setPassword(pwdEnc.encodePassword(user.getPlainPassword(), user.getSalt()));
        }

        // save new or updated user
        return userDao.save(user);
    }

    @Override
    public User getByUsername(String username) {
        return userDao.getByUsername(username);
    }

    @Override
    public User getByEmail(String email) {
        return userDao.getByEmail(email);
    }

    @Override
    public List<User> findByEnabled(boolean enabled) {
        return userDao.findByEnabled(enabled);
    }

    @Override
    public List<User> findByAccountNonExpired(boolean accountNonExpired) {
        return userDao.findByAccountNonExpired(accountNonExpired);
    }

    @Override
    public List<User> findByAccountNonLocked(boolean accountNonLocked) {
        return userDao.findByAccountNonLocked(accountNonLocked);
    }

    @Override
    public List<User> findByCredentialsNonExpired(boolean credentialsNonExpired) {
        return userDao.findByCredentialsNonExpired(credentialsNonExpired);
    }

    @Override
    public List<User> findAll() {
        return (List<User>) userDao.findAll();
    }

    @Override
    public boolean exists(Long id) {
        return userDao.exists(id);
    }

    @Override
    public User authenticate(String username, String password) {
        // get user by username
        User user = userDao.getByUsername(username);
        if (user != null) {
            ShaPasswordEncoder pwdEnc = new ShaPasswordEncoder();
            String pwd = pwdEnc.encodePassword(password, user.getSalt());
            if (pwd.equals(user.getPassword())) {
                return user;
            }
        }
        return null;
    }

}
