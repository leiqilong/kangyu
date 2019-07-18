package com.hlife.shilitianqi.service.impl;

import com.hlife.framework.util.GuidUtil;
import com.hlife.shilitianqi.dao.DeviceMapper;
import com.hlife.shilitianqi.model.Device;
import com.hlife.shilitianqi.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 设备管理服务层实现
 */
@Service
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    private DeviceMapper deviceMapper;


    @Override
    public List<Device> getDeviceList() {
        return this.deviceMapper.findAll();
    }

    @Override
    public String addDeviceList() {
        this.deviceMapper.dropCollection();
        List<Device> deviceList = new ArrayList<>();
        addBandDeviceList(deviceList);
        addOtherDeviceList(deviceList);
        addPresDeviceList(deviceList);
        addFormDeviceList(deviceList);
        addForm1DeviceList(deviceList);
        this.deviceMapper.addDeviceList(deviceList);
        return "操作成功";
    }

    private void addFormDeviceList(List<Device> deviceList) {
        deviceList.add(
                new Device()
                        .setDeviceId(GuidUtil.generateGuid())
                        .setDeviceCode("ky.stl.form.Y3D5YA9ZUQNW11IXEALFHQ2JKL6ERH1C")
                        .setDeviceName("儿童健康检查")
                        .setCalculationTypeList(new ArrayList<Device.CalculationType>() {{
                            this.add(
                                    new Device.CalculationType(
                                            "运动",
                                            "form",
                                            new Device.FieldPath[]{
                                                    new Device.FieldPath("jk_tyhd")
                                            }
                                    )
                            );
                            this.add(
                                    new Device.CalculationType(
                                            "饮水量",
                                            "form",
                                            new Device.FieldPath[]{
                                                    new Device.FieldPath("jk_ysl")
                                            }
                                    )
                            );
                            this.add(
                                    new Device.CalculationType(
                                            "睡眠",
                                            "form",
                                            new Device.FieldPath[]{
                                                    new Device.FieldPath("jk_smsj")
                                            }
                                    )
                            );
                            this.add(
                                    new Device.CalculationType(
                                            "心率",
                                            "form",
                                            new Device.FieldPath[]{
                                                    new Device.FieldPath("jk_xl")
                                            }
                                    )
                            );

                            this.add(
                                    new Device.CalculationType(
                                            "BMI",
                                            "common",
                                            new Device.FieldPath[]{
                                                    new Device.FieldPath("jk_bmi")
                                            }
                                    )
                            );
                        }})
        );

        deviceList.add(
                new Device()
                        .setDeviceId(GuidUtil.generateGuid())
                        .setDeviceCode("ky.stl.form.I5KE2SNR9UVGI3C4J5ZGMFWN237PDNWY")
                        .setDeviceName("孕期基础档案信息表")
                        .setCalculationTypeList(new ArrayList<Device.CalculationType>() {{
                            this.add(
                                    new Device.CalculationType(
                                            "体温",
                                            "common",
                                            new Device.FieldPath[]{
                                                    new Device.FieldPath("yqjcdaxx_tw")
                                            }
                                    )
                            );
                            this.add(
                                    new Device.CalculationType(
                                            "血压",
                                            "common",
                                            new Device.FieldPath[]{
                                                    new Device.FieldPath("yqjcdaxx_xy")
                                            }
                                    )
                            );
                            this.add(
                                    new Device.CalculationType(
                                            "现空腹血糖",
                                            "form",
                                            new Device.FieldPath[]{
                                                    new Device.FieldPath("yqjcdaxx_yhxt")
                                            }
                                    )
                            );
                            this.add(
                                    new Device.CalculationType(
                                            "运动",
                                            "form",
                                            new Device.FieldPath[]{
                                                    new Device.FieldPath("yqjcdaxx_yd2")
                                            }
                                    )
                            );

                            this.add(
                                    new Device.CalculationType(
                                            "饮水量",
                                            "form",
                                            new Device.FieldPath[]{
                                                    new Device.FieldPath("yqjcdaxx_ys1")
                                            }
                                    )
                            );
                            this.add(
                                    new Device.CalculationType(
                                            "情绪",
                                            "mood",
                                            new Device.FieldPath[]{
                                                    new Device.FieldPath("yqjcdaxx_ybxl1")
                                            }
                                    )
                            );

                            this.add(
                                    new Device.CalculationType(
                                            "睡眠",
                                            "form",
                                            new Device.FieldPath[]{
                                                    new Device.FieldPath("yqjcdaxx_sleep3")
                                            }
                                    )
                            );
                        }})
        );

        deviceList.add(
                new Device()
                        .setDeviceId(GuidUtil.generateGuid())
                        .setDeviceCode("ky.stl.form.GRWPA4ICVJP4U5X2WG54H6LUBL1F4PEJ")
                        .setDeviceName("孕期高血压健康检查")
                        .setCalculationTypeList(new ArrayList<Device.CalculationType>() {{
                            this.add(
                                    new Device.CalculationType(
                                            "体温",
                                            "common",
                                            new Device.FieldPath[]{
                                                    new Device.FieldPath("yqjcdaxx_tw")
                                            }
                                    )
                            );
                            this.add(
                                    new Device.CalculationType(
                                            "平均动脉压（MAP）",
                                            "common",
                                            new Device.FieldPath[]{
                                                    new Device.FieldPath("yqjcdaxx_dmy")
                                            }
                                    )
                            );
                            this.add(
                                    new Device.CalculationType(
                                            "血糖",
                                            "common",
                                            new Device.FieldPath[]{
                                                    new Device.FieldPath("yqjcdaxx_glu")
                                            }
                                    )
                            );
                            this.add(
                                    new Device.CalculationType(
                                            "运动",
                                            "form",
                                            new Device.FieldPath[]{
                                                    new Device.FieldPath("yqjcdaxx_yd2")
                                            }
                                    )
                            );

                            this.add(
                                    new Device.CalculationType(
                                            "饮水量",
                                            "form",
                                            new Device.FieldPath[]{
                                                    new Device.FieldPath("yqjcdaxx_ys1")
                                            }
                                    )
                            );
                            this.add(
                                    new Device.CalculationType(
                                            "情绪",
                                            "mood",
                                            new Device.FieldPath[]{
                                                    new Device.FieldPath("yqjcdaxx_ybxl1")
                                            }
                                    )
                            );

                            this.add(
                                    new Device.CalculationType(
                                            "睡眠",
                                            "form",
                                            new Device.FieldPath[]{
                                                    new Device.FieldPath("yqjcdaxx_sleep3")
                                            }
                                    )
                            );
                        }})
        );
    }

    private void addForm1DeviceList(List<Device> deviceList) {
        deviceList.add(
                new Device()
                        .setDeviceId(GuidUtil.generateGuid())
                        .setDeviceCode("ky.stl.form.EMB748BFX82VGIZ4WG8RMAN51LYT9RNR")
                        .setDeviceName("孕期糖尿病健康检查")
                        .setCalculationTypeList(new ArrayList<Device.CalculationType>() {{
                            this.add(
                                    new Device.CalculationType(
                                            "体温",
                                            "common",
                                            new Device.FieldPath[]{
                                                    new Device.FieldPath("yqjcdaxx_tw")
                                            }
                                    )
                            );
                            this.add(
                                    new Device.CalculationType(
                                            "血压",
                                            "common",
                                            new Device.FieldPath[]{
                                                    new Device.FieldPath("yqjcdaxx_xy")
                                            }
                                    )
                            );
                            this.add(
                                    new Device.CalculationType(
                                            "血糖",
                                            "common",
                                            new Device.FieldPath[]{
                                                    new Device.FieldPath("yqjcdaxx_kfxy", Device.X),
                                                    new Device.FieldPath("yqjcdaxx_kfxy1", Device.Y),
                                                    new Device.FieldPath("yqjcdaxx_kfxy2", Device.Z)
                                            }
                                    )
                            );
                            this.add(
                                    new Device.CalculationType(
                                            "运动",
                                            "form",
                                            new Device.FieldPath[]{
                                                    new Device.FieldPath("yqjcdaxx_yd2")
                                            }
                                    )
                            );

                            this.add(
                                    new Device.CalculationType(
                                            "饮水量",
                                            "form",
                                            new Device.FieldPath[]{
                                                    new Device.FieldPath("yqjcdaxx_ys1")
                                            }
                                    )
                            );
                            this.add(
                                    new Device.CalculationType(
                                            "情绪",
                                            "mood",
                                            new Device.FieldPath[]{
                                                    new Device.FieldPath("yqjcdaxx_ybxl1")
                                            }
                                    )
                            );

                            this.add(
                                    new Device.CalculationType(
                                            "睡眠",
                                            "form",
                                            new Device.FieldPath[]{
                                                    new Device.FieldPath("yqjcdaxx_sleep3")
                                            }
                                    )
                            );
                        }})
        );

        deviceList.add(
                new Device()
                        .setDeviceId(GuidUtil.generateGuid())
                        .setDeviceCode("ky.stl.form.JSWGXMC62VO9GDFLY38UEXZRQLUBERE2")
                        .setDeviceName("孕期便秘健康检查")
                        .setCalculationTypeList(new ArrayList<Device.CalculationType>() {{
                            this.add(
                                    new Device.CalculationType(
                                            "体温",
                                            "common",
                                            new Device.FieldPath[]{
                                                    new Device.FieldPath("yqjcdaxx_tw")
                                            }
                                    )
                            );
                            this.add(
                                    new Device.CalculationType(
                                            "血压",
                                            "common",
                                            new Device.FieldPath[]{
                                                    new Device.FieldPath("yqjcdaxx_xy")
                                            }
                                    )
                            );
                            this.add(
                                    new Device.CalculationType(
                                            "血糖",
                                            "form",
                                            new Device.FieldPath[]{
                                                    new Device.FieldPath("yqjcdaxx_yhxt"),
                                            }
                                    )
                            );
                            this.add(
                                    new Device.CalculationType(
                                            "运动",
                                            "form",
                                            new Device.FieldPath[]{
                                                    new Device.FieldPath("yqjcdaxx_yd2")
                                            }
                                    )
                            );

                            this.add(
                                    new Device.CalculationType(
                                            "饮水量",
                                            "form",
                                            new Device.FieldPath[]{
                                                    new Device.FieldPath("yqjcdaxx_ys1")
                                            }
                                    )
                            );
                            this.add(
                                    new Device.CalculationType(
                                            "情绪",
                                            "mood",
                                            new Device.FieldPath[]{
                                                    new Device.FieldPath("yqjcdaxx_ybxl1")
                                            }
                                    )
                            );

                            this.add(
                                    new Device.CalculationType(
                                            "睡眠",
                                            "form",
                                            new Device.FieldPath[]{
                                                    new Device.FieldPath("yqjcdaxx_sleep3")
                                            }
                                    )
                            );

                            this.add(
                                    new Device.CalculationType(
                                            "排便",
                                            "form",
                                            new Device.FieldPath[]{
                                                    new Device.FieldPath("yqjcdaxx_pbqk1")
                                            }
                                    )
                            );
                        }})
        );

        deviceList.add(
                new Device()
                        .setDeviceId(GuidUtil.generateGuid())
                        .setDeviceCode("ky.stl.form.WSXP3AUT1H3MEX2PQDHX496OOXV5ATBQ")
                        .setDeviceName("孕期胆汁淤积健康检查")
                        .setCalculationTypeList(new ArrayList<Device.CalculationType>() {{
                            this.add(
                                    new Device.CalculationType(
                                            "体温",
                                            "common",
                                            new Device.FieldPath[]{
                                                    new Device.FieldPath("yqjcdaxx_tw")
                                            }
                                    )
                            );
                            this.add(
                                    new Device.CalculationType(
                                            "血压",
                                            "common",
                                            new Device.FieldPath[]{
                                                    new Device.FieldPath("yqjcdaxx_xy")
                                            }
                                    )
                            );
                            this.add(
                                    new Device.CalculationType(
                                            "血糖",
                                            "form",
                                            new Device.FieldPath[]{
                                                    new Device.FieldPath("yqjcdaxx_yhxt"),
                                            }
                                    )
                            );
                            this.add(
                                    new Device.CalculationType(
                                            "运动",
                                            "form",
                                            new Device.FieldPath[]{
                                                    new Device.FieldPath("yqjcdaxx_yd2")
                                            }
                                    )
                            );

                            this.add(
                                    new Device.CalculationType(
                                            "饮水量",
                                            "form",
                                            new Device.FieldPath[]{
                                                    new Device.FieldPath("yqjcdaxx_ys1")
                                            }
                                    )
                            );
                            this.add(
                                    new Device.CalculationType(
                                            "情绪",
                                            "mood",
                                            new Device.FieldPath[]{
                                                    new Device.FieldPath("yqjcdaxx_ybxl1")
                                            }
                                    )
                            );

                            this.add(
                                    new Device.CalculationType(
                                            "睡眠",
                                            "form",
                                            new Device.FieldPath[]{
                                                    new Device.FieldPath("yqjcdaxx_sleep3")
                                            }
                                    )
                            );
                            this.add(
                                    new Device.CalculationType(
                                            "血清总胆红素",
                                            "common",
                                            new Device.FieldPath[]{
                                                    new Device.FieldPath("jcqkXqhs")
                                            }
                                    )
                            );

                            this.add(
                                    new Device.CalculationType(
                                            "血清胆汁酸",
                                            "common",
                                            new Device.FieldPath[]{
                                                    new Device.FieldPath("jcqkDzs")
                                            }
                                    )
                            );
                        }})
        );

        deviceList.add(
                new Device()
                        .setDeviceId(GuidUtil.generateGuid())
                        .setDeviceCode("ky.stl.form.RWDXLLT2KXSKZ85KUJN8KBYCPB9GDYTV")
                        .setDeviceName("孕期贫血健康检查")
                        .setCalculationTypeList(new ArrayList<Device.CalculationType>() {{
                            this.add(
                                    new Device.CalculationType(
                                            "体温",
                                            "common",
                                            new Device.FieldPath[]{
                                                    new Device.FieldPath("yqjcdaxx_tw")
                                            }
                                    )
                            );
                            this.add(
                                    new Device.CalculationType(
                                            "血压",
                                            "common",
                                            new Device.FieldPath[]{
                                                    new Device.FieldPath("yqjcdaxx_xy")
                                            }
                                    )
                            );
                            this.add(
                                    new Device.CalculationType(
                                            "血糖",
                                            "form",
                                            new Device.FieldPath[]{
                                                    new Device.FieldPath("yqjcdaxx_yhxt"),
                                            }
                                    )
                            );
                            this.add(
                                    new Device.CalculationType(
                                            "运动",
                                            "form",
                                            new Device.FieldPath[]{
                                                    new Device.FieldPath("yqjcdaxx_yd2")
                                            }
                                    )
                            );

                            this.add(
                                    new Device.CalculationType(
                                            "饮水量",
                                            "form",
                                            new Device.FieldPath[]{
                                                    new Device.FieldPath("yqjcdaxx_ys1")
                                            }
                                    )
                            );
                            this.add(
                                    new Device.CalculationType(
                                            "情绪",
                                            "mood",
                                            new Device.FieldPath[]{
                                                    new Device.FieldPath("yqjcdaxx_ybxl1")
                                            }
                                    )
                            );

                            this.add(
                                    new Device.CalculationType(
                                            "睡眠",
                                            "form",
                                            new Device.FieldPath[]{
                                                    new Device.FieldPath("yqjcdaxx_sleep3")
                                            }
                                    )
                            );
                            this.add(
                                    new Device.CalculationType(
                                            "血红蛋白",
                                            "form",
                                            new Device.FieldPath[]{
                                                    new Device.FieldPath("xcgjcXhdb")
                                            }
                                    )
                            );
                        }})
        );
    }

    private void addPresDeviceList(List<Device> deviceList) {
        String suffix = "-实际值/推荐值";
        deviceList.add(
                new Device()
                        .setDeviceId(GuidUtil.generateGuid())
                        .setDeviceCode("ky.stl.cf.tjsp.ld")
                        .setDeviceName("膳食营养")
                        .setCalculationTypeList(new ArrayList<Device.CalculationType>() {{
                            this.add(
                                    new Device.CalculationType(
                                            "能量" + suffix,
                                            "common",
                                            new Device.FieldPath[]{
                                                    new Device.FieldPath("Energy.Actual", Device.X),
                                                    new Device.FieldPath("Energy.Recommend", Device.Y)
                                            }
                                    )
                            );

                            this.add(
                                    new Device.CalculationType(
                                            "蛋白质" + suffix,
                                            "common",
                                            new Device.FieldPath[]{
                                                    new Device.FieldPath("Protein.Actual", Device.X),
                                                    new Device.FieldPath("Protein.Recommend", Device.Y)
                                            }
                                    )
                            );

                            this.add(
                                    new Device.CalculationType(
                                            "脂肪" + suffix,
                                            "common",
                                            new Device.FieldPath[]{
                                                    new Device.FieldPath("Fat.Actual", Device.X),
                                                    new Device.FieldPath("Fat.Recommend", Device.Y)
                                            }
                                    )
                            );

                            this.add(
                                    new Device.CalculationType(
                                            "碳水化合物" + suffix,
                                            "common",
                                            new Device.FieldPath[]{
                                                    new Device.FieldPath("Cho.Actual", Device.X),
                                                    new Device.FieldPath("Cho.Recommend", Device.Y)
                                            }
                                    )
                            );
                        }})
        );
    }

    private void addOtherDeviceList(List<Device> deviceList) {
        deviceList.add(
                new Device()
                        .setDeviceId(GuidUtil.generateGuid())
                        .setDeviceCode("ky.stl.bgd.tcf")
                        .setDeviceName("报告单体成分")
                        .setCalculationTypeList(new ArrayList<Device.CalculationType>() {{
                            this.add(
                                    new Device.CalculationType(
                                            "体脂含量",
                                            "common",
                                            new Device.FieldPath[]{
                                                    new Device.FieldPath("hdcl[0].DataItem.Tzhl", Device.X)
                                            }
                                    )
                            );
                        }})
        );
        deviceList.add(
                new Device()
                        .setDeviceId(GuidUtil.generateGuid())
                        .setDeviceCode("ky.stl.Water")
                        .setDeviceName("水杯")
                        .setCalculationTypeList(new ArrayList<Device.CalculationType>() {{
                            this.add(
                                    new Device.CalculationType(
                                            "饮水量",
                                            "common",
                                            new Device.FieldPath[]{
                                                    new Device.FieldPath("waterValue", Device.X)
                                            }
                                    )
                            );
                        }})
        );
    }

    private void addBandDeviceList(List<Device> deviceList) {
        deviceList.add(
                new Device()
                        .setDeviceId(GuidUtil.generateGuid())
                        .setDeviceCode("ky.stl.Band.steps")
                        .setDeviceName("手环")
                        .setCalculationTypeList(new ArrayList<Device.CalculationType>() {{
                            this.add(
                                    new Device.CalculationType(
                                            "运动",
                                            "common",
                                            new Device.FieldPath[]{
                                                    new Device.FieldPath("data.dayTotals.step", Device.X)
                                            }
                                    )
                            );
                        }})
        );
        deviceList.add(
                new Device()
                        .setDeviceId(GuidUtil.generateGuid())
                        .setDeviceCode("ky.stl.Band.bloodpressure")
                        .setDeviceName("手环")
                        .setCalculationTypeList(new ArrayList<Device.CalculationType>() {{
                            this.add(
                                    new Device.CalculationType(
                                            "血压",
                                            "common",
                                            new Device.FieldPath[]{
                                                    new Device.FieldPath("data[0].sbp", Device.X),
                                                    new Device.FieldPath("data[0].dbp", Device.Y)
                                            }
                                    )
                            );
                        }})
        );

        deviceList.add(
                new Device()
                        .setDeviceId(GuidUtil.generateGuid())
                        .setDeviceCode("ky.stl.Band.bloodoxygen")
                        .setDeviceName("手环")
                        .setCalculationTypeList(new ArrayList<Device.CalculationType>() {{
                            this.add(
                                    new Device.CalculationType(
                                            "血氧",
                                            "common",
                                            new Device.FieldPath[]{
                                                    new Device.FieldPath("data[0].so2", Device.X)
                                            }
                                    )
                            );
                        }})
        );

        deviceList.add(
                new Device()
                        .setDeviceId(GuidUtil.generateGuid())
                        .setDeviceCode("ky.stl.Band.heartRate")
                        .setDeviceName("手环")
                        .setCalculationTypeList(new ArrayList<Device.CalculationType>() {{
                            this.add(
                                    new Device.CalculationType(
                                            "心率",
                                            "common",
                                            new Device.FieldPath[]{
                                                    new Device.FieldPath("data[0].bpm", Device.X)
                                            }
                                    )
                            );
                        }})
        );

        deviceList.add(
                new Device()
                        .setDeviceId(GuidUtil.generateGuid())
                        .setDeviceCode("ky.stl.Band.sleep")
                        .setDeviceName("手环")
                        .setCalculationTypeList(new ArrayList<Device.CalculationType>() {{
                            this.add(
                                    new Device.CalculationType(
                                            "睡眠",
                                            "common",
                                            new Device.FieldPath[]{
                                                    new Device.FieldPath("ss", Device.X),
                                                    new Device.FieldPath("qs", Device.Y)
                                            }
                                    )
                            );
                        }})
        );
    }

    private void addarchives(List<Device> deviceList) {
        deviceList.add(
                new Device()
                        .setDeviceId(GuidUtil.generateGuid())
                        .setDeviceCode("ky.stl.archives")
                        .setDeviceName("档案数据")
                        .setCalculationTypeList(new ArrayList<Device.CalculationType>() {{
                            this.add(
                                    new Device.CalculationType(
                                            "性别",
                                            "form",
                                            new Device.FieldPath[]{
                                                    new Device.FieldPath("sex", Device.X)
                                            }
                                    )
                            );
                        }})
        );
    }
}
