package com.lc.kafka;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.TopicPartition;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;

/**
 * @author gujixian
 * @since 2023/8/3
 */
public class KConsumer {
    // kafka-consumer-groups.sh --bootstrap-server kfk:19092,kfk:29092,kfk:39092 --describe --group zs
    public static void main(String[] args) {
        Consumer<String, String> consumer = ClientUtil.instance().getConsumer();
        consumer.subscribe(Arrays.asList("xxoo"), new ConsumerRebalanceListener() {
            @Override
            public void onPartitionsRevoked(Collection<TopicPartition> partitionList) {
                System.out.print("Revoke 撤销分区：");
                for (TopicPartition partition : partitionList) {
                    System.out.print(partition.partition() + " ");
                }
                System.out.println();
            }

            @Override
            public void onPartitionsAssigned(Collection<TopicPartition> partitionList) {
                System.out.print("Assign 分配分区：");
                for (TopicPartition partition : partitionList) {
                    System.out.print(partition.partition() + " ");
                }
                System.out.println();
            }
        });
        while (true) {
            // 有消息就拉去，每次拉去的数量为：0 - MAX_POLL_RECORDS_CONFIG
            ConsumerRecords<String, String> recordList = consumer.poll(Duration.ofMillis(0));
            // TODO: 2023/8/3 拉取多条如何处理很关键：既要快，又要正确（offset），还有可能处理顺序消息
            for (ConsumerRecord<String, String> record : recordList) {
                int partition = record.partition();
                long offset = record.offset();
                System.out.println("key:" + record.key() + ", value:" + record.value() + ", partition:" + partition + "，offset:" + offset);
            }
        }
    }
}
