package cat.tecnocampus.customer.application.ports.in;

public interface DeductCreditUseCase {
    void deductCredit(Long id, double amount);

}
