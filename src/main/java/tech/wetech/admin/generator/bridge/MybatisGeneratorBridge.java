package tech.wetech.admin.generator.bridge;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.api.ProgressCallback;
import org.mybatis.generator.api.ShellCallback;
import org.mybatis.generator.config.*;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import tech.wetech.admin.generator.model.GeneratorConfig;
import tech.wetech.admin.generator.plugins.DbRemarksCommentGenerator;
import tech.wetech.admin.generator.util.JdbcConfigHelper;

/**
 * The bridge between web and the mybatis generator. All the operation to
 * mybatis generator should proceed through this class
 * <p>
 * Created by cjbi on 1/5/18.
 */
@Component
public class MybatisGeneratorBridge{

    private static final Logger LOGGER = LoggerFactory.getLogger(MybatisGeneratorBridge.class);

    private GeneratorConfig generatorConfig;

    private ProgressCallback progressCallback;

    public MybatisGeneratorBridge() {
    }

    public void setGeneratorConfig(GeneratorConfig generatorConfig) {
        this.generatorConfig = generatorConfig;
    }

    public void generate() throws Exception {
        Configuration configuration = new Configuration();
        Context context = new Context(ModelType.CONDITIONAL);
        configuration.addContext(context);
        context.addProperty("javaFileEncoding", "UTF-8");

        List<IgnoredColumn> ignoredColumns = generatorConfig.getIgnoredColumns();

        List<ColumnOverride> columnOverrides = generatorConfig.getColumnOverrides();

        // Table configuration
        TableConfiguration tableConfig = new TableConfiguration(context);
        tableConfig.setTableName(generatorConfig.getTableName());
        tableConfig.setDomainObjectName(generatorConfig.getModelName());

        // 针对 postgresql 单独配置
        if (JdbcConfigHelper.getDbType() == JdbcConfigHelper.DbType.PostgreSQL) {
            tableConfig.setDelimitIdentifiers(true);
        }

        // 添加GeneratedKey主键生成
        if (StringUtils.isNoneEmpty(generatorConfig.getGenerateKeys())) {
            tableConfig.setGeneratedKey(new GeneratedKey(generatorConfig.getGenerateKeys(),
                    JdbcConfigHelper.getDbType().name(), true, null));
        }

        if (generatorConfig.getMapperName() != null) {
            tableConfig.setMapperName(generatorConfig.getMapperName());
        }
        // add ignore columns
        if (ignoredColumns != null) {
            ignoredColumns.stream().forEach(ignoredColumn -> {
                tableConfig.addIgnoredColumn(ignoredColumn);
            });
        }
        if (columnOverrides != null) {
            columnOverrides.stream().forEach(columnOverride -> {
                tableConfig.addColumnOverride(columnOverride);
            });
        }
        if (generatorConfig.isUseActualColumnNames()) {
            tableConfig.addProperty("useActualColumnNames", "true");
        }

        if (generatorConfig.isUseTableNameAlias()) {
            tableConfig.setAlias(generatorConfig.getTableName());
        }

        JDBCConnectionConfiguration jdbcConfig = new JDBCConnectionConfiguration();
        jdbcConfig.setDriverClass(JdbcConfigHelper.getDriverClass());
        jdbcConfig.setConnectionURL(JdbcConfigHelper.getURL());
        jdbcConfig.setUserId(JdbcConfigHelper.getUsername());
        jdbcConfig.setPassword(JdbcConfigHelper.getPassword());
        // java model
        JavaModelGeneratorConfiguration modelConfig = new JavaModelGeneratorConfiguration();
        modelConfig.setTargetPackage(generatorConfig.getModelPackage());
        modelConfig.setTargetProject(
                generatorConfig.getProjectFolder() + "/" + generatorConfig.getModelPackageTargetFolder());
        // Mapper configuration
        SqlMapGeneratorConfiguration mapperConfig = new SqlMapGeneratorConfiguration();
        mapperConfig.setTargetPackage(generatorConfig.getMappingXMLPackage());
        mapperConfig.setTargetProject(
                generatorConfig.getProjectFolder() + "/" + generatorConfig.getMappingXMLTargetFolder());
        // DAO
        JavaClientGeneratorConfiguration daoConfig = new JavaClientGeneratorConfiguration();
        daoConfig.setConfigurationType("XMLMAPPER");
        daoConfig.setTargetPackage(generatorConfig.getDaoPackage());
        daoConfig.setTargetProject(generatorConfig.getProjectFolder() + "/" + generatorConfig.getDaoTargetFolder());

        context.setId("myid");
        context.addTableConfiguration(tableConfig);
        context.setJdbcConnectionConfiguration(jdbcConfig);
        context.setJdbcConnectionConfiguration(jdbcConfig);
        context.setJavaModelGeneratorConfiguration(modelConfig);
        context.setSqlMapGeneratorConfiguration(mapperConfig);
        context.setJavaClientGeneratorConfiguration(daoConfig);
        // Comment
        CommentGeneratorConfiguration commentConfig = new CommentGeneratorConfiguration();
        commentConfig.setConfigurationType(DbRemarksCommentGenerator.class.getName());
        if (generatorConfig.isComment()) {
            commentConfig.addProperty("columnRemarks", "true");
        }
        if (generatorConfig.isAnnotation()) {
            commentConfig.addProperty("annotations", "true");
        }
        context.setCommentGeneratorConfiguration(commentConfig);

        // 实体添加序列化
        PluginConfiguration serializablePluginConfiguration = new PluginConfiguration();
        serializablePluginConfiguration.setConfigurationType("org.mybatis.generator.plugins.SerializablePlugin");
        context.addPluginConfiguration(serializablePluginConfiguration);
        // toString, hashCode, equals插件
        if (generatorConfig.isNeedToStringHashcodeEquals()) {
            PluginConfiguration pluginConfiguration1 = new PluginConfiguration();
            pluginConfiguration1.setConfigurationType("org.mybatis.generator.plugins.EqualsHashCodePlugin");
            context.addPluginConfiguration(pluginConfiguration1);
            PluginConfiguration pluginConfiguration2 = new PluginConfiguration();
            pluginConfiguration2.setConfigurationType("org.mybatis.generator.plugins.ToStringPlugin");
            context.addPluginConfiguration(pluginConfiguration2);
        }
        // limit/offset插件
        if (generatorConfig.isOffsetLimit()) {
            // 当前支持MySQL和PostgreSQL
            if (JdbcConfigHelper.getDbType() == JdbcConfigHelper.DbType.MySQL
                    || JdbcConfigHelper.getDbType() == JdbcConfigHelper.DbType.PostgreSQL) {
                PluginConfiguration pluginConfiguration = new PluginConfiguration();
                pluginConfiguration.setConfigurationType("tech.wetech.admin.generator.plugins.MySQLLimitPlugin");
                context.addPluginConfiguration(pluginConfiguration);
            }
        }

        //文件模板插件配置
        if(StringUtils.isEmpty(generatorConfig.getServiceName())) {
            generatorConfig.setServiceName(generatorConfig.getModelName() + "Service");
        }
        if(StringUtils.isEmpty(generatorConfig.getServiceImplName())) {
            generatorConfig.setServiceImplName(generatorConfig.getModelName() + "ServiceImpl");
        }
        if(StringUtils.isEmpty(generatorConfig.getControllerName())) {
            generatorConfig.setControllerName(generatorConfig.getModelName() + "Controller");
        }

        //Service interface
        LOGGER.info("config servie interface plugin");
        PluginConfiguration t1 = new PluginConfiguration();
        t1.setConfigurationType("tech.wetech.admin.generator.plugins.TemplateFilePlugin");
        t1.addProperty("targetProject",generatorConfig.getProjectFolder() + "/" + generatorConfig.getServiceTargetFolder());
        t1.addProperty("targetPackage",generatorConfig.getServicePackage());
        t1.addProperty("templatePath","generator/ftl/service.ftl");
        t1.addProperty("serviceName",generatorConfig.getServiceName());
        t1.addProperty("fileName","${serviceName}.java");
        context.addPluginConfiguration(t1);

        //service impl
        LOGGER.info("config serviceImpl plugin");
        PluginConfiguration t2 = new PluginConfiguration();
        t2.setConfigurationType("tech.wetech.admin.generator.plugins.TemplateFilePlugin");
        t2.addProperty("targetProject", generatorConfig.getProjectFolder() + "/" + generatorConfig.getServiceImplTargetFolder());
        t2.addProperty("targetPackage",generatorConfig.getServiceImplPackage());
        t2.addProperty("templatePath","generator/ftl/serviceImpl.ftl");
        t2.addProperty("serviceImplName", generatorConfig.getServiceImplName());
        t2.addProperty("serviceName", generatorConfig.getServiceName());
        t2.addProperty("servicePackage",generatorConfig.getServicePackage());
        t2.addProperty("daoName",StringUtils.isEmpty(generatorConfig.getMapperName())?generatorConfig.getModelName()+"Mapper": generatorConfig.getMapperName());
        t2.addProperty("daoPackage",generatorConfig.getDaoPackage());
        t2.addProperty("fileName","${serviceImplName}.java");
        context.addPluginConfiguration(t2);

        //controller
        LOGGER.info("config controller plugin");
        PluginConfiguration t3 = new PluginConfiguration();
        t3.setConfigurationType("tech.wetech.admin.generator.plugins.TemplateFilePlugin");
        t3.addProperty("targetProject",generatorConfig.getProjectFolder() + "/" + generatorConfig.getControllerTargetFolder());
        t3.addProperty("targetPackage", generatorConfig.getControllerPackage());
        t3.addProperty("templatePath","generator/ftl/controller.ftl");
        t3.addProperty("serviceName", generatorConfig.getServiceName());
        t3.addProperty("servicePackage",generatorConfig.getServicePackage());
        t3.addProperty("controllerName", generatorConfig.getControllerName());
        t3.addProperty("controllerPackage",generatorConfig.getControllerPackage());
        t3.addProperty("moduleName",generatorConfig.getModuleName());
        t3.addProperty("fileName","${controllerName}.java");
        context.addPluginConfiguration(t3);

        //jsp
        LOGGER.info("config jsp plugin");
        PluginConfiguration t4 = new PluginConfiguration();
        String jspName = generatorConfig.getJspName();
        if(StringUtils.isEmpty(jspName)) {
            jspName = generatorConfig.getModelName();
        }
        t4.setConfigurationType("tech.wetech.admin.generator.plugins.TemplateFilePlugin");
        t4.addProperty("targetProject",generatorConfig.getProjectFolder() + "/" + generatorConfig.getJspTargetFolder());
        t4.addProperty("targetPackage", "");
        t4.addProperty("templatePath","generator/ftl/jsp.ftl");
        t4.addProperty("jspName",jspName);
        t4.addProperty("moduleName",generatorConfig.getModuleName());
        t4.addProperty("fileName", "${jspName}.jsp");
        context.addPluginConfiguration(t4);

        PluginConfiguration tt1 = new PluginConfiguration();
        tt1.setConfigurationType("tech.wetech.admin.generator.plugins.TemplateFilePlugin");
        tt1.addProperty("targetProject",generatorConfig.getProjectFolder() + "/" + generatorConfig.getMappingXMLTargetFolder());
        tt1.addProperty("targetPackage",generatorConfig.getMappingXMLPackage());
        tt1.addProperty("templatePath","generator/ftl/test-one.ftl");
        tt1.addProperty("fileName","${tableClass.shortClassName}Test.txt");
        context.addPluginConfiguration(tt1);

        PluginConfiguration tt2 = new PluginConfiguration();
        tt2.setConfigurationType("tech.wetech.admin.generator.plugins.TemplateFilePlugin");
        tt2.addProperty("singleMode","false");
        tt2.addProperty("targetProject",generatorConfig.getProjectFolder() + "/" + generatorConfig.getMappingXMLTargetFolder());
        tt2.addProperty("targetPackage",generatorConfig.getMappingXMLPackage());
        tt2.addProperty("templatePath","generator/ftl/test-all.ftl");
        tt2.addProperty("fileName","TestAll.txt");
        context.addPluginConfiguration(tt2);

        context.setTargetRuntime("MyBatis3");

        List<String> warnings = new ArrayList<>();
        Set<String> fullyqualifiedTables = new HashSet<>();
        Set<String> contexts = new HashSet<>();
        ShellCallback shellCallback = new DefaultShellCallback(true); // override=true
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(configuration, shellCallback, warnings);
        myBatisGenerator.generate(progressCallback, contexts, fullyqualifiedTables);
    }

    public void setProgressCallback(ProgressCallback progressCallback) {
        this.progressCallback = progressCallback;
    }

}
