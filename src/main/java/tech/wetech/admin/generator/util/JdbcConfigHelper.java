package tech.wetech.admin.generator.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.wetech.admin.common.utils.PropertiesUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * MBG 数据库连接配置 <br>
 * Created by cjbi on 2018/1/5.
 */
public class JdbcConfigHelper{

    private static final Logger _LOG = LoggerFactory.getLogger(JdbcConfigHelper.class);

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

    public static String getUsername() {
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

    public static List<String> getTableNames() throws SQLException {
        String url = getURL();
        _LOG.info("getTableNames, connection url: {}", url);
        Connection conn = getConnection();
        try {
            List<String> tables = new ArrayList<>();
            DatabaseMetaData md = conn.getMetaData();
            ResultSet rs;
            DbType dbType = getDbType();
            if (dbType == DbType.SQL_Server) {
                String sql = "select name from sysobjects  where xtype='u' or xtype='v' ";
                rs = conn.createStatement().executeQuery(sql);
                while (rs.next()) {
                    tables.add(rs.getString("name"));
                }
            } else if (dbType == DbType.Oracle) {
                rs = md.getTables(null,getUsername().toUpperCase(),null,new String[]{"TABLE","VIEW"});
            } else {
                rs = md.getTables(null, "%", "%", new String[] {"TABLE", "VIEW"});
            }
            while(rs.next()) {
                tables.add(rs.getString(3));
            }
            return tables;
        } finally {
            close(conn);
        }
    }

    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(getURL(), getUsername(), getPassword());
        return conn;
    }

    public static void close(Connection conn) {
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void close(PreparedStatement ps) {
        try {
            if (ps != null)
                ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void close(ResultSet rs) {
        try {
            if (rs != null)
                rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
