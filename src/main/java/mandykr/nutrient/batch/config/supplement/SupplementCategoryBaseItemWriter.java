package mandykr.nutrient.batch.config.supplement;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mandykr.nutrient.batch.domain.SupplementCategory;
import mandykr.nutrient.batch.domain.SupplementCategoryBase;
import mandykr.nutrient.batch.repository.SupplementCategoryBaseRepository;
import mandykr.nutrient.batch.repository.SupplementCategoryRepository;
import org.springframework.batch.item.ItemWriter;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public class SupplementCategoryBaseItemWriter implements ItemWriter<SupplementCategoryBase> {
    private final SupplementCategoryBaseRepository categoryBaseRepository;
    private final SupplementCategoryRepository categoryRepository;

    @Override
    public void write(List<? extends SupplementCategoryBase> items) throws Exception {
        log.info("items : ");
        items.forEach(c -> log.info("item: {}", c.getName()));

        if (items.isEmpty()) {
            return;
        }
        categoryBaseRepository.saveAll(items);
        List<SupplementCategory> categories = items.stream().map(SupplementCategory::of).collect(Collectors.toList());
        categoryRepository.saveAll(categories);
    }
}
