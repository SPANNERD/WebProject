package com.dstudio.web.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Transient;
import java.io.Serializable;

/**
 * @author wd824
 */
@Setter
@Getter
public class MiniRowEntity implements Serializable {

    public final static String STATE_UPDATE = "modified";
    public final static String STATE_INSERT = "added";
    public final static String STATE_DELETE = "removed";
    public final static String STATE_UPDATE_ENABLE = "update_enable";

    @Transient
    @JsonProperty("_state")
    private String rowState;

}
