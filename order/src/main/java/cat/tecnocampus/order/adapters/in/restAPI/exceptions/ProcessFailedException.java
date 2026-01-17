package cat.tecnocampus.order.adapters.in.restAPI.exceptions;

public class ProcessFailedException extends RuntimeException {
    public ProcessFailedException(String s) {
        super("Process failed: " + s);
    }
}
