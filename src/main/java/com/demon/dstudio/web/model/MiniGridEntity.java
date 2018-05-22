package com.demon.dstudio.web.model;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @author wd824
 */
@Getter
@Setter
public class MiniGridEntity<T> implements Serializable {

    private List<T> data;

}
