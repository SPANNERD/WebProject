package com.dstudio.service;

import com.dstudio.model.BaseModel;
import com.dstudio.model.PageEntity;
import com.dstudio.model.PageResult;

import java.util.List;

/**
 * @author wd824
 */
public interface AbstractService<T extends BaseModel> {

    List<T> listAll();

    PageResult<T> listByPage(T entity, PageEntity pageEntity);

    int count(T entity);

    T get(String id);

    T getByFilter(T record);

    boolean save(T entity);

    boolean remove(String id);

    boolean update(T entity);

    void batchSave(List<T> list);

}
