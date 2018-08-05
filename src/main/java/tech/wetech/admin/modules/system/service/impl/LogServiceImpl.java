package tech.wetech.admin.modules.system.service.impl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tech.wetech.admin.core.utils.PageResultSet;
import tech.wetech.admin.modules.system.mapper.LogMapper;
import tech.wetech.admin.modules.system.po.Log;
import tech.wetech.admin.modules.system.query.LogQuery;
import tech.wetech.admin.modules.system.service.LogService;
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
    @Transactional
    public int create(Log log) {
        return logMapper.insertSelective(log);
    }

    @Override
    public PageResultSet<Log> findByPage(LogQuery log) {
        PageHelper.offsetPage(log.getOffset(), log.getLimit());
        if (!StringUtils.isEmpty(log.getOrderBy())) {
            PageHelper.orderBy(log.getOrderBy());
        }
        Weekend weekend = Weekend.of(Log.class);
        WeekendCriteria<Log, Object> criteria = weekend.weekendCriteria();
        if (!StringUtils.isEmpty(log.getUsername())) {
            criteria.andLike(Log::getUsername, "%" + log.getUsername() + "%");
        }
        if (!StringUtils.isEmpty(log.getIp())) {
            criteria.andLike(Log::getIp, "%" + log.getIp() + "%");
        }
        if (!StringUtils.isEmpty(log.getReqMethod())) {
            criteria.andLike(Log::getReqMethod, "%" + log.getReqMethod() + "%");
        }
        if (!StringUtils.isEmpty(log.getExecMethod())) {
            criteria.andLike(Log::getExecMethod, "%" + log.getExecMethod() + "%");
        }
        if (!StringUtils.isEmpty(log.getExecDesc())) {
            criteria.andLike(Log::getExecDesc, "%" + log.getExecDesc() + "%");
        }
        if (!StringUtils.isEmpty(log.getStatus())) {
            criteria.andLike(Log::getStatus, "%" + log.getStatus() + "%");
        }
        if (!StringUtils.isEmpty(log.getStartDate()) && !StringUtils.isEmpty(log.getEndDate())) {
            criteria.andGreaterThanOrEqualTo(Log::getCreateTime, log.getStartDate()).andLessThanOrEqualTo(Log::getCreateTime, log.getEndDate());
        }
        PageResultSet<Log> resultSet = new PageResultSet<>();
        List<Log> list = logMapper.selectByExample(weekend);
        long count = logMapper.selectCountByExample(weekend);
        resultSet.setRows(list);
        resultSet.setTotal(count);
        return resultSet;
    }
}
