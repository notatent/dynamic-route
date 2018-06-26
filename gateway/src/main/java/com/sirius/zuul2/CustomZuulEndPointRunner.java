package com.sirius.zuul2;

import com.netflix.zuul.FilterLoader;
import com.netflix.zuul.FilterUsageNotifier;
import com.netflix.zuul.filters.ZuulFilter;
import com.netflix.zuul.message.http.HttpRequestMessage;
import com.netflix.zuul.message.http.HttpResponseMessage;
import com.netflix.zuul.netty.filter.FilterRunner;
import com.netflix.zuul.netty.filter.ZuulEndPointRunner;
import com.netflix.zuul.netty.server.MethodBinding;

public class CustomZuulEndPointRunner extends ZuulEndPointRunner {

    public CustomZuulEndPointRunner(FilterUsageNotifier usageNotifier,
            FilterLoader filterLoader,
            FilterRunner<HttpResponseMessage, HttpResponseMessage> respFilters) {
        super(usageNotifier, filterLoader, respFilters);
    }

    @Override
    protected ZuulFilter<HttpRequestMessage, HttpResponseMessage> newProxyEndpoint(HttpRequestMessage zuulRequest) {
        return new CustomVipProxyEndpoint(zuulRequest,
                getChannelHandlerContext(zuulRequest),
                getNextStage(),
                MethodBinding.NO_OP_BINDING);
    }
}
