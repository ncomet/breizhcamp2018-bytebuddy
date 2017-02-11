package frameworks.security.interfaces;

/**
 * LECTRA
 * Safe interface
 * @author n.comet
 */
public interface Safe {

    <T> T safeInstance(Class<T> instance);
}
