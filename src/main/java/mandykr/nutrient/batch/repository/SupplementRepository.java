package mandykr.nutrient.batch.repository;

import mandykr.nutrient.batch.domain.Supplement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SupplementRepository extends JpaRepository<Supplement, Long> {
    Optional<Supplement> findByPrdlstReportNo(String prdlstReportNo);
}
