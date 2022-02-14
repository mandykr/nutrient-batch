package mandykr.nutrient.batch.repository;

import mandykr.nutrient.batch.domain.SupplementCategoryBase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SupplementCategoryBaseRepository extends JpaRepository<SupplementCategoryBase, Long> {
    Optional<SupplementCategoryBase> findByName(String name);
}
