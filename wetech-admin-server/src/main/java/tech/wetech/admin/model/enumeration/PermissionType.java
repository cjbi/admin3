package tech.wetech.admin.model.enumeration;

/**
 * @author cjbi
 */
public enum PermissionType {
    MENU(1, "菜单"),
    BUTTON(2, "按钮");
    private final int code;
    private final String name;

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static PermissionType fromCode(int code) {
        for (PermissionType value : PermissionType.values()) {
            if (value.getCode() == code) {
                return value;
            }
        }
        return null;
    }

    PermissionType(int code, String name) {
        this.code = code;
        this.name = name;
    }

}
