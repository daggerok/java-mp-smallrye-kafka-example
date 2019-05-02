package com.github.daggerok;

import io.smallrye.reactive.messaging.extension.ReactiveMessagingExtension;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

import javax.enterprise.context.ApplicationScoped;
import java.util.concurrent.TimeUnit;

@Slf4j
@ApplicationScoped
public class App {
  public static void main(String[] args) {
    Weld weld = new Weld()//.addPackages(true, App.class)
                          .addExtension(new ReactiveMessagingExtension())
    ;
    WeldContainer container = weld.initialize();
    container.select(App.class).get();
//    SeContainerInitializer initializer = SeContainerInitializer
//        .newInstance()
////        .addPackages(true, App.class)
//        .addExtensions(new ReactiveMessagingExtension())
//        ;
//    try (SeContainer container = initializer.initialize()) {
      while (container.isRunning()) {
        Try.run(() -> TimeUnit.SECONDS.sleep(5));
      }
//    }
  }
}
