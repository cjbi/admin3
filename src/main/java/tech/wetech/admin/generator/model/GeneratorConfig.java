package tech.wetech.admin.generator.model;

import org.mybatis.generator.config.ColumnOverride;
import org.mybatis.generator.config.IgnoredColumn;

import java.util.List;

/**
 *
 * GeneratorConfig is the Config of mybatis generator config exclude database
 * config
 * <p>
 * Created by cjbi on 6/16/16.
 */
public class GeneratorConfig {

	private String projectFolder;

	private String moduleName;

	private String modelPackage;

	private String modelPackageTargetFolder;

	private String daoPackage;

	private String daoTargetFolder;

	private String mapperName;

	private String mappingXMLPackage;

	private String mappingXMLTargetFolder;

	private String tableName;

	private String modelName;

	private boolean offsetLimit;

	private boolean comment;

	private boolean needToStringHashcodeEquals;

	private boolean annotation;

	private boolean useActualColumnNames;

	private String generateKeys;

	private boolean useTableNameAlias;

	//custom config by cjbi
	private String serviceImplName;

	private String serviceImplPackage;

	private String serviceImplTargetFolder;

	private String serviceName;

	private String servicePackage;

	private String serviceTargetFolder;

	private String controllerName;

	private String controllerPackage;

	private String controllerTargetFolder;

	private String jspName;

	private String jspTargetFolder;

	/**
	 * 要忽略的行
	 */
	private List<IgnoredColumn> ignoredColumns;

	/**
	 * 重写的行
	 */
	private List<ColumnOverride> columnOverrides;

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

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getServicePackage() {
		return servicePackage;
	}

	public void setServicePackage(String servicePackage) {
		this.servicePackage = servicePackage;
	}

	public String getControllerName() {
		return controllerName;
	}

	public void setControllerName(String controllerName) {
		this.controllerName = controllerName;
	}

	public String getControllerPackage() {
		return controllerPackage;
	}

	public void setControllerPackage(String controllerPackage) {
		this.controllerPackage = controllerPackage;
	}

	public String getJspName() {
		return jspName;
	}

	public void setJspName(String jspName) {
		this.jspName = jspName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getProjectFolder() {
		return projectFolder;
	}

	public void setProjectFolder(String projectFolder) {
		this.projectFolder = projectFolder;
	}

	public String getModelPackage() {
		return modelPackage;
	}

	public void setModelPackage(String modelPackage) {
		this.modelPackage = modelPackage;
	}

	public String getModelPackageTargetFolder() {
		return modelPackageTargetFolder;
	}

	public void setModelPackageTargetFolder(String modelPackageTargetFolder) {
		this.modelPackageTargetFolder = modelPackageTargetFolder;
	}

	public String getDaoPackage() {
		return daoPackage;
	}

	public void setDaoPackage(String daoPackage) {
		this.daoPackage = daoPackage;
	}

	public String getDaoTargetFolder() {
		return daoTargetFolder;
	}

	public void setDaoTargetFolder(String daoTargetFolder) {
		this.daoTargetFolder = daoTargetFolder;
	}

	public String getMappingXMLPackage() {
		return mappingXMLPackage;
	}

	public void setMappingXMLPackage(String mappingXMLPackage) {
		this.mappingXMLPackage = mappingXMLPackage;
	}

	public String getMappingXMLTargetFolder() {
		return mappingXMLTargetFolder;
	}

	public void setMappingXMLTargetFolder(String mappingXMLTargetFolder) {
		this.mappingXMLTargetFolder = mappingXMLTargetFolder;
	}

	public boolean isOffsetLimit() {
		return offsetLimit;
	}

	public void setOffsetLimit(boolean offsetLimit) {
		this.offsetLimit = offsetLimit;
	}

	public boolean isComment() {
		return comment;
	}

	public void setComment(boolean comment) {
		this.comment = comment;
	}

    public boolean isNeedToStringHashcodeEquals() {
        return needToStringHashcodeEquals;
    }

    public void setNeedToStringHashcodeEquals(boolean needToStringHashcodeEquals) {
        this.needToStringHashcodeEquals = needToStringHashcodeEquals;
    }

    public boolean isAnnotation() {
		return annotation;
	}

	public void setAnnotation(boolean annotation) {
		this.annotation = annotation;
	}

	public boolean isUseActualColumnNames() {
		return useActualColumnNames;
	}

	public void setUseActualColumnNames(boolean useActualColumnNames) {
		this.useActualColumnNames = useActualColumnNames;
	}

	public String getMapperName() {
		return mapperName;
	}

	public void setMapperName(String mapperName) {
		this.mapperName = mapperName;
	}

	public String getGenerateKeys() {
		return generateKeys;
	}

	public void setGenerateKeys(String generateKeys) {
		this.generateKeys = generateKeys;
	}

	public boolean getUseTableNameAlias() {
		return useTableNameAlias;
	}

	public void setUseTableNameAlias(boolean useTableNameAlias) {
		this.useTableNameAlias = useTableNameAlias;
	}

	public boolean isUseTableNameAlias() {
		return useTableNameAlias;
	}

	public String getServiceTargetFolder() {
		return serviceTargetFolder;
	}

	public void setServiceTargetFolder(String serviceTargetFolder) {
		this.serviceTargetFolder = serviceTargetFolder;
	}

	public String getControllerTargetFolder() {
		return controllerTargetFolder;
	}

	public void setControllerTargetFolder(String controllerTargetFolder) {
		this.controllerTargetFolder = controllerTargetFolder;
	}

	public String getJspTargetFolder() {
		return jspTargetFolder;
	}

	public void setJspTargetFolder(String jspTargetFolder) {
		this.jspTargetFolder = jspTargetFolder;
	}

	public String getServiceImplName() {
		return serviceImplName;
	}

	public void setServiceImplName(String serviceImplName) {
		this.serviceImplName = serviceImplName;
	}

	public String getServiceImplPackage() {
		return serviceImplPackage;
	}

	public void setServiceImplPackage(String serviceImplPackage) {
		this.serviceImplPackage = serviceImplPackage;
	}

	public String getServiceImplTargetFolder() {
		return serviceImplTargetFolder;
	}

	public void setServiceImplTargetFolder(String serviceImplTargetFolder) {
		this.serviceImplTargetFolder = serviceImplTargetFolder;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
}
