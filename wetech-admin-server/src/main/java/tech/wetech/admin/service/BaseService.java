package tech.wetech.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import tech.wetech.mybatis.ThreadContext;
import tech.wetech.mybatis.domain.Page;
import tech.wetech.mybatis.mapper.BaseMapper;

import java.util.List;

/**
 * @param <T>
 * @author cjbi@outlook.com
 */
public abstract class BaseService<T> implements IService<T> {

    @Autowired
    protected BaseMapper<T> mapper;

    public BaseMapper<T> getMapper() {
        return mapper;
    }

    @Override
    public List<T> queryAll() {
        return mapper.selectAll();
    }

    @Override
    public List<T> queryList(T entity) {
        return mapper.selectList(entity);
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
    public List<T> queryList(T entity, Page page) {
        ThreadContext.setPage(page.getPageNumber(), page.getPageSize(), true);
        return mapper.selectList(entity);
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
    public int deleteById(Long id) {
        return mapper.deleteByPrimaryKey(id);
    }

    @Override
    public int count(T entity) {
        return mapper.count(entity);
    }
}
