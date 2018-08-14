package com.demon.dstudio.web.controller;

import com.demon.dstudio.web.model.MiniGridEntity;
import com.demon.dstudio.web.model.MiniRowEntity;
import com.demon.dstudio.web.model.ResponseMsg;
import com.demon.dstudio.web.service.AbstractRowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author demon
 */
public abstract class AbstractRowController<T extends MiniRowEntity,  S extends AbstractRowService> extends
        AbstractController<T, S> {

    @Autowired
    private S service;

    @PostMapping("/batch_save")
    public ResponseMsg batchSave(@RequestBody MiniGridEntity<T> gridEntity) {
        service.batchSave(gridEntity.getData());
        return ResponseMsg.success("");
    }

}
