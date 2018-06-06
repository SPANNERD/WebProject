package com.demon.dstudio.web.controller;

import com.demon.dstudio.web.model.*;
import com.demon.dstudio.web.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import tgtools.exceptions.APPErrorException;
import tgtools.util.GUID;
import tgtools.web.develop.model.BaseModel;

import java.lang.reflect.ParameterizedType;
import java.util.Map;

/**
 * @author wd824
 */
public abstract class AbstractController<T extends BaseModel, S extends AbstractService> {

    private Class<T> cls;

    @Autowired
    private S service;

    public AbstractController() {
        ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();
        this.cls = (Class<T>) parameterizedType.getActualTypeArguments()[0];
    }

    @PostMapping("/list_all")
    public ResponseMsg listAll() {
        return ResponseMsg.success(service.listAll());
    }

    @PostMapping("/list")
    public ResponseMsg list(@RequestParam Map<String, String> param) throws Exception  {
        String pageIndex = param.get("pageIndex");
        String pageSize = param.get("pageSize");
        PageEntity pageEntity = new PageEntity(pageIndex, pageSize);

        PageResult<T> pageResult = service.listByPage(cls.newInstance(), pageEntity);
        return ResponseMsg.success(pageResult.getData(), pageResult.getTotal());
    }

    @PostMapping("/get")
    public ResponseMsg get(@RequestParam("id") String id) throws APPErrorException  {
        return ResponseMsg.success(service.get(id));
    }

    @PostMapping("/update")
    public ResponseMsg update(@RequestBody T entity) throws APPErrorException {
        if (service.update(entity)) {
            return ResponseMsg.success("更新成功！");
        } else {
            return ResponseMsg.fail(-1, "更新失败！");
        }
    }

    @PostMapping("/save")
    public ResponseMsg save(@RequestBody T entity) throws APPErrorException {
        entity.setId(GUID.newGUID());
        entity.setRev(System.currentTimeMillis());
        service.save(entity);
        return ResponseMsg.success(entity);
    }

    @PostMapping("/remove")
    public ResponseMsg remove(@RequestParam("id") String id) throws APPErrorException {
        service.remove(id);
        return ResponseMsg.success("删除成功！");
    }

    @PostMapping("/batch_save")
    public ResponseMsg batchSave(@RequestBody MiniGridEntity<T> gridEntity) {
        service.batchSave(gridEntity.getData());
        return ResponseMsg.success("");
    }

}
