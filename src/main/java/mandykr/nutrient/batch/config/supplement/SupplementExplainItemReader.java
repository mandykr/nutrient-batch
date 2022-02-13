package mandykr.nutrient.batch.config.supplement;

import lombok.RequiredArgsConstructor;
import mandykr.nutrient.batch.openapi.dto.foodsafety.SupplementExplainResponse;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.util.List;

@RequiredArgsConstructor
public class SupplementExplainItemReader implements ItemReader<SupplementExplainResponse> {
    private final List<SupplementExplainResponse> explainList;

    @Override
    public SupplementExplainResponse read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (explainList.isEmpty()) {
            return null;
        }
        return explainList.remove(0);
    }
}
