package tech.wetech.admin.generator.util;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

/**
 * 代码生成器
 * <p>
 * Created by cjbi on 2018/1/7.
 * @author cjbi
 */
public class GeneratorHelper {

    private static GeneratorHelper helper = null;

    public static GeneratorHelper getInstance() {
        if(helper == null) {
            helper = new GeneratorHelper();
        }
        return helper;
    }

    public static void main(String[] args) throws Exception {
        List<String> warnings = new ArrayList<>();
        boolean overwrite = true;
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(
                GeneratorHelper.class.getResourceAsStream("/generator/generatorConfig.xml"));
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
    }
}
