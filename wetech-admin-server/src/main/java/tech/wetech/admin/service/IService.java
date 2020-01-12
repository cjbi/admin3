package tech.wetech.admin.service;

import tech.wetech.mybatis.domain.Page;

import java.util.List;

/**
 * 通用接口
 *
 * @param <T>
 * @author cjbi@outlook.com
 */
public interface IService<T> {

    List<T> queryAll();

    List<T> queryList(T entity);

    T queryOne(T entity);

    T queryById(Object id);

    List<T> queryList(T entity, Page page);

    int create(T entity);

    int updateAll(T entity);

    int updateNotNull(T entity);

    int deleteById(Long id);

    int count(T entity);

}
