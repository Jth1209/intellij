package edu.du.passwordencodingtest;

import edu.du.passwordencodingtest.encoder.PasswordEncoder;
import groovy.util.logging.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class PasswordEncodingTestApplicationTests {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void contextLoads() {
    }

    @Test
    void encode() {
        String id = "test@aaa.com";
        String password= "1234";
        String encrypt = passwordEncoder.encrypt(id, password);
        System.out.println(encrypt);
    }

}
