package com.jd.laf.web.vertx.spring.boot;

import com.jd.laf.web.vertx.RouteProvider;
import com.jd.laf.web.vertx.RoutingVerticle;
import com.jd.laf.web.vertx.config.RouterBuilder;
import com.jd.laf.web.vertx.spring.RoutingVerticleProvider;
import com.jd.laf.web.vertx.spring.SpringEnvironment;
import com.jd.laf.web.vertx.spring.SpringRouterBuilder;
import com.jd.laf.web.vertx.spring.VerticleProvider;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@EnableConfigurationProperties(VertxWebProperties.class)
public class VertxWebAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(RouterBuilder.class)
    public RouterBuilder routerBuilder(VertxWebProperties webProperties){
        return new SpringRouterBuilder(webProperties.getHandler());
    }

    @Bean
    @ConditionalOnMissingBean(RoutingVerticleProvider.class)
    public VerticleProvider routingVerticle(
            org.springframework.core.env.Environment environment,
            ApplicationContext context,
            VertxWebProperties webProperties,
            ObjectProvider<RouterBuilder> routerBuilder,
            ObjectProvider<List<RouteProvider>> provider) {
        return new RoutingVerticleProvider(() -> new RoutingVerticle(new SpringEnvironment(environment, context),
                webProperties.getHttp().toHttpServerOptions(), routerBuilder.getIfAvailable(), provider.getIfAvailable()),
                webProperties.getRouting().toDeploymentOptions());
    }


}
