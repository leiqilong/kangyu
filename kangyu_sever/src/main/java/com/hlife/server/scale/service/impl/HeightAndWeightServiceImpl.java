package com.hlife.server.scale.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hlife.framework.base.PageParam;
import com.hlife.framework.base.PageResult;
import com.hlife.framework.config.BusinessConfig;
import com.hlife.framework.util.GuidUtil;
import com.hlife.framework.util.StringUtil;
import com.hlife.server.core.constant.CoreConstant;
import com.hlife.server.core.model.Record;
import com.hlife.server.core.service.RecordService;
import com.hlife.server.scale.dao.HeightAndWeightMapper;
import com.hlife.server.scale.model.HeightAndWeight;
import com.hlife.server.scale.service.HeightAndWeightService;
import com.hlife.server.scale.utils.HeightAndWeightUtils;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class HeightAndWeightServiceImpl implements HeightAndWeightService {

    @Autowired
    private RecordService recordService;
    /*@Autowired
    private ChildOtherArchivesInfoService childOtherArchivesInfoService;
    @Autowired
    private GaugeGrowService gaugeGrowService;*/
    @Autowired
    private HeightAndWeightMapper heightAndWeightMapper;
    @Autowired
    private BusinessConfig businessConfig;
    @Autowired
    private WeChatComponent weChatComponent;

    /*@Override
    public HeightAndWeight getHeightAdnWeightTestResult(HeightAndWeight heightAndWeight) {
        checkHeightAndWeight(heightAndWeight);

        String guid = heightAndWeight.getGuid();
        double height = heightAndWeight.getHeight();
        double weight = heightAndWeight.getWeight();

        HeightAndWeight lastHeightAndWeight
                = this.heightAndWeightMapper.findLastHeightAndWeight(guid, heightAndWeight.getCreateTime());

        if (lastHeightAndWeight != null) {
            heightAndWeight.putHeightIncrease(lastHeightAndWeight.getHeight())
                    .putWeightIncrease(lastHeightAndWeight.getWeight());
        }

        Record record = this.recordService.findOne(guid).putAgeDetail();

        double monthAge = record.getAgeDetail().getMonthAge();
        heightAndWeight.setMonthAge(monthAge);


        String sexValue = CoreConstant.Gender.getInstanceBySex(record.getSex()).getValue();
        GaugeGrow gaugeGrow = this.gaugeGrowService.findGaugeGrowByMonthOfAgeAndSex(monthAge, sexValue);

        if (gaugeGrow != null) {
            heightAndWeight.setHeightEvaluation(gaugeGrow.getHeightGender(height))
                    .setWeightEvaluation(gaugeGrow.getWeightGender(weight));
        }

        if (monthAge > 24) {
            return heightAndWeight;
        }

        ChildOther childOther = this.childOtherArchivesInfoService.findOne(record.getGuid()).getInfo();

        int gestationalWeek = 40;
        if (childOther != null
                && (gestationalWeek = Optional.ofNullable(childOther.getGestationalWeek()).orElse(gestationalWeek)) < 37) {
            int correctValue = (40 - gestationalWeek) * 7 - childOther.getGestationalDay();
            DateUtil.Age correctAge = DateUtil.getAge(DateUtil.getCorrectDate(record.getBirthday(), correctValue));
            double correctMouthAge = correctAge.getMonthAge();
            GaugeGrow correctGaugeGrow = this.gaugeGrowService
                    .findGaugeGrowByMonthOfAgeAndSex(correctMouthAge, sexValue);
            if (correctGaugeGrow != null) {
                heightAndWeight.setCorrectMonthAge(correctMouthAge)
                        .setHeightCorrectEvaluation(correctGaugeGrow.getHeightGender(height))
                        .setWeightCorrectEvaluation(correctGaugeGrow.getWeightGender(weight));
            }
        }

        return heightAndWeight;
    }*/

    @Override
    public HeightAndWeight getHeightAdnWeightTestResult(HeightAndWeight heightAndWeight) {
        checkHeightAndWeight(heightAndWeight);

        String guid = heightAndWeight.getGuid();
        double height = heightAndWeight.getHeight();
        double weight = heightAndWeight.getWeight();

        Record record = this.recordService.findOne(guid).putAgeDetail();

        int monthAgeInt = record.getAgeDetail().getMonthAgeInt();
        heightAndWeight.setMonthAgeInt(monthAgeInt);

        HeightAndWeight lastHeightAndWeight
                = this.heightAndWeightMapper.findLastHeightAndWeight(guid, monthAgeInt);

        if (lastHeightAndWeight != null) {
            heightAndWeight.putHeightIncrease(lastHeightAndWeight.getHeight())
                    .putWeightIncrease(lastHeightAndWeight.getWeight());
        }

        String sexValue = CoreConstant.Gender.getInstanceBySex(record.getSex()).getValue();

        HeightAndWeightUtils.HeightStand heightStand = new HeightAndWeightUtils.HeightStand.Builder(sexValue, monthAgeInt).getInstance();
        HeightAndWeightUtils.WeightStand weightStand = new HeightAndWeightUtils.WeightStand.Builder(sexValue, monthAgeInt).getInstance();

        if (heightStand != null) {
            heightAndWeight.setHeightEvaluation(heightStand.getJudgeStand(height));
        }

        if (weightStand != null) {
            heightAndWeight.setWeightEvaluation(weightStand.getJudgeStand(weight));
        }

        /*GaugeGrow gaugeGrow = this.gaugeGrowService.findGaugeGrowByMonthOfAgeAndSex(monthAge, sexValue);

        if (gaugeGrow != null) {
            heightAndWeight.setHeightEvaluation(gaugeGrow.getHeightGender(height))
                    .setWeightEvaluation(gaugeGrow.getWeightGender(weight));
        }*/

        /*if (monthAgeInt > 24) {
            return heightAndWeight;
        }

        ChildOther childOther = this.childOtherArchivesInfoService.findOne(record.getGuid()).getInfo();

        int gestationalWeek = 40;
        if (childOther != null
                && (gestationalWeek = Optional.ofNullable(childOther.getGestationalWeek()).orElse(gestationalWeek)) < 37) {
            int correctValue = (40 - gestationalWeek) * 7 - childOther.getGestationalDay();
            DateUtil.Age correctAge = DateUtil.getAge(DateUtil.getCorrectDate(record.getBirthday(), correctValue));
            int correctMouthAge = correctAge.getMonthAgeInt();

            HeightAndWeightUtils.HeightStand correctHeightStand = new HeightAndWeightUtils.HeightStand.Builder(sexValue, correctMouthAge).getInstance();
            HeightAndWeightUtils.WeightStand correctWeightStand = new HeightAndWeightUtils.WeightStand.Builder(sexValue, correctMouthAge).getInstance();

            if (correctHeightStand != null) {
                heightAndWeight.setHeightEvaluation(correctHeightStand.getJudgeStand(height));
            }

            if (correctWeightStand != null) {
                heightAndWeight.setWeightEvaluation(correctHeightStand.getJudgeStand(weight));
            }
        }*/

        return heightAndWeight;
    }


    /*@Override
    public HeightAndWeight saveOrEditHeightAndWeight(HeightAndWeight heightAndWeight) {
        checkHeightAndWeight(heightAndWeight);

        String hwId = heightAndWeight.getHwId();

        if (StringUtil.stringIsNull(hwId)) {
            heightAndWeight.setHwId(GuidUtil.generateGuid())
                    .setCreateTime(new Date());
        } else {
            Document queryDoc = new Document("hwId", hwId);
            HeightAndWeight record = this.heightAndWeightMapper.findOne(queryDoc);
            if (record == null) {
                throw new RuntimeException("数据不存在了！");
            }

            heightAndWeight.setCreateTime(record.getCreateTime());

            this.heightAndWeightMapper.remove(queryDoc);
        }

        return this.heightAndWeightMapper.save(heightAndWeight);
    } */

    @Override
    public HeightAndWeight saveOrEditHeightAndWeight(HeightAndWeight heightAndWeight) {
        checkHeightAndWeight(heightAndWeight);

        Document queryDoc = new Document("guid", heightAndWeight.getGuid())
                .append("monthAgeInt", heightAndWeight.getMonthAgeInt());

        if (this.heightAndWeightMapper.isExists(queryDoc)) {
            this.heightAndWeightMapper.remove(queryDoc);
        }

        HeightAndWeight haw = this.heightAndWeightMapper.save(
                heightAndWeight.setHwId(GuidUtil.generateGuid())
                        .setCreateTime(new Date())
        );

        new Thread(() -> this.sendMessage(haw))
                .start();

        return haw;
    }

    @Override
    public PageResult<HeightAndWeight> findHeightAndWeightHistoryPagination(JSONObject jsonObject) {
        Document queryDoc = new Document();
        String guid = jsonObject.getString("guid");
        if (StringUtil.stringIsNotNull(guid)) {
            queryDoc.put("guid", guid);
        }

        PageParam pageParam = new PageParam(jsonObject.getInteger(PageParam.PAGE_SIZE),
                jsonObject.getInteger(PageParam.PAGE_NUM));
        return this.heightAndWeightMapper.findHeightAndWeightHistoryPagination(queryDoc, pageParam);
    }

    /*@Override
    public JSONObject findHeightAndWeightGraphData(String guid) {
        Record record = this.recordService.findOne(guid).putAgeDetail();

        if (record.getAgeDetail().getYear() >= 0) {
            return this.findHeightAndWeightGraphData(record);
        }

        JSONObject heightJsonObject = new JSONObject();
        JSONObject weightJsonObject = new JSONObject();

        List<Double> monthList = getMonthList(guid, heightJsonObject, weightJsonObject);

        double minPeriod = monthList.stream().min(Double::compareTo).orElse(0d) - 2;
        double maxPeriod = monthList.stream().max(Double::compareTo).orElse(0d) + 2;

        JSONObject gaugeGrowParam = new JSONObject()
                .fluentPut("sex", CoreConstant.Gender.getInstanceBySex(record.getSex()).getValue())
                .fluentPut("max", maxPeriod)
                .fluentPut("min", minPeriod);

        List<GaugeGrow> gaugeGrowList = this.gaugeGrowService.findGaugeGrowByPeriodAndSex(gaugeGrowParam);

        JSONArray sgfusansdArray = new JSONArray(), sgfuersdArray = new JSONArray(), sgfuyisdArray = new JSONArray(),
                sgmidnumArray = new JSONArray(),
                sgyisdArray = new JSONArray(), sgersdArray = new JSONArray(), sgsansdArray = new JSONArray(),
                wtfusansdArray = new JSONArray(), wtfuersdArray = new JSONArray(), wtfuyisdArray = new JSONArray(),
                wtmidnumArray = new JSONArray(),
                wtyisdArray = new JSONArray(), wtersdArray = new JSONArray(), wtsansdArray = new JSONArray();

        for (GaugeGrow gaugeGrow : gaugeGrowList) {
            sgfusansdArray.add(gaugeGrow.getMonthOfAgeSgfusansdEntry());
            sgfuersdArray.add(gaugeGrow.getMonthOfAgeSgfuersdEntry());
            sgfuyisdArray.add(gaugeGrow.getMonthOfAgeSgfuyisdEntry());
            sgmidnumArray.add(gaugeGrow.getMonthOfAgeSgmidnumEntry());
            sgyisdArray.add(gaugeGrow.getMonthOfAgeSgyisdEntry());
            sgersdArray.add(gaugeGrow.getMonthOfAgeSgersdEntry());
            sgsansdArray.add(gaugeGrow.getMonthOfAgeSgsansdEntry());

            wtfusansdArray.add(gaugeGrow.getMonthOfAgeWtfusansdEntry());
            wtfuersdArray.add(gaugeGrow.getMonthOfAgeWtfuersdEntry());
            wtfuyisdArray.add(gaugeGrow.getMonthOfAgeWtfuyisdEntry());
            wtmidnumArray.add(gaugeGrow.getMonthOfAgeWtmidnumEntry());
            wtyisdArray.add(gaugeGrow.getMonthOfAgeWtyisdEntry());
            wtersdArray.add(gaugeGrow.getMonthOfAgeWtersdEntry());
            wtsansdArray.add(gaugeGrow.getMonthOfAgeWtsansdEntry());
        }

        heightJsonObject
                .fluentPut("sgfusansdArray", sgfusansdArray)
                .fluentPut("sgfuersdArray", sgfuersdArray)
                .fluentPut("sgfuyisdArray", sgfuyisdArray)
                .fluentPut("sgmidnumArray", sgmidnumArray)
                .fluentPut("sgyisdArray", sgyisdArray)
                .fluentPut("sgersdArray", sgersdArray)
                .fluentPut("sgsansdArray", sgsansdArray);

        weightJsonObject
                .fluentPut("wtfusansdArray", wtfusansdArray)
                .fluentPut("wtfuersdArray", wtfuersdArray)
                .fluentPut("wtfuyisdArray", wtfuyisdArray)
                .fluentPut("wtmidnumArray", wtmidnumArray)
                .fluentPut("wtyisdArray", wtyisdArray)
                .fluentPut("wtersdArray", wtersdArray)
                .fluentPut("wtsansdArray", wtsansdArray);

        return new JSONObject().fluentPut("heightData", heightJsonObject)
                .fluentPut("weightData", weightJsonObject);
    }*/

    @Override
    public JSONObject findHeightAndWeightGraphData(String guid) {
        Record record = this.recordService.findOne(guid).putAgeDetail();

        return this.findHeightAndWeightGraphData(record);
    }

    @Override
    public List<HeightAndWeight> findHeightAndWeightList(JSONObject jsonObject) {
        Document queryDoc = new Document();
        String guid = jsonObject.getString("guid");
        if (StringUtil.stringIsNotNull(guid)) {
            queryDoc.put("guid", guid);
        }

        return this.heightAndWeightMapper.findHeightAndWeightList(queryDoc);
    }

    @Override
    public HeightAndWeight wpaSave(HeightAndWeight heightAndWeight) {
        return this.saveOrEditHeightAndWeight(this.getHeightAdnWeightTestResult(heightAndWeight));
    }

    /**
     * 传入值验证
     *
     * @param heightAndWeight 传入值
     */
    private void checkHeightAndWeight(HeightAndWeight heightAndWeight) {
        if (StringUtil.stringIsNull(heightAndWeight.getGuid())) {
            throw new IllegalArgumentException("患者guid为空");
        }

        if (heightAndWeight.getHeight() == null) {
            throw new IllegalArgumentException("身高为空");
        }

        if (heightAndWeight.getWeight() == null) {
            throw new IllegalArgumentException("体重为空");
        }
    }

    private JSONObject findHeightAndWeightGraphData(Record record) {
        String sex = record.getSex();
        JSONObject heightJsonObject = new JSONObject();
        JSONObject weightJsonObject = new JSONObject();

        List<Integer> monthList = this.getMonthList(record.getGuid(), heightJsonObject, weightJsonObject);

        int maxMonth = monthList.stream().filter(Objects::nonNull).max(Integer::compareTo).orElse(206) + 10;
        int minMonth = monthList.stream().filter(integer -> Objects.nonNull(integer) && integer > 10).min(Integer::compareTo).orElse(10) - 10;


        heightJsonObject.putAll(HeightAndWeightUtils.getHeightStandJson(sex, maxMonth, minMonth));
        weightJsonObject.putAll(HeightAndWeightUtils.getWeightStandJson(sex, maxMonth, minMonth));

        return new JSONObject().fluentPut("heightData", heightJsonObject)
                .fluentPut("weightData", weightJsonObject);
    }

    private List<Integer> getMonthList(String guid, JSONObject heightJsonObject, JSONObject weightJsonObject) {
        List<HeightAndWeight> heightAndWeightList = this.findHeightAndWeightList(new JSONObject().fluentPut("guid", guid));

        JSONArray heightArray = new JSONArray(), weightArray = new JSONArray();
        //, correctHeightArray = new JSONArray(), correctWeightArray = new JSONArray();

        List<Integer> monthList = new ArrayList<>();

        for (HeightAndWeight heightAndWeight : heightAndWeightList) {
            heightArray.add(heightAndWeight.getMonthIntHeightEntry());
            weightArray.add(heightAndWeight.getMonthIntWeightEntry());
            monthList.add(heightAndWeight.getMonthAgeInt());

            /*if (heightAndWeight.getCorrectMonthAge() != null) {
                monthList.add(heightAndWeight.getCorrectMonthAge());
                correctHeightArray.add(heightAndWeight.getCorrectMonthHeightEntry());
                correctWeightArray.add(heightAndWeight.getCorrectMonthWeightEntry());
            }*/
        }
        heightJsonObject.put("heightArray", heightArray);
        weightJsonObject.put("weightArray", weightArray);

        /*heightJsonObject.put("correctHeightArray", correctHeightArray);
        weightJsonObject.put("correctWeightArray", correctWeightArray);*/
        return monthList;
    }


    private void sendMessage(HeightAndWeight heightAndWeight) {
        Record record = this.recordService.findOne(heightAndWeight.getGuid());

        if (record == null || StringUtil.stringIsNull(record.getWeixinid())) {
            return;
        }

        sendHeightMessage(heightAndWeight.getHeightEvaluation(), record);
        sendWeightMessage(heightAndWeight.getWeightEvaluation(), record);
    }

    private void sendWeightMessage(String weightEvaluation, Record record) {
        if (StringUtil.stringIsNull(weightEvaluation)) {
            return;
        }

        JSONObject tagJSONObject = new JSONObject().fluentPut("tagName", "体重").fluentPut("tagValue", weightEvaluation);

        this.weChatComponent.putMessage(record, tagJSONObject);
    }

    private void sendHeightMessage(String heightEvaluation, Record record) {
        if (StringUtil.stringIsNull(heightEvaluation)) {
            return;
        }

        JSONObject tagJSONObject = new JSONObject().fluentPut("tagName", "身高").fluentPut("tagValue", heightEvaluation);

        this.weChatComponent.putMessage(record, tagJSONObject);
    }
}
