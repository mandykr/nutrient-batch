package mandykr.nutrient.batch.config.supplement;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mandykr.nutrient.batch.domain.Supplement;
import mandykr.nutrient.batch.domain.SupplementCategory;
import mandykr.nutrient.batch.openapi.OpenapiConfig;
import mandykr.nutrient.batch.openapi.domain.ApiRequester;
import mandykr.nutrient.batch.openapi.domain.FoodsafetyApiRequester;
import mandykr.nutrient.batch.openapi.dto.foodsafety.SupplementExplainResponse;
import mandykr.nutrient.batch.openapi.dto.foodsafety.SupplementProductResponse;
import mandykr.nutrient.batch.repository.SupplementCategoryRepository;
import mandykr.nutrient.batch.repository.SupplementRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManagerFactory;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class SupplementConfiguration {
    private final int CHUNK = 1000;
    private final RestTemplate restTemplate;
    private final OpenapiConfig apiConfig;
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;
    private final SupplementRepository supplementRepository;
    private final SupplementCategoryRepository categoryRepository;

    @Bean
    public Job supplementJob() {
        FoodsafetyApiRequester<SupplementProductResponse> productRequester =
                new FoodsafetyApiRequester<>(SupplementProductResponse.class, restTemplate, apiConfig.getSupplementProduct());
        FoodsafetyApiRequester<SupplementExplainResponse> explainRequester =
                new FoodsafetyApiRequester<>(SupplementExplainResponse.class, restTemplate, apiConfig.getSupplementProduct());

        return jobBuilderFactory.get("supplementJob")
                .incrementer(new RunIdIncrementer())
                .start(supplementProductApiCallStep(productRequester))
                .next(supplementExplainApiCallStep(explainRequester))
                .next(SupplementCategoryStep())
                .build();
    }

    @Bean
    public Step supplementProductApiCallStep(ApiRequester<SupplementProductResponse> requester) {
        requester.request();
        return stepBuilderFactory.get("supplementProductApiCallStep")
                .<SupplementProductResponse, Supplement>chunk(CHUNK)
                .reader(new SupplementProductItemReader(requester.getResponses()))
                .processor(new SupplementProductItemProcessor(supplementRepository))
                .writer(new SupplementProductItemWriter(supplementRepository))
                .build();
    }

    @Bean
    public Step supplementExplainApiCallStep(ApiRequester<SupplementExplainResponse> requester) {
        requester.request();
        return stepBuilderFactory.get("supplementExplainApiCallStep")
                .<SupplementExplainResponse, Supplement>chunk(CHUNK)
                .reader(new SupplementExplainItemReader(requester.getResponses()))
                .processor(new SupplementExplainItemProcessor(supplementRepository))
                .writer(new SupplementExplainItemWriter(supplementRepository))
                .build();
    }

    @Bean
    public Step SupplementCategoryStep() {
        return stepBuilderFactory.get("SupplementCategoryStep")
                .<Supplement, SupplementCategory>chunk(CHUNK)
                .reader(supplementCategoryItemReader())
                .processor(new SupplementCategoryItemProcessor(categoryRepository))
                .writer(new SupplementCategoryItemWriter(categoryRepository))
                .build();
    }

    private JpaCursorItemReader<Supplement> supplementCategoryItemReader() {
        return new JpaCursorItemReaderBuilder<Supplement>()
                .name("supplementCategoryItemReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("select s.hItemNm from supplement s group by hItemNm")
                .build();
    }
}
