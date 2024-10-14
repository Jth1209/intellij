package edu.du.sb1010.aop2;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

//@Component
public class MorningGreet implements Greet{

    @Override
    public void greeting() {
        System.out.println("---------------------------");

        System.out.println("good Morning~");

        System.out.println("----------------------------");
    }
}
