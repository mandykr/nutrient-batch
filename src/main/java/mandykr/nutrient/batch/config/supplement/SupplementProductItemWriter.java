package mandykr.nutrient.batch.config.supplement;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mandykr.nutrient.batch.dto.openapi.foodsafety.SupplementProductResponse;
import mandykr.nutrient.batch.domain.Supplement;
import mandykr.nutrient.batch.repository.SupplementRepository;
import org.springframework.batch.item.database.JpaItemWriter;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public class SupplementProductItemWriter extends JpaItemWriter<SupplementProductResponse> {
    private final SupplementRepository supplementRepository;

    @Override
    public void write(List<? extends SupplementProductResponse> items) {
        log.info("SupplementProductResponse : ");
        items.forEach(s -> log.info("item: {}", s.getPrdlstNm()));

        if (items.isEmpty()) {
            return;
        }

        List<Supplement> result = items.stream()
                .distinct()
                .map(SupplementProductResponse::createSupplement)
                .collect(Collectors.toList());
        supplementRepository.saveAll(result);
    }
}
