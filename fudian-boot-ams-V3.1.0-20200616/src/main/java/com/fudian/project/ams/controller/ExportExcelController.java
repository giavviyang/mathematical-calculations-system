package com.fudian.project.ams.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.fudian.common.pojo.CommonResult;
import com.fudian.common.utils.StringUtils;
import com.fudian.common.utils.data.DataStoreWrapper;
import com.fudian.common.utils.poi.ExcelUtils;
import com.fudian.log.annotation.OperInfo;
import com.fudian.log.annotation.OperTitle;
import com.fudian.project.ams.service.ExportExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description TODO
 * @ClassName ExportExcelController
 * @Author mr.zhang
 * @Date 2020/4/1 13:51
 * @Version 1.0.0
 **/
@OperTitle("Excel导出")
@RestController
@RequestMapping("export/excel/")
public class ExportExcelController {

    @Autowired
    DataStoreWrapper redisCache;

    @Autowired
    private ExportExcelService exportExcelServiceImpl;


    private JSONArray parseJSONString(String jsonString, String field) {
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        JSONArray jsonArray = jsonObject.getJSONArray(field);
        return jsonArray;
    }

    @OperInfo(info = "导出Excel")
    @PostMapping("exportClick")
    public void exportClick(@RequestBody String exportData, HttpServletResponse response) {
        System.out.println(exportData);
        JSONObject jsonObject = JSONObject.parseObject(exportData);
        Integer status = jsonObject.getInteger("status");
        JSONArray rows = jsonObject.getJSONArray("row");
        byte[] bytes ;
        if(status == 1){
            // 库房
            exportExcelServiceImpl.exportRoom(rows,response);
        } else if(status == 2){
            // 库房检查
            exportExcelServiceImpl.exportRoomCheck(rows,response);
        } else if(status == 3){
            // 零散材料
            exportExcelServiceImpl.exportScatteredMaterial(rows,response);
        } else if(status == 4){
            // 正本档案
            exportExcelServiceImpl.exportWholeFile(rows,response);
        } else if(status == 5){
            // 部门
            exportExcelServiceImpl.exportDept(rows,response);
        } else if(status == 6){
            // 档案部门
            exportExcelServiceImpl.exportFileDept(rows,response);
        } else if(status == 7){
            // 角色
            exportExcelServiceImpl.exportRole(rows,response);
        } else if(status == 8){
            // 用户
            exportExcelServiceImpl.exportUser(rows,response);
        } else if(status == 9){
            // 字典
            exportExcelServiceImpl.exportDict(rows,response);
        } else if(status == 10){
            // 回收
            exportExcelServiceImpl.exportBack(rows,response);
        } else if(status == 11){
            // 电子查阅申请
            exportExcelServiceImpl.exportESFileSeeApply(rows,response);
        } else if(status == 12){
            // 实体查阅申请
            exportExcelServiceImpl.exportFileSeeApply(rows,response);
        } else if(status == 13){
            // 实体借阅申请
            exportExcelServiceImpl.exportFileLendApply(rows,response);
        } else if(status == 14){
            // 临时电子审批
            exportExcelServiceImpl.exportCacheESFileSeeCheck(rows,response);
        } else if(status == 15){
            // 电子查阅审批
            exportExcelServiceImpl.exportESFileSeeCheck(rows,response);
        } else if(status == 16){
            // 实体查阅审批
            exportExcelServiceImpl.exportFileSeeCheck(rows,response);
        } else if(status == 17){
            // 实体借阅审批
            exportExcelServiceImpl.exportFileLendCheck(rows,response);
        } else if(status == 18){
            // 规章制度
            exportExcelServiceImpl.exportRules(rows,response);
        } else if(status == 19){
            // 常用模板
            exportExcelServiceImpl.exportTemplate(rows,response);
        } else if(status == 20){
            // 存储配置
            exportExcelServiceImpl.exportStorageConfig(rows,response);
        }
    }

    @OperInfo(info = "导出Excel")
    @PostMapping("exportExcelDynamicCol")
    public CommonResult exportExcelDynamicCol(@RequestBody String exportData, HttpServletRequest request, HttpServletResponse response){
        JSONObject params = JSONObject.parseObject(exportData,Feature.OrderedField);
        try {
            byte[] bytes = ExcelUtils.exportDynamicExcel(params.getString("colTitle"), params.getString("colWidth"), params.getString("dataList"),params.getString("tableTitle") , params.getString("sheetTitle"), response,params.getString("downLoadName"));
            String id = StringUtils.uuid();
            redisCache.setCacheObject(id,bytes);
            CommonResult commonResult = CommonResult.success("",id );
            commonResult.put("fileName",params.getString("downLoadName") + ".xls");
            return commonResult;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return CommonResult.error();
    }
}
