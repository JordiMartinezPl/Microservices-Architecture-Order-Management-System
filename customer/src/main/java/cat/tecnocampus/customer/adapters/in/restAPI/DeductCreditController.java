package cat.tecnocampus.customer.adapters.in.restAPI;

import cat.tecnocampus.customer.application.ports.in.DeductCreditUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeductCreditController {

    DeductCreditUseCase deductCreditService;

    public DeductCreditController(DeductCreditUseCase deductCreditService){
        this.deductCreditService=deductCreditService;
    }

    @PostMapping("customers/{id}/deduct/{amount}")
    public ResponseEntity<String> deductCredit(@PathVariable Long id, @PathVariable double amount) {
        deductCreditService.deductCredit(id, amount);
        return ResponseEntity.ok("Customer credit has been deducted");
    }
}
