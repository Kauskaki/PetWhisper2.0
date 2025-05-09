package com.petpawology.petwhisper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Account {
    List<Friend> friends = new ArrayList<>();


    public Account(){
        friends.add(new Friend("Alice", Arrays.asList(new Pet("Buddy"), new Pet("Mittens"))));
        friends.add(new Friend("Bob", Arrays.asList(new Pet("Rex"), new Pet("Whiskers"))));
    }
}
