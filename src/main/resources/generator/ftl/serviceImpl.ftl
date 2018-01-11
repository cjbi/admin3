package ${package};

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import ${daoPackage}.${daoName};
import ${servicePackage}.${serviceName};
import ${tableClass.fullClassName};
import ${tableClass.fullClassName}Example;
import tech.wetech.admin.web.dto.DataTableModel;

import java.util.*;

@Service
public class ${serviceImplName} implements ${serviceName} {

    @Autowired
    private ${daoName} ${daoName?uncap_first};

    @Override
    public DataTableModel<${tableClass.shortClassName}> findByPage(DataTableModel<${tableClass.shortClassName}> model) {
        ${tableClass.shortClassName}Example example = new ${tableClass.shortClassName}Example();
        example.setOffset(model.getStart());
        example.setLimit(model.getLength());
        if (!StringUtils.isEmpty(model.getKeywords())) {
            // example.or().and${tableClass.shortClassName}Like("%" + model.getKeywords() + "%");
            // example.or().andDescriptionLike("%" + model.getKeywords() + "%");
        }
        List<${tableClass.shortClassName}> ${tableClass.variableName}List = ${daoName?uncap_first}.selectByExample(example);
        long count = ${daoName?uncap_first}.countByExample(example);

        model.setData(${tableClass.variableName}List);
        model.setRecordsTotal(count);
        return model;
    }

    @Override
    public int create${tableClass.shortClassName}(${tableClass.shortClassName} ${tableClass.variableName}) {
        return ${daoName?uncap_first}.insert(${tableClass.variableName});
    }

    @Override
    public int update${tableClass.shortClassName}(${tableClass.shortClassName} ${tableClass.variableName}) {
        return ${daoName?uncap_first}.updateByPrimaryKeySelective(${tableClass.variableName});
    }

    @Override
    public void delete${tableClass.shortClassName}(Long ${tableClass.variableName}Id) {
        ${daoName?uncap_first}.deleteByPrimaryKey(${tableClass.variableName}Id);
    }

    @Override
    public ${tableClass.shortClassName} findOne(Long ${tableClass.variableName}Id) {
        return ${daoName?uncap_first}.selectByPrimaryKey(${tableClass.variableName}Id);
    }

    @Override
    public List<${tableClass.shortClassName}> find(${tableClass.shortClassName}Example example) {
        return ${daoName?uncap_first}.selectByExample(example);
    }

    @Override
    public List<${tableClass.shortClassName}> findAll() {
        return ${daoName?uncap_first}.selectByExample(new ${tableClass.shortClassName}Example());
    }

}
