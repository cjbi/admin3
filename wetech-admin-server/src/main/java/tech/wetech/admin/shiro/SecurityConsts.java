package tech.wetech.admin.shiro;

public class SecurityConsts {
    public static final String LOGIN_SALT = "storyweb-bp";
    //request请求头属性
    public static final String REQUEST_AUTH_HEADER="Authorization";

    //JWT-account
    public static final String ACCOUNT = "account";

    //Shiro redis 前缀
    public static final String PREFIX_SHIRO_CACHE = "storyweb-bp:cache:";

    //redis-key-前缀-shiro:refresh_token
    public final static String PREFIX_SHIRO_REFRESH_TOKEN = "storyweb-bp:refresh_token:";

    //JWT-currentTimeMillis
    public final static String CURRENT_TIME_MILLIS = "currentTimeMillis";

    /**
     * token过期时间，单位分钟
     */
    public static final  int TOKEN_EXPIRE_TIME = 120;

    public static final int REFRESH_TOKEN_EXPIRE_TIME = 1440;

    public static final int SHIRO_CACHE_EXPIRE_TIME = 120;

    public static final String SECRET_KEY = "storywebkey";
}