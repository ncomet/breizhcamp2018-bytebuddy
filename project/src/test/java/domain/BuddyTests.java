package domain;

import frameworks.mock.Cosmockpolitan;
import interceptors.GetterSetterInterceptor;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import org.testng.annotations.Test;

import java.lang.reflect.Modifier;
import java.util.Collections;

import static net.bytebuddy.implementation.FixedValue.value;
import static net.bytebuddy.implementation.MethodDelegation.to;
import static net.bytebuddy.matcher.ElementMatchers.*;
import static org.assertj.core.api.Assertions.assertThat;

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

    @Test
    public void shouldCreateAnActress() throws Exception {

        Actor actor = new ByteBuddy()
                .subclass(Actor.class)
                .name("domain.FemaleActress")
                .method(named("characterName")).intercept(value("Daenerys Targaryen"))
                .method(named("firstName")).intercept(value("Emilia"))
                .method(named("lastName")).intercept(value("Clarke"))
                .defineField("favoriteActor", Actor.class, Modifier.PUBLIC)
                .make().load(getClass().getClassLoader())
                .getLoaded()
                .newInstance();

        System.out.println(actor.greet());

    }

}
