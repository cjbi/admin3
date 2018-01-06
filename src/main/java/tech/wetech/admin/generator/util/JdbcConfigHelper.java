package tech.wetech.admin.generator.util;

import tech.wetech.admin.common.utils.PropertiesUtil;

import java.util.Properties;

/**
 * MBG 数据库连接配置 <br>
 * Created by cjbi on 2018/1/5.
 */
public class JdbcConfigHelper {

    final private static String PROP_NAME = "config";

    final private static String PROP_DRIVER_CLASS = "connection.driverClassName";

    final private static String PROP_URL = "connection.url";

    final private static String PROP_USERNAME = "connection.username";

    final private static String PROP_PASSWORD = "connection.password";

    final private static Properties config = null;

    final private static DbType dbType = null;

    private JdbcConfigHelper() {

    }

    public enum DbType {
        MySQL("com.mysql.jdbc.Driver"), Oracle("oracle.jdbc.driver.OracleDriver"), PostgreSQL(
                "org.postgresql.Driver"), SQL_Server("com.microsoft.sqlserver.jdbc.SQLServerDriver");

        private final String driverClass;

        DbType(String driverClass) {
            this.driverClass = driverClass;
        }

        public String getDriverClass() {
            return driverClass;
        }

    }

    private static Properties getConfig() {
        if (config == null) {
            return PropertiesUtil.getInstance().load(PROP_NAME);
        }
        return config;
    }

    public static DbType getDbType() {
        if (dbType == null) {
            for (DbType dbType : DbType.values()) {
                if (dbType.getDriverClass().equals(getDriverClass())) {
                    return dbType;
                }
            }
        }
        return dbType;
    }

    public static String getDriverClass() {
        return getConfig().getProperty(PROP_DRIVER_CLASS);
    }

    public static String getURL() {
        return getConfig().getProperty(PROP_URL);
    }

    public static String getPropUsername() {
        return getConfig().getProperty(PROP_USERNAME);
    }

    public static String getPassword() {
        return getConfig().getProperty(PROP_PASSWORD);
    }

    public static String getConnectorLibPath() {
        String driverClass = getDriverClass();
        String jarFilePath = null;
        try {
            Class clazz = Class.forName(driverClass);
            jarFilePath = clazz.getProtectionDomain().getCodeSource().getLocation().getFile();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return jarFilePath;
    }

}
