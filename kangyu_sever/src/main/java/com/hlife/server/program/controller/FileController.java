package com.hlife.server.program.controller;

import com.hlife.framework.base.ResultVO;
import com.hlife.server.program.model.MyFile;
import com.hlife.server.program.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件 controller
 */
@RestController
@RequestMapping(value = "file")
public class FileController {

    @Autowired
    private FileService fileService;

    /**
     * 文件上传
     * @param file 文件
     * @return 文件
     */
    @PostMapping("/upload")
    @ResponseBody
    public ResultVO<MyFile> upload(@RequestParam("file") MultipartFile file) {

        return new ResultVO<>(fileService.uploadFile(file));
    }

    /**
     * 删除文件
     * @param myFile 文件
     * @return 删除情况
     */
    @PostMapping("/removeFile")
    @ResponseBody
    public ResultVO<Boolean> removeFile(@RequestBody MyFile myFile) {
        return new ResultVO<>(fileService.removeFile(myFile.getPath()));
    }

    /**
     * 文件预览
     *
     * @param myFile 文件
     * @return 文件流
     */
    @PostMapping("/preview")
    @ResponseBody
    public ResultVO<String> preview(@RequestBody MyFile myFile) {
        return new ResultVO<>(fileService.preview(myFile.getPath()));
    }
}
