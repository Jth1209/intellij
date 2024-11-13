package edu.du.sb1024.event;

import edu.du.sb1024.entity.Order;
import edu.du.sb1024.entity.Products;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class OrderEvent extends ApplicationEvent {

    private Products products;

    public OrderEvent(Object source,Products products) {
        super(source);
        this.products = products;
    }
}
