package tech.wetech.admin.service.system;

import tech.wetech.admin.common.base.PageResultSet;
import tech.wetech.admin.model.system.Log;

public interface LogService {

    /**
     * 创建日志
     * @param log
     * @return
     */
    int create(Log log);

    PageResultSet<Log> findByPage(Log log);

}
