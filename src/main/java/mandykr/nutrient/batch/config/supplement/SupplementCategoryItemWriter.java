package mandykr.nutrient.batch.config.supplement;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mandykr.nutrient.batch.domain.SupplementCategory;
import mandykr.nutrient.batch.repository.SupplementCategoryRepository;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class SupplementCategoryItemWriter implements ItemWriter<SupplementCategory> {
    private final SupplementCategoryRepository categoryRepository;

    @Override
    public void write(List<? extends SupplementCategory> items) throws Exception {
        log.info("items : ");
        items.forEach(c -> log.info("item: {}", c.getName()));

        if (items.isEmpty()) {
            return;
        }
        categoryRepository.saveAll(items);
    }
}
