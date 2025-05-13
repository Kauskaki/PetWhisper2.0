package com.petpawology.petwhisper.friends;
import com.petpawology.petwhisper.Pet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Account {
    List<com.petpawology.petwhisper.friends.Friend> friends = new ArrayList<>();


    public Account(){
        friends.add(new com.petpawology.petwhisper.friends.Friend("Alice", Arrays.asList(new Pet("Buddy"), new Pet("Mittens"))));
        friends.add(new Friend("Bob", Arrays.asList(new Pet("Rex"), new Pet("Whiskers"))));
    }
}
