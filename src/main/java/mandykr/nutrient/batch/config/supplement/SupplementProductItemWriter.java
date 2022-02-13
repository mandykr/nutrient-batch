package mandykr.nutrient.batch.config.supplement;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mandykr.nutrient.batch.domain.Supplement;
import mandykr.nutrient.batch.repository.SupplementRepository;
import org.springframework.batch.item.database.JpaItemWriter;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class SupplementProductItemWriter extends JpaItemWriter<Supplement> {
    private final SupplementRepository supplementRepository;

    @Override
    public void write(List<? extends Supplement> items) {
        log.info("items : ");
        items.forEach(s -> log.info("item: {}", s.getPrdlstNm()));

        if (items.isEmpty()) {
            return;
        }
        supplementRepository.saveAll(items);
    }
}
