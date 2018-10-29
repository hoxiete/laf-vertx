package com.jd.laf.web.vertx;

import io.vertx.ext.web.impl.Utils;
import org.junit.Test;

public class UtilsTest {

    public static final int count = 1000000;

    @Test
    public void testNormalizePath() {
        String path = Utils.normalizePath("www.jd.com/abcd/../acd%41");
        System.out.println(path);
        path = Utils.normalizePath("www.jd.com/abcd/..");
        System.out.println(path);
        path = Utils.normalizePath("www.jd.com/abcd//");
        System.out.println(path);
        path = Utils.normalizePath("www.jd.com/abcd/../..");
        System.out.println(path);
        path = Utils.normalizePath("www.jd.com/abcd/../..a");
        System.out.println(path);
        path = Utils.normalizePath("www.jd.com/abcd/%2E./acd%41");
        System.out.println(path);

        long time = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            Utils.normalizePath("/v1/namespace/hexiaofeng/config/test/profiles/test");
        }
        System.out.println("tps:" + count * 1000 / (System.currentTimeMillis() - time));

    }


}
