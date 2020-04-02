package tech.wetech.admin.model.constant;

/**
 * 常量定义
 * @author cjbi
 */
public class Constants {

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
    public static final Long MENU_ROOT_ID = 0L;
    
    /**
     * 组织机构根id
     */
    public static final Long ORG_ROOT_ID = 0L;
    /**
     * 新建用户使用的默认密码
     */
    public static final String DEFAULT_PASSWORD = "123456";

    /**
     * 构造函数私有化，避免被实例化
     */
    private Constants() {
    }

}
