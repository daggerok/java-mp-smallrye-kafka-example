# Kafka Smallrye MicrpProfile
Kafka messaging java example of SmallRye micro-profile using maven

_this app requires kafka_

```bash
./run-local-kafka.sh
# or: ./run-kafka-in-docker.sh
```

_build and run fatJar_

```bash
mvn clean package
java -jar target/*-all.jar
```

_run using maven_

```bash
mvn clean compile exec:java -Dexec.mainClass=com.github.daggerok.App
# or
mvn clean compile exec:java -Dexec.mainClass=org.jboss.weld.environment.se.StartMain 
```

_for debugging purpose run App main class right from IDE in debug mode_

* [SmallRye Reactive Messaging: Interacting with Apache Kafka](https://smallrye.io/smallrye-reactive-messaging/#_interacting_with_apache_kafka)
