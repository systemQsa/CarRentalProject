package com.myproject.command;

import java.util.HashMap;

public class Users {
    private static HashMap<String,String > map = new HashMap<>();

    public static HashMap<String, String > getMap() {
        map.put("hello","admin");
        map.put("user","user");
        map.put("sasha","admin");
        return map;
    }

    public static void addUser(String key,String value){
        map.put(key, value);
    }

    public static String getRole(String key){
        return map.get(key);
    }
}