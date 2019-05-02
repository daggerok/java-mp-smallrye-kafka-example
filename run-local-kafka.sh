#!/usr/bin/env bash

# find support version here: https://www.apache.org/dist/kafka/
export SCALA_VERSION="2.12"
export KAFKA_VERSION="2.2.0"
#export KAFKA_VERSION="2.1.1"
export KAFKA_NAME="kafka_${SCALA_VERSION}-${KAFKA_VERSION}"
export KAFKA_HOME="/tmp/${KAFKA_NAME}"
if ! [[ -d "${KAFKA_HOME}" ]]; then
  wget -qO- https://www.apache.org/dist/kafka/${KAFKA_VERSION}/${KAFKA_NAME}.tgz | tar xvz
  mv -f ${KAFKA_NAME} /tmp/
fi

echo "Start Zookeeper..."
${KAFKA_HOME}/bin/zookeeper-server-start.sh ${KAFKA_HOME}/config/zookeeper.properties &

sleep 10s
echo "Start Kafka..."
${KAFKA_HOME}/bin/kafka-server-start.sh ${KAFKA_HOME}/config/server.properties &

sleep 3s
echo "Kafka broker started, press CTRL+C to exit"
trap ctrl_c INT
function ctrl_c() {
  echo "Shutting down Kafka and Zookeeper..."
  ${KAFKA_HOME}/bin/kafka-server-stop.sh ${KAFKA_HOME}/config/server.properties
  ${KAFKA_HOME}/bin/zookeeper-server-stop.sh ${KAFKA_HOME}/config/zookeeper.properties
  #echo "Stopping Kafka and Zookeeper"
  #for port in 9092 2181 ; do
  #  for pid in $(lsof -i:${port} -t) ; do
  #    kill ${pid} >/dev/null 2>&1 | true
  #  done
  #done
  exit 0
}
# Wait forever
read -r -d '' _ </dev/tty
