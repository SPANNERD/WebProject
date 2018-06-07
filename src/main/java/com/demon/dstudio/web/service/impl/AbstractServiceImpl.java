package com.demon.dstudio.web.service.impl;

import com.demon.dstudio.web.model.MiniRowEntity;
import com.demon.dstudio.web.model.PageEntity;
import com.demon.dstudio.web.model.PageResult;
import com.demon.dstudio.web.service.AbstractService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import tgtools.web.develop.model.BaseModel;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * @author wd824
 */
public abstract class AbstractServiceImpl<T extends BaseModel, M extends Mapper<T>> implements AbstractService<T> {

    private Class<T> cls;

    @Autowired
    private M mapper;

    public AbstractServiceImpl() {
        ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();
        this.cls = (Class<T>) parameterizedType.getActualTypeArguments()[0];
    }

    @Override
    public List<T> listAll() {
        Example example = new Example(cls);
        return mapper.selectByExample(example);
    }

    @Override
    public PageResult<T> listByPage(T entity, PageEntity pageEntity) {
        int pageIndex = Integer.parseInt(pageEntity.getPageIndex());
        int pageSize = Integer.parseInt(pageEntity.getPageSize());
        Example example = new Example(cls);
        List<T> list = mapper.selectByExampleAndRowBounds(example, new RowBounds((pageIndex - 1) * pageSize, pageSize));
        return new PageResult<>(mapper.selectCount(entity), list);
    }


    @Override
    public List<T> list(T entity) {
        return mapper.select(entity);
    }

    @Override
    public int count(T entity) {
        return mapper.selectCount(entity);
    }

    @Override
    public T get(String id) {
        return mapper.selectByPrimaryKey(id);
    }



    @Override
    public T getByFilter(T record) {
        return mapper.selectOne(record);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean save(T entity) {
        return 1 == mapper.insert(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean remove(String id) {
        return 1 == mapper.deleteByPrimaryKey(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean update(T entity) {
        return 1 == mapper.updateByPrimaryKeySelective(entity);
    }


}
