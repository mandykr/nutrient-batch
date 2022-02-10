package mandykr.nutrient.batch.repository;

import mandykr.nutrient.batch.domain.Supplement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplementRepository extends JpaRepository<Supplement, Long> {
}
