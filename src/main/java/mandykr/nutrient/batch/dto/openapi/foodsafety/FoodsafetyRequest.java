package mandykr.nutrient.batch.dto.openapi.foodsafety;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class FoodsafetyRequest {
    private String name;
    private String url;
    private String serviceId;
    private String keyId;
}
