package edu.du.sb1010;

import edu.du.sb1010.aop2.Greet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Sb1010Application {

    @Autowired
    Greet greet;

    public void execute(){
        greet.greeting();
    }

    public static void main(String[] args) {

        SpringApplication.run(Sb1010Application.class, args).getBean(Sb1010Application.class).execute();
    }

}
