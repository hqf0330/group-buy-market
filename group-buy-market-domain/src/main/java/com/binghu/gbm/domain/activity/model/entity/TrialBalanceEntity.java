package com.binghu.gbm.domain.activity.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrialBalanceEntity {

    /**
     * 商品id
     */
    private String goodsId;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 商品原价
     */
    private BigDecimal originalPrice;

    /**
     * 商品折扣价
     */
    private BigDecimal deductPrice;

    /**
     * 拼团目标数量
     */
    private Integer targetCount;

    /**
     * 拼团开始时间
     */
    private Date startTime;

    /**
     * 拼团结束时间
     */
    private Date endTime;

    /**
     * 是否可见拼团
     */
    private Boolean isVisible;

    /**
     * 是否可参与进团
     */
    private Boolean isEnable;
}
