package edu.du.sb1024.event;

import edu.du.sb1024.entity.Order;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class ProductEvent extends ApplicationEvent {

    private Order order;

    public ProductEvent(Object source , Order order) {
        super(source);
        this.order = order;
    }
}
