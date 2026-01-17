package cat.tecnocampus.product.application.ports.in;

public class ProductPriceResponse {
    private double price;

    public ProductPriceResponse() {
    }

    public ProductPriceResponse(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
