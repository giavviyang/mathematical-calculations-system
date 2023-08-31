package com.fudian.project.system.controller;

import com.fudian.api.system.pojo.SysOperLog;
import com.fudian.api.system.pojo.SysUser;
import com.fudian.log.annotation.OperInfo;
import com.fudian.log.annotation.OperTitle;
import com.fudian.project.system.service.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description 操作日志管理Controller
 * @ClassName SysOperLogController
 * @Author mr.hou
 * @Date 2019/12/25 2:19 下午
 * @Version 1.0.0
 **/
@RestController
@RequestMapping("sysOperLog")
@OperTitle("操作日志")
public class SysOperLogController {

    private static final Logger log = LoggerFactory.getLogger(SysOperLogController.class);

    @Autowired
    private com.fudian.project.system.service.SysOperLogService SysOperLogService;

    @Autowired
    private SysUserService userService;

    /**
     * 新增操作日志
     * @param log 操作日志
     * @return
     */
    @PostMapping("addLog")
    @OperInfo(info = "新增操作日志")
    public boolean addLog(SysOperLog log){
        SysUser sysUser = userService.queryUser("admin", "admin");
       SysOperLogService.insertOrUpdate(log);
        return true;
    }


}
