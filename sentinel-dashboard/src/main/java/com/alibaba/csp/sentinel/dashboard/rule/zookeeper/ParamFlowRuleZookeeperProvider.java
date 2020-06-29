package com.alibaba.csp.sentinel.dashboard.rule.zookeeper;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.ParamFlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRuleZookeeperProvider;
import org.apache.curator.framework.CuratorFramework;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


public class ParamFlowRuleZookeeperProvider implements DynamicRuleZookeeperProvider<ParamFlowRuleEntity> {


    @Autowired
    private CuratorFramework client;

    public static final String PATH = "/sentinel/rules/%s/param";

    @Override
    public String getPathFormat() {
        return PATH;
    }

    @Override
    public CuratorFramework getClient() {
        return client;
    }

    @Override
    public Class<ParamFlowRuleEntity> getTClass() {
        return ParamFlowRuleEntity.class;
    }
}
