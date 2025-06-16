package ru.yandex.practicum.bank.exchange.config;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.common.TopicPartition;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.listener.ConsumerAwareRebalanceListener;

import java.util.Collection;
import java.util.List;

@Configuration
public class KafkaOffsetConfiguration implements ConsumerAwareRebalanceListener {

    private static final String RATES_TOPIC = "rates";

    @Override
    public void onPartitionsAssigned(Consumer<?, ?> consumer, Collection<TopicPartition> partitions) {
        for (TopicPartition partition : partitions) {
            if (RATES_TOPIC.equals(partition.topic())) {
                var offset = consumer.endOffsets(List.of(partition))
                        .getOrDefault(partition, 0L);
                consumer.seek(partition, offset == 0 ? 0 : offset - 1);
            }
        }
    }
}
