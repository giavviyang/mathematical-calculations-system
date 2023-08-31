package com.fudian.project.system.controller;

import com.fudian.common.pojo.CommonResult;
import com.fudian.project.system.service.SysFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("sysFile")
public class SysFileController {

    @Autowired
    private SysFileService sysFileService;

    /**
     * 根据材料id查询
     * @param ids
     * @return
     */
    @GetMapping("queryE01z1Grid")
    public CommonResult queryByE01z1Ids(String ids){
        CommonResult commonResult =sysFileService.queryByE01z1Ids(ids);
        return commonResult;
    }

}
