package com.lc.kafka;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.TopicPartition;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * @author gujixian
 * @since 2023/8/3
 */
public class KConsumerAutoCommit {
    // kafka-consumer-groups.sh --bootstrap-server kfk:19092,kfk:29092,kfk:39092 --describe --group zs
    public static void main(String[] args) {
        Consumer<String, String> consumer = ClientUtil.instance().getConsumer("true");
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
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(200));
            // TODO: 2023/8/3 拉取多条如何处理很关键：既要快，又要正确（offset），还有可能处理顺序消息
            if (records.isEmpty()) {
                continue;
            }
            System.out.println("批次大小：" + records.count());
            for (ConsumerRecord<String, String> record : records) {
                int partition = record.partition();
                long offset = record.offset();
                try {
                    // 模拟业务处理时间
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("key:" + record.key() + ", value:" + record.value() + ", partition:" + partition + "，offset:" + offset);
            }
        }
    }
}
