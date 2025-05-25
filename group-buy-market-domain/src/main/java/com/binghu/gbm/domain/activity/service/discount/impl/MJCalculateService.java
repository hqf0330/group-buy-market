package com.binghu.gbm.domain.activity.service.discount.impl;

import com.binghu.gbm.domain.activity.model.valobj.GroupBuyActivityDiscountVO;
import com.binghu.gbm.domain.activity.service.discount.AbstractDiscountCalculateService;
import com.binghu.gbm.types.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service("MJ")
public class MJCalculateService extends AbstractDiscountCalculateService {

    @Override
    protected BigDecimal doCalculate(BigDecimal originalPrice, GroupBuyActivityDiscountVO.GroupBuyDiscount groupBuyDiscount) {
        log.info("优惠策略折扣计算: {}", groupBuyDiscount.getDiscountType().getCode());

        // 满减折扣表达式
        String marketExpr = groupBuyDiscount.getMarketExpr();
        String[] split = marketExpr.split(Constants.SPLIT);
        BigDecimal x = new BigDecimal(split[0].trim());
        BigDecimal y = new BigDecimal(split[1].trim());

        // 不满足满减价格
        if (originalPrice.compareTo(x) < 0) {
            return originalPrice;
        }

        // 折扣价格
        BigDecimal deductPrice = originalPrice.subtract(y);

        // 兜底
        if (deductPrice.compareTo(BigDecimal.ZERO) <= 0) {
            return new BigDecimal(0.01);
        }

        return deductPrice;
    }
}
