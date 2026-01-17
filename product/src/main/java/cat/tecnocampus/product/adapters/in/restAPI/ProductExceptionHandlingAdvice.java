package cat.tecnocampus.product.adapters.in.restAPI;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ProductExceptionHandlingAdvice {

    @ExceptionHandler({ProductNotFoundException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String productNotFoundException(Exception exception) {
        return exception.getMessage();
    }

    @ExceptionHandler({IllegalArgumentException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String productIllegalArgumentException(Exception exception) {
        return exception.getMessage();
    }

    @ExceptionHandler({ProductReserveException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String productReserveException(Exception exception) {
        return exception.getMessage();
    }

}
