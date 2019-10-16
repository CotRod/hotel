//package com.github.cotrod.hotel.service.impl;
//
//import com.github.cotrod.hotel.dao.UserDao;
//import com.github.cotrod.hotel.dao.impl.DefaultUserDao;
//import com.github.cotrod.hotel.model.User;
//import com.github.cotrod.hotel.service.AuthService;
//
//import java.util.List;
//
//public class DefaultAuthService implements AuthService {
//    private UserDao userDao = DefaultUserDao.getInstance();
//    private static class SingletonHolder{
//        static final DefaultAuthService HOLDER_INSTANCE = new DefaultAuthService();
//    }
//
//    public static AuthService getInstance() {
//        return DefaultAuthService.SingletonHolder.HOLDER_INSTANCE;
//    }
//
//    @Override
//    public User getUser(String login, String password) {
//        User user = userDao.getUserByLogin(login);
//        if(user!=null){
//            if (user.getPassword().equals(password)){
//                return user;
//            }
//        }
//        return null;
//    }
//
//    @Override
//    public User saveUser(String login, String password) {
//        User user = userDao.getUserByLogin(login);
//        if (user==null){
//            user = new User(login,password);
//            userDao.save(user);
//            return user;
//        }else {
//            return null;
//        }
//    }
//
//    @Override
//    public boolean changePassword(User user, String newPass1, String newPass2) {
//        if(newPass1.equals(newPass2)){
//            String oldPass = userDao.getUserByLogin(user.getLogin()).getPassword();
//            if (user.getPassword().equals(oldPass)){
//                userDao.changePassword(user.getLogin(),newPass1);
//                return true;
//            }
//        }
//        return false;
//    }
//
//    @Override
//    public void deleteUser(String login) {
//        userDao.deleteUser(login);
//    }
//
//    @Override
//    public List<User> getUsers(){
//        return userDao.getUsers();
//    }
//}