package tech.wetech.admin.generator;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 代码生成器
 * <p>
 * Created by cjbi on 2018/1/7.
 *
 * @author cjbi
 */
public class Generator {

    public static final Logger LOGGER = LoggerFactory.getLogger(Generator.class);

    private static Generator helper = null;

    public static Generator getInstance() {
        if (helper == null) {
            helper = new Generator();
        }
        return helper;
    }

    public static void main(String[] args) throws Exception {
        List<String> warnings = new ArrayList<>();
        boolean overwrite = true;
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(
                Generator.class.getResourceAsStream("/generator/generatorConfig.xml"));
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
        warnings.forEach(w -> {
            LOGGER.warn(w);
        });
    }
}
