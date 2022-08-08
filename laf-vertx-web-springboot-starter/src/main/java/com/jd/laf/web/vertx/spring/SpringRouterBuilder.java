package com.jd.laf.web.vertx.spring;

import com.jd.laf.web.vertx.config.RouterBuilder;
import com.jd.laf.web.vertx.config.VertxConfig;

public class SpringRouterBuilder implements RouterBuilder {

    private VertxConfig config;

    public SpringRouterBuilder(VertxConfig config) {
        this.config = config;
    }

    @Override
    public VertxConfig build() throws Exception {
        return config;
    }
}
