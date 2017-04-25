package frameworks.security.impl;

import static net.bytebuddy.implementation.MethodDelegation.to;
import static net.bytebuddy.matcher.ElementMatchers.isAnnotatedWith;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import frameworks.security.annotation.Lock;
import frameworks.security.interfaces.Safe;

/**
 * LECTRA
 * BuddySafe class
 * @author n.comet
 */
public class BuddySafe implements Safe {

    private String keyAttempt;

    public BuddySafe(String keyAttempt) {
        this.keyAttempt = keyAttempt;
    }

    @Override
    public <T> T safeInstance(Class<T> instance) {
        try {
            return new ByteBuddy()
                    .subclass(instance)
                    .method(isAnnotatedWith(Lock.class))
                    .intercept(to(this))
                    .make()
                    .load(instance.getClassLoader())
                    .getLoaded()
                    .newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RuntimeType
    public Object intercept(@SuperCall Callable<?> superMethod, @Origin Method method) throws Exception {
        if (!method.getAnnotation(Lock.class).key().equals(keyAttempt)) {
            throw new IllegalStateException("[" + method + "] cannot be called, key is not correct.");
        }
        return superMethod.call();
    }
}
