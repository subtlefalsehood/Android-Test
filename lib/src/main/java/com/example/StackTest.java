package com.example;

import java.util.EmptyStackException;
import java.util.Stack;

import static java.lang.System.out;

/**
 * Created by chenqiuyi on 16/8/4.
 */
public class StackTest {
    static void showPop(Stack st) {
        Integer a = (Integer) st.pop();
        out.println("stack pop->" + a);
        out.println("stack:" + st);
    }

    static void showPush(Stack st, Integer a) {
        st.push(a);
        out.println("stack push<-" + a);
        out.println("stack:" + st);
    }

    public static void main(String a[]) {
        Stack stack = new Stack();
        showPush(stack, 12);
        showPush(stack, 49);
        showPush(stack, 59);
        showPop(stack);
        showPop(stack);
        showPop(stack);
        try {
            showPop(stack);
        } catch (EmptyStackException e) {
            e.printStackTrace();
        }
    }
}
