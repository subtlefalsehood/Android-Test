package com.example;

import java.util.HashMap;
import java.util.Map;

public class CountClass {

    public static void main(String[] args) {
        Map<Character, Integer[]> map = new HashMap<>();
        String[] s = {"A", "BCD", "EF", "GH", "IJKL", "MN", "OP", "RQ", "S",
                "TU", "VW", "POM", "VWNFRSHQ", "ACDKEIGBJTU", "I", "MVNWQPODH",
                "LJCFUTAB", "GEK", "RS", "EWK", "GU", "IJ", "RNSQ", "HD",
                "PMO", "ACLTBVF", "WV", "NSDF", "RHQ", "A", "MPO", "CB", "LK",
                "TU", "EIJG", "CBFGWE", "A", "TJLIOUVK", "QMRDSPHN", "UJLB",
                "RHQ", "KENIVW", "CADTG", "FSOPM", "TUK", "WIV", "HNSRQD",
                "BAECG", "JFL", "MPO"};
        for (int z = 0; z < 23; z++) {
            Integer[] integers = new Integer[23];
            for (int y = 0; y < 23; y++) {
                integers[y] = 0;
            }
            map.put((char) (z + 65), integers);
        }
        for (String value : s) {
            for (int i = 0; i < value.length(); i++) {
                for (int j = 0; j < value.length(); j++) {
                    if (i == j) {
                        continue;
                    }
                    map.get(value.charAt(i))[value.charAt(j) - 65]++;
                }
            }
        }
        for (int i = 0; i < map.size(); i++) {
            String nameString = "";
            for (int j = 0; j < map.get((char) (i + 65)).length; j++) {

                if (i == j) {
                    continue;
                }
                if (map.get((char) (i + 65))[j] == 0) {
                    continue;
                }
                nameString += (char) (i + 65) + "->" + (char) (j + 65) + "="
                        + map.get((char) (i + 65))[j] + ",";
            }
            System.out.println(nameString);
        }
    }
}
