package com.github.cotrod.hotel.model;

public class ChangePassDTO {
    private String oldPass;
    private String newPass1;
    private String newPass2;

    public ChangePassDTO(String oldPass, String newPass1, String newPass2) {
        this.oldPass = oldPass;
        this.newPass1 = newPass1;
        this.newPass2 = newPass2;
    }

    public String getOldPass() {
        return oldPass;
    }

    public String getNewPass1() {
        return newPass1;
    }

    public String getNewPass2() {
        return newPass2;
    }
}
