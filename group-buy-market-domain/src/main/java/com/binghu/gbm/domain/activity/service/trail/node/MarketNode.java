package com.binghu.gbm.domain.activity.service.trail.node;

import com.binghu.gbm.domain.activity.model.entity.MarketProductEntity;
import com.binghu.gbm.domain.activity.model.entity.TrialBalanceEntity;
import com.binghu.gbm.domain.activity.model.valobj.GroupBuyActivityDiscountVO;
import com.binghu.gbm.domain.activity.model.valobj.SkuVO;
import com.binghu.gbm.domain.activity.service.discount.IDiscountCalculateService;
import com.binghu.gbm.domain.activity.service.trail.AbstractGroupBuyMarketSupport;
import com.binghu.gbm.domain.activity.service.trail.factory.DefaultActivityStrategyFactory;
import com.binghu.gbm.domain.activity.service.trail.thread.QueryGroupBuyActivityDiscountVOThreadTask;
import com.binghu.gbm.domain.activity.service.trail.thread.QuerySkuVOFromDBThreadTask;
import com.binghu.gbm.types.design.framework.tree.StrategyHandler;
import com.binghu.gbm.types.enums.ResponseCode;
import com.binghu.gbm.types.exception.AppException;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
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

    @Resource
    private Map<String, IDiscountCalculateService> discountCalculateServiceMap;

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

        GroupBuyActivityDiscountVO groupBuyActivityDiscountVO = dynamicContext.getGroupBuyActivityDiscountVO();
        GroupBuyActivityDiscountVO.GroupBuyDiscount groupBuyDiscount = groupBuyActivityDiscountVO.getGroupBuyDiscount();
        SkuVO skuVO = dynamicContext.getSkuVO();

        IDiscountCalculateService discountCalculateService = discountCalculateServiceMap.get(groupBuyDiscount.getMarketPlan());
        if (discountCalculateService == null) {
            log.info("不存在类型为: {}的计算服务，支持类型为: {}", groupBuyDiscount.getMarketPlan(), discountCalculateServiceMap.keySet());
            throw new AppException(ResponseCode.E0001.getCode(), ResponseCode.E0001.getInfo());
        }

        BigDecimal deductPrice = discountCalculateService.calculate(requestParam.getUserId(), skuVO.getOriginalPrice(), groupBuyDiscount);
        dynamicContext.setDeductionPrice(deductPrice);

        return router(requestParam, dynamicContext);
    }

    @Override
    public StrategyHandler<MarketProductEntity, DefaultActivityStrategyFactory.DynamicContext, TrialBalanceEntity> get(MarketProductEntity requestParam, DefaultActivityStrategyFactory.DynamicContext dynamicContext) throws Exception {
        return endNode;
    }
}
