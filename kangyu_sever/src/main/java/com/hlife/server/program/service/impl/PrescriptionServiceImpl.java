package com.hlife.server.program.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hlife.framework.base.PageParam;
import com.hlife.framework.base.PageResult;
import com.hlife.framework.util.DateUtil;
import com.hlife.framework.util.GuidUtil;
import com.hlife.framework.util.StringUtil;
import com.hlife.server.program.dao.PrescriptionMapper;
import com.hlife.server.program.model.MyFile;
import com.hlife.server.program.model.Prescription;
import com.hlife.server.program.service.FileService;
import com.hlife.server.program.service.PrescriptionService;
import com.hlife.server.scenes.constant.ScenesConstant;
import com.hlife.server.scenes.service.MatchCustomFormAndTagService;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class PrescriptionServiceImpl implements PrescriptionService {

    @Autowired
    private PrescriptionMapper prescriptionMapper;
    @Autowired
    private MatchCustomFormAndTagService matchCustomFormAndTagService;
    @Autowired
    private FileService fileService;

    @Override
    public PageResult<Prescription> findPrescriptionPageResult(JSONObject jsonObject) {
        Document queryDoc = new Document();

        String title = jsonObject.getString("title");
        if (StringUtil.stringIsNotNull(title)) {
            queryDoc.append("title", Pattern.compile("^.*" + title + ".*$", Pattern.CASE_INSENSITIVE));
        }
        JSONArray daterange = jsonObject.getJSONArray("daterange");
        if (daterange != null && daterange.size() > 1) {
            queryDoc.append("createTime", DateUtil.parseDateRange(daterange));
        }

        PageParam pageParam = new PageParam(jsonObject.getInteger(PageParam.PAGE_SIZE),
                jsonObject.getInteger(PageParam.PAGE_NUM));
        return this.prescriptionMapper.findPrescriptionPageResult(queryDoc, pageParam);
    }

    @Override
    public Prescription savePrescription(Prescription prescription) {
        String id = prescription.getId();
        if (StringUtil.stringIsNotNull(id)) {
            Prescription record = this.prescriptionMapper.findOne(id);
            if (record == null) {
                throw new RuntimeException("这条数据已不存在");
            }
            prescription.setId(prescription.getId())
                    .setCreateTime(prescription.getCreateTime());
            this.deletePrescriptionSelf(id);
        } else {
            prescription.setId(GuidUtil.generateGuid())
                    .setCreateTime(new Date());
        }

        Prescription resultData = prescriptionMapper.save(prescription);

        this.matchCustomFormAndTagService.addMatchCustomFormAndTagList(
                new JSONObject()
                        .fluentPut("customFormId", ScenesConstant.RelatedFormType.PRESCRIPTION.getNewFormId(prescription.getId()))
                        .fluentPut("tagIdList", prescription.getCustomFormTags())

        );
        return resultData;
    }

    @Override
    public List<Prescription> findPrescriptionList(JSONObject jsonObject) {
        Document queryDoc = new Document();
        JSONArray idList = jsonObject.getJSONArray("idList");
        if (idList != null && idList.size() > 0) {
            queryDoc.append("id", new Document("$in", idList));
        }
        return this.prescriptionMapper.findPrescriptionList(queryDoc);
    }

    @Override
    public List<MyFile> findFileList(JSONObject jsonObject) {
        return this.findPrescriptionList(jsonObject).stream()
                .filter(Objects::nonNull)
                .map(Prescription::getFileList)
                .filter(Objects::nonNull)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    @Override
    public List<MyFile> findPrescriptionPictureList(JSONObject jsonObject) {
        return this.findFileList(jsonObject).stream()
                //.map(MyFile::getPath)
                .map(this::apply)
                .collect(Collectors.toList());
    }

    public long deletePrescription(String id) {
        List<MyFile> fileList = this.deletePrescriptionSelf(id);

        if (fileList != null && fileList.size() > 0) {
            for (MyFile myFile: fileList) {
                fileService.removeFile(myFile.getPath());
            }
        }

        return 1;
    }


    public boolean notExist(String id) {
        return !this.isExist(id);
    }

    @Override
    public boolean isExist(String id) {
        return this.prescriptionMapper.isExist(id);
    }

    private List<MyFile> deletePrescriptionSelf(String id) {
        Prescription record = this.prescriptionMapper.findOne(id);

        if (record == null) {
            throw new RuntimeException("这条数据已不存在");
        }


        this.matchCustomFormAndTagService.deleteMatchCustomFormAndTagListByFormId(ScenesConstant.RelatedFormType.PRESCRIPTION.getNewFormId(id));
        this.prescriptionMapper.delete(id);
        return record.getFileList();
    }

    private MyFile apply(MyFile myFile) {
        return myFile.setBase64Str(fileService.preview(myFile.getPath()));
        //return fileService.preview(path);
    }
}
