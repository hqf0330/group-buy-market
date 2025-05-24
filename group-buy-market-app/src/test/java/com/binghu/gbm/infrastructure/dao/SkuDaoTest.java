package com.binghu.gbm.infrastructure.dao;

import com.alibaba.fastjson.JSON;
import com.binghu.gbm.infrastructure.dao.po.Sku;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class SkuDaoTest {

    @Resource
    private ISkuDao skuDao;

    @Test
    public void test01() {
        Sku sku = skuDao.querySkuByGoodsId("9890001");
        log.info("测试结果：{}", JSON.toJSONString(sku));
    }
}
