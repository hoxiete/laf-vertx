package com.jd.laf.web.vertx.config;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;

/**
 * 构造器
 */
public class XmlRouterBuilder implements RouterBuilder {

    public static final String DEFAULT_ROUTING_CONFIG_FILE = "routing.xml";
    private String file = "";

    public XmlRouterBuilder() {
        this.file = DEFAULT_ROUTING_CONFIG_FILE;
    }
    public XmlRouterBuilder(String file) {
        this.file = file;
    }

    /**
     * 构造配置，没有处理继承
     *
     * @param reader
     * @return
     * @throws JAXBException
     */
    public VertxConfig build(final Reader reader) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(VertxConfig.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (VertxConfig) unmarshaller.unmarshal(reader);
    }

    /**
     * 构造配置，没有处理继承
     *
     * @throws IOException
     * @throws JAXBException
     */
    public VertxConfig build() throws IOException, JAXBException {
        if (file == null || file.isEmpty()) {
            throw new IllegalStateException("file can not be empty.");
        }
        InputStream in;
        BufferedReader reader = null;
        try {
            File f = new File(file);
            if (f.exists()) {
                in = new FileInputStream(f);
            } else {
                in = Thread.currentThread().getContextClassLoader().getResourceAsStream(file);
                if (in == null) {
                    in = VertxConfig.class.getClassLoader().getResourceAsStream(file);
                    if (in == null) {
                        throw new IOException("file is not found. " + file);
                    }
                }
            }
            reader = new BufferedReader(new InputStreamReader(in));
            return build(reader);
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

}