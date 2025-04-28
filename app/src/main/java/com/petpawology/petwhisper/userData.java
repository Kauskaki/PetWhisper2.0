package com.petpawology.petwhisper;

//Local user information
public class userData {
    private String userName;
    private String userEmail;
    public userData(String userName, String userEmail) {
        this.userName = userName;
        this.userEmail = userEmail;
    }

    public String getEmail(String usrEmail){ return userEmail; }

    public String getUserName() {
        return userName;
    }
}
