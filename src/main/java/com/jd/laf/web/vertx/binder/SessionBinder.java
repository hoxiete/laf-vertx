package com.jd.laf.web.vertx.binder;

import com.jd.laf.binding.binder.Binder;
import com.jd.laf.binding.reflect.exception.ReflectionException;
import com.jd.laf.web.vertx.annotation.CSession;
import io.vertx.ext.web.RoutingContext;

/**
 * 请求绑定
 */
public class SessionBinder implements Binder {
    @Override
    public boolean bind(final Context context) throws ReflectionException {
        RoutingContext ctx = (RoutingContext) context.getSource();
        return context.bind(ctx.session());
    }

    @Override
    public Class<?> annotation() {
        return CSession.class;
    }
}
