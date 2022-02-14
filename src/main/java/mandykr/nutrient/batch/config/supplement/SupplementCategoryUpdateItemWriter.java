package mandykr.nutrient.batch.config.supplement;

import lombok.RequiredArgsConstructor;
import mandykr.nutrient.batch.domain.Supplement;
import mandykr.nutrient.batch.domain.SupplementCategoryBase;
import mandykr.nutrient.batch.repository.SupplementRepository;
import org.springframework.batch.item.database.JpaItemWriter;

import java.util.List;

@RequiredArgsConstructor
public class SupplementCategoryUpdateItemWriter extends JpaItemWriter<SupplementCategoryBase> {
    private final SupplementRepository supplementRepository;

    @Override
    public void write(List<? extends SupplementCategoryBase> items) {
        items.forEach(this::addCategory);
    }

    private void addCategory(SupplementCategoryBase category) {
        List<Supplement> supplements = supplementRepository.findAllByhItemNm(category.getName());
        supplements.forEach(s -> s.addCategory(category));
    }
}
