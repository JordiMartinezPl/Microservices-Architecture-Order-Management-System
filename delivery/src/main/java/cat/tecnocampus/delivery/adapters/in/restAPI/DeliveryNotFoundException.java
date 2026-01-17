package cat.tecnocampus.delivery.adapters.in.restAPI;

public class DeliveryNotFoundException extends RuntimeException {
    public DeliveryNotFoundException(long id) {
        super("Delivery with order " + id + " not found");
    }
}
