package com.fudian.project.system.controller;


import com.fudian.api.system.pojo.GlobalOrg;
import com.fudian.common.enumns.ExceptionEnum;
import com.fudian.common.exception.FuDianException;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.common.pojo.CommonResult;
import com.fudian.common.utils.CpuUtil;
import com.fudian.project.system.service.GlobalOrgService;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;


/**
 * @author xyh
 * @title: GlobalOrgController
 * @projectName platform
 * @description: TODO
 * @date 2019/12/26 14:57
 */
@RestController
@RequestMapping("globalOrg")
public class GlobalOrgController {
    private static final Logger log = LoggerFactory.getLogger(GlobalOrgController.class);

    @Autowired
    private GlobalOrgService globalOrgService;


    // 公钥位置
    @Value("${license.pubKeyPath}")
    private String pubKeyPath;

    /**
     * 条件查询全宗列表信息
     * @param globalOrg
     * @return CommonGridResult
     */
    @GetMapping("queryGlobalOrgList")
    public CommonGridResult queryGlobalOrgList(GlobalOrg globalOrg){
        CommonGridResult commonGridResult=globalOrgService.queryGlobalOrgList(globalOrg);
        return  commonGridResult;
    }

    /**
     * 查询所有全宗列表信息
     * @return CommonGridResult
     */
    @GetMapping("queryAllGlobalOrgList")
    public CommonResult queryAllGlobalOrgList(){
        CommonResult commonResult=globalOrgService.queryAllGlobalOrgList();
        return  commonResult;
    }

//    /**
//     * 解析授权码
//     * @param license 授权码
//     * @return 全宗信息
//     * @throws Exception
//     */
//    @GetMapping("analysisCode")
//    public CommonResult<OrgInfo> analysisCode(
//            @ApiParam(required = true)
//                    String license
//    ) throws Exception {
//        OrgInfo orgInfo = this.getOrgInfoByLicense(license);
//        return CommonResult.success(orgInfo);
//    }


    /**
     * 向数据库中插入对象
     * @param globalOrg
     * @return
     */
    @PostMapping("insertGlobalOrg")
    public CommonResult<GlobalOrg> insertGlobalOrg(GlobalOrg  globalOrg){
        Boolean boo= globalOrgService.save(globalOrg);
        CommonResult<GlobalOrg> commonResult=null;
        if(boo){
             commonResult=new CommonResult<>(500,"新增失败",null);
        }else{
             commonResult=new CommonResult<>(200,"新增成功",null);
        }
        return  commonResult;
    }

    /**
     * 删除当前数据信息
     * @param globalOrgId
     * @return CommonResult<GlobalOrg>
     */
    @DeleteMapping("deleteGlobalOrgById")
    public CommonResult<GlobalOrg> deleteGlobalOrgById (String globalOrgId){
        CommonResult<GlobalOrg> commonResult=null;
        if(globalOrgId==null){
           new FuDianException(ExceptionEnum.ID_CANNOT_BE_NULL);
        }
       Boolean boo= globalOrgService.deleteGlobalOrgById(globalOrgId);
        if(boo){
            commonResult=new CommonResult<>(500,"当前数据有效无法删除",null);
        }else{
            commonResult=new CommonResult<>(200,"删除成功",null);
        }
        return  commonResult;
    }

    /**
     * 校验更改后的授权码是否
     * 为预期内容，
     * @return
     */
    @PostMapping("confirmPermissionsCode")
    public CommonResult<GlobalOrg> confirmPermissionsCode(GlobalOrg  globalOrg){
        CommonResult<GlobalOrg> commonResult=globalOrgService.confirmPermissionsCode(globalOrg);
        return  commonResult;
    }

    /**
     * 修改全宗信息
     * @param globalOrg  全宗对象
     * @return CommonResult<GlobalOrg>
     */
    @PutMapping("updateGlobalOrgById")
    public CommonResult<GlobalOrg> updateGlobalOrgById(GlobalOrg  globalOrg){
        CommonResult commonResult= globalOrgService.updateGlobalOrgById(globalOrg);
        return commonResult;
    }

    /**
     * 检查当前数据库是否可以联通
     * @param globalorg 全宗对象
     * @return CommonResult 公共返回参数
     */
    @PostMapping("dataBaseConfirm")
    public CommonResult dataBaseConfirm(GlobalOrg globalorg){
        boolean boo=globalOrgService.dataBaseConfirm(globalorg);
        CommonResult commonResult=new CommonResult();
        if (boo) {
            commonResult.setCode(200);
            commonResult.setMsg("链接成功");
        }else {
            commonResult.setCode(500);
            commonResult.setMsg("链接失败，请检查当前URL、用户名、密码以及端口");
        }
        return   commonResult;
    }

    /**
     * 验证当前文件存储
     * 是否畅通
     * @param globalorg  全宗对象
     * @return CommonResult
     */
    @PostMapping("fileBaseConfirm")
    public CommonResult fileBaseConfirm(GlobalOrg globalorg){
        boolean boo=globalOrgService.fileBaseConfirm(globalorg);
        CommonResult commonResult=new CommonResult();
        if (boo) {
            commonResult.setCode(200);
            commonResult.setMsg("链接成功");
        }else {
            commonResult.setCode(500);
            commonResult.setMsg("链接失败，请检查当前URL与用户名密码");
        }
        return   commonResult;
    }


//    private OrgInfo getOrgInfoByLicense(String license) throws Exception {
//        // 解析token
//        OrgInfo orgInfo = null;
//        try {
//            orgInfo = JwtUtils.getInfoFromToken(license, RsaUtils.getPublicKey(pubKeyPath));
//        } catch (Exception e) {
//            log.info("License验证失败");
//            throw new FdException("");
//        }
//
//        String licenseKey = CpuUtil.generateLicenseKey();
//
//        if (!licenseKey.equals(orgInfo.getLicenseKey())) {
//            log.info("服务器信息不匹配.");
//            throw new FdException("License验证失败");
//        }
//
//        //验证是否过期
//        long currentTimeMillis = System.currentTimeMillis();
//        if (currentTimeMillis > orgInfo.getEndTime()) {
//            log.info("当前系统已过期,请联系供应商续期.");
//            throw new FdException("License已经过期");
//        }
//        return orgInfo;
//    }
}
