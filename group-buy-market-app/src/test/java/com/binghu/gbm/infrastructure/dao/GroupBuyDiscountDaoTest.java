package com.binghu.gbm.infrastructure.dao;

import com.alibaba.fastjson.JSON;
import com.binghu.gbm.infrastructure.dao.po.GroupBuyDiscount;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class GroupBuyDiscountDaoTest {

    @Resource
    private IGroupBuyDiscountDao groupBuyDiscountDao;

    @Test
    public void test01() {
        List<GroupBuyDiscount> groupBuyDiscounts = groupBuyDiscountDao.queryGroupBuyDiscountList();
        log.info("{}", JSON.toJSONString(groupBuyDiscounts));
    }
}
