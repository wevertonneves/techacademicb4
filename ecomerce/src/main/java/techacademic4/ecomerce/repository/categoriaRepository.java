package techacademic4.ecomerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import techacademic4.ecomerce.Domain.categoria;


public interface categoriaRepository extends JpaRepository<categoria, Long> {
}
