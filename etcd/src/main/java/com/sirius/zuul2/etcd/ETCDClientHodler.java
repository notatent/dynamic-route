package com.sirius.zuul2.etcd;

import com.coreos.jetcd.Client;
import com.coreos.jetcd.data.ByteSequence;
import io.grpc.util.RoundRobinLoadBalancerFactory;

public class ETCDClientHodler {

    private static Client client = null;

    public static void init(String user, String authority, String... endpoints) {
        client = Client.builder()
                .endpoints(endpoints)
                .loadBalancerFactory(RoundRobinLoadBalancerFactory.getInstance())
                .user(new ByteSequence(user))
                .authority(authority)
                .build();
    }

    public static Client client() {
        return client;
    }

}
