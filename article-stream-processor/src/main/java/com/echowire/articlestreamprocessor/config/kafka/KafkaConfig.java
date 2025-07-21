package com.echowire.articlestreamprocessor.config.kafka;

import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.api.common.serialization.DeserializationSchema;

public class KafkaConfig {

    private static String kafkaServer = "localhost:9092";

    public static <T> KafkaSource<T> articleKafkaSource(DeserializationSchema<T> schema) {
        return KafkaSource.<T>builder()
                .setBootstrapServers(kafkaServer)
                .setTopics("articles")
                .setGroupId("article-flink-consumer")
                .setStartingOffsets(OffsetsInitializer.latest())
                .setValueOnlyDeserializer(schema)
                .build();
    }
}
