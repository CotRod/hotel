package com.github.cotrod.hotel.model;

public class UserLoginDTO {
    private String login;
    private String password;

    public UserLoginDTO(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
