package com.binghu.gbm.types.design.framework.tree;

public interface StrategyMapper<T, D, R> {

   StrategyHandler<T, D, R> get(T requestParam, D dynamicContext) throws Exception;

}
