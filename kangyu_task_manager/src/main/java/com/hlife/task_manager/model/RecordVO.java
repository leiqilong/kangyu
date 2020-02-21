package com.hlife.task_manager.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class RecordVO implements Serializable {

    private String guid;
    private String name;
    private String wid;
    private String Weixinid;
}
