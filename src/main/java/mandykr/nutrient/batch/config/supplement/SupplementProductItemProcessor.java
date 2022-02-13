package mandykr.nutrient.batch.config.supplement;

import lombok.RequiredArgsConstructor;
import mandykr.nutrient.batch.domain.Supplement;
import mandykr.nutrient.batch.openapi.dto.foodsafety.SupplementProductResponse;
import mandykr.nutrient.batch.repository.SupplementRepository;
import org.springframework.batch.item.ItemProcessor;

@RequiredArgsConstructor
public class SupplementProductItemProcessor implements ItemProcessor<SupplementProductResponse, Supplement> {
    private final SupplementRepository supplementRepository;

    @Override
    public Supplement process(SupplementProductResponse item) throws Exception {
        return supplementRepository.findByPrdlstReportNo(item.getPrdlstReportNo()).orElse(null);
    }
}
