package mandykr.nutrient.batch.repository;

import mandykr.nutrient.batch.domain.SupplementCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SupplementCategoryRepository extends JpaRepository<SupplementCategory, Long> {
    Optional<SupplementCategory> findByName(String name);
}
