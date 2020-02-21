package com.hlife.task_manager.model;

import com.alibaba.fastjson.JSONArray;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class JobMapR {
    private List<RecordVO> records;
    private JSONArray forms;
    private String pushType;
}
