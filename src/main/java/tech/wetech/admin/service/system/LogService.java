package tech.wetech.admin.service.system;

import tech.wetech.admin.model.system.LogWithBLOBs;
import tech.wetech.admin.web.dto.DataTableModel;

public interface LogService {

    /**
     * 创建日志
     * @param logWithBLOBs
     * @return
     */
    int createLogWithBLOBs(LogWithBLOBs logWithBLOBs);

    DataTableModel<LogWithBLOBs> list(DataTableModel<LogWithBLOBs> model);

}
