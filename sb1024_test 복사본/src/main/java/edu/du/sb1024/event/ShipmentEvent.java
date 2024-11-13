package edu.du.sb1024.event;

import edu.du.sb1024.entity.Shipment;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class ShipmentEvent extends ApplicationEvent {

    private Shipment shipment;

    public ShipmentEvent(Object source,Shipment shipment) {
        super(source);
        this.shipment = shipment;
    }
}
