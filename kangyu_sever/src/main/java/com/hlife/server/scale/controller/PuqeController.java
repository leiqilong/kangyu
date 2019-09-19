package com.hlife.server.scale.controller;

import com.alibaba.fastjson.JSONObject;
import com.hlife.framework.base.PageResult;
import com.hlife.framework.base.ResultVO;
import com.hlife.server.scale.model.Puqe;
import com.hlife.server.scale.model.SnapIv;
import com.hlife.server.scale.service.PuqeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 妊娠期恶心呕吐量表结果控制器
 */
@RestController
@RequestMapping(value = "/puqe")
public class PuqeController {

    @Autowired
    private PuqeService puqeService;

    /**
     * 保存 puge 测量结果
     * @param puge 妊娠期恶心呕吐量表结果
     * @return 妊娠期恶心呕吐量表结果
     */
    @PostMapping(value = "/savePuqe")
    public ResultVO<Puqe> savePuqe(@RequestBody Puqe puge) {
        return new ResultVO<>(this.puqeService.savePuqe(puge));
    }

    /**
     * 分页查询
     *
     * @param jsonObject pagesize 每页大小
     *                   pagenum 页数
     *
     * @return resultVO.restultData 当前页数据
     */
    @PostMapping(value = "/findPuqePagination")
    public ResultVO<PageResult<Puqe>> findPuqePagination(@RequestBody JSONObject jsonObject) {
        return new ResultVO<>(this.puqeService.findPuqePagination(jsonObject));
    }
}
