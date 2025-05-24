package com.binghu.gbm.domain.activity.service.trail.node;

import com.binghu.gbm.domain.activity.model.entity.MarketProductEntity;
import com.binghu.gbm.domain.activity.model.entity.TrialBalanceEntity;
import com.binghu.gbm.domain.activity.service.trail.AbstractGroupBuyMarketSupport;
import com.binghu.gbm.domain.activity.service.trail.factory.DefaultActivityStrategyFactory;
import com.binghu.gbm.types.design.framework.tree.StrategyHandler;
import com.binghu.gbm.types.enums.ResponseCode;
import com.binghu.gbm.types.exception.AppException;
import jakarta.annotation.Resource;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RootNode extends AbstractGroupBuyMarketSupport {

    @Resource
    private SwitchNode switchNode;

    @Override
    protected TrialBalanceEntity doApply(MarketProductEntity requestParam, DefaultActivityStrategyFactory.DynamicContext dynamicContext) throws Exception {
        if (StringUtils.isBlank(requestParam.getUserId())
        || StringUtils.isBlank(requestParam.getChannel())
        || StringUtils.isBlank(requestParam.getSource())
        || StringUtils.isBlank(requestParam.getGoodsId())) {
            throw new AppException(ResponseCode.ILLEGAL_PARAMETER.getCode(), ResponseCode.ILLEGAL_PARAMETER.getInfo());
        }
        return router(requestParam, dynamicContext);
    }

    @Override
    public StrategyHandler<MarketProductEntity, DefaultActivityStrategyFactory.DynamicContext, TrialBalanceEntity> get(MarketProductEntity requestParam, DefaultActivityStrategyFactory.DynamicContext dynamicContext) throws Exception {
        return switchNode;
    }
}
