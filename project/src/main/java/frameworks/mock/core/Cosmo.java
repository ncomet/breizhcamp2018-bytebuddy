package frameworks.mock.core;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.FixedValue;

import static net.bytebuddy.matcher.ElementMatchers.*;

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
                    .method(not(isNative().or(isEquals()).or(returns(TypeDescription.VOID))))
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
