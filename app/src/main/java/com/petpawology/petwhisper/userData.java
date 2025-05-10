package com.petpawology.petwhisper;

import java.util.UUID;

//Local user information
public class userData {
    private String userName;
    private final String userEmail;

    private int userID = UUID.randomUUID().hashCode();
    public userData(String userName, String userEmail, int userID) {
        this.userName = userName;
        this.userEmail = userEmail;

        if (this.userID != userID) {
            this.userID = userID;
        }
    }

    public userData(String userEmail, int userID) {
        this.userEmail = userEmail;
        if(userID != 0){
            this.userID = userID;
        }
    }

    public String getEmail(String usrEmail){ return userEmail; }

    public int getUserID() {
        return userID;
    }

    public String getUserName() {
        return userName;
    }
}
