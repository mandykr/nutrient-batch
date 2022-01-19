package mandykr.nutrient.batch.config.supplement;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Slf4j
@Configuration
public class SupplementConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job supplementJob() {
        return jobBuilderFactory.get("supplementJob")
                .incrementer(new RunIdIncrementer())
                .start(categoryApiCallStep())
                .build();
    }

    @Bean
    public Step categoryApiCallStep() {
        return stepBuilderFactory.get("testStep")
                .tasklet(((contribution, chunkContext) -> {
                    log.info("hello batch");
                    return RepeatStatus.FINISHED;
                })).build();
    }
}
