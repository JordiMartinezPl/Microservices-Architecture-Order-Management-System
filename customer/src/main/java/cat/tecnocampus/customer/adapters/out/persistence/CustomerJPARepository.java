package cat.tecnocampus.customer.adapters.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerJPARepository extends JpaRepository<CustomerEntity, Long> {
    Optional<CustomerEntity> findById(Long id);
}
