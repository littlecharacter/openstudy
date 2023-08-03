package com.lc.kafka;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author gujixian
 * @since 2023/8/3
 */
public class KProducer {
    public static void main(String[] args) throws Exception {
        String topic = "xxoo";
        Producer<String, String> producer = ClientUtil.instance().getProducer();
        int n = 0;
        while (true) {
            n += 3;
            for (int i = n - 3; i < n; i++) {
                for (int j = n - 3; j < n; j++) {
                    ProducerRecord<String, String> record = new ProducerRecord<>(topic, "item" + j, "item" + j + ":value" + i);
                    Future<RecordMetadata> send = producer.send(record);
                    RecordMetadata recordMetadata = send.get();
                    int partition = recordMetadata.partition();
                    long offset = recordMetadata.offset();
                    System.out.println("key:" + record.key() + ", value:" + record.value() + ", partition:" + partition + "，offset:" + offset);
                }
            }
            TimeUnit.SECONDS.sleep(1);
        }
    }
}
