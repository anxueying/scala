package day03;

import java.util.Random;

/**
 * @author Shelly An
 * @create 2020/8/2 1:57
 */
public class Spark02_RDD_Random {
    public static void main(String[] args) {
        Random r1 = new Random(10);
        for (int i = 0; i < 5; i++) {
            System.out.println(r1.nextInt(10));
        }

        System.out.println("-------------------");

        Random r2 = new Random(10);
        for (int i = 0; i < 5; i++) {
            System.out.println(r2.nextInt(10));
        }
    }
}
