package domain;

import static net.bytebuddy.implementation.MethodDelegation.to;
import static net.bytebuddy.matcher.ElementMatchers.nameStartsWith;
import static net.bytebuddy.matcher.ElementMatchers.named;
import static org.assertj.core.api.Assertions.assertThat;

import interceptors.MutantInterceptor;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;

import org.mockito.Mockito;
import org.testng.annotations.Test;

import frameworks.mock.Cosmockpolitan;
import interceptors.GetterSetterInterceptor;

/**
 * LECTRA
 * BuddyTests class
 * @author n.comet
 */
public class BuddyTests {

    @Test
    public void testJava() {
        final Cat cat = new Cat();
        cat.setName("Garfield");
        cat.feed("croquettes");
        cat.feed("patee");

        assertThat(cat.getStomach()).containsExactly("croquettes", "patee");
        assertThat(cat.getName()).isEqualTo("Garfield");
    }

    @Test
    public void testBuddy() throws IllegalAccessException, InstantiationException {
        final Cat cat = new ByteBuddy()
                .subclass(Cat.class)
               /* .method(named("getStomach"))
                .intercept(to(MutantInterceptor.class)) */
                .make()
                .load(getClass().getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
                .getLoaded().newInstance();

        cat.setName("Garfield");
        cat.feed("croquettes");
        cat.feed("patee");

        assertThat(cat.getStomach()).containsExactly("croquettes", "patee");
        assertThat(cat.getName()).isEqualTo("Garfield");
    }

    @Test
    public void testGettersSetters() throws Exception {

        final Cat cat = new ByteBuddy()
                .subclass(Cat.class)
                .method(nameStartsWith("get").or(nameStartsWith("set")))
                .intercept(to(GetterSetterInterceptor.class))
                .make().load(getClass().getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
                .getLoaded().newInstance();

        cat.setName("Felix");

        assertThat(cat.getName()).isEqualTo("Felix");

    }

    @Test
    public void testMockito() throws Exception {
        final Cat mock = Mockito.mock(Cat.class);

        mock.setName("Felix");

        assertThat(mock.getName()).isNull();
    }

    @Test
    public void testCosmockpolitan() throws Exception {
        final Cat mock = Cosmockpolitan.mock(Cat.class);

        mock.setName("Felix");

        assertThat(mock.getName()).isNull();
    }

}
