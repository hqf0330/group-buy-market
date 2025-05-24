package com.binghu.gbm.infrastructure.dao;

import com.binghu.gbm.infrastructure.dao.po.GroupBuyActivity;
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
public class GroupBuyActivityDaoTest {

    @Resource
    private IGroupBuyActivityDao groupBuyActivityDao;

    @Test
    public void test01() {
        List<GroupBuyActivity> groupBuyActivities = groupBuyActivityDao.queryGroupBuyActivityList();
        log.info("测试结果：{}", groupBuyActivities);
    }
}
