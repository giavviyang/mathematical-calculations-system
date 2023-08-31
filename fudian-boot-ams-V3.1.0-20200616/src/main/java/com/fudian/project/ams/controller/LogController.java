package com.fudian.project.ams.controller;

import com.fudian.api.system.pojo.*;
import com.fudian.api.system.service.*;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.common.pojo.CommonResult;
import com.fudian.common.utils.StringUtils;
import com.fudian.common.utils.ip.IpUtils;
import com.fudian.log.annotation.OperInfo;
import com.fudian.log.annotation.OperTitle;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * @author lutao
 * @create 2020/3/5
 * 操作日志管理
 */
@RestController
@RequestMapping("test")
@OperTitle("操作日志管理")
public class LogController {
    @Autowired
    private SysOperLogApi operLogService;
    @Autowired
    private SysLogininfoApi sysLogininfoService;
    @Autowired
    private SysBrowseApi sysBrowseLogService;

    @Autowired
    private SysDeptApi sysDeptService;

    @Autowired
    TokenApi tokenService;
    /**
     * 功能描述：获取当前条件下所有的操作记录
     * @author lutao
     * @param
     * @return :
     * @date    2020/3/5
    */
    @GetMapping("queryAllOperLog")
    @OperInfo(info = "获取当前条件下所有的操作记录", params = {"sysOperLog"})
    public CommonGridResult queryAllOperLog(SysOperLog sysOperLog){
        return operLogService.queryOperLog(sysOperLog);
    }


    /************************************************************登录日志************************************************************/
    @GetMapping("queryAllLoginLog")
    @OperInfo(info = "查询登录日志", params = {"sysLogininfo"})
    public CommonGridResult queryAllLoginLog(SysLogininfo sysLogininfo){

        List<SysLogininfo> logininfos=sysLogininfoService.queryAllLog(sysLogininfo);
        Page page= (Page) logininfos;
        CommonGridResult result=null;
        if (logininfos!=null&&logininfos.size()>0){
            result=new CommonGridResult(sysLogininfo.getPageNum(),sysLogininfo.getPageSize(),page.getTotal(),logininfos);
            return result;
        }else {
            result=new CommonGridResult(sysLogininfo.getPageNum(),sysLogininfo.getPageSize(),0,logininfos);
            return result;
        }
    }



    /************************************************************浏览日志************************************************************/

    @GetMapping("queryAllBrowseLog")
    @OperInfo(info = "查询浏览日志", params = {"sysBrowseLog"})
    public CommonGridResult queryAllBrowseLog(SysBrowseLog sysBrowseLog){
        CommonGridResult result=null;
        List<SysBrowseLog> logList=sysBrowseLogService.queryAllLog(sysBrowseLog);
        Page page= (Page) logList;
        if (logList!=null&&logList.size()>0){
            result=new CommonGridResult(sysBrowseLog.getPageNum(),sysBrowseLog.getPageSize(),page.getTotal(),logList);
            return result;
        }else {
            result=new CommonGridResult(sysBrowseLog.getPageNum(),sysBrowseLog.getPageSize(),0,logList);
            return result;
        }
    }

    @PostMapping("addLookLog")
    @OperInfo(info = "新增日志", params = {"a0101","e01z111a"})
    public CommonResult addLookLog(HttpServletRequest request, String a0101, String e01z111a){
        LoginUser loginUser = tokenService.getLoginUser(request);
        SysUser sysUser = loginUser == null?null:loginUser.getUser();
        SysBrowseLog sysBrowseLog = new SysBrowseLog();
        sysBrowseLog.setA0101(a0101);
        sysBrowseLog.setE01z111a(e01z111a);
        sysBrowseLog.setId(StringUtils.uuid());
        if(sysUser != null){
            sysBrowseLog.setUserId(sysUser.getUserId());
            sysBrowseLog.setUserName(sysUser.getUserName());
        }

        SysDept sysDept = sysDeptService.getById(sysUser.getDeptId());
        if(sysDept!=null){
            sysBrowseLog.setDeptId(sysDept.getId());
            sysBrowseLog.setDeptName(sysDept.getDeptName());
        }
        sysBrowseLog.setOperatorType(0);
        sysBrowseLog.setOperIp(IpUtils.getIpAddr(request));
        sysBrowseLog.setOperTime(new Date());
        sysBrowseLogService.save(sysBrowseLog);

        return CommonResult.success();
    }
}
