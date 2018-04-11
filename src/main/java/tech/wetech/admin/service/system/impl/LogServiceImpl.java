package tech.wetech.admin.service.system.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tech.wetech.admin.common.base.Page;
import tech.wetech.admin.common.base.PageResultSet;
import tech.wetech.admin.mapper.system.LogMapper;
import tech.wetech.admin.model.system.LogExample;
import tech.wetech.admin.model.system.LogWithBLOBs;
import tech.wetech.admin.service.system.LogService;
import tech.wetech.admin.web.dto.DataTableModel;
import tech.wetech.admin.web.dto.system.UserDto;

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
    public int createLogWithBLOBs(LogWithBLOBs logWithBLOBs) {
        return logMapper.insertSelective(logWithBLOBs);
    }

    @Override
    public PageResultSet<LogWithBLOBs> findByPage(Page page) {
        LogExample example = new LogExample();
        example.setOffset(page.getOffset());
        example.setLimit(page.getLimit());
        example.setOrderByClause("create_time desc");//时间倒序
        if(!StringUtils.isEmpty(page.getSearch())) {
            String value = "%" + page.getSearch() + "%";
            example.or().andUsernameLike(value);//用户名
            example.or().andIpLike(value);//ip地址
            example.or().andReqMethodLike(value);//请求方法
            example.or().andExecMethodLike(value);//执行方法
            example.or().andExecDescLike(value);//执行描述
            example.or().andStatusLike(value);//状态
        }
        PageResultSet<LogWithBLOBs> resultSet = new PageResultSet<>();
        long count = logMapper.countByExample(example);
        List<LogWithBLOBs> list = logMapper.selectByExampleWithBLOBs(example);
        resultSet.setRows(list);
        resultSet.setTotal(count);
        return resultSet;
    }
}
