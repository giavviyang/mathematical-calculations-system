package com.fudian.project.system.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fudian.common.pojo.CommonResult;
import com.fudian.common.utils.PasswordUtils;
import com.fudian.common.utils.StringUtils;
import com.fudian.project.system.pojo.LicenseKey;
import com.fudian.project.system.service.LicenseKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: WangYuZhuo
 * @Date: 2020/7/20 18:14
 * @Desc: 校验linense信息是否正确
 */
@RestController
@RequestMapping("license")
public class LicenseController {




    @Autowired
    LicenseKeyService licenseKeyService;


    /**
     * 验证系统内的license文件
     * @return
     */
    @GetMapping("licenseKeyCheck")
    public CommonResult licenseKeyCheck(){

//        licenseKeyService.flushRedis();

        List<LicenseKey> list = licenseKeyService.getLicenses();
        if(list.size() == 0 || list.size()>1){
            //多个license文件或者缺少license文件
            return CommonResult.success(201);
        }
        LicenseKey licenseKey = list.get(0);

        String license = licenseKey.getLicense();

        boolean b = PasswordUtils.checkLicense(license);


//        if(!b){
//            //license文件失效
//            return CommonResult.success(202);
//        }

        return CommonResult.success();
    }

    /**
     * 生成系统的licenseKey
     * @param data
     * @return
     */
    @PostMapping("generatorLicenseKey")
    public CommonResult generatorLicenseKey(@RequestBody String data){
        JSONObject jsonObject = JSON.parseObject(data);
        //将cpuid添加进json串中
        jsonObject.put(PasswordUtils.CPU_KEY,PasswordUtils.getCpuId());
        //将json字符串生成key
        String licenseKey = PasswordUtils.AESEncode(jsonObject.toJSONString(),PasswordUtils.LICENSE_KEY,PasswordUtils.IV);
        return CommonResult.success("",licenseKey);
    }

    /**
     * 注册license文件
     * @param data
     * @return
     */
    @PostMapping("regeditLicense")
    public CommonResult regeditLicense(@RequestBody String data){
        JSONObject jsonObject = JSON.parseObject(data);
        String license = jsonObject.getString("code");
        boolean b = PasswordUtils.checkLicense(license);
        if(b){
            //添加license进数据库
            LicenseKey licenseKey = new LicenseKey();
            licenseKey.setId(StringUtils.uuid());
            licenseKey.setLicense(license);
            //删除原有的license文件
            licenseKeyService.remove(Wrappers.emptyWrapper());
            b = licenseKeyService.save(licenseKey);
            if(b){
                //刷新license
                licenseKeyService.flushRedis();
                return CommonResult.success();
            }else{
                return CommonResult.error("操作失败");
            }
        }else{
            return CommonResult.error("license文件验证失败");
        }
    }

}
