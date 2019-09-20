package com.hlife.framework.base;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Transient;

import java.util.List;

@Data
@Accessors(chain = true)
public abstract class BaseTree {

    private String parentId;

    private String nodeId;

    @Transient
    private List<BaseTree> children;

    @Transient
    private Boolean isLeaf;
}
