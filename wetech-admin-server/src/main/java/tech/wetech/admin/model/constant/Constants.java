package tech.wetech.admin.model.constant;

/**
 * 常量定义
 * @author cjbi
 */
public class Constants {

    /**
     * 服务端的私钥，在任何场景都不应该流露出去。一旦客户端得知这个secret, 那就意味着客户端是可以自我签发jwt了。
     */
    public static final String JWT_SECRET = "7786df7fc3a34e26a61c034d5ec8245d";

    /**
     * 有效期7天
     */
    public static final long JWT_TTL = 7L * 24L * 60L * 60L * 1000L;

    /**
     * 当前用户
     */
    public static final String CURRENT_USER = "user";

    /**
     * 权限集合
     */
    public static final String PERMISSIONS = "permissions";

    /**
     * 菜单根id
     */
    public static final Long MENU_ROOT_ID = 1L;

    /**
     * 菜单树
     */
    public static final String MENU_TREE = "menuTree";

    public static final String SHARP = "#";
    /**
     * 组织机构根id
     */
    public static final Long ORG_ROOT_ID = 0L;


    /**
     * 构造函数私有化，避免被实例化
     */
    private Constants() {
    }

}
