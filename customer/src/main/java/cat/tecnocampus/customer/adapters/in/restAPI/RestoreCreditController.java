package cat.tecnocampus.customer.adapters.in.restAPI;

import cat.tecnocampus.customer.application.ports.in.RestoreCreditUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestoreCreditController {

    private final RestoreCreditUseCase restoreCreditUseCase;

    public RestoreCreditController(RestoreCreditUseCase restoreCreditUseCase){
        this.restoreCreditUseCase = restoreCreditUseCase;
    }

    @PostMapping("/customers/{id}/restore/{amount}")
    public ResponseEntity<String> restoreCredit(@PathVariable Long id, @PathVariable double amount) {
        restoreCreditUseCase.restoreCredit(id, amount);
        return ResponseEntity.ok("Customer credit restored");
    }
}
