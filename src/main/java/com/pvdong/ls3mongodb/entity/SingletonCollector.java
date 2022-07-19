package com.pvdong.ls3mongodb.entity;

import java.util.HashMap;
import java.util.Map;

public class SingletonCollector {
    private Map<String, String> singleton = new HashMap<String, String>();

    private static SingletonCollector instance = null;

    public SingletonCollector() {}

    public static SingletonCollector getInstance() {
        if (instance == null) {
            instance = new SingletonCollector();
        }
        return instance;
    }

    public void put(String str, String id) {
        singleton.put(str, id);
    }
}
