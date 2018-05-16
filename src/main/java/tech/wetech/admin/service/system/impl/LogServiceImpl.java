package tech.wetech.admin.service.system.impl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tech.wetech.admin.core.base.PageResultSet;
import tech.wetech.admin.mapper.system.LogMapper;
import tech.wetech.admin.model.system.Log;
import tech.wetech.admin.service.system.LogService;
import tk.mybatis.mapper.weekend.Weekend;
import tk.mybatis.mapper.weekend.WeekendCriteria;

import java.util.List;

/**
 * 日志服务
 * Created by cjbi on 2017/12/14.
 */
@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private LogMapper logMapper;


    @Override
    public int create(Log log) {
        return logMapper.insert(log);
    }

    @Override
    public PageResultSet<Log> findByPage(Log log) {
        PageHelper.offsetPage(log.getOffset(), log.getLimit());
        if(!StringUtils.isEmpty(log.getOrderBy())) {
            PageHelper.orderBy(log.getOrderBy());
        }
        Weekend weekend = Weekend.of(Log.class);
        WeekendCriteria<Log, Object> criteria = weekend.weekendCriteria();
        if (!StringUtils.isEmpty(log.getSearch())) {
            String value = "%" + log.getSearch() + "%";
            criteria.andLike(Log::getUsername, value)
                    .orLike(Log::getIp, value)
                    .orLike(Log::getReqMethod, value)
                    .orLike(Log::getExecMethod, value)
                    .orLike(Log::getExecDesc, value)
                    .orLike(Log::getStatus, value);
        }
        PageResultSet<Log> resultSet = new PageResultSet<>();
        List<Log> list = logMapper.selectByExample(weekend);
        long count = logMapper.selectCountByExample(weekend);
        resultSet.setRows(list);
        resultSet.setTotal(count);
        return resultSet;
    }
}
