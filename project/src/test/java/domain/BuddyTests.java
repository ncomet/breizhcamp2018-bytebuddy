package domain;

import static net.bytebuddy.implementation.FixedValue.value;
import static net.bytebuddy.implementation.MethodDelegation.to;
import static net.bytebuddy.matcher.ElementMatchers.*;
import static org.assertj.core.api.Assertions.assertThat;

import interceptors.StrangeFeelingInterceptor;
import net.bytebuddy.ByteBuddy;

import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import org.mockito.Mockito;
import org.testng.annotations.Test;

import frameworks.mock.Cosmockpolitan;
import interceptors.GetterSetterInterceptor;

import java.util.Collections;

/**
 * LECTRA
 * BuddyTests class
 * @author n.comet
 */
public class BuddyTests {

    @Test
    public void java() {
        final Cat cat = new Cat();
        cat.setName("Garfield");
        cat.feed("croquettes");
        cat.feed("patée");

        assertThat(cat.getStomach()).containsExactly("croquettes", "patée");
        assertThat(cat.getName()).isEqualTo("Garfield");
    }

    @Test
    public void buddyIntro() throws Exception {
        final Cat cat = new ByteBuddy()
                .subclass(Cat.class)
                /*.method(named("getStomach"))
                .intercept(to(StrangeFeelingInterceptor.class))*/
                .make()
                .load(getClass().getClassLoader())
                .getLoaded().newInstance();

        cat.setName("Garfield");
        cat.feed("croquettes");
        cat.feed("patée");

        assertThat(cat.getStomach()).containsExactly("croquettes", "patée");
        assertThat(cat.getName()).isEqualTo("Garfield");
    }

    @Test
    public void interceptors() throws Exception {

        final Cat cat = new ByteBuddy()
                .subclass(Cat.class)
                .method(isGetter().or(isSetter()))
                .intercept(to(GetterSetterInterceptor.class))
                .make().load(getClass().getClassLoader())
                .getLoaded().newInstance();

        cat.setName("Felix");

        assertThat(cat.getName()).isEqualTo("Felix");

    }

    @Test
    public void mockito() throws Exception {
        final Cat mock = Mockito.mock(Cat.class);

        mock.setName("Felix");

        assertThat(mock.getName()).isNull();
    }

    @Test
    public void cosmockpolitan() throws Exception {
        final Cat mock = Cosmockpolitan.mock(Cat.class);

        mock.setName("Felix");

        assertThat(mock.getName()).isNull();
    }

    @Test
    public void classloadingStrategy() throws Exception {

        final Cat cat = new ByteBuddy()
                .subclass(Cat.class)
                .method(named("getStomach"))
                .intercept(value(Collections.singleton("NotToday")))
                .make().load(getClass().getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
                .getLoaded().newInstance();

        assertThat(cat.getStomach()).containsExactly("NotToday");

    }

}
