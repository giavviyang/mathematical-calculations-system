package com.fudian.filestorage.controller;

import com.alibaba.fastjson.JSONArray;
import com.fudian.api.system.pojo.LoginUser;
import com.fudian.api.system.pojo.SysUser;
import com.fudian.api.system.service.TokenApi;
import com.fudian.common.constant.HttpStatus;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.common.pojo.CommonResult;
import com.fudian.common.utils.StringUtils;
import com.fudian.filestorage.entity.StorageConfig;
import com.fudian.filestorage.service.StorageConfigService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * 文件上传容器配置
 */
@RestController
@RequestMapping("/storageConfig")
public class StorageConfigController {

    @Autowired
    private StorageConfigService storageConfigService;

    @Autowired
    private TokenApi tokenService;

    /**
     * 查询所有
     * @return
     */
    @GetMapping("/selectAll")
    public CommonGridResult selectAll(StorageConfig storageConfig) {
        CommonGridResult result;
        try {
            PageHelper.startPage(storageConfig.getPageNum(),storageConfig.getPageSize());
            Page<StorageConfig> page = (Page<StorageConfig>) storageConfigService.select(storageConfig);

            result = new CommonGridResult(page.getPageNum(),page.getPageSize(),page.getTotal(),page.getResult());
        } catch (Exception e) {
            e.printStackTrace();
            result = null;
        }
        return result;
    }

    /**
     * 根据条件查询
     * @return
     */
    @GetMapping("/select")
    public CommonResult select(StorageConfig storageConfig) {
        CommonResult result;
        try {
            List<StorageConfig> storageConfigList = storageConfigService.select(storageConfig);
            result = new CommonResult(HttpStatus.SUCCESS,"查询成功",storageConfigList);
        } catch (Exception e) {
            e.printStackTrace();
            result = new CommonResult(HttpStatus.ERROR,"服务器繁忙");
        }
        return result;
    }

    /**
     * 修改
     * @return
     */
    @PutMapping("/updateById")
    public CommonResult updateById(StorageConfig storageConfig, HttpServletRequest request) {
        CommonResult result;
        try {
            LoginUser loginUser = tokenService.getLoginUser(request);
            if(loginUser != null){
                SysUser sysUser = loginUser.getUser();
                storageConfig.setUpdateName(sysUser.getUserName());
            }
            storageConfig.setUpdateTime(new Date());
            boolean flag = storageConfigService.updateById(storageConfig);
            if (flag) {
                result = new CommonResult(HttpStatus.SUCCESS,"修改成功");
            } else {
                result = new CommonResult(HttpStatus.ERROR,"修改失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = new CommonResult(HttpStatus.ERROR,"服务器繁忙");
        }
        return result;
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public CommonResult delete(String ids) {
        CommonResult result;
        try {
            List<String> idList = JSONArray.parseArray(ids).toJavaList(String.class);
            boolean flag = storageConfigService.removeByIds(idList);
            if (flag) {
                result = new CommonResult(HttpStatus.SUCCESS,"删除成功");
            } else {
                result = new CommonResult(HttpStatus.ERROR,"删除失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = new CommonResult(HttpStatus.ERROR,"服务器繁忙");
        }
        return result;
    }

    /**
     * 新增
     */
    @PostMapping("/insert")
    public CommonResult insert(StorageConfig storageConfig, HttpServletRequest request) {
        CommonResult result;
        try {
            LoginUser loginUser = tokenService.getLoginUser(request);
            if(loginUser != null){
                SysUser sysUser = loginUser.getUser();
                storageConfig.setCreateName(sysUser.getUserName());
            }
            //取出本地路径最后的斜杠
            if ("3".equals(storageConfig.getType())) {
                String basePath = storageConfig.getBasePath();
                if (StringUtils.isNotBlank(basePath)) {
                    if ("/".endsWith(basePath) || "\\".endsWith(basePath)) {
                        storageConfig.setBasePath(basePath.substring(0,basePath.length()-1));
                    }
                }
            }
            storageConfig.setCreateTime(new Date());
            storageConfig.setId(StringUtils.uuid());
            boolean flag = storageConfigService.save(storageConfig);
            if (flag) {
                result = new CommonResult(HttpStatus.SUCCESS,"新增成功");
            } else {
                result = new CommonResult(HttpStatus.ERROR,"新增失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = new CommonResult(HttpStatus.ERROR,"服务器繁忙");
        }
        return result;
    }

    /**
     * 启用
     */
    @PutMapping("/active")
    public CommonResult active(String id) {
        CommonResult result;
        try {
            storageConfigService.active(id);
            result = new CommonResult(HttpStatus.SUCCESS,"启用成功");
        }catch(RuntimeException e) {
            result = new CommonResult(HttpStatus.ERROR,e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            result = new CommonResult(HttpStatus.ERROR,"服务器繁忙");
        }
        return result;
    }

    /**
     * 测试连接
     */
    @PostMapping("/test")
    public CommonResult test(StorageConfig storageConfig) {
        CommonResult result;
        try {
            boolean flag = false;
            if ("3".equals(storageConfig.getType())) {
                File file = new File(storageConfig.getBasePath());
                if (file.isDirectory() && file.exists()) {
                    flag = true;
                }
            } else {
                flag = storageConfigService.test(storageConfig);
            }
            if (flag) {
                result = new CommonResult(HttpStatus.SUCCESS,"连接成功");
            } else {
                result = new CommonResult(HttpStatus.ERROR,"连接失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = new CommonResult(HttpStatus.ERROR,"连接失败");
        }
        return result;
    }

}
