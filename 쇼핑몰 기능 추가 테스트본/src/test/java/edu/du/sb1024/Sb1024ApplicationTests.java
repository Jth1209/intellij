package edu.du.sb1024;

import edu.du.sb1024.entity.Comment;
import edu.du.sb1024.entity.Order;
import edu.du.sb1024.entity.OrderDto;
import edu.du.sb1024.service.OrdersService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

@SpringBootTest
class Sb1024ApplicationTests {

    @Autowired
    private EntityManagerFactory emf;
    @Autowired
    private OrdersService ordersService;

    @Test
    void contextLoads() {
    }

    @Test
    void test() throws Exception {
        List<OrderDto> os = ordersService.selectOrderList();
        if(os.isEmpty()) {
            System.out.println("출력에는 이상이 없습니다.");
        }
    }
}
