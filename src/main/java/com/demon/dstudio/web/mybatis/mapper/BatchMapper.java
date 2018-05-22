package com.demon.dstudio.web.mybatis.mapper;

import com.demon.dstudio.web.mybatis.provider.BatchProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.List;

/**
 * @author wd824
 */
@tk.mybatis.mapper.annotation.RegisterMapper
public interface BatchMapper<T> {

    /**
     * 批量插入
     * @param recordList
     * @return
     */
    @InsertProvider(type = BatchProvider.class, method = "dynamicSQL")
    int insertList(List<T> recordList);

    @UpdateProvider(type = BatchProvider.class, method = "dynamicSQL")
    int batchUpdateByPrimaryKey(List<T> recordList);
}
