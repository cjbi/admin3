<#assign dateTime = .now>
package ${package};

import ${tableClass.fullClassName};
import ${tableClass.fullClassName}Example;
import java.util.List;
import java.util.Set;

/**
*
* Created by wetech-generator on ${dateTime?date}.
* @author
*/
public interface ${serviceName} {

    DataTableModel<${tableClass.shortClassName}> findByPage(DataTableModel<${tableClass.shortClassName}> model)

    int create${tableClass.shortClassName}(${tableClass.shortClassName} ${tableClass.variableName});

    int update${tableClass.shortClassName}(${tableClass.shortClassName} ${tableClass.variableName});

    void delete${tableClass.shortClassName}(Long ${tableClass.variableName}Id);

    ${tableClass.shortClassName} findOne(Long ${tableClass.variableName}Id);

    List<${tableClass.shortClassName}> find(${tableClass.shortClassName}Example example);

    List<${tableClass.shortClassName}> findAll();

}