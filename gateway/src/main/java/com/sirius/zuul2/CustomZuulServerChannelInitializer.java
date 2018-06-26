package com.sirius.zuul2;

import com.netflix.netty.common.channel.config.ChannelConfig;
import com.netflix.zuul.FilterLoader;
import com.netflix.zuul.FilterUsageNotifier;
import com.netflix.zuul.message.http.HttpResponseMessage;
import com.netflix.zuul.netty.filter.ZuulEndPointRunner;
import com.netflix.zuul.netty.filter.ZuulFilterChainRunner;
import com.netflix.zuul.netty.server.ZuulServerChannelInitializer;
import io.netty.channel.group.ChannelGroup;

public class CustomZuulServerChannelInitializer extends ZuulServerChannelInitializer {

    public CustomZuulServerChannelInitializer(int port,
            ChannelConfig channelConfig,
            ChannelConfig channelDependencies,
            ChannelGroup channels) {
        super(port, channelConfig, channelDependencies, channels);
    }

    @Override
    protected ZuulEndPointRunner getEndpointRunner(ZuulFilterChainRunner<HttpResponseMessage> responseFilterChain,
            FilterUsageNotifier filterUsageNotifier,
            FilterLoader filterLoader) {
         return new CustomZuulEndPointRunner(filterUsageNotifier, filterLoader, responseFilterChain);
    }
}
