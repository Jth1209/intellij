package edu.du.sb1024.event;

import edu.du.sb1024.entity.Shipment;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class ShipmentEvent2 extends ApplicationEvent {

    private Shipment shipment;

    public ShipmentEvent2(Object source, Shipment shipment) {
        super(source);
        this.shipment = shipment;
    }
}
