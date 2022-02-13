package mandykr.nutrient.batch.config.supplement;

import lombok.RequiredArgsConstructor;
import mandykr.nutrient.batch.domain.CategoryType;
import mandykr.nutrient.batch.domain.Supplement;
import mandykr.nutrient.batch.domain.SupplementCategory;
import mandykr.nutrient.batch.repository.SupplementCategoryRepository;
import org.springframework.batch.item.ItemProcessor;

import java.util.Optional;

@RequiredArgsConstructor
public class SupplementCategoryItemProcessor implements ItemProcessor<Supplement, SupplementCategory> {
    private final SupplementCategoryRepository categoryRepository;

    @Override
    public SupplementCategory process(Supplement item) throws Exception {
        SupplementCategory category = null;
        String name = item.getHItemNm();
        Optional<SupplementCategory> findCategory = categoryRepository.findByName(name);
        if (findCategory.isEmpty()) {
            category = new SupplementCategory(CategoryType.base, name, SupplementCategory.MIN_DEPTH);
        }

        return category;
    }
}
