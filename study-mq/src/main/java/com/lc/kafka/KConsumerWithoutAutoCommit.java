package com.lc.kafka;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 多线程版2：效率更高一些
 *
 * @author gujixian
 * @since 2023/8/3
 */
public class KConsumerWithoutAutoCommit {
    // kafka-consumer-groups.sh --bootstrap-server kfk:19092,kfk:29092,kfk:39092 --describe --group zs
    public static void main(String[] args) {
        Consumer<String, String> consumer = ClientUtil.instance().getConsumer("false");
        consumer.subscribe(Arrays.asList("xxoo"));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            if (records.isEmpty()) {
                System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++批次大小：0");
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }
            System.out.println("------------------------------------------------------------------------------------批次大小：" + records.count());
            // 本次拉取的数据包含的分区
            Set<TopicPartition> partitionSet = records.partitions();
            // 按分区处理拉取的消息
            Map<TopicPartition, Future> taskMap = new HashMap<>(partitionSet.size());
            for (TopicPartition partition : partitionSet) {
                // 本次拉取中某个分区的消息
                List<ConsumerRecord<String, String>> recordList = records.records(partition);
                // for (ConsumerRecord<String, String> record : recordList) {
                //     System.out.println("key:" + record.key() + ", value:" + record.value() + ", partition:" + partition + "，offset:" + record.offset());
                //     // 1.以记录的粒度提交 offset
                //     Map<TopicPartition, OffsetAndMetadata> map = new HashMap<>();
                //     map.put(partition, new OffsetAndMetadata(record.offset() + 1));
                //     consumer.commitSync(map);
                //     // consumer.commitAsync(map, new OffsetCommitCallback() {
                //     //     @Override
                //     //     public void onComplete(Map<TopicPartition, OffsetAndMetadata> offsets, Exception exception) {
                //     //         System.out.println("提交完成");
                //     //     }
                //     // });
                // }
                // 2.以分区的粒度提交 offset（kafka 维护 offset 以分区为粒度，也就是一个分区一个 offset，kafka-consumer-groups.sh 可以查看每个分区的 offset 信息）
                ExecutorService es = ClientUtil.instance().getThreadPool(partition.partition());
                taskMap.put(partition, es.submit(new PartitionHandler(recordList)));
            }
            taskMap.forEach((partition, future) -> {
                long offset;
                try {
                    offset = (Long) future.get();
                } catch (Exception e) {
                    // 哪个 partition 出错了，记录一下即可，反正也不提交
                    e.printStackTrace();
                    // 继续
                    return;
                }
                Map<TopicPartition, OffsetAndMetadata> map = new HashMap<>();
                map.put(partition, new OffsetAndMetadata(offset));
                // 注意，不管同步提交还是异步提交，只要不是和业务在一个事务里，都可能消费了，但没提交（业务处理完后，在未提交之前宕机）
                // 所以，优先选择异步提交！！！-> 做幂等
                consumer.commitSync(map);
                // consumer.commitAsync(map, new OffsetCommitCallback() {
                //     @Override
                //     public void onComplete(Map<TopicPartition, OffsetAndMetadata> offsets, Exception exception) {
                //         System.out.println("提交完成");
                //     }
                // });
            });
            // 3.以批次的粒度提交 offset
            // consumer.commitSync();
            // consumer.commitAsync();
        }
    }

    private static class PartitionHandler implements Callable<Long> {
        List<ConsumerRecord<String, String>> recordList;

        public PartitionHandler(List<ConsumerRecord<String, String>> recordList) {
            this.recordList = recordList;
        }

        @Override
        public Long call() {
            for (ConsumerRecord<String, String> record : recordList) {
                try {
                    // 模拟业务处理时间
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getName() + "：key:" + record.key() + ", value:" + record.value() + ", partition:" + record.partition() + "，offset:" + record.offset());
            }
            return recordList.get(recordList.size() - 1).offset() + 1;
        }
    }
}
