package tech.wetech.admin.service.system;

import tech.wetech.admin.core.base.PageResultSet;
import tech.wetech.admin.model.system.entity.Log;
import tech.wetech.admin.model.system.request.LogQueryDto;

public interface LogService {

    /**
     * 创建日志
     * @param log
     * @return
     */
    int create(Log log);

    /**
     * 分页查询日志
     * @param log
     * @return
     */
    PageResultSet<Log> findByPage(LogQueryDto log);

}
