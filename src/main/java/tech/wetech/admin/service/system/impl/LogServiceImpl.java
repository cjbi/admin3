package tech.wetech.admin.service.system.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.wetech.admin.mapper.system.LogMapper;
import tech.wetech.admin.model.system.LogWithBLOBs;
import tech.wetech.admin.service.system.LogService;

/**
 * Created by cjbi on 2017/12/14.
 */
@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private LogMapper logMapper;


    @Override
    public int createLogWithBLOBs(LogWithBLOBs logWithBLOBs) {
        return logMapper.insertSelective(logWithBLOBs);
    }
}
