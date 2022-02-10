package mandykr.nutrient.batch.config.supplement;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mandykr.nutrient.batch.dto.openapi.foodsafety.SupplementProductResponse;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class SupplementProductItemReader implements ItemReader<SupplementProductResponse> {
    private final List<SupplementProductResponse> productList;

    @Override
    public SupplementProductResponse read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (productList.isEmpty()) {
            return null;
        }
        return productList.remove(0);
    }
}
