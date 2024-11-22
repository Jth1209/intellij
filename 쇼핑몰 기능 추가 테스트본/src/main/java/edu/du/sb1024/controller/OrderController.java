package edu.du.sb1024.controller;

import edu.du.sb1024.entity.AuthInfo;
import edu.du.sb1024.entity.Member;
import edu.du.sb1024.entity.Order;
import edu.du.sb1024.entity.Products;
import edu.du.sb1024.event.OrderEventPublisher;
import edu.du.sb1024.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpSession;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
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
    public Products createOrder(@RequestBody Products products) {

        return orderService.saveOrder(products);
    }

    @GetMapping
    public String getAllOrders(Model model,HttpSession session) {
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        EntityManager em = emf.createEntityManager();
        Member member = em.find(Member.class,authInfo.getId());
        List<Products> orders = em.createQuery("select p from Products p where p.member = :member",Products.class).setParameter("member", member).getResultList();
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
        model.addAttribute("orders", orders);
        model.addAttribute("id",authInfo.getId());
        return "/info/order/order";
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Optional<Products> getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }

    @GetMapping("/delete/{id}")
    public String deleteOrder1(@PathVariable Long id) {
        EntityManager em = emf.createEntityManager();
        Products order = em.find(Products.class, id);
        orderEventPublisher.userSideEvent2(order);
        em.close();
        orderService.deleteOrder(id);
        return "redirect:/orders";
    }
}