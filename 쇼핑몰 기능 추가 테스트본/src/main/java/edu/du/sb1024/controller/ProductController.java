package edu.du.sb1024.controller;

import edu.du.sb1024.entity.*;
import edu.du.sb1024.service.OrdersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final OrdersService ordersService;

    private final EntityManagerFactory emf;

    @GetMapping("/product/main")
    public String productList(@RequestParam(value = "type" , required = false , defaultValue = "all") String type, @RequestParam(value = "keyword",required = false , defaultValue = "no") String keyword, Model model, @PageableDefault(page = 0, size = 8) Pageable pageable) throws Exception {
        String key = keyword.trim();
        List<OrderDto> list = ordersService.configuration(type,key);
        // 페이지 정보에 따라 현재 페이지의 시작 인덱스를 계산
        final int start = (int) pageable.getOffset();
        // 현재 페이지의 끝 인덱스를 계산하되, 목록 크기를 초과하지 않도록 함
        final int end = Math.min((start + pageable.getPageSize()), list.size());
        // 현재 페이지의 아이템 서브리스트를 포함하는 Page 객체 생성
        final Page<OrderDto> page = new PageImpl<>(list.subList(start, end), pageable, list.size());
        // 페이지 객체를 모델에 추가하여 뷰에서 접근 가능하도록 함
        model.addAttribute("list", page);
//		model.addAttribute("list", list);
        // 게시물 목록을 표시할 뷰 이름 반환
        return "/product/main";
    }

    @GetMapping("/product/write")
    public String write() {
        return "/product/write";
    }
    @GetMapping("/product/insert")// write 페이지에서 받은 정보를 저장 orders 테이블에
    public String insert(@RequestParam(value = "type") String type, OrderDto orders, MultipartHttpServletRequest multipartHttpServletRequest, HttpSession session) throws Exception {
        AuthInfo auth = (AuthInfo) session.getAttribute("authInfo");
        orders.setMid(auth.getId());
        orders.setType(type);
        ordersService.insertOrder(orders, multipartHttpServletRequest);
        return "redirect:/product/main";
    }
    @GetMapping("/product/update/{id}")//모달 처리
    public String update(@PathVariable("id") String id, Model model){
        EntityManager em = emf.createEntityManager();
        Order order = em.find(Order.class,id);
        model.addAttribute("order",order);
        em.close();
        return "/product/update";
    }
    @GetMapping("/product/delete/{id}")//그냥 처리
    public String delete(@PathVariable("id") int id) throws Exception {
        ordersService.deleteOrder(id);
        return "redirect:/product/main";
    }

    @PostMapping("/product/save/{id}")//이거 그냥 메인 페이지에 수량을 조절할 수 있게 만들어서 메인에서 대부분 처리할 수 있는 간단한 경매장 형식으로 만들기
    public String save(@PathVariable("id") Long id,
                       HttpSession session,
                       @RequestParam("cnt") int count,
                       RedirectAttributes redirectAttributes) throws Exception {
        AuthInfo auth = (AuthInfo) session.getAttribute("authInfo");
        EntityManager em = emf.createEntityManager();
        Order order = em.find(Order.class,id);
        if(order.getQuantity() < count){
            redirectAttributes.addFlashAttribute("msg", "구매하려는 상품의 수량이 부족합니다");
            return "redirect:/product/main";
        }
        em.getTransaction().begin();
        order.setQuantity(order.getQuantity() - count);
        Products p = Products.builder().name(order.getName()).price(order.getPrice() * count).des(order.getDes()).status("검토 대기중").type(order.getType()).member(em.find(Member.class,auth.getId())).oid(id).build();
        em.persist(p);
        em.getTransaction().commit();
        em.close();
        return "redirect:/product/main";
    }
}
