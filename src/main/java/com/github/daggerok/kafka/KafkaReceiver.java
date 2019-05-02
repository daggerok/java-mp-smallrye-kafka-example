package com.github.daggerok.kafka;

import io.smallrye.reactive.messaging.kafka.KafkaMessage;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.weld.environment.se.events.ContainerInitialized;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import java.time.Instant;
import java.util.concurrent.CompletionStage;

@Slf4j
@ApplicationScoped
public class KafkaReceiver {

  public void observe(@Observes ContainerInitialized event) {
    log.info(event.getClass().getSimpleName());
  }

  @Incoming("kafka")
  public CompletionStage<Void> consume(KafkaMessage<String, String> message) {
    // message.getKey(); message.getHeaders(); message.getPartition();
    String[] split = message.toString().split("@");
    String addr = split.length == 2 ? split[1] : "";
    String payload = message.getPayload();
    String topic = message.getTopic();
    Long timestamp = message.getTimestamp();
    log.info("{}: '{}' from '{}' at '{}'", addr, payload, topic, Instant.ofEpochMilli(timestamp));
    return message.ack();
  }
}
