package mandykr.nutrient.batch.config.supplement;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mandykr.nutrient.batch.domain.Supplement;
import mandykr.nutrient.batch.domain.SupplementCategoryBase;
import mandykr.nutrient.batch.openapi.OpenapiConfig;
import mandykr.nutrient.batch.openapi.domain.ApiRequester;
import mandykr.nutrient.batch.openapi.domain.FoodsafetyApiRequester;
import mandykr.nutrient.batch.openapi.dto.foodsafety.SupplementExplainResponse;
import mandykr.nutrient.batch.openapi.dto.foodsafety.SupplementProductResponse;
import mandykr.nutrient.batch.repository.SupplementCategoryBaseRepository;
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
    private final SupplementCategoryBaseRepository categoryBaseRepository;
    private final SupplementCategoryRepository categoryRepository;

    @Bean
    public Job supplementJob() {
        FoodsafetyApiRequester<SupplementProductResponse> productRequester =
                new FoodsafetyApiRequester<>(SupplementProductResponse.class, restTemplate, apiConfig.getSupplementProduct());
        FoodsafetyApiRequester<SupplementExplainResponse> explainRequester =
                new FoodsafetyApiRequester<>(SupplementExplainResponse.class, restTemplate, apiConfig.getSupplementExplain());

        return jobBuilderFactory.get("supplementJob")
                .incrementer(new RunIdIncrementer())
                .start(supplementProductApiCallStep(productRequester))// TODO: Decide, Listener 추가
                .next(supplementExplainApiCallStep(explainRequester))
                .next(SupplementCategoryStep())
                .next(SupplementCategoryUpdateStep())
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
                .<Supplement, SupplementCategoryBase>chunk(CHUNK)
                .reader(supplementCategoryBaseItemReader())
                .processor(new SupplementCategoryBaseItemProcessor(categoryBaseRepository))
                .writer(new SupplementCategoryBaseItemWriter(categoryBaseRepository, categoryRepository))
                .build();
    }

    private JpaCursorItemReader<Supplement> supplementCategoryBaseItemReader() {
        return new JpaCursorItemReaderBuilder<Supplement>()
                .name("supplementCategoryBaseItemReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("select s from Supplement s group by hItemNm")
                .build();
    }

    @Bean
    public Step SupplementCategoryUpdateStep() {
        return stepBuilderFactory.get("SupplementCategoryUpdateStep")
                .<SupplementCategoryBase, SupplementCategoryBase>chunk(CHUNK)
                .reader(supplementCategoryUpdateItemReader())
                .writer(new SupplementCategoryUpdateItemWriter(supplementRepository))
                .build();
    }

    private JpaCursorItemReader<SupplementCategoryBase> supplementCategoryUpdateItemReader() {
        return new JpaCursorItemReaderBuilder<SupplementCategoryBase>()
                .name("supplementCategoryUpdateItemReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("select c from SupplementCategoryBase c")
                .build();
    }
}
