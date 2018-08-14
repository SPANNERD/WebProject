package com.demon.dstudio.web.service.impl;

import com.demon.dstudio.web.model.MiniRowEntity;
import com.demon.dstudio.web.service.AbstractRowService;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author demon
 */
public class AbstractRowServiceImpl<T extends MiniRowEntity, D extends Mapper<T>> extends AbstractServiceImpl<T, D>
        implements AbstractRowService<T> {

    @Override
    public void batchSave(List<T> list) {
        for (T row : list) {
            if (MiniRowEntity.STATE_INSERT.equals(row.getRowState())) {
                row.initNew();
                save(row);
            }
            if (MiniRowEntity.STATE_UPDATE.equals(row.getRowState())) {
                update(row);
            }
            if (MiniRowEntity.STATE_DELETE.equals(row.getRowState())) {
                remove(row.getId());
            }
        }
    }
}
