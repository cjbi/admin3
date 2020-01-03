package tech.wetech.admin.service.impl;

;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.wetech.admin.service.BaseService;
import tech.wetech.admin.mapper.LogMapper;
import tech.wetech.admin.model.entity.Log;
import tech.wetech.admin.service.LogService;

/**
 * 日志服务
 * Created by cjbi on 2017/12/14.
 */
@Service
public class LogServiceImpl extends BaseService<Log> implements LogService {

    @Autowired
    private LogMapper logMapper;
}
