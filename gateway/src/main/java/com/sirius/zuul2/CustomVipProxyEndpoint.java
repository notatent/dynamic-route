package com.sirius.zuul2;

import com.netflix.zuul.filters.endpoint.ProxyEndpoint;
import com.netflix.zuul.message.http.HttpRequestMessage;
import com.netflix.zuul.message.http.HttpResponseMessage;
import com.netflix.zuul.netty.NettyRequestAttemptFactory;
import com.netflix.zuul.netty.filter.FilterRunner;
import com.netflix.zuul.netty.server.MethodBinding;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

public class CustomVipProxyEndpoint extends ProxyEndpoint {

    public static final String XX_RPC_SERVICE_NAME = "xx-rpc-service-name";
    public static final String XX_RPC_CLIENT_NAME = "xx-rpc-client-name";

    public CustomVipProxyEndpoint(HttpRequestMessage inMesg,
            ChannelHandlerContext ctx,
            FilterRunner<HttpResponseMessage, ?> filters,
            MethodBinding<?> methodBinding) {
        super(inMesg, ctx, filters, methodBinding);
    }

    public CustomVipProxyEndpoint(HttpRequestMessage inMesg,
            ChannelHandlerContext ctx,
            FilterRunner<HttpResponseMessage, ?> filters,
            MethodBinding<?> methodBinding, NettyRequestAttemptFactory requestAttemptFactory) {
        super(inMesg, ctx, filters, methodBinding, requestAttemptFactory);
    }

    @Override
    protected Pair<String, String> injectCustomVip(HttpRequestMessage request) {
        String restClientVIP = request.getHeaders().getFirst(XX_RPC_SERVICE_NAME);
        if (restClientVIP == null) {
            restClientVIP = request.getQueryParams().getFirst(XX_RPC_SERVICE_NAME);
        }

        String restClientName = request.getHeaders().getFirst(XX_RPC_CLIENT_NAME);
        if (restClientName == null) {
            restClientName = request.getQueryParams().getFirst(XX_RPC_CLIENT_NAME);
        }

        return ImmutablePair.of(restClientVIP, restClientName);
    }
}
