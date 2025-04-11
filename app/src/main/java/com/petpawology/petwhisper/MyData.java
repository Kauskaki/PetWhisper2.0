package com.petpawology.petwhisper;

import android.content.Context;
public class MyData { 
    private String userName;
    private int userAge;

    public MyData(String userName, int userAge) {
        this.userName = userName;
        this.userAge = userAge;
    }

    public MyData(Context applicationContext) {
    }

    public void setEmail(String usrEmail){
        this.userName = usrEmail;
    }
    public void setAge(int usrAge){
        
    }

    public String getUserName() {
        return userName;
    }
    public int getUserAge() {
        return userAge;
    }
}
