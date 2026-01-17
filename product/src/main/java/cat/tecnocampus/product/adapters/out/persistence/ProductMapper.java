package cat.tecnocampus.product.adapters.out.persistence;


import cat.tecnocampus.product.model.Product;

public class ProductMapper {

    public static ProductEntity toEntity(Product product) {
        if (product == null) {
            return null;
        }
        ProductEntity entity = new ProductEntity();
        entity.setId(product.getId());
        entity.setName(product.getName());
        entity.setDescription(product.getDescription());
        entity.setPrice(product.getPrice());
        entity.setStock(product.getStock());
        entity.setStatus(product.getStatus());
        return entity;
    }

    public static Product toDomain(ProductEntity entity) {
        if (entity == null) {
            return null;
        }
        Product product = new Product();
        product.setId(entity.getId());
        product.setName(entity.getName());
        product.setDescription(entity.getDescription());
        product.setPrice(entity.getPrice());
        product.setStock(entity.getStock());
        product.setStatus(Product.Status.valueOf(entity.getStatus().name()));
        return product;
    }
}

