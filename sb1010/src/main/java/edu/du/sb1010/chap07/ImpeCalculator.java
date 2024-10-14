package edu.du.sb1010.chap07;

public class ImpeCalculator implements Calculator {

    @Override
    public long factorial(long num) {
        long result = 1;
        for (int i = 1; i <= num; i++) {
            result = num * i;
        }
        return result;
    }
}
