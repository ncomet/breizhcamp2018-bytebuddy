package frameworks.security.interfaces;

public interface Safe {

    <T> T safeInstance(Class<T> instance);
}
