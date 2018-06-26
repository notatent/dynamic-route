package com.sirius.zuul2;

import com.coreos.jetcd.data.ByteSequence;
import com.coreos.jetcd.kv.GetResponse;
import com.coreos.jetcd.options.GetOption;
import com.netflix.client.config.CommonClientConfigKey;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractServerList;
import com.netflix.loadbalancer.Server;
import com.sirius.zuul2.etcd.ETCDClientHodler;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ETCDServerList extends AbstractServerList<Server> {

    private static Logger logger = LoggerFactory.getLogger(ETCDServerList.class);

    // TODO config
    static {
        ETCDClientHodler.init("http://127.0.0.1:23791", "http://127.0.0.1:23792", "http://127.0.0.1:23793");
    }

    protected IClientConfig clientConfig;
    protected String clientName;
    protected String serviceName;

    @Override
    public void initWithNiwsConfig(IClientConfig clientConfig) {
        this.clientConfig = clientConfig;
        this.clientName = clientConfig.getClientName();
        this.serviceName = clientConfig.get(CommonClientConfigKey.DeploymentContextBasedVipAddresses);
        Validate.notBlank(this.serviceName, "VIP address for client:[{}] is null", this.clientName);
    }

    @Override
    public List<Server> getInitialListOfServers() {
        return getUpdatedListOfServers();
    }

    @Override
    public List<Server> getUpdatedListOfServers() {

        List<Server> servers = new ArrayList<>();

        GetOption option = GetOption.newBuilder().withPrefix(new ByteSequence("true")).build();
        String prefix = String.format("/endpoints/rpc/%s/", this.serviceName);
        try {
            GetResponse response = ETCDClientHodler
                    .client()
                    .getKVClient()
                    .get(new ByteSequence(prefix), option)
                    .get();

            logger.info("service:{} endpoint count is:{}", prefix, response.getCount());
            response.getKvs().forEach(kv -> {
                String[] host_port = kv.getKey().toStringUtf8().replace(prefix, "").split("#");
                logger.info("########{}", Arrays.toString(host_port));
                Server server = new Server("http", host_port[0], Integer.parseInt(host_port[1]));
                servers.add(server);
            });
        } catch (Exception e) {
            // TODO logger
            e.printStackTrace();
        }

        return servers;
    }

}
