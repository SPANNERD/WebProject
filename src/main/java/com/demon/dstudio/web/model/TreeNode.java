package com.demon.dstudio.web.model;

import lombok.Data;

import javax.persistence.Transient;

/**
 * @author wd824
 */
@Data
public class TreeNode extends BaseModel {

    @Transient
    private boolean checked;


}
