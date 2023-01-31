package com.jhipster;

import com.jhipster.AppApp;
import com.jhipster.config.AsyncSyncConfiguration;
import com.jhipster.config.EmbeddedElasticsearch;
import com.jhipster.config.EmbeddedKafka;
import com.jhipster.config.EmbeddedRedis;
import com.jhipster.config.EmbeddedSQL;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

/**
 * Base composite annotation for integration tests.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(classes = { AppApp.class, AsyncSyncConfiguration.class })
@EmbeddedRedis
@EmbeddedElasticsearch
@EmbeddedKafka
@EmbeddedSQL
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public @interface IntegrationTest {
}
