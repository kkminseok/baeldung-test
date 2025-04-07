#/bin/bash
docker exec -it kafka kafka-topics --bootstrap-server localhost:9092 \
                        --create --topic test-topic \
                        --partitions 1 --replication-factor 1
docker exec -it kafka kafka-topics --bootstrap-server localhost:9092 --list