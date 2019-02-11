package com.example.countbunny.launchmodetest1;


import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    static int B = 1;

    static {
        B = 2;
        System.out.print(B);
    }

    interface Interface0 {
        int A = 0;
    }

    interface Interface1 extends Interface0 {
        int A = 1;
    }

    interface Interface2 {
        int A = 2;
    }

    static class Parent implements Interface1 {
        public static final int A = 3;

    }

    static class Stub implements Interface2 {
        public static final int A = 4;
    }

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
//        System.out.print(Stub.A);
    }

    @Test
    public void whatEqualA() {
        assertTrue(Stub.A == 4);
    }

    @Test
    public void autoPackage() {
        Integer a = 1;
        Integer b = 2;
        Integer c = 3;
        Integer d = 3;
        Integer e = 321;
        Integer f = 321;
        Long g = 3L;
        System.out.println(" c == d :" + (c == d));
        System.out.println(" e == f :" + (e == f));
        System.out.println(" c == (a + b) :" + (c == (a + b)));
        System.out.println(" c.equals(a + b) :" + (c.equals(a + b)));
        System.out.println(" g == a + b :" + (g == a + b));
        System.out.println(" g.equals(a + b) :" + (g.equals(a + b)));
    }
}