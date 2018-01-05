package tech.wetech.common;

import com.alibaba.druid.pool.DruidDataSource;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tech.wetech.admin.common.utils.PropertiesUtil;
import java.util.Properties;

/**
 * 测试数据源 Created by cjbi on 2018/1/5.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring.xml" })
public class DataSourceTest{

    @Autowired
    private DruidDataSource dataSource;

    final private static String PROPERTIES_NAME = "config";

    @Value("${connection.driverClassName}")
    private String driverClassName;

    @Value("${connection.url}")
    private String url;

    @Value("${connection.username}")
    private String username;

    @Value("${connection.password}")
    private String password;

    @Test
    public void testDriverFilePath() {
        Class clazz = dataSource.getDriver().getClass();
        // 获取jdbc驱动类路径
        String classFilePath = clazz.getResource("").getPath();
        // 获取jdbc jar包路径
        String jarFilePath = clazz.getProtectionDomain().getCodeSource().getLocation().getFile();
        System.out.println(String.format("驱动类路径为: %s，jdbc包路径为: %s", classFilePath, jarFilePath));
        Assert.assertNotNull(classFilePath);
        Assert.assertNotNull(jarFilePath);
    }

    @Test
    public void testDriverFilePath2() throws ClassNotFoundException {
        Properties properties = PropertiesUtil.getInstance().load(PROPERTIES_NAME);
        String driverClassName = properties.getProperty("connection.driverClassName");
        String url = properties.getProperty("connection.url");
        String username = properties.getProperty("connection.username");
        String password = properties.getProperty("connection.password");
        System.out.println(String.format("驱动类名称为：%s，连接地址为：%s，用户名为：%s，密码为：%s", driverClassName, url, username, password));
        Class clazz = Class.forName(driverClassName);
        // 获取jdbc驱动类路径
        String classFilePath = clazz.getResource("").getPath();
        // 获取jdbc jar包路径
        String jarFilePath = clazz.getProtectionDomain().getCodeSource().getLocation().getFile();
        System.out.println(String.format("驱动类路径为: %s，jdbc包路径为: %s", classFilePath, jarFilePath));
        Assert.assertNotNull(classFilePath);
        Assert.assertNotNull(jarFilePath);
    }

    @Test
    public void testDriverFilePath3() throws ClassNotFoundException {
        System.out.println(String.format("驱动类名称为：%s，连接地址为：%s，用户名为：%s，密码为：%s", driverClassName, url, username, password));
        Class clazz = Class.forName(driverClassName);
        // 获取jdbc驱动类路径
        String classFilePath = clazz.getResource("").getPath();
        // 获取jdbc jar包路径
        String jarFilePath = clazz.getProtectionDomain().getCodeSource().getLocation().getFile();
        System.out.println(String.format("驱动类路径为: %s，jdbc包路径为: %s", classFilePath, jarFilePath));
        Assert.assertNotNull(classFilePath);
        Assert.assertNotNull(jarFilePath);
    }

}
