package tech.wetech.admin.modules.system.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
    public void create(Log log) {
        logMapper.insertSelective(log);
    }

    @Override
    public PageResultSet<Log> findByPage(LogQuery log) {

        if (!StringUtils.isEmpty(log.getOrderBy())) {
            PageHelper.orderBy(log.getOrderBy());
        }

        Weekend example = Weekend.of(Log.class);
        WeekendCriteria<Log, Object> criteria = example.weekendCriteria();

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
        Page page = PageHelper.offsetPage(log.getOffset(), log.getLimit()).doSelectPage(()-> logMapper.selectByExample(example));

        resultSet.setRows(page.getResult());
        resultSet.setTotal(page.getTotal());
        return resultSet;
    }
}
