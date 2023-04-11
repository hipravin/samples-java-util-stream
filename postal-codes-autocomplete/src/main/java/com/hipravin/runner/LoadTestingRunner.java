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
@Order(Runners.ORDER_LOAD_TEST)
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
        log.info("Load test - started...");

        for (int i = 0; i < 10_000; i++) {
            testSearchService.search("10").stream().findFirst().ifPresent(p -> {});
        }
        log.info("Load test - starting no close");
        for (int i = 0; i < 10_000; i++) {
            testSearchService.searchNoTerminalNoClose("10").stream().findFirst().ifPresent(p ->{});
        }
        log.info("Load test - finished");
    }
}
