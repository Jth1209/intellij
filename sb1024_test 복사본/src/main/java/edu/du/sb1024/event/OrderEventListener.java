package edu.du.sb1024.event;

import edu.du.sb1024.entity.Order;
import edu.du.sb1024.entity.Shipment;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

@Configuration
@RequiredArgsConstructor
public class OrderEventListener {
    final EntityManagerFactory emf;

    @EventListener
    public void handleOrderEvent(OrderEvent orderEvent) {
        Order order = orderEvent.getOrder();
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Shipment sm = Shipment.builder().oid(order.getId()).name(order.getName()).quantity(order.getQuantity()).price(order.getPrice()).des(order.getDes()).member(order.getMember()).status(order.getStatus()).build();
        em.persist(sm);//사용자가 요청한 물건의 검토 상태를 확인할 수 있도록 하기 위해서 관리자가 검토 상태를 업데이트하면 사용자가 즉시 확인할 수 있도록 하는 상태변경 이벤트도 추가해야함.

        em.getTransaction().commit();
        em.close();
    }
    @EventListener//사용자를 통해 아이템 삭제 시, 관리자 페이지에도 삭제되는 것.
    public void deleteOrderEvent(OrderEvent2 orderEvent) {
        Order order = orderEvent.getOrder();
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Shipment sm = em.createQuery("select s from Shipment s where s.id = :oid",Shipment.class).setParameter("oid",order.getId()).getSingleResult();

        em.remove(sm);

        em.getTransaction().commit();
        em.close();
    }

    @EventListener//관리자를 통해 아이템 삭제 시, 사용자의 주문 또한 삭제되는 것.
    public void deleteShipmentEvent(ShipmentEvent shipmentEvent) {
        Shipment sm = shipmentEvent.getShipment();
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Order o = em.find(Order.class, sm.getOid());

        em.remove(o);
        em.getTransaction().commit();
        em.close();
    }

    @EventListener
    public void changeStatusEvent(ShipmentEvent2 shipmentEvent) {
        Shipment sm = shipmentEvent.getShipment();
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Order o = em.find(Order.class, sm.getOid());
        o.setStatus(sm.getStatus());
        em.getTransaction().commit();
        em.close();
    }
}
