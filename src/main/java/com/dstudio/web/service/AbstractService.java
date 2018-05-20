package com.dstudio.web.service;

import com.dstudio.web.model.BaseModel;
import com.dstudio.web.model.PageEntity;
import com.dstudio.web.model.PageResult;

import java.util.List;

/**
 * @author wd824
 */
public interface AbstractService<T extends BaseModel> {

    List<T> listAll();

    PageResult<T> listByPage(T entity, PageEntity pageEntity);

    List<T> list(T entity);

    int count(T entity);

    T get(String id);

    T getByFilter(T record);

    boolean save(T entity);

    boolean remove(String id);

    boolean update(T entity);

    void batchSave(List<T> list);

}
