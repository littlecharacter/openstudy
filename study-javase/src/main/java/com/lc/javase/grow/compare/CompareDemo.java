package com.lc.javase.grow.compare;

import com.lc.javase.other.pojo.Person;
import com.lc.javase.other.pojo.User;
import javafx.util.Pair;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author gujixian
 * @since 2024/2/2
 */
public class CompareDemo {
    public static void main(String[] args) {


    }

    private static class CompareTask implements Runnable, DataComparison<Person, User> {
        private final long requestId;

        CompareTask(long requestId) {
            this.requestId = requestId;
        }

        @Override
        public boolean compare(Person source, User target) {
            return false;
        }

        @Override
        public void run() {
            // 新的业务逻辑
            // xxService.xx();
            // 从回调中心拿 source 和 target
            Pair<CompletableFuture<Object>, CompletableFuture<Object>> future = FutureCenter.getFuture(requestId);
            try {
                Person source = (Person) future.getKey().get();
                User target = (User) future.getValue().get();
                System.out.println(compare(source, target));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
