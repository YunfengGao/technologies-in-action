# win-docker 运行kafka

```shell script
# 下载镜像
docker pull zookeeper
docker pull wurstmeister/kafka

# 启动zookeeper
docker run -d --name zookeeper -p 2181:2181 -t zookeeper

# 进入zookeeper
docker exec -it zookeeper /bin/bash

# 启动kafka
docker run -d --name kafka -p 9092:9092 -e KAFKA_BROKER_ID=0 -e KAFKA_ZOOKEEPER_CONNECT=192.168.8.111:2181 -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://192.168.8.111:9092 -e KAFKA_LISTENERS=PLAINTEXT://0.0.0.0:9092 -t wurstmeister/kafka

# 进入kafka
docker exec -it kafka /bin/bash

# producer:创建topic
./kafka-console-producer.sh --broker-list localhost:9092 --topic test
# 发送一条message
> message1

# consumer:消费message
./kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic test --from-beginning
```
