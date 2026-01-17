package cat.tecnocampus.orderhistory.adapters.in.restAPI;

import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Primary
@ControllerAdvice
public class OrderHistoryExceptionHandlingAdvice {


    @ExceptionHandler({OrderHistoryNotFoundException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleOrderHistoryNotFound(OrderHistoryNotFoundException ex) {
        return ex.getMessage();
    }


}
