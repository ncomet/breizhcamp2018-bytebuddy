package frameworks.mock.core;

import static net.bytebuddy.matcher.ElementMatchers.any;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.FixedValue;

/**
 * LECTRA
 * Cosmo class
 * @author n.comet
 */
public class Cosmo {


    public <T> T mock(Class<T> classToMock) {
        try {
            return new ByteBuddy()
                    .subclass(classToMock)
                    .method(any())
                    .intercept(FixedValue.nullValue())
                    .make()
                    .load(classToMock.getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
                    .getLoaded()
                    .newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

}
