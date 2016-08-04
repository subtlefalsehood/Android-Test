package com.example;

import java.util.BitSet;

import static java.lang.System.out;

/**
 * Created by chenqiuyi on 16/8/3.
 */
public class BitSetTest {
    public static void main(String args[]) {
        BitSet bitSet1 = new BitSet(16);
        BitSet bitSet2 = new BitSet(16);
        for (int i = 0; i < 16; i++) {
            if (i % 2 == 0) {
                bitSet1.set(i);
            }
            if (i % 5 != 0) {
                bitSet2.set(i);
            }
        }
        out.println("Initial pattern in bitSet1");
        out.println(bitSet1);
        out.println("Initial pattern in bitSet2");
        out.println(bitSet2);
//        bitSet2.and(bitSet1);
//        out.println("\nbitSet2 AND bitSet1");
//        out.println(bitSet2);
//        bitSet2.or(bitSet1);
//        out.println("\nbitSet2 OR bitSet1");
//        out.println(bitSet2);
        bitSet2.xor(bitSet1);
        out.println("\nbitSet2 XOR bitSet1");
        out.println(bitSet2);
    }
}
