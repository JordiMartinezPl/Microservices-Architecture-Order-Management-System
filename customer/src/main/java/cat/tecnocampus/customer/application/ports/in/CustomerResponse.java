package cat.tecnocampus.customer.application.ports.in;

public record CustomerResponse(String name, String email, String address, double creditBalance) {
}
