package test;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.zookeeper.ZookeeperDataSource;
import com.alibaba.csp.sentinel.init.InitFunc;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRuleManager;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRuleManager;
import com.alibaba.csp.sentinel.slots.system.SystemRule;
import com.alibaba.csp.sentinel.slots.system.SystemRuleManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

/**
 * Zookeeper ReadableDataSource Demo
 *
 * @author guonanjun
 */
public class ZookeeperDataSourceDemo implements InitFunc {


    @Override
    public void init() throws Exception {

        Properties properties = System.getProperties();
        Set<Map.Entry<Object, Object>> set = properties.entrySet();
        System.out.println("=============================");
        String ProjectName = "";
        for (Map.Entry<Object, Object> objectObjectEntry : set) {
            if ("project.name".equals(objectObjectEntry.getKey())) {
                ProjectName = objectObjectEntry.getValue().toString();
            }

            System.out.println(objectObjectEntry.getKey() + ":" + objectObjectEntry.getValue());
        }

        System.out.println(properties.getProperty("env"));
        System.out.println("=============================");
        if ("".equals(ProjectName)) {
            throw new Exception("应用名称未指定");
        }
        final String RemoteAddress = "sunazl.cn:2181";
        String FlowPath = "/sentinel/rules/" + ProjectName + "/flow";
        String DegradePath = "/sentinel/rules/" + ProjectName + "/degrade";
        String ParamPath = "/sentinel/rules/" + ProjectName + "/param";
        String SystemPath = "/sentinel/rules/" + ProjectName + "/system";
        String AuthorityPath = "/sentinel/rules/" + ProjectName + "/authority";
        //流控
        ReadableDataSource<String, List<FlowRule>> flowRuleDataSource = new ZookeeperDataSource<>(RemoteAddress, FlowPath,
                source -> JSON.parseObject(source, new TypeReference<List<FlowRule>>() {}));
        FlowRuleManager.register2Property(flowRuleDataSource.getProperty());
        //降级
        ReadableDataSource<String, List<DegradeRule>> DegradeRuleDataSource = new ZookeeperDataSource<>(RemoteAddress, DegradePath,
                source -> JSON.parseObject(source, new TypeReference<List<DegradeRule>>() {}));
        DegradeRuleManager.register2Property(DegradeRuleDataSource.getProperty());
        //授权
        ReadableDataSource<String, List<AuthorityRule>> AuthorityRuleDataSource = new ZookeeperDataSource<>(RemoteAddress, AuthorityPath,
                source -> JSON.parseObject(source, new TypeReference<List<AuthorityRule>>() {}));
        AuthorityRuleManager.register2Property(AuthorityRuleDataSource.getProperty());
        //配置?
        ReadableDataSource<String, List<ParamFlowRule>> paramFlowRleDataSource = new ZookeeperDataSource<>(RemoteAddress,ParamPath,
                source -> JSON.parseObject(source, new TypeReference<List<ParamFlowRule>>() {}));
        ParamFlowRuleManager.register2Property(paramFlowRleDataSource.getProperty());
        //系统
        ReadableDataSource<String, List<SystemRule>> systemRuleDataSource = new ZookeeperDataSource<>(RemoteAddress,SystemPath,
                source -> JSON.parseObject(source, new TypeReference<List<SystemRule>>() {}));
        SystemRuleManager.register2Property(systemRuleDataSource.getProperty());



    }
}
