package com.binghu.gbm.domain.activity.service.trail;

import com.binghu.gbm.domain.activity.model.entity.MarketProductEntity;
import com.binghu.gbm.domain.activity.model.entity.TrialBalanceEntity;

/**
 * 营销首页服务接口
 */
public interface IIndexGroupBuyMarketService {
    TrialBalanceEntity indexMarketTrial(MarketProductEntity marketProductEntity) throws Exception;
}
