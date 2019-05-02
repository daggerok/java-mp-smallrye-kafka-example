package com.github.daggerok.kafka;

import com.github.daggerok.log.LogMe;
import io.smallrye.reactive.messaging.kafka.KafkaMessage;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import javax.enterprise.context.ApplicationScoped;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.TimeUnit.SECONDS;

@ApplicationScoped
public class KafkaSender {

  private static final String TOPIC = "kafka";

  private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

  @Outgoing("data")
  public CompletionStage<KafkaMessage<String, String>> send() {
    CompletableFuture<KafkaMessage<String, String>> future = new CompletableFuture<>();
    scheduleDelay(() -> {
      KafkaMessage<String, String> kafkaMessage = KafkaMessage.of(TOPIC, null, "hello SmallRye reactive MicroProfile");
      future.complete(kafkaMessage);
    });
    return future;
  }

  @LogMe
  private void scheduleDelay(Runnable runnable) {
    executor.schedule(runnable, 3, SECONDS);
  }
}
