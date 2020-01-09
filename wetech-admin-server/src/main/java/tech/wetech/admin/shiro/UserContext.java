package tech.wetech.admin.shiro;

public class UserContext implements AutoCloseable {
    static final ThreadLocal<LoginUser> current = new ThreadLocal<>();

    public UserContext(LoginUser user) {
        current.set(user);
    }

    public static LoginUser getCurrentUser() {
        return current.get();
    }

    public void close() {
        current.remove();
    }
}