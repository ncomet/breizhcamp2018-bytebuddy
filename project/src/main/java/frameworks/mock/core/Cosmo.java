package frameworks.mock.core;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.implementation.FixedValue;

import static net.bytebuddy.matcher.ElementMatchers.isEquals;
import static net.bytebuddy.matcher.ElementMatchers.isNative;
import static net.bytebuddy.matcher.ElementMatchers.not;
import static net.bytebuddy.matcher.ElementMatchers.returns;

/**
 * LECTRA
 * Cosmo class
 *
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
                    .load(classToMock.getClassLoader())
                    .getLoaded()
                    .newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

}
