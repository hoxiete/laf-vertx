package com.jd.laf.web.vertx.config;

import javax.xml.bind.annotation.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 配置
 */
@XmlRootElement(name = "vertx")
@XmlAccessorType(XmlAccessType.NONE)
public class VertxConfig {
    //路由处理器
    @XmlElementWrapper
    @XmlElement(name = "route")
    List<RouteConfig> routes = new ArrayList<>(50);
    //消息处理器
    @XmlElementWrapper
    @XmlElement(name = "message")
    List<MessageConfig> messages = new ArrayList<>(10);
    //连接处理器
    @XmlAttribute
    String connection;
    //连接异常处理器
    @XmlAttribute
    String exception;

    public VertxConfig() {
    }

    public List<RouteConfig> getRoutes() {
        return routes;
    }

    public void setRoutes(List<RouteConfig> routes) {
        this.routes = routes;
    }

    public List<MessageConfig> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageConfig> messages) {
        this.messages = messages;
    }

    public String getConnection() {
        return connection;
    }

    public void setConnection(String connection) {
        this.connection = connection;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    /**
     * 添加路由处理器配制
     *
     * @param config 路由处理器配制
     */
    public void add(final RouteConfig config) {
        if (config != null) {
            routes.add(config);
        }
    }

    /**
     * 添加路由处理器配制
     *
     * @param config 路由处理器配制
     */
    public void add(final MessageConfig config) {
        if (config != null) {
            messages.add(config);
        }
    }

    /**
     * 处理继承
     *
     * @param config
     * @return
     */
    public static VertxConfig inherit(final VertxConfig config) {
        if (config == null) {
            return null;
        }
        List<RouteConfig> routes = config.getRoutes();
        if (routes == null || routes.isEmpty()) {
            return config;
        }
        LinkedList<RouteConfig> inherits = config.getRoutes().stream().
                filter(a -> !a.isRoute() && a.getInherit() != null && !a.getInherit().isEmpty()).
                collect(Collectors.toCollection(LinkedList::new));
        if (inherits == null || inherits.isEmpty()) {
            return config;
        }

        Map<String, RouteConfig> map = config.getRoutes().stream().filter(a -> a.getName() != null)
                .collect(Collectors.toMap(a -> a.getName(), a -> a));
        RouteConfig parent;
        //当前节点遍历过的节点，防止递归
        Set<RouteConfig> graph = new HashSet<>(map.size());
        for (RouteConfig cfg : inherits) {
            //获取当前节点
            parent = map.get(cfg.getInherit());
            //清理当前节点遍历过的节点
            graph.clear();
            graph.add(cfg);
            while (parent != null && graph.add(parent)) {
                cfg.inherit(parent);
                parent = parent.getInherit() == null || parent.getInherit().isEmpty() ? null : map.get(parent.getInherit());
            }
        }
        return config;
    }

}
