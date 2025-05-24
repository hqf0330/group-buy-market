package com.binghu.gbm.domain.activity.service.trail;

import com.binghu.gbm.domain.activity.adapter.repository.IActivityRepository;
import com.binghu.gbm.domain.activity.model.entity.MarketProductEntity;
import com.binghu.gbm.domain.activity.model.entity.TrialBalanceEntity;
import com.binghu.gbm.domain.activity.service.trail.factory.DefaultActivityStrategyFactory;
import com.binghu.gbm.types.design.framework.tree.AbstractMultiThreadStrategyRouter;
import jakarta.annotation.Resource;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public abstract class AbstractGroupBuyMarketSupport extends AbstractMultiThreadStrategyRouter<MarketProductEntity, DefaultActivityStrategyFactory.DynamicContext, TrialBalanceEntity> {

    protected long timeout = 500;

    @Resource
    protected IActivityRepository activityRepository;

    @Override
    protected void multiThread(MarketProductEntity requestParam, DefaultActivityStrategyFactory.DynamicContext dynamicContext) throws ExecutionException, InterruptedException, TimeoutException {
        //todo 缺省
    }
}
