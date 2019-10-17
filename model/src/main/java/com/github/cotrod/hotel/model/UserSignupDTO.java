package com.github.cotrod.hotel.model;

public class UserSignupDTO {
    private String login;
    private String password;
    private String firstName;
    private String lastName;

    public UserSignupDTO(String login, String password, String firstName, String lastName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
