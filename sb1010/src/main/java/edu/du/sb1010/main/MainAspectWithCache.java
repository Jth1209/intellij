package edu.du.sb1010.main;

import edu.du.sb1010.chap07.Calculator;
import edu.du.sb1010.config.CacheAppCtx;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainAspectWithCache {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(CacheAppCtx.class);

        Calculator cal = ctx.getBean("calculator",Calculator.class);
        cal.factorial(7);
        cal.factorial(7);
        cal.factorial(5);
        cal.factorial(5);
        ctx.close();
    }
}
