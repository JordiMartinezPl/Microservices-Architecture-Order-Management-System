package cat.tecnocampus.product.adapters.out.persistence;

import cat.tecnocampus.product.model.Product;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ProductRepository implements cat.tecnocampus.product.application.ports.out.ProductRepository {

    private final ProductJPARepository productJPARepository;

    public ProductRepository(ProductJPARepository productJPARepository) {
        this.productJPARepository = productJPARepository;
    }

    @Override
    public Product save(Product product) {
        ProductEntity entity = productJPARepository.save(ProductMapper.toEntity(product));
        return ProductMapper.toDomain(entity);

    }

    @Override
    public Optional<Product> findById(Long id) {
        return productJPARepository.findById(id).map(ProductMapper::toDomain);
    }

}