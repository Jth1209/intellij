package edu.du.sb1014;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
class Sb1014ApplicationTests {

    @Autowired
    MemoRepository memoRepository;

    @Test
    void contextLoads() {
        log.info("Just Log");
    }

    @Test
    void insert(){
        IntStream.range(1,11).forEach(i->{
            Memo memo = Memo.builder().text(i+"번째").build();
            memoRepository.save(memo);
        });
    }

    @Test
    void 테스트_다찾아오기(){
        insert();
        List<Memo> memos = memoRepository.findAll();
        for(Memo memo : memos) {
            System.out.println(memo);
        }
    }

    @Test
    void findById(){
        Memo memo = memoRepository.findById(3);
        log.info(memo.getText());
    }

    @Test
    void 셀렉트해오기(){
        List<Memo> memos = memoRepository.selectById();
        for(Memo memo : memos) {
            System.out.println(memo);
        }
        System.out.println("종료");
    }
}
