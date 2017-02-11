package interceptors;

import java.util.concurrent.Callable;

import net.bytebuddy.implementation.bind.annotation.Argument;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

/**
 * LECTRA
 * GetterSetterInterceptor class
 * @author n.comet
 */
public class GetterSetterInterceptor {

    @RuntimeType
    public static Object get(@SuperCall Callable<String> callable) {
        System.out.println("call to [get] method");
        try {
            return callable.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void set(@Argument(0) Object value, @SuperCall Callable<?> callable) {
        System.out.println("call to [set] method");
        try {
            callable.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
