package tech.wetech.admin.modules.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.wetech.admin.modules.base.service.impl.BaseService;
import tech.wetech.admin.modules.system.mapper.LogMapper;
import tech.wetech.admin.modules.system.po.Log;
import tech.wetech.admin.modules.system.service.LogService;

/**
 * 日志服务
 * Created by cjbi on 2017/12/14.
 */
@Service
public class LogServiceImpl extends BaseService<Log> implements LogService {

    @Autowired
    private LogMapper logMapper;
}
