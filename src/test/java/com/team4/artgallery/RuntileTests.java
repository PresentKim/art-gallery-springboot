package com.team4.artgallery;

import com.team4.artgallery.util.Assert;
import org.junit.jupiter.api.Test;

import java.util.List;

class RuntileTests {


    @Test
    void test() throws Exception {
        int a = 0;
        System.out.println("int a = 0; " + Assert.checkIsEmpty(a));
        a = 1;
        System.out.println("int a = 1; " + Assert.checkIsEmpty(a));

        byte b = 0;
        System.out.println("byte b = 0; " + Assert.checkIsEmpty(b));
        b = 1;
        System.out.println("byte b = 1; " + Assert.checkIsEmpty(b));

        short s = 0;
        System.out.println("short s = 0; " + Assert.checkIsEmpty(s));
        s = 1;
        System.out.println("short s = 1; " + Assert.checkIsEmpty(s));

        long l = 0;
        System.out.println("long l = 0; " + Assert.checkIsEmpty(l));
        l = 1;
        System.out.println("long l = 1; " + Assert.checkIsEmpty(l));


        float f = 0;
        System.out.println("float f = 0; " + Assert.checkIsEmpty(f));
        f = 1;
        System.out.println("float f = 1; " + Assert.checkIsEmpty(f));

        double d = 0;
        System.out.println("double d = 0; " + Assert.checkIsEmpty(d));
        d = 1;
        System.out.println("double d = 1; " + Assert.checkIsEmpty(d));

        String str = "";
        System.out.println("String str = \"\"; " + Assert.checkIsEmpty(str));
        str = "a";
        System.out.println("String str = \"a\"; " + Assert.checkIsEmpty(str));

        List<Integer> list = List.of();
        System.out.println("List<Integer> list = List.of(); " + Assert.checkIsEmpty(list));
        list = List.of(1);
        System.out.println("List<Integer> list = List.of(1); " + Assert.checkIsEmpty(list));

        int[] arr = new int[0];
        System.out.println("int[] arr = new int[0]; " + Assert.checkIsEmpty(arr));
        arr = new int[1];
        System.out.println("int[] arr = new int[1]; " + Assert.checkIsEmpty(arr));

        byte[] barr = new byte[0];
        System.out.println("byte[] barr = new byte[0]; " + Assert.checkIsEmpty(barr));
        barr = new byte[1];
        System.out.println("byte[] barr = new byte[1]; " + Assert.checkIsEmpty(barr));
    }

}
