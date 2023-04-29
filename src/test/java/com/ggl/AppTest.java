package com.ggl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        String path = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        System.out.println(path);
    }

    @Test
    public void testStreamReduce() {
        Integer reduce = Stream.of(1,3,5,8,9).reduce(4,(a,b)->{
            return a+b;
        });
        System.out.println(reduce);
    }
    @Test
    public void testComparator() {
        int[] arr = { 5, 9, 3, 6, 7 };
        ArrayList<Integer> arrayList = new ArrayList();
        arrayList.addAll(Arrays.stream(arr).collect(() -> new ArrayList<>(), (list, i) -> {
            list.add(i);
        }, (a1, a2) -> a1.addAll(a2)));
        arrayList.sort((i, j) -> {
            return i - j;
        });
        System.out.println(arrayList);
    }
    @Test
    public void accumulate() {
        int i=5;
        int[][] mx=new int[5][5];
        int t=0;
        for(int column=0;column<5;){
            for(int row=0;row<5;){
                if(row>=column){
                mx[row][column]=t+1;
                ++t;
                }
                ++row;
            }
            ++column;
        }
        for (int column = 0; column < 5;) {
            for (int row = 0; row < 5;) {
                if(column>=row){
                System.out.print(mx[column][row]);
                }
                ++row;
            }
            System.out.print("\r\n");
            ++column;
        }
    }

}
