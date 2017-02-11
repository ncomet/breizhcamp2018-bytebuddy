package interceptors;

import java.util.Set;
import java.util.concurrent.Callable;

import net.bytebuddy.implementation.bind.annotation.SuperCall;

/**
 * LECTRA
 * MutantInterceptor class
 *
 * @author n.comet
 */
public class MutantInterceptor {

    public static Set<String> intercept(@SuperCall Callable<Set<String>> callable) {
        try {
            final Set<String> stomach = callable.call();
            stomach.add("baby alien");
            return stomach;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
