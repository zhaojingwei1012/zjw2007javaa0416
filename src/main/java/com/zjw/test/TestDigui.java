package com.zjw.test;

import org.junit.Test;

/**
 * @version 1.0
 * @author： 赵静薇
 * @date： 2021-04-09 20:25
 */
public class TestDigui {
    /**
     * 计算阶乘
     */
    //StackOverflowError：栈溢出（内存不够用）   OutOfMemoryError：堆溢出（内存不够用）
    public Long getJiecheng(Long x){
        if(x == 1){
            return 1L;
        }
        return x*getJiecheng(x-1);
    }
    @Test
    public void test(){
        Long jiecheng = getJiecheng(100L);
        System.out.println(jiecheng);
    }



    /**
     * 1 1 2 3 5 8 13 21 ...
     * an = an-1 + an-2
     */
    public Long getFeishi(Long x){
        if(x == 1 || x == 2){
            return 1L;
        }
        return getFeishi(x - 1) + getFeishi(x - 2);
    }
    @Test
    public void test1(){
        Long feishi = getFeishi(5L);
        System.out.println(feishi);
    }
}
