package com.github.cotrod.hotel.dao.impl;

import com.github.cotrod.hotel.dao.EMUtil;
import com.github.cotrod.hotel.dao.UserDao;
import com.github.cotrod.hotel.dao.entity.Client;
import com.github.cotrod.hotel.dao.entity.User;
import com.github.cotrod.hotel.model.Role;
import com.github.cotrod.hotel.model.UserDTO;
import com.github.cotrod.hotel.model.UserSignupDTO;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class DefaultUserDao implements UserDao {
    private static final Logger log = LoggerFactory.getLogger(DefaultUserDao.class);

    private static class SingletonHolder {
        static final UserDao HOLDER_INSTANCE = new DefaultUserDao();
    }

    public static UserDao getInstance() {
        return DefaultUserDao.SingletonHolder.HOLDER_INSTANCE;
    }

    @Override
    public Long save(UserSignupDTO userSignup) {
        Client client = new Client(userSignup.getFirstName(), userSignup.getLastName());
        User user = new User(userSignup.getLogin(), userSignup.getPassword());
        user.setRole(Role.USER);
        client.setUser(user);
        user.setClient(client);
        Session session = EMUtil.getEntityManager();
        session.beginTransaction();
        session.save(client);
        session.getTransaction().commit();
        session.close();
        return client.getId();
    }


    @Override
    public UserDTO getUserByLogin(String login) {
        Session session = EMUtil.getEntityManager().getSession();
        User userFromDB = session.byNaturalId(User.class).using("login", login).load();
        UserDTO userDTO;
        session.close();
        if (userFromDB != null) {
            return createUserDTO(userFromDB);
        }
        return null;
    }

    @Override
    public UserDTO getUserById(Long id) {
        Session session = EMUtil.getEntityManager().getSession();
        User userFromDB = session.get(User.class, id);
        session.close();
        if (userFromDB != null) {
            return createUserDTO(userFromDB);
        }
        return null;
    }

    @Override
    public List<UserDTO> getUsers() {
        List<UserDTO> users = new ArrayList<>();
        Session session = EMUtil.getEntityManager().getSession();
        Query query = session.createQuery("from User as us where us.role = 'USER'");
        query.setReadOnly(true);
        query.list().forEach(user -> users.add(createUserDTO((User) user)));
        session.close();
        if (users.size() > 0) {
            return users;
        }
        return null;
    }

    @Override
    public void deleteUser(Long id) {
        Session session = EMUtil.getEntityManager().getSession();
        session.beginTransaction();
        Client clientForDelete = session.get(Client.class, id);
        session.delete(clientForDelete);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void changePassword(Long id, String password) {
        Session session = EMUtil.getEntityManager().getSession();
        session.beginTransaction();
        User userFromDB = session.get(User.class, id);
        userFromDB.setPassword(password);
        session.getTransaction().commit();
        session.close();
    }

    private UserDTO createUserDTO(User userFromDB) {
        long id = userFromDB.getId();
        String login = userFromDB.getLogin();
        String password = userFromDB.getPassword();
        Role role = userFromDB.getRole();
        String firstName = userFromDB.getClient().getFirstName();
        return new UserDTO(id, login, password, role, firstName);
    }
}
