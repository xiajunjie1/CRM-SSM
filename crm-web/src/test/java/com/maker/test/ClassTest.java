package com.maker.test;

import org.junit.jupiter.api.Test;

public class ClassTest {
    @Test
    public void getArrayClassTest(){
        String[] strs=new String[]{"test"};
        System.err.println(strs.getClass().getName());
    }
    @Test
    public void getSuffix(){
        String filename="text.xlsx";
        String suffix=filename.substring(filename.lastIndexOf(".")+1);
        System.err.println(suffix);
    }
}
