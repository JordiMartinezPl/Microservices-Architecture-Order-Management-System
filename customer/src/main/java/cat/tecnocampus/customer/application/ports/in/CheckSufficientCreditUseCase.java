package cat.tecnocampus.customer.application.ports.in;

public interface CheckSufficientCreditUseCase {
    boolean hasSufficientCredit(Long id, double amount);
}
