package tech.wetech.admin.modules.base.service.impl;

import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import tech.wetech.admin.modules.base.query.PageQuery;
import tech.wetech.admin.modules.base.service.IService;
import tk.mybatis.mapper.code.Style;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.util.StringUtil;

import java.util.List;

/**
 * @param <T>
 * @author cjbi@outlook.com
 */
public abstract class BaseService<T> implements IService<T> {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected Mapper<T> mapper;

    public Mapper<T> getMapper() {
        return mapper;
    }

    @Override
    public List<T> queryAll() {
        return mapper.selectAll();
    }

    @Override
    public List<T> queryList(T entity) {
        return mapper.select(entity);
    }

    @Override
    public T queryOne(T entity) {
        return mapper.selectOne(entity);
    }

    @Override
    public T queryById(Object id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public List<T> queryList(T entity, PageQuery pageQuery) {
        if (pageQuery.getOrderBy() != null) {
            pageQuery.setOrderBy(StringUtil.convertByStyle(pageQuery.getOrderBy(), Style.camelhump));
        }
        return PageHelper.startPage(pageQuery)
                .doSelectPage(() -> mapper.select(entity));
    }

    @Override
    public int create(T entity) {
        return mapper.insertSelective(entity);
    }

    @Override
    public int updateAll(T entity) {
        return mapper.updateByPrimaryKey(entity);
    }

    @Override
    public int updateNotNull(T entity) {
        return mapper.updateByPrimaryKeySelective(entity);
    }

    @Override
    public int delete(T entity) {
        return mapper.delete(entity);
    }

    @Override
    public int deleteById(Object id) {
        return mapper.deleteByPrimaryKey(id);
    }

    @Override
    public int count(T entity) {
        return mapper.selectCount(entity);
    }
}
