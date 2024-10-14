package edu.du.sb1010.chap07;

public class ExeTimeCalculator implements Calculator{
    private Calculator delegate;

    public ExeTimeCalculator(Calculator delegate) {
        this.delegate=delegate;
    }

    @Override
    public long factorial(long num) {
        Long start = System.nanoTime();
        long result = delegate.factorial(num);
        Long end = System.nanoTime();
        return result;
    }
}
