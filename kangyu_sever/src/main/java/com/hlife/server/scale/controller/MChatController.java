package com.hlife.server.scale.controller;

import com.alibaba.fastjson.JSONObject;
import com.hlife.framework.base.ResultVO;
import com.hlife.server.scale.model.MChat;
import com.hlife.server.scale.service.MChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * MChat 控制器
 */
@RestController
@RequestMapping(value = "/mChat")
public class MChatController {

    @Autowired
    private MChatService mChatService;

    /**
     * 保存MChat记录
     *
     * @param mChat MChat记录
     * @return resultVO.resultData 保存的 MChat记录
     */
    @PostMapping(value = "/saveMChat")
    public ResultVO<MChat> saveMChat(@RequestBody MChat mChat) {
        return new ResultVO<>(this.mChatService.saveMChat(mChat));
    }

    /**
     * 查询MChat记录
     *
     * @param jsonObject 查询条件 <br/>
     *                   guid 患者guid </>
     * @return
     */
    @PostMapping(value = "/findMChatHistory")
    public ResultVO<List<MChat>> findMChatHistory(@RequestBody JSONObject jsonObject) {
        return new ResultVO<>(this.mChatService.findMChatHistory(jsonObject));
    }
}
