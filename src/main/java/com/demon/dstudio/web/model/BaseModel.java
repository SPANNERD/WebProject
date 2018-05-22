package com.demon.dstudio.web.model;

import lombok.Data;
import tgtools.util.GUID;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.OrderBy;

/**
 * @author wd824
 */
@Data
public class BaseModel extends MiniRowEntity {

    public static void init(BaseModel model) {
        model.setId(GUID.newGUID());
        model.setRev(System.currentTimeMillis());
    }
}
