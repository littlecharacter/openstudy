package com.lc.kafka;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 多线程版1：效率略低一些
 * 隐含bug：有时候生产端停了，但是消费端一直不能将消息消费完！！！
 *
 * @author gujixian
 * @since 2023/8/3
 */
public class KConsumerNonAutoCommit {
    // kafka-consumer-groups.sh --bootstrap-server kfk:19092,kfk:29092,kfk:39092 --describe --group zs
    public static void main(String[] args) {
        Consumer<String, String> consumer = ClientUtil.instance().getConsumer("false");
        consumer.subscribe(Arrays.asList("xxoo"));
        Lock consumerLock = new ReentrantLock();
        while (true) {
            consumerLock.lock();
            ConsumerRecords<String, String> records;
            try {
                // 一直拉取，每次拉去的数量为：0 - MAX_POLL_RECORDS_CONFIG
                // 如果使用多线程的话，一定要设置拉取间隔，不然工作线程饥饿（如果没有消息产生这里会一直循环）
                records = consumer.poll(Duration.ofMillis(100));
            } finally {
                consumerLock.unlock();
            }
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
                es.submit(new PartitionHandler(consumer, consumerLock, partition, recordList));
            }
            // 3.以批次的粒度提交 offset
            // consumer.commitSync();
            // consumer.commitAsync();
        }
    }

    private static class PartitionHandler implements Runnable {
        Consumer<String, String> consumer;
        Lock consumerLock;
        TopicPartition partition;
        List<ConsumerRecord<String, String>> recordList;

        public PartitionHandler(Consumer<String, String> consumer,
                                Lock consumerLock,
                                TopicPartition partition,
                                List<ConsumerRecord<String, String>> recordList) {
            this.consumer = consumer;
            this.consumerLock = consumerLock;
            this.partition = partition;
            this.recordList = recordList;
        }

        @Override
        public void run() {
            for (ConsumerRecord<String, String> record : recordList) {
                try {
                    // 模拟业务处理时间
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getName() + "：key:" + record.key() + ", value:" + record.value() + ", partition:" + partition + "，offset:" + record.offset());
            }
            long offset = recordList.get(recordList.size() - 1).offset();
            Map<TopicPartition, OffsetAndMetadata> map = new HashMap<>();
            map.put(partition, new OffsetAndMetadata(offset + 1));
            // 注意，不管同步提交还是异步提交，只要不是和业务在一个事务里，都可能消费了，但没提交（业务处理完后，在未提交之前宕机）
            // 所以，优先选择异步提交！！！-> 做幂等
            // consumerLock.lock();
            // try {
            //     consumer.commitSync(map);
            // } finally {
            //     consumerLock.unlock();
            // }
            consumerLock.lock();
            try {
                consumer.commitAsync(map, null);
            } finally {
                consumerLock.unlock();
            }
            // consumer.commitAsync(map, new OffsetCommitCallback() {
            //     @Override
            //     public void onComplete(Map<TopicPartition, OffsetAndMetadata> offsets, Exception exception) {
            //         System.out.println("提交完成");
            //     }
            // });
        }
    }
}
