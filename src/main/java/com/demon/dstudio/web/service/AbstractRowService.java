package com.demon.dstudio.web.service;

import com.demon.dstudio.web.model.MiniRowEntity;

import java.util.List;

/**
 * @author demon
 */
public interface AbstractRowService<T extends MiniRowEntity> extends AbstractService<T> {

    void batchSave(List<T> list);
}
