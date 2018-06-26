package com.sirius.zuul2;

import com.netflix.client.config.CommonClientConfigKey;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import com.netflix.spectator.api.Registry;
import com.netflix.zuul.origins.BasicNettyOrigin;

public class SmartBasicNettyOrigin extends BasicNettyOrigin {

    public SmartBasicNettyOrigin(String name, String vip, Registry registry) {
        super(name, vip, registry);
    }

    @Override
    protected IClientConfig setupClientConfig(String name) {

        IClientConfig niwsClientConfig = DefaultClientConfigImpl.getClientConfigWithDefaultValues(name);
        niwsClientConfig.loadProperties(name);

        niwsClientConfig.set(CommonClientConfigKey.ClientClassName, name);

        // 这里动态设置传入的vip, 即要请求的后端服务的service元数据(name+version)
        niwsClientConfig.set(CommonClientConfigKey.DeploymentContextBasedVipAddresses, getVip());
        // 使用etcd作为注册中心存储
        niwsClientConfig.set(CommonClientConfigKey.NIWSServerListClassName, ETCDServerList.class.getCanonicalName());

        return niwsClientConfig;
    }
}
