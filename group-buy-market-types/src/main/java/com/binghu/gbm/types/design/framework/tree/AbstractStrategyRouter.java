package com.binghu.gbm.types.design.framework.tree;

public abstract class AbstractStrategyRouter<T, D, R> implements StrategyMapper<T, D, R>, StrategyHandler<T, D, R> {

    protected StrategyHandler<T, D, R> defaultStrategyHandler = StrategyHandler.DEAULT;

    public R router(T requestParam, D dynamicContext) throws Exception {
        StrategyHandler<T, D, R> strategyHandler = get(requestParam, dynamicContext);
        if (strategyHandler != null) return strategyHandler.apply(requestParam, dynamicContext);
        return defaultStrategyHandler.apply(requestParam, dynamicContext);
    }

}
