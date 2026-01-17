package cat.tecnocampus.order.adapters.in.restAPI.exceptions;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(long id) {
        super("Order with id: " + id + " not found");
    }

    public OrderNotFoundException(String s) {
        super("Could not find : " + s);
    }

}
