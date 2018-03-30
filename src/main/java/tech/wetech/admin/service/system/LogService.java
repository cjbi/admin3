package tech.wetech.admin.service.system;

import tech.wetech.admin.common.base.Page;
import tech.wetech.admin.common.base.PageResultSet;
import tech.wetech.admin.model.system.LogWithBLOBs;

public interface LogService {

    /**
     * 创建日志
     * @param logWithBLOBs
     * @return
     */
    int createLogWithBLOBs(LogWithBLOBs logWithBLOBs);

    PageResultSet<LogWithBLOBs> findByPage(Page page);

}
