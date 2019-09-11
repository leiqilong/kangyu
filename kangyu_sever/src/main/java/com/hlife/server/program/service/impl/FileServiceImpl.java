package com.hlife.server.program.service.impl;

import com.hlife.framework.config.BusinessConfig;
import com.hlife.framework.util.DateUtil;
import com.hlife.framework.util.GuidUtil;
import com.hlife.server.program.constant.ProgramConstant;
import com.hlife.server.program.model.MyFile;
import com.hlife.server.program.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Base64;
import java.util.Objects;

/**
 * 文件上传下载业务层实现
 */
@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private BusinessConfig businessConfig;

    @Override
    public MyFile uploadFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new RuntimeException("传入文件为空");
        }

        String dirPath = businessConfig.getFilePath() + File.separator + DateUtil.getTodayStr(DateUtil.DATE_FMT) + File.separator;
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String targetPath = dirPath + GuidUtil.generateGuid() + ProgramConstant.FileType.getInstance(file.getContentType()).getSuffix();
        File targetFile = new File(targetPath);

        try {
            file.transferTo(targetFile);
            return new MyFile()
                    .setName(file.getOriginalFilename())
                    .setPath(targetPath)
                    .setStatus(MyFile.Status.SUCCESS.getName());
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }



    @Override
    public boolean removeFile(String filePath) {
        File file = new File(filePath);

        if (!file.exists()) {
            return false;
        }

        if (file.isDirectory()) {
            String[] fileList = file.list();

            if (fileList != null && fileList.length > 0) {
                for (String path: Objects.requireNonNull(file.list())) {
                    removeFile(path);
                }
            }
        }

        if (file.delete()) {
            File parent = file.getParentFile();
            if (Objects.requireNonNull(parent.list()).length == 0) {
                return parent.delete();
            }
            return true;
        }

        return false;
    }

    @Override
    public String preview(String path) {
        BufferedInputStream bufferedInputStream = null;
        try {
            bufferedInputStream = new BufferedInputStream(new FileInputStream(path));
            byte[] buff = new byte[bufferedInputStream.available()];
            bufferedInputStream.read(buff);
            //return new BASE64Encoder().encode(buff);
            return Base64.getEncoder().encodeToString(buff);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedInputStream != null) {
                try {
                    bufferedInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        return null;
    }
}
