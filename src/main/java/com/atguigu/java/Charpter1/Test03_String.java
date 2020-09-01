package com.atguigu.java.Charpter1;

import java.lang.reflect.Field;

/**
 * @author Shelly An
 * @create 2020/7/23 17:22
 */
public class Test03_String {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        String s = " a b ";

        /**
         * 通过反射改变s的内容
         * 以此说明，string不可变的意思是，无法改变内存地址值，而非内容
         */

        Class<? extends String> aClass = s.getClass();
        Field f = aClass.getDeclaredField("value");

        f.setAccessible(true);
        char[] cs = (char[]) f.get(s);
        cs[2] = 'd';

        System.out.println(s);


    }
}
