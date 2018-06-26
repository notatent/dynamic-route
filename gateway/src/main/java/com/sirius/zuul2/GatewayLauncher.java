package com.sirius.zuul2;

import com.google.inject.Injector;
import com.netflix.config.ConfigurationManager;
import com.netflix.governator.InjectorBuilder;
import com.netflix.zuul.netty.server.BaseServerStartup;
import com.netflix.zuul.netty.server.Server;

public class GatewayLauncher {

    public static void main(String[] args) {
        start();
    }

    public static void start() {
        long startTime = System.currentTimeMillis();
        int exitCode = 0;

        Server server = null;

        try {
            ConfigurationManager.loadCascadedPropertiesFromResources("application");
            Injector injector = InjectorBuilder.fromModule(new DynamicRouteModule()).createInjector();
            BaseServerStartup serverStartup = injector.getInstance(BaseServerStartup.class);
            server = serverStartup.server();

            long startupDuration = System.currentTimeMillis() - startTime;

            server.start(true);
        } catch (Throwable t) {
            t.printStackTrace();
            exitCode = 1;
        } finally {
            // server shutdown
            if (server != null) {
                server.stop();
            }

            System.exit(exitCode);
        }
    }
}
