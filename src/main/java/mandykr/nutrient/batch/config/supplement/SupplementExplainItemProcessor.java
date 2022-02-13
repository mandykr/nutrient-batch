package mandykr.nutrient.batch.config.supplement;

import lombok.RequiredArgsConstructor;
import mandykr.nutrient.batch.domain.Supplement;
import mandykr.nutrient.batch.openapi.dto.foodsafety.SupplementExplainResponse;
import mandykr.nutrient.batch.repository.SupplementRepository;
import org.springframework.batch.item.ItemProcessor;

import java.util.Optional;

@RequiredArgsConstructor
public class SupplementExplainItemProcessor implements ItemProcessor<SupplementExplainResponse, Supplement> {
    private final SupplementRepository supplementRepository;

    @Override
    public Supplement process(SupplementExplainResponse item) throws Exception {
        Supplement supplement = null;
        Optional<Supplement> findSupplement = supplementRepository.findByPrdlstReportNo(item.getPrdlstReportNo());
        if (findSupplement.isPresent()) {
            supplement = SupplementExplainResponse.createSupplement(item);
        }
        return supplement;
    }
}
