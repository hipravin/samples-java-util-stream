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
        value = "application.runners.oomtest.enabled",
        havingValue = "true"
)
@Order(Runners.ORDER_LOAD_TEST)
public class NoCloseOomInSingleTransactionTestingRunner implements ApplicationRunner {
    private static final Logger log = LoggerFactory.getLogger(NoCloseOomInSingleTransactionTestingRunner.class);

    final TestSearchService testSearchService;

    public NoCloseOomInSingleTransactionTestingRunner(TestSearchService testSearchService) {
        this.testSearchService = testSearchService;
    }

    @Override
    public void run(ApplicationArguments args) {
        log.info("Test - started...");
        try {
//            testSearchService.searchTerminalNoCloseExceptionRepeated("1");
            testSearchService.searchExceptionRepeatedProperClose("1");
        } catch(RuntimeException e) {
            log.error(e.getMessage(), e);
        }
        log.info("Test - finished");
    }
}
