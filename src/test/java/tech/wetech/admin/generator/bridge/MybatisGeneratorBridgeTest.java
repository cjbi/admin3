package tech.wetech.admin.generator.bridge;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.junit.Test;
import tech.wetech.admin.generator.model.GeneratorConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Created by cjbi on 2018/1/5.
 */
public class MybatisGeneratorBridgeTest{

    @Test
    public void testGenerate() throws Exception {
        GeneratorConfig generatorConfig = new GeneratorConfig();
        generatorConfig.setProjectFolder("D:/aaa");//项目目录
        generatorConfig.setModelPackage("tech.wetech.admin.model");//实体类包名
        generatorConfig.setModelPackageTargetFolder("src/main/java");//实体类存放目录
        generatorConfig.setDaoPackage("tech.wetech.admin.mapper");//dao接口包名
        generatorConfig.setDaoTargetFolder("src/main/java");//dao存放目录
        generatorConfig.setMapperName("");//自定义接口名称(选填)
        generatorConfig.setMappingXMLPackage("mybatis.system");//映射xml文件包名
        generatorConfig.setMappingXMLTargetFolder("src/main/resource");//映射xml文件存放目录
        generatorConfig.setTableName("sys_log");//表名
        generatorConfig.setDomainObjectName("Log");//实体类名
        generatorConfig.setOffsetLimit(true);//是否分页
        generatorConfig.setComment(true);//是否生成实体域注释(来自表注释)
        generatorConfig.setNeedToStringHashcodeEquals(true);//是否生成toString/hashCode/equals方法
        generatorConfig.setAnnotation(false);//是否生成JPA注解
        generatorConfig.setUseActualColumnNames(false);//是否使用实际的列名
        generatorConfig.setGenerateKeys("");//
        generatorConfig.setUseTableNameAlias(false);//是否xml中生成表的表面
        if (!checkDirs(generatorConfig)) {
            return;
        }
        MybatisGeneratorBridge bridge = new MybatisGeneratorBridge();
        bridge.setGeneratorConfig(generatorConfig);
        bridge.generate();
    }

    /**
     * 检查并创建不存在的文件夹
     *
     * @return
     */
    private boolean checkDirs(GeneratorConfig config) {
        List<String> dirs = new ArrayList<>();
        dirs.add(config.getProjectFolder());
        dirs.add(FilenameUtils
                .normalize(config.getProjectFolder().concat("/").concat(config.getModelPackageTargetFolder())));
        dirs.add(FilenameUtils.normalize(config.getProjectFolder().concat("/").concat(config.getDaoTargetFolder())));
        dirs.add(FilenameUtils
                .normalize(config.getProjectFolder().concat("/").concat(config.getMappingXMLTargetFolder())));
        boolean haveNotExistFolder = false;
        for (String dir : dirs) {
            File file = new File(dir);
            if (!file.exists()) {
                haveNotExistFolder = true;
            }
        }
        if (haveNotExistFolder) {

            try {
                for (String dir : dirs) {
                    FileUtils.forceMkdir(new File(dir));
                }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

}