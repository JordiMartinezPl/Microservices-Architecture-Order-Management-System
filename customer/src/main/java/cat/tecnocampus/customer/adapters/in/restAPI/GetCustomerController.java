package cat.tecnocampus.customer.adapters.in.restAPI;

import cat.tecnocampus.customer.application.ports.in.CustomerResponse;
import cat.tecnocampus.customer.application.ports.in.GetCustomerUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class GetCustomerController {

    private final GetCustomerUseCase getCustomerService;

    public GetCustomerController(GetCustomerUseCase getCustomerUseCase){
        this.getCustomerService=getCustomerUseCase;
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<CustomerResponse> getCustomer(@PathVariable Long id) {
        return getCustomerService.getCustomer(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
