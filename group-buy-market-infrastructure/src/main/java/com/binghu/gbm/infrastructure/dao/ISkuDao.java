package com.binghu.gbm.infrastructure.dao;

import com.binghu.gbm.infrastructure.dao.po.Sku;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ISkuDao {

    Sku querySkuByGoodsId(String goodsId);

}
