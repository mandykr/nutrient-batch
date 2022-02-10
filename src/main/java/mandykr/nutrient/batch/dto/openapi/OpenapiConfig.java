package mandykr.nutrient.batch.dto.openapi;

import lombok.Getter;
import lombok.Setter;
import mandykr.nutrient.batch.dto.openapi.foodsafety.FoodsafetyRequest;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties("openapi")
@Getter
@Setter
public class OpenapiConfig {
    private FoodsafetyRequest supplementProduct;
    private FoodsafetyRequest supplementExplain;
}
