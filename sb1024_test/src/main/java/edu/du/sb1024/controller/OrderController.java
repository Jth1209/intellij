package edu.du.sb1024.controller;

import edu.du.sb1024.entity.Order;
import edu.du.sb1024.event.OrderEventPublisher;
import edu.du.sb1024.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/orders")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderEventPublisher orderEventPublisher;

    @Autowired
    private EntityManagerFactory emf;

    @PostMapping
    @ResponseBody
    public Order createOrder(@RequestBody Order order) {

        return orderService.saveOrder(order);
    }

    @GetMapping
    public String getAllOrders(Model model) {
        List<Order> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "/info/order/order";
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Optional<Order> getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }

    @GetMapping("/new")
    public String newOrderForm(Model model) {
        model.addAttribute("order", new Order());
        return "/info/order/item";
    }

    @PostMapping("/save")
    public String saveOrder(@ModelAttribute Order order) {
        log.info("Order created: " + order);
        Order orders = orderService.saveOrder(order);
        orderEventPublisher.userSideEvent(orders);//이걸 써주는 곳에서 OrderEventListener에 있는 코드가 실행된다.
        return "redirect:/orders";
    }

    @GetMapping("/delete/{id}")
    public String deleteOrder1(@PathVariable Long id) {
        EntityManager em = emf.createEntityManager();
        Order order = em.find(Order.class, id);
        orderEventPublisher.userSideEvent2(order);
        em.close();
        orderService.deleteOrder(id);
        return "redirect:/orders";
    }
}