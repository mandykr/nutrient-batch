package mandykr.nutrient.batch.config.supplement;

import lombok.RequiredArgsConstructor;
import mandykr.nutrient.batch.domain.Supplement;
import mandykr.nutrient.batch.domain.SupplementCategoryBase;
import mandykr.nutrient.batch.repository.SupplementCategoryBaseRepository;
import org.springframework.batch.item.ItemProcessor;

import java.util.Optional;

@RequiredArgsConstructor
public class SupplementCategoryBaseItemProcessor implements ItemProcessor<Supplement, SupplementCategoryBase> {
    private final SupplementCategoryBaseRepository categoryBaseRepository;

    @Override
    public SupplementCategoryBase process(Supplement item) throws Exception {
        SupplementCategoryBase category = null;
        String name = item.getHItemNm();
        Optional<SupplementCategoryBase> findCategory = categoryBaseRepository.findByName(name);
        if (findCategory.isEmpty()) {
            category = new SupplementCategoryBase(name, SupplementCategoryBase.MIN_DEPTH);
        }

        return category;
    }
}
