package tech.wetech.admin.modules.system.enums;

public enum ResourceType {
    MENU("菜单"), BUTTON("按钮");

    private final String info;

    private ResourceType(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
}