package edu.du.sb1010.aop2;

import org.springframework.stereotype.Component;

@Component
public class EveningGreet implements Greet {
    @Override
    public void greeting() {
        System.out.println("---------------------------");

        System.out.println("good Evening~");

        System.out.println("----------------------------");
    }
}
