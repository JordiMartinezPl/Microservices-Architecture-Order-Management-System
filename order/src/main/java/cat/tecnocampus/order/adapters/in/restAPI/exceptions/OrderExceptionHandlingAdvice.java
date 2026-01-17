package cat.tecnocampus.order.adapters.in.restAPI.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class OrderExceptionHandlingAdvice {

    @ExceptionHandler({OrderNotFoundException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String objectAlreadyExists(Exception exception) {
        return exception.getMessage();
    }

    @ExceptionHandler({NotEnoughCreditException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String customerDoesNotEnoughCredit(Exception exception) {
        return exception.getMessage();
    }

    @ExceptionHandler({DeliveryReserveException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String DeliveryReserveFailed(Exception exception) {
        return exception.getMessage();
    }

    @ExceptionHandler({StockReservationException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String notEnoughStock(Exception exception) {
        return exception.getMessage();
    }

    @ExceptionHandler({IllegalArgumentException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String customerIllegalArgument(Exception exception) {
        return exception.getMessage();
    }

    @ExceptionHandler({CannotCancelException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String cannotCancelDelivery(Exception exception) {
        return exception.getMessage();
    }


}
