package edu.du.sb1024.controller;

import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import javax.persistence.EntityManagerFactory;

@Controller
@Slf4j
@RequiredArgsConstructor
public class CommentController {
    final private EntityManagerFactory emf;

//    @PostMapping("/comment")
//    public String comment(){
//
//    }
}
