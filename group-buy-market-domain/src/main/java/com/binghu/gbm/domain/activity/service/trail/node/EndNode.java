package com.binghu.gbm.domain.activity.service.trail.node;

import com.binghu.gbm.domain.activity.model.entity.MarketProductEntity;
import com.binghu.gbm.domain.activity.model.entity.TrialBalanceEntity;
import com.binghu.gbm.domain.activity.model.valobj.GroupBuyActivityDiscountVO;
import com.binghu.gbm.domain.activity.model.valobj.SkuVO;
import com.binghu.gbm.domain.activity.service.trail.AbstractGroupBuyMarketSupport;
import com.binghu.gbm.domain.activity.service.trail.factory.DefaultActivityStrategyFactory;
import com.binghu.gbm.types.design.framework.tree.StrategyHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
public class EndNode extends AbstractGroupBuyMarketSupport {

    @Override
    protected TrialBalanceEntity doApply(MarketProductEntity requestParam,
                                         DefaultActivityStrategyFactory.DynamicContext dynamicContext) {
        GroupBuyActivityDiscountVO groupBuyActivityDiscountVO = dynamicContext.getGroupBuyActivityDiscountVO();
        SkuVO skuVO = dynamicContext.getSkuVO();
        return TrialBalanceEntity.builder()
                .goodsId(skuVO.getGoodsId())
                .goodsName(skuVO.getGoodsName())
                .originalPrice(skuVO.getOriginalPrice())
                .deductPrice(new BigDecimal("0.00"))
                .targetCount(groupBuyActivityDiscountVO.getTarget())
                .startTime(groupBuyActivityDiscountVO.getStartTime())
                .endTime(groupBuyActivityDiscountVO.getEndTime())
                .isVisible(false)
                .isEnable(false)
                .build();
    }

    @Override
    public StrategyHandler<MarketProductEntity, DefaultActivityStrategyFactory.DynamicContext, TrialBalanceEntity> get(MarketProductEntity requestParam, DefaultActivityStrategyFactory.DynamicContext dynamicContext) throws Exception {
        return defaultStrategyHandler;
    }
}
