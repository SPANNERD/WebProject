package com.demon.dstudio.web.mybatis.mapper;

import tk.mybatis.mapper.common.base.BaseSelectMapper;
import tk.mybatis.mapper.common.example.SelectByExampleMapper;
import tk.mybatis.mapper.common.example.SelectCountByExampleMapper;
import tk.mybatis.mapper.common.example.SelectOneByExampleMapper;

/**
 * @author wd824
 */
public interface SelectMapper<T> extends BaseSelectMapper<T>,
        SelectByExampleMapper<T>,
        SelectOneByExampleMapper<T>,
        SelectCountByExampleMapper<T> {
}
