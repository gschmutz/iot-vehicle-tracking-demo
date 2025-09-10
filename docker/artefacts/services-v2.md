# iot-vehicle-tracking-platform - List of Services

| Service | Links | External<br>Port | Internal<br>Port | Description
|--------------|------|------|------|------------
|[adminer](./documentation/services/adminer )|[Web UI](http://192.168.1.112:28131)|28131<br>|8080<br>|Relational Database Admin UI
|[akhq](./documentation/services/akhq )|[Web UI](http://192.168.1.112:28107) - [Rest API](http://192.168.1.112:28107/api)|28107<br>28320<br>|8080<br>28081<br>|Kafka GUI
|[cloudbeaver](./documentation/services/cloudbeaver )|[Web UI](http://192.168.1.112:8978)|8978<br>|8978<br>|Cloud Database Manager
|[kafka-1](./documentation/services/kafka )||9092<br>19092<br>29092<br>39092<br>9992<br>1234<br>|9092<br>19092<br>29092<br>39092<br>9992<br>1234<br>|Kafka Broker 1
|[kafka-2](./documentation/services/kafka )||9093<br>19093<br>29093<br>39093<br>9993<br>1235<br>|9093<br>19093<br>29093<br>39093<br>9993<br>1234<br>|Kafka Broker 2
|[kafka-3](./documentation/services/kafka )||9094<br>19094<br>29094<br>39094<br>9994<br>1236<br>|9094<br>19094<br>29094<br>39094<br>9994<br>1234<br>|Kafka Broker 3
|[kafka-connect-1](./documentation/services/kafka-connect )|[Rest API](http://192.168.1.112:8083)|8083<br>|8083<br>|Kafka Connect Node 1
|[kafka-connect-2](./documentation/services/kafka-connect )|[Rest API](http://192.168.1.112:8084)|8084<br>|8084<br>|Kafka Connect Node 2
|[kafka-connect-ui](./documentation/services/kafka-connect-ui )|[Web UI](http://192.168.1.112:28103)|28103<br>|8000<br>|Kafka Connect GUI
|[kafka-ui](./documentation/services/kafka-ui )|[Web UI](http://192.168.1.112:28179)|28179<br>|8080<br>|Kafka GUI
|[kcat](./documentation/services/kcat )||||Generic non-JVM Kafka consumer and producer
|[ksqldb-cli](./documentation/services/ksqldb-cli )||||ksqlDB Command Line Utility
|[ksqldb-server-1](./documentation/services/ksqldb )|[Rest API](http://192.168.1.112:8088)|8088<br>1095<br>|8088<br>1095<br>|ksqlDB Streaming Database - Node 1
|[ksqldb-server-2](./documentation/services/ksqldb )|[Rest API](http://192.168.1.112:8089)|8089<br>1096<br>|8088<br>1095<br>|ksqlDB Streaming Database - Node 2
|[markdown-viewer](./documentation/services/markdown-viewer )|[Web UI](http://192.168.1.112:80)|80<br>|3000<br>|Platys Platform homepage viewer
|[minio-1](./documentation/services/minio )|[Web UI](http://192.168.1.112:9010)|9000<br>9010<br>|9000<br>9010<br>|Software-defined Object Storage
|[minio-mc](./documentation/services/minio )||||MinIO Console
|[mosquitto-1](./documentation/services/mosquitto )||1883<br>9101<br>|1883<br>9001<br>|MQTT Broker
|[mqtt-ui](./documentation/services/hivemq-ui )|[Web UI](http://192.168.1.112:28136)|28136<br>|80<br>|MQTT UI
|[mysql](./documentation/services/mysql )||3306<br>|3306<br>|Relational Database
|[nifi2-1](./documentation/services/nifi )|[Web UI](https://192.168.1.112:18083/nifi) - [Rest API](https://192.168.1.112:18083/nifi-api)|18083<br>10015<br>1273<br>|18083<br>10015/tcp<br>1234<br>|NiFi Data Integration Engine (V2)
|[postgresql](./documentation/services/postgresql )||5432<br>|5432<br>|Open-Source object-relational database system
|[redis-1](./documentation/services/redis )||6379<br>|6379<br>|Key-value store
|[schema-registry-1](./documentation/services/schema-registry )|[Rest API](http://192.168.1.112:8081)|8081<br>|8081<br>|Confluent Schema Registry
|[schema-registry-ui](./documentation/services/schema-registry-ui )|[Web UI](http://192.168.1.112:28102)|28102<br>|8000<br>|Schema Registry UI
|[wetty](./documentation/services/wetty )|[Web UI](http://192.168.1.112:3001)|3001<br>|3000<br>|A terminal window in Web-Browser|

**Note:** init container ("init: true") are not shown