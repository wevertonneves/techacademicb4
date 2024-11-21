package techacademic4.ecomerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import techacademic4.ecomerce.Domain.produtos;

public interface produtosRepository  extends JpaRepository<produtos, Long> {
}
