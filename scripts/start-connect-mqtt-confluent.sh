#!/bin/bash

echo "removing MQTT Source Connectors"

curl -X "DELETE" "$DOCKER_HOST_IP:8083/connectors/mqtt-truck-position-source"

echo "creating MQTT Source Connector - Position"

curl -X "POST" "$DOCKER_HOST_IP:8083/connectors" \
     -H "Content-Type: application/json" \
     --data '{
  "name": "mqtt-truck-position-source",
  "config": {
    "connector.class": "io.confluent.connect.mqtt.MqttSourceConnector",
    "tasks.max": "1",
    "mqtt.server.uri": "tcp://mqtt-1:1883",
    "mqtt.topics": "truck/+/position",
    "mqtt.clean.session.enabled":"true",
    "mqtt.connect.timeout.seconds":"30",
    "mqtt.keepalive.interval.seconds":"60",
    "mqtt.qos":"0"
    "kafka.topic":"truck_position",
    "confluent.topic.bootstrap.servers": "broker-1:9092",
    "confluent.topic.replication.factor": "3",
    }
  }'

