package cat.tecnocampus.order.adapters.in.restAPI.exceptions;

public class DeliveryReserveException extends RuntimeException {
    public DeliveryReserveException(String s) {
        super("Could not process the delivery reservation: " + s);
    }
}
