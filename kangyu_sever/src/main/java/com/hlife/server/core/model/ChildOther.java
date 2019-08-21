package com.hlife.server.core.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ChildOther {

    private Integer gestationalWeek;

    private Integer gestationalDay;

}
