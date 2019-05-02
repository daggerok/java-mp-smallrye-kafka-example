package com.github.daggerok.kafka;

import io.smallrye.reactive.messaging.annotations.Emitter;
import io.smallrye.reactive.messaging.annotations.Stream;
import lombok.extern.slf4j.Slf4j;
import org.jboss.weld.environment.se.events.ContainerInitialized;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@ApplicationScoped
public class KafkaEmitter {

  @Inject // kafka
  @Stream("my-stream")
  Emitter<String> emitter;

  public void observe(@Observes ContainerInitialized event) {
    log.info(event.getClass().getSimpleName());
    produceMessagesToKafka();
  }

  public void produceMessagesToKafka() {
    ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    AtomicInteger counter = new AtomicInteger(Integer.MAX_VALUE - 10);
    executorService.scheduleAtFixedRate(
        () -> emitter.send("Hey " + counter.getAndIncrement()),
        0,
        333,
        TimeUnit.MILLISECONDS
    );
  }
}
