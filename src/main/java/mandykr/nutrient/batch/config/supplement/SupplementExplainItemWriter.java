package mandykr.nutrient.batch.config.supplement;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mandykr.nutrient.batch.domain.Supplement;
import mandykr.nutrient.batch.repository.SupplementRepository;
import org.springframework.batch.item.database.JpaItemWriter;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class SupplementExplainItemWriter extends JpaItemWriter<Supplement> {
    private final SupplementRepository supplementRepository;

    @Override
    public void write(List<? extends Supplement> items) {
        log.info("items : ");
        items.forEach(s -> log.info("item: {}", s.getPrdlstNm()));

        if (items.isEmpty()) {
            return;
        }
        List<Supplement> supplements = supplementRepository.findAll();
        editSupplements(supplements, items);
    }

    private void editSupplements(List<? extends Supplement> supplements, List<? extends Supplement> items) {
        items.forEach(item -> {
            supplements.forEach(s -> {
                if (item.equals(s)) {
                    s.editExplain(item);
                }
            });
        });
    }
}
