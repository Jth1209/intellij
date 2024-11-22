package edu.du.sb1024.controller;

import edu.du.sb1024.entity.*;
import edu.du.sb1024.event.OrderEventPublisher;
import edu.du.sb1024.service.OrdersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpSession;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final OrdersService ordersService;

    private final EntityManagerFactory emf;

    private final OrderEventPublisher orderEventPublisher;

    @GetMapping("/product/main")
    public String productList(@RequestParam(value = "type", required = false, defaultValue = "all") String type, @RequestParam(value = "keyword", required = false, defaultValue = "no") String keyword, Model model, @PageableDefault(page = 0, size = 4) Pageable pageable, HttpSession session) throws Exception {
        String key = keyword.trim();
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        List<OrderDto> list = ordersService.configuration(type, key);
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
        for (OrderDto lis : list) {
            List<OrderFileDto> file = ordersService.selectOrderFileList(lis.getId());
            lis.setFileList(file);
            lis.setPrice(numberFormat.format(Double.parseDouble(lis.getPrice())));//String을 double로 치환하고 다시 String으로 변환
            lis.setCnt(1);
        }
        // 페이지 정보에 따라 현재 페이지의 시작 인덱스를 계산
        final int start = (int) pageable.getOffset();
        // 현재 페이지의 끝 인덱스를 계산하되, 목록 크기를 초과하지 않도록 함
        final int end = Math.min((start + pageable.getPageSize()), list.size());
        // 현재 페이지의 아이템 서브리스트를 포함하는 Page 객체 생성
        final Page<OrderDto> page = new PageImpl<>(list.subList(start, end), pageable, list.size());
        // 페이지 객체를 모델에 추가하여 뷰에서 접근 가능하도록 함
        model.addAttribute("list", page);
        model.addAttribute("uid", authInfo.getId());
//		model.addAttribute("list", list);
        // 게시물 목록을 표시할 뷰 이름 반환
        return "/info/product/orderMain";
    }

    @GetMapping("/product/write")
    public String write() {
        return "/info/product/orderCreate";
    }

    @PostMapping("/product/insert")// write 페이지에서 받은 정보를 저장 orders 테이블에
    public String insert(@RequestParam(value = "type") String type, OrderDto orders, MultipartHttpServletRequest multipartHttpServletRequest, HttpSession session) throws Exception {
        AuthInfo auth = (AuthInfo) session.getAttribute("authInfo");
        orders.setMemberId(auth.getId());
        orders.setType(type);
        ordersService.insertOrder(orders, multipartHttpServletRequest);
        //products랑 shipment에 저장하는 이벤트 추가
        return "redirect:/product/main";
    }

    @GetMapping("/product/delete/{id}")//그냥 처리
    public String delete(@PathVariable("id") Long id) throws Exception {//상품 아이디
        EntityManager em = emf.createEntityManager();
        Order order = em.find(Order.class, id);
        List<Shipment> s = em.createQuery("select s from Shipment s where s.oid = :oid", Shipment.class).setParameter("oid", order.getId()).getResultList();
        if (!s.isEmpty()) {
            orderEventPublisher.deleteAllEvent(order);//상품이 삭제가 되면, 장바구니와 물품 상태 수정 페이지에서 모두 삭제
        }
        ordersService.deleteOrder(id);
        em.close();
        return "redirect:/product/main";
    }

    @PostMapping("/product/save")//이거 그냥 메인 페이지에 수량을 조절할 수 있게 만들어서 메인에서 대부분 처리할 수 있는 간단한 경매장 형식으로 만들기
    public String save(@RequestParam("id") Long id,//상품 아이디
                       @RequestParam("cnt") int cnt,
                       HttpSession session,
                       RedirectAttributes redirectAttributes) throws Exception {
        AuthInfo auth = (AuthInfo) session.getAttribute("authInfo");
        EntityManager em = emf.createEntityManager();
        Order order = em.find(Order.class, id);
        if (order.getQuantity() < cnt) {
            redirectAttributes.addFlashAttribute("msg", "구매하려는 상품의 수량이 부족합니다");
            return "redirect:/product/main";
        }
        em.getTransaction().begin();
        Products p = Products.builder().name(order.getName()).price(order.getPrice() * cnt).des(order.getDes()).status("검토 대기중").type(order.getType()).member(em.find(Member.class, auth.getId())).oid(id).quantity(cnt).build();
        em.persist(p);
        order.setQuantity(order.getQuantity() - cnt);
        orderEventPublisher.userSideEvent(p);
        em.getTransaction().commit();
        em.close();
        return "redirect:/product/main";
    }
}
