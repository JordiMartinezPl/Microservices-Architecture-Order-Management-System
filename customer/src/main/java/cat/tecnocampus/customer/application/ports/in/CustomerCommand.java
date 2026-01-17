package cat.tecnocampus.customer.application.ports.in;

public record CustomerCommand(String name, String email, String address) {
}