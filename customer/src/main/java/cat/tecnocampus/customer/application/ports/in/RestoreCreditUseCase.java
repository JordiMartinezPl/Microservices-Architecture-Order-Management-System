package cat.tecnocampus.customer.application.ports.in;

public interface RestoreCreditUseCase {
    void restoreCredit(Long id, double amount);

}
