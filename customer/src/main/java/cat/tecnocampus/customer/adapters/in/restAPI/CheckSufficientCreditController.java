package cat.tecnocampus.customer.adapters.in.restAPI;

import cat.tecnocampus.customer.application.ports.in.CheckSufficientCreditUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class CheckSufficientCreditController {
    private final CheckSufficientCreditUseCase checkSufficientCreditUseCase;

    public CheckSufficientCreditController(CheckSufficientCreditUseCase checkSufficientCreditUseCase){
        this.checkSufficientCreditUseCase = checkSufficientCreditUseCase;
    }

    @GetMapping("/customers/{id}/credit/{amount}")
        public ResponseEntity<Boolean> hasSufficientCredit(@PathVariable Long id, @PathVariable double amount) {
            boolean hasCredit = checkSufficientCreditUseCase.hasSufficientCredit(id, amount);
            return ResponseEntity.ok(hasCredit);
        }

}
