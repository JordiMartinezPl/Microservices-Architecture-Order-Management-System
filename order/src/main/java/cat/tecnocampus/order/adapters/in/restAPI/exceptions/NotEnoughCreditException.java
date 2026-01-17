package cat.tecnocampus.order.adapters.in.restAPI.exceptions;

public class NotEnoughCreditException extends RuntimeException {
    public NotEnoughCreditException() {
        super("Not enough credit");
    }
}
