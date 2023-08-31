package com.fudian.project.system.controller;

import com.alibaba.fastjson.JSONArray;
import com.fudian.api.system.pojo.LoginUser;
import com.fudian.api.system.pojo.SysUser;
import com.fudian.api.system.pojo.WatermarkConfig;
import com.fudian.common.constant.HttpStatus;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.common.pojo.CommonResult;
import com.fudian.common.utils.StringUtils;
import com.fudian.log.annotation.OperInfo;
import com.fudian.log.annotation.OperTitle;
import com.fudian.project.system.security.service.TokenService;
import com.fudian.project.system.service.WatermarkConfigService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("watermark")
@OperTitle("水印管理")
public class WatermarkConfigController {

    @Autowired
    private WatermarkConfigService watermarkConfigService;

    @Autowired
    private TokenService tokenService;

    /**
     * 查询所有
     * @return
     */
    @GetMapping("/selectAll")
    @OperInfo(info = "查询水印列表")
    public CommonGridResult selectAll(WatermarkConfig config) {
        CommonGridResult result;
        try {
            PageHelper.startPage(config.getPageNum(),config.getPageSize());
            Page<WatermarkConfig> page = (Page<WatermarkConfig>) watermarkConfigService.select(config);
            result = new CommonGridResult(page.getPageNum(),page.getPageSize(),page.getTotal(),page.getResult());
        } catch (Exception e) {
            e.printStackTrace();
            result = null;
        }
        return result;
    }

    /**
     * 修改
     * @return
     */
    @PostMapping("/updateById")
    @OperInfo(info = "修改水印")
    public CommonResult updateById(WatermarkConfig config, HttpServletRequest request, MultipartFile file) {
        CommonResult result;
        try {
            String type = config.getType();
            if (type == null || "".equals(type)) {
                return new CommonResult(HttpStatus.ERROR,"类型不能为空");
            }
            if ("1".equals(type)) { //图片
                config.setText(null);
                config.setColor(null);
                config.setFontSize(null);
                if ( file != null) {
                        config.setImage(file.getBytes());
                } else {
                    config.setImage(watermarkConfigService.getById(config.getId()).getImage());
                }
            } else { //文字
                config.setImage(null);
                config.setImgSize(null);
            }
            LoginUser loginUser = tokenService.getLoginUser(request);
            if(loginUser != null){
                SysUser sysUser = loginUser.getUser();
                config.setUpdateName(sysUser.getUserName());
            }
            config.setUpdateTime(new Date());
            boolean flag = watermarkConfigService.updateById(config);
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
    @OperInfo(info = "删除水印")
    public CommonResult delete(String ids) {
        CommonResult result;
        try {
            List<String> idList = JSONArray.parseArray(ids).toJavaList(String.class);
            boolean flag = watermarkConfigService.removeByIds(idList);
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
    @OperInfo(info = "新增水印")
    public CommonResult insert(WatermarkConfig config, HttpServletRequest request, MultipartFile file) {
        CommonResult result;
        try {
            String type = config.getType();
            if (type == null || "".equals(type)) {
                return new CommonResult(HttpStatus.ERROR,"类型不能为空");
            }
            if ("1".equals(type)) { //图片
                config.setText(null);
                config.setColor(null);
                config.setFontSize(null);
                if ( file != null) {
                    if (file.getBytes().length > 0) {
                        config.setImage(file.getBytes());
                    }
                }
            } else { //文字
                config.setImage(null);
                config.setImgSize(null);
            }
            LoginUser loginUser = tokenService.getLoginUser(request);
            if(loginUser != null){
                SysUser sysUser = loginUser.getUser();
                config.setCreateName(sysUser.getUserName());
            }
            config.setCreateTime(new Date());
            config.setId(StringUtils.uuid());
            boolean flag = watermarkConfigService.save(config);
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
    @OperInfo(info = "启用水印")
    public CommonResult active(String id) {
        CommonResult result;
        try {
            boolean flag = watermarkConfigService.active(id);
            if (flag) {
                result = new CommonResult(HttpStatus.SUCCESS,"启用成功");
            } else {
                result = new CommonResult(HttpStatus.ERROR,"启用失败");
            }
        }catch(RuntimeException e) {
            result = new CommonResult(HttpStatus.ACCEPTED,"连接不可用");
        } catch (Exception e) {
            e.printStackTrace();
            result = new CommonResult(HttpStatus.ERROR,"服务器繁忙");
        }
        return result;
    }

    /**
     * 停用
     */
    @PutMapping("/disable")
    @OperInfo(info = "停用水印")
    public CommonResult disable(String id) {
        CommonResult result;
        try {
            boolean flag = watermarkConfigService.disable(id);
            if (flag) {
                result = new CommonResult(HttpStatus.SUCCESS,"停用成功");
            } else {
                result = new CommonResult(HttpStatus.ERROR,"停用失败");
            }
        }catch(RuntimeException e) {
            result = new CommonResult(HttpStatus.ACCEPTED,"连接不可用");
        } catch (Exception e) {
            e.printStackTrace();
            result = new CommonResult(HttpStatus.ERROR,"服务器繁忙");
        }
        return result;
    }
}
