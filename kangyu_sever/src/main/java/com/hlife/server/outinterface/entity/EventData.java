package com.hlife.server.outinterface.entity;

import lombok.Data;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class EventData<T> implements Serializable {

    private String Hzguid;
    private String Weixinid;
    private String location;
    private String DevicType;
    private String bgdname;
    private String bgdate;
    private String dataGuid;
    private T data;

    public enum DevicType {
        PUQE_SCALE("ky.stl.scale.puqe", "妊娠期恶心呕吐量表");

        @Getter
        private String type;
        @Getter
        private String name;

        DevicType(String type, String name)  {
            this.type = type;
            this.name = name;
        }
    }
}
