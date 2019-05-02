#!/usr/bin/env bash

for container in $(docker ps -q -f name=kafka -f name=zookeeper) ; do
  docker rm -f -v ${container}
done

docker pull confluent/zookeeper >/dev/null
docker pull confluent/kafka >/dev/null

echo "Start Confluent Zookeeper..."
docker run -i --rm \
  --name zookeeper \
  -p 2181:2181 \
  confluent/zookeeper &

sleep 10s
echo "Start Confluent Kafka..."
docker run -i --rm \
  --name kafka \
  -p 9092:9092 \
  --link zookeeper:zookeeper \
  --env KAFKA_ADVERTISED_HOST_NAME=localhost \
  confluent/kafka &

sleep 3s
echo "Kafka broker started, press CTRL+C to exit"
trap ctrl_c INT
function ctrl_c() {
  echo "Killing Kafka and Zookeeper"
  docker rm -f -v kafka zookeeper
  exit 0
}
# Wait forever
read -r -d '' _ </dev/tty
