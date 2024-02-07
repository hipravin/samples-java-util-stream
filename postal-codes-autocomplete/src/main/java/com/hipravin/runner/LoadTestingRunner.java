package com.hipravin.runner;

import com.hipravin.post.persist.TestSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(
        value = "application.runners.loadtest.enabled",
        havingValue = "true"
)
@Order(Runners.OOM_TEST)
public class LoadTestingRunner implements ApplicationRunner {
    private static final Logger log = LoggerFactory.getLogger(LoadTestingRunner.class);

    final TestSearchService testSearchService;

    public LoadTestingRunner(TestSearchService testSearchService) {
        this.testSearchService = testSearchService;
    }

    @Override
    public void run(ApplicationArguments args) {
        //trying to get either error or slowdown when Stream instance is not properly closed
        //but everything works fine so far
        int iterations = 10_000;
        log.info("Load test - started...");

        for (int i = 0; i < iterations; i++) {
            testSearchService.search("10").stream().findFirst().ifPresent(p -> {
            });
        }
        log.info("Load test - starting no close no terminal");
        for (int i = 0; i < iterations; i++) {
            testSearchService.searchNoTerminalNoClose("10").stream().findFirst().ifPresent(p -> {
            });
        }
        log.info("Load test - starting no close exception");
        for (int i = 0; i < iterations; i++) {
            try {
                testSearchService.searchTerminalNoCloseException("10").stream().findFirst().ifPresent(p -> {
                });
            } catch (RuntimeException e) {
                //no op
            }
        }
        log.info("Load test - finished");
    }
}
