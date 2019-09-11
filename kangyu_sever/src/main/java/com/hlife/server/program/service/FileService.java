package com.hlife.server.program.service;

import com.hlife.server.program.model.MyFile;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传下载业务层接口
 */
public interface FileService {

    /**
     * 文件上传
     *
     * @param file 原文件
     * @param dirPath 新文件路径
     * @return 新文件
     */
    MyFile uploadFile(MultipartFile file, String dirPath);

    /**
     * 文件删除
     *
     * @param filePath 文件路径
     * @return 删除成功与否
     */
    boolean removeFile(String filePath);

    /**
     * 文件预览
     *
     * @param path 路径
     * @return 文件64位
     */
    String preview(String path);
}
