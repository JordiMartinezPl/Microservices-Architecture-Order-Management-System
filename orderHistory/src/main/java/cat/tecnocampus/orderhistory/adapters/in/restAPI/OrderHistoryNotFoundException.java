package cat.tecnocampus.orderhistory.adapters.in.restAPI;

public class OrderHistoryNotFoundException extends RuntimeException {
    public OrderHistoryNotFoundException(String s) {
        super(s);
    }
}
