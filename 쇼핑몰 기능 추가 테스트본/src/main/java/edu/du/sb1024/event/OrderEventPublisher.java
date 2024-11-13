package edu.du.sb1024.event;

import edu.du.sb1024.entity.Order;
import edu.du.sb1024.entity.Products;
import edu.du.sb1024.entity.Shipment;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class OrderEventPublisher {

    final ApplicationEventPublisher publisher;

    public void userSideEvent(final Products products) {
        OrderEvent oe = new OrderEvent(this,products);
        publisher.publishEvent(oe);
    }
    public void userSideEvent2(final Products products){
        OrderEvent2 oe = new OrderEvent2(this,products);
        publisher.publishEvent(oe);
    }
    public void adminSideEvent(final Shipment shipment){
        ShipmentEvent se = new ShipmentEvent(this,shipment);
        publisher.publishEvent(se);
    }//추가로 로그인 한 유저의 role이 "ADMIN"일 경우, 직접 사용자를 삭제할 수 있는 페이지를 만들어야 함.
    public void adminSideEvent2(final Shipment shipment){
        ShipmentEvent2 se = new ShipmentEvent2(this,shipment);
        publisher.publishEvent(se);
    }
    public void deleteAllEvent(final Order order){
        ProductEvent pe = new ProductEvent(this,order);
        publisher.publishEvent(pe);
    }
}
