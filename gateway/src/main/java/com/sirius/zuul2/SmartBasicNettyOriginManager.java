package com.sirius.zuul2;

import com.netflix.spectator.api.Registry;
import com.netflix.zuul.context.SessionContext;
import com.netflix.zuul.origins.OriginManager;

import javax.inject.Inject;
import java.util.concurrent.ConcurrentHashMap;

public class SmartBasicNettyOriginManager implements OriginManager<SmartBasicNettyOrigin> {

    private final Registry registry;
    private final ConcurrentHashMap<String, SmartBasicNettyOrigin> originMappings;

    @Inject
    public SmartBasicNettyOriginManager(Registry registry) {
        this.registry = registry;
        this.originMappings = new ConcurrentHashMap<>();
    }

    @Override
    public SmartBasicNettyOrigin getOrigin(String name, String vip, String uri, SessionContext ctx) {
        return originMappings.computeIfAbsent(name, n -> createOrigin(name, vip, uri, false, ctx));
    }

    @Override
    public SmartBasicNettyOrigin createOrigin(String name,
            String vip,
            String uri,
            boolean useFullVipName,
            SessionContext ctx) {
        return new SmartBasicNettyOrigin(name, vip, registry);
    }
}
