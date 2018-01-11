package tech.wetech.admin.web.dto.system;

import org.mybatis.generator.config.ColumnOverride;
import org.mybatis.generator.config.IgnoredColumn;
import tech.wetech.admin.generator.model.GeneratorConfig;

import java.util.List;

/**
 * 代码生成器传输类
 * <p>
 * Created by cjbi on 2018/1/10.
 */
public class GeneratorDto {

    /**
     * 生成器配置
     */
    private GeneratorConfig generatorConfig;

    /**
     * 要忽略的行
     */
    private List<IgnoredColumn> ignoredColumns;

    /**
     * 重写的行
     */
    private List<ColumnOverride> columnOverrides;

    public GeneratorConfig getGeneratorConfig() {
        return generatorConfig;
    }

    public void setGeneratorConfig(GeneratorConfig generatorConfig) {
        this.generatorConfig = generatorConfig;
    }

    public List<IgnoredColumn> getIgnoredColumns() {
        return ignoredColumns;
    }

    public void setIgnoredColumns(List<IgnoredColumn> ignoredColumns) {
        this.ignoredColumns = ignoredColumns;
    }

    public List<ColumnOverride> getColumnOverrides() {
        return columnOverrides;
    }

    public void setColumnOverrides(List<ColumnOverride> columnOverrides) {
        this.columnOverrides = columnOverrides;
    }
}
