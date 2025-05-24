package com.binghu.gbm.types.design.framework.tree;

import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public abstract class AbstractMultiThreadStrategyRouter<T, D, R> implements StrategyMapper<T, D, R>, StrategyHandler<T, D, R> {

    @Getter
    @Setter
    protected StrategyHandler<T, D, R> defaultStrategyHandler = StrategyHandler.DEAULT;

    public R router(T requestParam, D dynamicContext) throws Exception {
        StrategyHandler<T, D, R> strategyHandler = get(requestParam, dynamicContext);
        if (strategyHandler != null) return strategyHandler.apply(requestParam, dynamicContext);
        return defaultStrategyHandler.apply(requestParam, dynamicContext);
    }

    @Override
    public R apply(T requestParam, D dynamicContext) throws Exception {
        // 异步加载数据
        multiThread(requestParam, dynamicContext);
        // 受理业务逻辑
        return doApply(requestParam, dynamicContext);
    }

    protected abstract void multiThread(T requestParam, D dynamicContext) throws ExecutionException, InterruptedException, TimeoutException;

    protected abstract R doApply(T requestParam, D dynamicContext) throws Exception;
}
