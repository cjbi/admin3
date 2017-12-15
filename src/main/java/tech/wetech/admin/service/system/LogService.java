package tech.wetech.admin.service.system;

import tech.wetech.admin.model.system.LogWithBLOBs;

public interface LogService {

    /**
     * 创建日志
     * @param logWithBLOBs
     * @return
     */
    int createLogWithBLOBs(LogWithBLOBs logWithBLOBs);
}
