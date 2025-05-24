package com.binghu.gbm.domain.activity.service.trail.node;

import com.binghu.gbm.domain.activity.model.entity.MarketProductEntity;
import com.binghu.gbm.domain.activity.model.entity.TrialBalanceEntity;
import com.binghu.gbm.domain.activity.model.valobj.GroupBuyActivityDiscountVO;
import com.binghu.gbm.domain.activity.model.valobj.SkuVO;
import com.binghu.gbm.domain.activity.service.trail.AbstractGroupBuyMarketSupport;
import com.binghu.gbm.domain.activity.service.trail.factory.DefaultActivityStrategyFactory;
import com.binghu.gbm.domain.activity.service.trail.thread.QueryGroupBuyActivityDiscountVOThreadTask;
import com.binghu.gbm.domain.activity.service.trail.thread.QuerySkuVOFromDBThreadTask;
import com.binghu.gbm.types.design.framework.tree.StrategyHandler;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Slf4j
@Service
public class MarketNode extends AbstractGroupBuyMarketSupport {

    @Resource
    private EndNode endNode;

    @Resource
    private ThreadPoolExecutor threadPoolExecutor;

    @Override
    protected void multiThread(MarketProductEntity requestParam, DefaultActivityStrategyFactory.DynamicContext dynamicContext) throws ExecutionException, InterruptedException, TimeoutException {
        QueryGroupBuyActivityDiscountVOThreadTask queryGroupBuyActivityDiscountVOThreadTask = new QueryGroupBuyActivityDiscountVOThreadTask(requestParam.getSource(), requestParam.getChannel(), activityRepository);
        FutureTask<GroupBuyActivityDiscountVO> groupBuyActivityDiscountVOFutureTask = new FutureTask<>(queryGroupBuyActivityDiscountVOThreadTask);
        threadPoolExecutor.execute(groupBuyActivityDiscountVOFutureTask);

        QuerySkuVOFromDBThreadTask querySkuVOFromDBThreadTask = new QuerySkuVOFromDBThreadTask(requestParam.getGoodsId(), activityRepository);
        FutureTask<SkuVO> skuVOFutureTask = new FutureTask<>(querySkuVOFromDBThreadTask);
        threadPoolExecutor.execute(skuVOFutureTask);

        // 将值注入到上下文中
        dynamicContext.setGroupBuyActivityDiscountVO(groupBuyActivityDiscountVOFutureTask.get(timeout, TimeUnit.MINUTES));
        dynamicContext.setSkuVO(skuVOFutureTask.get(timeout, TimeUnit.MINUTES));
    }

    @Override
    protected TrialBalanceEntity doApply(MarketProductEntity requestParam, DefaultActivityStrategyFactory.DynamicContext dynamicContext) throws Exception {
        return router(requestParam, dynamicContext);
    }

    @Override
    public StrategyHandler<MarketProductEntity, DefaultActivityStrategyFactory.DynamicContext, TrialBalanceEntity> get(MarketProductEntity requestParam, DefaultActivityStrategyFactory.DynamicContext dynamicContext) throws Exception {
        return endNode;
    }
}
