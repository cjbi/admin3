package tech.wetech.admin.modules.system.service;

import tech.wetech.admin.core.utils.PageResultSet;
import tech.wetech.admin.modules.system.po.Log;
import tech.wetech.admin.modules.system.query.LogQuery;

public interface LogService {

    /**
     * 创建日志
     * @param log
     * @return
     */
    void create(Log log);

    /**
     * 分页查询日志
     * @param log
     * @return
     */
    PageResultSet<Log> findByPage(LogQuery log);

}
