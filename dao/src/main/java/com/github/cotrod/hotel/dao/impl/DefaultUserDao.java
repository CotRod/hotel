package com.github.cotrod.hotel.dao.impl;

import com.github.cotrod.hotel.dao.EMUtil;
import com.github.cotrod.hotel.dao.UserDao;
import com.github.cotrod.hotel.dao.entity.Client;
import com.github.cotrod.hotel.dao.entity.User;
import com.github.cotrod.hotel.model.Role;
import com.github.cotrod.hotel.model.UserDTO;
import com.github.cotrod.hotel.model.UserSignupDTO;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.RollbackException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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

        try {
            session.beginTransaction();
            session.save(client);
            session.getTransaction().commit();
        } catch (RollbackException e) {
            log.warn("", e);
        } finally {
            session.close();
        }
        return client.getId();
    }


    @Override
    public UserDTO getUserByLogin(String login) {
        CriteriaBuilder cb = EMUtil.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<User> criteria = cb.createQuery(User.class);
        Root<User> userRoot = criteria.from(User.class);
        criteria.select(userRoot).where(cb.equal(userRoot.get("login"), login));
        List<User> usersFromDB = EMUtil.getEntityManager().createQuery(criteria).getResultList();
        if (usersFromDB.size() > 0) {
            return createUserDTO(usersFromDB.get(0));
        }
//        Session session = EMUtil.getEntityManager().getSession();
//        User userFromDB = session.byNaturalId(User.class).using("login", login).load();
//        session.close();
//        if (userFromDB != null) {
//            return createUserDTO(userFromDB);
//        }
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
        CriteriaBuilder cb = EMUtil.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<User> criteria = cb.createQuery(User.class);
        Root<User> userRoot = criteria.from(User.class);
        criteria.select(userRoot).where(cb.equal(userRoot.get("role"), Role.USER));
        List<User> usersFromDB = EMUtil.getEntityManager().createQuery(criteria).getResultList();

        if (usersFromDB.size() > 0) {
            usersFromDB.forEach(user -> users.add(createUserDTO((User) user)));
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
        try {
            session.beginTransaction();
            User userFromDB = session.get(User.class, id);
            userFromDB.setPassword(password);
            session.getTransaction().commit();
        } catch (RollbackException e) {
            log.warn("", e);
        } finally {
            session.close();
        }
    }

    private UserDTO createUserDTO(User userFromDB) {
        long id = userFromDB.getClient().getId();
        String login = userFromDB.getLogin();
        String password = userFromDB.getPassword();
        Role role = userFromDB.getRole();
        String firstName = userFromDB.getClient().getFirstName();
        return new UserDTO(id, login, password, role, firstName);
    }
}
