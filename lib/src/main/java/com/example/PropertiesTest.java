package com.example;

import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

/**
 * Created by chenqiuyi on 16/8/4.
 */
public class PropertiesTest {
    public static void main(String a[]) {
        Properties capitals = new Properties();
        Set states;
        String str;

        capitals.put("Illinois", "Springfield");
        capitals.put("Missouri", "Jefferson City");
        capitals.put("Washington", "Olympia");
        capitals.put("California", "Sacramento");
        capitals.put("Indiana", "Indianapolis");

        states = capitals.keySet();
        Iterator iterator = states.iterator();
        while (iterator.hasNext()) {
            str = (String) iterator.next();
            System.out.println("The capital of " + str + " is " + capitals.get(str));
        }
        str = capitals.getProperty("Flan", "not found");
        System.out.println(str);
    }
}
