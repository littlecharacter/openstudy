package com.lc.javase.util;

import com.lc.javase.pojo.Result;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

public class RetryUtil<T> {
    private static final int MAX_RETRY_COUNT = 5;

    public Result<T> retry(RetryCode retryCode) {
        Result<T> result = null;
        for (int retryCount = 1; retryCount <= MAX_RETRY_COUNT; retryCount++) {
            try {
                result = retryCode.execute();
                if (result != null && result.isSuccess()) {
                    return result;
                }
            } catch (Exception e) {
                if (retryCount == MAX_RETRY_COUNT) {
                    // 报警，人工处理、job异步处理或者（近实时）对账系统自动校准
                    throw e; //或者result封装异常
                }
            }
        }
        return result != null ? result : ResultUtil.fail();
    }

    public Result<T> retryWithTransaction(RetryCode retryCode) {
        for (int retryCount = 1; retryCount <= MAX_RETRY_COUNT; retryCount++) {
            int runRetryCount = retryCount;
            Result<T> result = TransactionTemplateSingleton.getTxTemplate().execute(status -> {
                Result<T> runResult = null;
                try {
                    runResult = retryCode.execute();
                    if (runResult != null && runResult.isSuccess()) {
                        return runResult;
                    }
                } catch (Exception e) {
                    if (runRetryCount == MAX_RETRY_COUNT) {
                        status.setRollbackOnly();
                        // 报警，人工处理、job异步处理或者（近实时）对账系统自动校准
                        throw e; //或者result封装异常
                    }
                }
                status.setRollbackOnly();
                return runResult;
            });
            if (result != null && result.isSuccess()) {
                return result;
            }
        }
        return ResultUtil.fail();
    }

    private static class TransactionTemplateSingleton {
        private static TransactionTemplate txTemplate;

        private TransactionTemplateSingleton() {
        }

        private static TransactionTemplate getTxTemplate() {
            if (txTemplate == null) {
                // 配置文件中配置，并从Spring工厂中获取，这里用new代替
                // DataSourceTransactionManager transactionManager = SpringUtil.getBean(DataSourceTransactionManager.class);
                txTemplate = new TransactionTemplate(new DataSourceTransactionManager());
                //设置其隔离级别为嵌套事务
                txTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_NESTED);
            }
            return txTemplate;
        }
    }

    public interface RetryCode {
        <T> Result<T> execute();
    }
}
