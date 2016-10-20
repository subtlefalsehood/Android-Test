package com.example;

import java.util.Enumeration;
import java.util.Hashtable;
import static java.lang.System.out;
/**
 * Created by chenqiuyi on 16/8/4.
 */
public class HashTableTest {
    public static void main(String a[]){
        Hashtable hashtable = new Hashtable();
        Enumeration names;
        Double aDouble;
        hashtable.put("zara",new Double(344.4));
        hashtable.put("james",new Double(344.4));
        hashtable.put("jack",new Double(344.4));
        hashtable.put("rose",new Double(344.4));
        names = hashtable.keys();
        while (names.hasMoreElements()){
            String s = (String) names.nextElement();
            out.println(s+"'s value is "+hashtable.get(s));
        }
        double v = (double) hashtable.get("zara");
        hashtable.put("zara",new Double(v+1000));
        out.println("zara's new value is "+hashtable.get("zara"));
    }
}
