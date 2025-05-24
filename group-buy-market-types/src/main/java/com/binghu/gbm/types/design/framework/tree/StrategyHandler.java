package com.binghu.gbm.types.design.framework.tree;

public interface StrategyHandler<T, D, R> {

    StrategyHandler DEAULT = (T, D) -> null;

    R apply(T requestParam, D dynamicContext) throws Exception;
}
