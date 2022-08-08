package com.jd.laf.web.vertx.config;

import javax.xml.bind.JAXBException;
import java.io.IOException;

/**
 * 构造器
 */
public interface RouterBuilder {

    /**
     * 构造配置，没有处理继承
     *
     * @throws IOException
     * @throws JAXBException
     */
    public VertxConfig build() throws Exception;

}