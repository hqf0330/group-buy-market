package com.binghu.gbm.domain.activity.service.trail.thread;

import com.binghu.gbm.domain.activity.adapter.repository.IActivityRepository;
import com.binghu.gbm.domain.activity.model.valobj.SkuVO;

import java.util.concurrent.Callable;

public class QuerySkuVOFromDBThreadTask implements Callable<SkuVO> {

    private final String goodsId;

    private final IActivityRepository activityRepository;

    public QuerySkuVOFromDBThreadTask(String goodsId, IActivityRepository activityRepository) {
        this.goodsId = goodsId;
        this.activityRepository = activityRepository;
    }

    @Override
    public SkuVO call() throws Exception {
        return activityRepository.querySkuByGoodsId(goodsId);
    }
}
