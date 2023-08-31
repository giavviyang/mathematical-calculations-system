package com.fudian.project.ams.service.impl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fudian.api.ams.pojo.A01;
import com.fudian.api.system.pojo.GlobalOrg;
import com.fudian.api.system.pojo.SysDept;
import com.fudian.api.system.pojo.SysDictionary;
import com.fudian.api.system.service.GlobalOrgApi;
import com.fudian.api.system.service.SysDeptApi;
import com.fudian.api.system.service.SysDictionaryApi;
import com.fudian.common.enumns.RedisEnum;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.common.pojo.CommonResult;
import com.fudian.common.utils.StringUtils;
import com.fudian.common.utils.data.DataStoreWrapper;
import com.fudian.common.vo.*;
import com.fudian.project.ams.pojo.WfRule;
import com.fudian.project.ams.pojo.WfTemplate;
import com.fudian.project.ams.service.ExportExcelService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description Excel导出服务实现类
 * @ClassName ExportExcelServiceImpl
 * @Author mr.zhang
 * @Date 2020/4/2 9:48
 * @Version 1.0.0
 **/
@Service
public class ExportExcelServiceImpl implements ExportExcelService {

    @Autowired
    DataStoreWrapper redisCache;
    

    @Autowired
    private GlobalOrgApi globalOrgServiceImpl;

    @Autowired
    private SysDictionaryApi sysDictionaryService;

    @Autowired
    private SysDeptApi sysDeptServiceImpl;





    @Override
    public void exportESFileSeeApply(JSONArray params, HttpServletResponse response) {
        exportESFileSee(params, response, "电子档案查阅申请");
    }

    @Override
    public void exportFileSeeApply(JSONArray params, HttpServletResponse response) {
        exportFileSee(params, response, "实体档案查阅申请");
    }

    @Override
    public void exportFileLendApply(JSONArray params, HttpServletResponse response) {
        exportFileLend(params, response, "实体档案借阅申请");
    }

    @Override
    public void exportCacheESFileSeeCheck(JSONArray params, HttpServletResponse response) {
        exportESFileSee(params, response, "临时档案查阅审批");
    }

    @Override
    public void exportESFileSeeCheck(JSONArray params, HttpServletResponse response) {
        exportESFileSee(params, response, "电子档案查阅审批");
    }

    @Override
    public void exportFileSeeCheck(JSONArray params, HttpServletResponse response) {
        exportFileSee(params, response, "实体档案查阅审批");
    }

    @Override
    public void exportFileLendCheck(JSONArray params, HttpServletResponse response) {
        exportFileLend(params, response, "实体档案借阅审批");
    }

    @Override
    public void exportRules(JSONArray params, HttpServletResponse response) {
        exportRules(params, response, "规章制度表");
    }

    @Override
    public void exportTemplate(JSONArray params, HttpServletResponse response) {
        exportTemplate(params, response, "常用模板表");
    }

    @Override
    public void exportStorageConfig(JSONArray params, HttpServletResponse response) {
        exportStorageConfig(params, response, "存储配置表");
    }

    @Override
    public void exportFileReceiveLog(String params, HttpServletResponse response) {
        System.out.println(params);
    }

    @Override
    public void exportRoom(JSONArray params, HttpServletResponse response) {
        exportRoom(params, response, "库房存储表");
    }

    @Override
    public void exportRoomCheck(JSONArray params, HttpServletResponse response) {
        exportRoomCheck(params, response, "库房安全检查表");
    }

    @Override
    public void exportScatteredMaterial(JSONArray params, HttpServletResponse response) {
        exportScatteredMaterial(params, response, "零散材料任务处理表");
    }

    @Override
    public void exportWholeFile(JSONArray params, HttpServletResponse response) {
        exportWholeFile(params, response, "整本档案任务处理表");
    }

    @Override
    public void exportDept(JSONArray params, HttpServletResponse response) {
        exportDept(params, response, "机构管理表");
    }

    @Override
    public void exportFileDept(JSONArray params, HttpServletResponse response) {
        exportFileDept(params, response, "档案机构管理表");
    }

    @Override
    public void exportRole(JSONArray params, HttpServletResponse response) {
        exportRole(params, response, "角色管理列表");
    }

    @Override
    public void exportUser(JSONArray params, HttpServletResponse response) {
        exportUser(params, response, "用户管理表");
    }

    @Override
    public void exportDict(JSONArray params, HttpServletResponse response) {
        exportDict(params, response, "字典管理表");
    }

    @Override
    public void exportBack(JSONArray params, HttpServletResponse response) {
        exportBack(params, response, "材料回收表");
    }

    private void exportCommon(JSONArray params, HttpServletResponse response,String title,Class clazz){
        List list = setValues(params, clazz);
        Workbook sheets = ExcelExportUtil.exportExcel(new ExportParams(title, title), clazz, list);
        if (sheets != null) {
            try {
                writeToWeb(sheets, response, title);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void exportBack(JSONArray params, HttpServletResponse response, String title) {
        exportCommon(params, response, title, BackExportExcelVo.class);
    }

    private void exportDict(JSONArray params, HttpServletResponse response, String title) {
        exportCommon(params, response, title, DictExportExcelVo.class);
    }

    private void exportUser(JSONArray params, HttpServletResponse response, String title) {
        exportCommon(params, response, title, UserExportExcelVo.class);
    }

    private void exportRole(JSONArray params, HttpServletResponse response, String title) {
        exportCommon(params, response, title, RoleExportExcelVo.class);
    }

    private void exportFileDept(JSONArray params, HttpServletResponse response, String title) {
        exportCommon(params, response, title, FileDeptExportExcelVo.class);
    }

    private void exportDept(JSONArray params, HttpServletResponse response, String title) {
        exportCommon(params, response, title, DeptExportExcelVo.class);
    }

    private void exportWholeFile(JSONArray params, HttpServletResponse response, String title) {
        exportCommon(params, response, title, WholeFileExportExcelVo.class);
    }

    private void exportScatteredMaterial(JSONArray params, HttpServletResponse response, String title) {
        exportCommon(params, response, title, ScatteredMaterialExportExcelVo.class);
    }

    private void exportRoomCheck(JSONArray params, HttpServletResponse response, String title) {
        exportCommon(params, response, title, RoomCheckExportExcelVo.class);
    }

    private void exportRoom(JSONArray params, HttpServletResponse response, String title) {
        exportCommon(params, response, title, RoomExportExcelVo.class);
    }

    private void exportFileReceiveLog(String params, HttpServletResponse response, String title) {
        // TODO 台账暂停
    }

    private void exportTemplate(JSONArray params, HttpServletResponse response, String title) {
        exportCommon(params, response, title, WfTemplateExportExcelVo.class);
    }

    private void exportStorageConfig(JSONArray params, HttpServletResponse response,String title) {
        exportCommon(params, response, title, StorageConfigExportExcelVo.class);
    }

    private void exportRules(JSONArray params, HttpServletResponse response, String title) {
        exportCommon(params, response, title, WfRuleExportExcelVo.class);
    }

    private void exportFileLend(JSONArray params, HttpServletResponse response, String title) {
        exportCommon(params, response, title, E01z9ExportExcelVo.class);
    }

    private void exportFileSee(JSONArray params, HttpServletResponse response, String title) {
        exportCommon(params, response, title, E01z8ExportExcelVo.class);
    }

    private void exportESFileSee(JSONArray params, HttpServletResponse response, String title) {
        exportCommon(params, response, title, E01zcExportExcelVo.class);
    }



    private void writeToWeb(Workbook sheets, HttpServletResponse response, String fileName) throws IOException {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        sheets.write(outputStream);
        byte[] bytes = outputStream.toByteArray();
        CommonResult commonResult = CommonResult.success();
        if(bytes == null){
            commonResult = CommonResult.error();
        }else{
            String id = StringUtils.uuid();
            redisCache.setCacheObject(id,bytes, RedisEnum.SHORT);
            commonResult.setData(id);
            commonResult.put("fileName",fileName + ".xls");
        }
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(commonResult);
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/html;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.print(jsonObject.toJSONString());
        writer.flush();
        writer.close();



    }

    private List setValues(List data, Class clazz) {
        if (clazz.isAssignableFrom(A01ExportExcelVo.class)) {
            List<A01ExportExcelVo> a01ExportExcelVos = new ArrayList<>();
            setExportPersonInfo(data, a01ExportExcelVos);
            return a01ExportExcelVos;
        } else if (clazz.isAssignableFrom(E01zcExportExcelVo.class)) {
            List<E01zcExportExcelVo> e01zcExportExcelVos = new ArrayList<>();
            setExportESFileValues(data, e01zcExportExcelVos);
            return e01zcExportExcelVos;
        } else if (clazz.isAssignableFrom(E01z8ExportExcelVo.class)) {
            List<E01z8ExportExcelVo> e01z8ExportExcelVos = new ArrayList<>();
            setExportFileValues(data, e01z8ExportExcelVos);
            return e01z8ExportExcelVos;
        } else if (clazz.isAssignableFrom(E01z9ExportExcelVo.class)) {
            List<E01z9ExportExcelVo> e01z9ExportExcelVos = new ArrayList<>();
            setExportFileLendValues(data, e01z9ExportExcelVos);
            return e01z9ExportExcelVos;
        } else if (clazz.isAssignableFrom(WfRuleExportExcelVo.class)) {
            List<WfRule> wfRules = new ArrayList<>();
            setExportWfRuleValues(data, wfRules);
            return wfRules;
        } else if (clazz.isAssignableFrom(WfTemplateExportExcelVo.class)) {
            List<WfTemplate> wfTemplates = new ArrayList<>();
            setExportWfTemplateValues(data, wfTemplates);
            return wfTemplates;
        } else if (clazz.isAssignableFrom(RoomExportExcelVo.class)) {
            List<RoomExportExcelVo> roomExportExcelVos = new ArrayList<>();
            setExportRoomValues(data, roomExportExcelVos);
            return roomExportExcelVos;
        } else if (clazz.isAssignableFrom(RoomCheckExportExcelVo.class)) {
            List<RoomCheckExportExcelVo> roomCheckExportExcelVos = new ArrayList<>();
            setExportRoomCheckValues(data, roomCheckExportExcelVos);
            return roomCheckExportExcelVos;
        } else if (clazz.isAssignableFrom(ScatteredMaterialExportExcelVo.class)) {
            List<ScatteredMaterialExportExcelVo> scatteredMaterialExportExcelVos = new ArrayList<>();
            setExportScatteredMaterialValues(data, scatteredMaterialExportExcelVos);
            return scatteredMaterialExportExcelVos;
        } else if (clazz.isAssignableFrom(WholeFileExportExcelVo.class)) {
            List<WholeFileExportExcelVo> wholeFileExportExcelVos = new ArrayList<>();
            setExportWholeFileValues(data, wholeFileExportExcelVos);
            return wholeFileExportExcelVos;
        } else if (clazz.isAssignableFrom(DeptExportExcelVo.class)) {
            List<DeptExportExcelVo> deptExportExcelVos = new ArrayList<>();
            setExportDeptValues(data, deptExportExcelVos);
            return deptExportExcelVos;
        } else if (clazz.isAssignableFrom(FileDeptExportExcelVo.class)) {
            List<FileDeptExportExcelVo> fileDeptExportExcelVos = new ArrayList<>();
            setExportFileDeptValues(data, fileDeptExportExcelVos);
            return fileDeptExportExcelVos;
        } else if (clazz.isAssignableFrom(RoleExportExcelVo.class)) {
            List<RoleExportExcelVo> roleExportExcelVos = new ArrayList<>();
            setExportRoleValues(data, roleExportExcelVos);
            return roleExportExcelVos;
        } else if (clazz.isAssignableFrom(UserExportExcelVo.class)) {
            List<UserExportExcelVo> userExportExcelVos = new ArrayList<>();
            setExportUserValues(data, userExportExcelVos);
            return userExportExcelVos;
        } else if (clazz.isAssignableFrom(DictExportExcelVo.class)) {
            List<DictExportExcelVo> dictExportExcelVos = new ArrayList<>();
            setExportDictValues(data, dictExportExcelVos);
            return dictExportExcelVos;
        } else if (clazz.isAssignableFrom(BackExportExcelVo.class)) {
            List<BackExportExcelVo> backExportExcelVos = new ArrayList<>();
            setExportBackValues(data, backExportExcelVos);
            return backExportExcelVos;
        } else if (clazz.isAssignableFrom(StorageConfigExportExcelVo.class)) {
            List<BackExportExcelVo> backExportExcelVos = new ArrayList<>();
            setExportStorageConfigValues(data, backExportExcelVos);
            return backExportExcelVos;
        }
        return null;
    }

    public SysDictionary queryDict(String type, String code) {
        LambdaQueryWrapper<SysDictionary> sysDictionaryLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sysDictionaryLambdaQueryWrapper.eq(SysDictionary::getDictType, type);
        sysDictionaryLambdaQueryWrapper.eq(SysDictionary::getDictCode, code);
        SysDictionary sysDictionary = sysDictionaryService.getOne(sysDictionaryLambdaQueryWrapper);
        if (sysDictionary == null) {
            sysDictionary = new SysDictionary();
            sysDictionary.setFullName("");
        }
        return sysDictionary;
    }

    private String dateHandler(String date) {
        if (date != null && !"".equals(date)) {
            try {
                return new SimpleDateFormat("yy-MM-dd HH:mm:ss").format(new Date(date));
            } catch (Exception e) {
                try {
                    return new SimpleDateFormat("yy-MM-dd HH:mm:ss").format(new Date(Long.parseLong(date)));
                } catch (Exception e1) {
                    return date.substring(0, 10);
                }
            }
        }
        return "";
    }

    private void setCommon(List data,List vos,Class clazz){
        if (data != null) {
            int i = 1;
            for (Object o : data) {
                JSONObject map = (JSONObject) o;
                map.put("xh", i);
                i++;
                String string = map.toJSONString();
                vos.add(JSONObject.parseObject(string, clazz));
            }
        }
    }

    private void setExportPersonInfo(List data, List a01ExportExcelVos) {
        if (data != null) {
            int i=1;
            for (Object o : data) {
                A01 a01 = (A01) o;
                A01ExportExcelVo a01ExportExcelVo = new A01ExportExcelVo();
//                a01ExportExcelVo.setId(a01.getId() == null ? "" : a01.getId());
                a01ExportExcelVo.setXh(i);
                i++;
                // 处理机构
                if (a01.getOrgId() != null) {
                    LambdaQueryWrapper<GlobalOrg> globalOrgLambdaQueryWrapper = new LambdaQueryWrapper<>();
                    globalOrgLambdaQueryWrapper.eq(GlobalOrg::getGlobalOrgId, a01.getOrgId());
                    GlobalOrg globalOrg = globalOrgServiceImpl.getOne(globalOrgLambdaQueryWrapper);
                    //a01ExportExcelVo.setOrgId(globalOrg.getGlobalOrgName());
                } else {
                    //a01ExportExcelVo.setOrgId("");
                }

//                a01ExportExcelVo.setShowIndex(a01.getShowIndex() == null ? "" : String.valueOf(a01.getShowIndex()));
                a01ExportExcelVo.setA0101(a01.getA0101() == null ? "" : a01.getA0101());
                //a01ExportExcelVo.setA0102(a01.getA0102() == null ? "" : a01.getA0102());
                // 处理性别
                if (a01.getA0104() != null) {
                    SysDictionary a0104 = queryDict("GB22611", a01.getA0104());
                    //a01ExportExcelVo.setA0104(a0104.getFullName());
                } else {
                    //a01ExportExcelVo.setA0104("");
                }
                a01ExportExcelVo.setA0107(a01.getA0107() == null ? "" : a01.getA0107());
                //a01ExportExcelVo.setA0111a(a01.getA0111a() == null ? "" : a01.getA0111a());
                // 处理籍贯代码
                if (a01.getA0111b() != null) {
                    SysDictionary a0111b = queryDict("ZB01", a01.getA0111b());
                    //a01ExportExcelVo.setA0111b(a0111b.getFullName());
                } else {
                    //a01ExportExcelVo.setA0111b("");
                }
                //a01ExportExcelVo.setA0111b(a01.getA0111b() == null ? "" : a01.getA0111b());
                //a01ExportExcelVo.setA0114a(a01.getA0114a() == null ? "" : a01.getA0114a());
                // 处理出生地代码
                if (a01.getA0114b() != null) {
                    SysDictionary a0114b = queryDict("ZB01", a01.getA0114b());
                    //a01ExportExcelVo.setA0114b(a0114b.getFullName());
                } else {
                    //a01ExportExcelVo.setA0114b("");
                }
                //a01ExportExcelVo.setA0115a(a01.getA0115a() == null ? "" : a01.getA0115a());
                // 处理成长地代码
                if (a01.getA0115b() != null) {
                    SysDictionary a0115b = queryDict("ZB01", a01.getA0115b());
                    //a01ExportExcelVo.setA0115b(a0115b.getFullName());
                } else {
                    //a01ExportExcelVo.setA0115b("");
                }
                // 处理名族
                if (a01.getA0117() != null) {
                    SysDictionary a0117 = queryDict("GB3304", a01.getA0117());
                    //a01ExportExcelVo.setA0117(a0117.getFullName());
                } else {
                    //a01ExportExcelVo.setA0117("");
                }
                // 处理健康状况
                if (a01.getA0127() != null) {
                    SysDictionary a0127 = queryDict("GB22613", a01.getA0127());
                    //a01ExportExcelVo.setA0127(a0127.getFullName());
                } else {
                    //a01ExportExcelVo.setA0127("");
                }

                //a01ExportExcelVo.setA0128(a01.getA0128() == null ? "" : a01.getA0128());
                // 处理婚姻状况
                if (a01.getA0131() != null) {
                    SysDictionary a0131 = queryDict("GB22612", a01.getA0131());
                    //a01ExportExcelVo.setA0131(a0131.getFullName());
                } else {
                    //a01ExportExcelVo.setA0131("");
                }
                a01ExportExcelVo.setA0134(a01.getA0134() == null ? "" : a01.getA0134());
                //a01ExportExcelVo.setA0137(a01.getA0137() == null ? "" : a01.getA0137());
                // 处理政治面貌
                if (a01.getA0141() != null) {
                    SysDictionary a0141 = queryDict("GB4762", a01.getA0141());
                    //a01ExportExcelVo.setA0141(a0141.getFullName());
                } else {
                    //a01ExportExcelVo.setA0141("");
                }
                a01ExportExcelVo.setA0144(a01.getA0144() == null ? "" : a01.getA0144());
                // 处理个人身份
                if (a01.getA0151() != null) {
                    SysDictionary a0151 = queryDict("GB22614", a01.getA0151());
                    a01ExportExcelVo.setA0151(a0151.getFullName());
                } else {
                    a01ExportExcelVo.setA0151("");
                }
                //a01ExportExcelVo.setA0154(a01.getA0154() == null ? "" : a01.getA0154());
                //a01ExportExcelVo.setA0157a(a01.getA0157a() == null ? "" : a01.getA0157a());
                // 处理人事关系所在单位代码
                // TODO 代码表存在问题 暂时注释
                //a01ExportExcelVo.setA0157b(a01.getA0157b() == null ? "" : a01.getA0157b());
//                    if (a01.getA0157b() != null) {
//                        SysDictionary a0157b = queryDict("ZB02", a01.getA0157b());
//                        a01ExportExcelVo.setA0157b(a0157b.getFullName());
//                    } else {
//                        a01ExportExcelVo.setA0157b("");
//                    }
                // 所在政区
                if (a01.getA0161() != null) {
                    SysDictionary a0161 = queryDict("ZB01", a01.getA0161());
                    //a01ExportExcelVo.setA0161(a0161.getFullName());
                } else {
                    //a01ExportExcelVo.setA0161("");
                }
                // 隶 属关系
                if (a01.getA0164() != null) {
                    SysDictionary a0164 = queryDict("ZB87", a01.getA0164());
                    //a01ExportExcelVo.setA0164(a0164.getFullName());
                } else {
                    //a01ExportExcelVo.setA0164("");
                }
                // 单位级别
                if (a01.getA0167() != null) {
                    SysDictionary a0167 = queryDict("ZB03", a01.getA0167());
                    //a01ExportExcelVo.setA0167(a0167.getFullName());
                } else {
                    //a01ExportExcelVo.setA0167("");
                }
                // 性质
                if (a01.getA0171() != null) {
                    SysDictionary a0171 = queryDict("ZB04", a01.getA0171());
                    //a01ExportExcelVo.setA0171(a0171.getFullName());
                } else {
                    //a01ExportExcelVo.setA0171("");
                }
                // 行业
                if (a01.getA0174() != null) {
                    SysDictionary a0171 = queryDict("GBT4754", a01.getA0174());
                    //a01ExportExcelVo.setA0174(a0171.getFullName());
                } else {
                    //a01ExportExcelVo.setA0174("");
                }
                // 户口性质
                if (a01.getA0177() != null) {
                    SysDictionary a0177 = queryDict("ZB07", a01.getA0177());
                    //a01ExportExcelVo.setA0177(a0177.getFullName());
                } else {
                    //a01ExportExcelVo.setA0177("");
                }
                // 户籍所在地
                if (a01.getA0181a() != null) {
                    SysDictionary a0181a = queryDict("ZB01", a01.getA0181a());
                    //a01ExportExcelVo.setA0181a(a0181a.getFullName());
                } else {
                    //a01ExportExcelVo.setA0181a("");
                }
                //a01ExportExcelVo.setA0181b(a01.getA0181b() == null ? "" : a01.getA0181b());
                // 有效证件类型
                if (a01.getA0183() != null) {
                    SysDictionary a0183 = queryDict("ZB76", a01.getA0183());
                    //a01ExportExcelVo.setA0183(a0183.getFullName());
                } else {
                    //a01ExportExcelVo.setA0183("");
                }
                //a01ExportExcelVo.setA0184(a01.getA0184() == null ? "" : a01.getA0184());
                //a01ExportExcelVo.setA0187a(a01.getA0187a() == null ? "" : a01.getA0187a());
                //a01ExportExcelVo.setA0187b(a01.getA0187b() == null ? "" : a01.getA0187b());
                // 声音信息标识
                if (a01.getA0191() != null) {
                    SysDictionary a0191 = queryDict("AMS07", a01.getA0191());
                    //a01ExportExcelVo.setA0191(a0191.getFullName());
                } else {
                    //a01ExportExcelVo.setA0191("");
                }
                // 信息标志
                if (a01.getFlag() != null) {
                    SysDictionary flag = queryDict("AMS08", a01.getFlag());
                    //a01ExportExcelVo.setFlag(flag.getFullName());
                } else {
                    //a01ExportExcelVo.setFlag("");
                }
                // 部门
                if (a01.getDeptId() != null && !a01.getDeptId().isEmpty()) {
//                    LambdaQueryWrapper<SysDept> sysDeptLambdaQueryWrapper = new LambdaQueryWrapper<>();
//                    sysDeptLambdaQueryWrapper.eq(SysDept::getId, a01.getDeptId());
                    SysDept sysDept = sysDeptServiceImpl.getById(a01.getDeptId());
                    if (sysDept != null) {
                        a01ExportExcelVo.setDeptId(sysDept.getDeptName());
                    } else {
                        a01ExportExcelVo.setDeptId("");
                    }
                } else {
                    a01ExportExcelVo.setDeptId("");
                }
                if (a01.getDuty() != null) {
                    SysDictionary duty = queryDict("ZB09", a01.getDuty());
                    a01ExportExcelVo.setDuty(duty.getFullName());
                } else {
                    a01ExportExcelVo.setDuty("");
                }
                //a01ExportExcelVo.setDuty(a01.getDuty() == null ? "" : a01.getDuty());
                //a01ExportExcelVo.setGrade(a01.getGrade() == null ? "" : a01.getGrade());
                // 状态  FDA01State
                if (a01.getState() != null) {
                    SysDictionary status = queryDict("FDA01State", a01.getState());
                    a01ExportExcelVo.setState(status.getFullName());
                } else {
                    a01ExportExcelVo.setState("");
                }
                // 学历 ZB64
                if (a01.getEducation() != null) {
                    SysDictionary education = queryDict("ZB64", a01.getEducation());
                    a01ExportExcelVo.setEducation(education.getFullName());
                } else {
                    a01ExportExcelVo.setEducation("");
                }
                //a01ExportExcelVo.setNumber(a01.getNumber() == null ? "" : a01.getNumber());
                //a01ExportExcelVo.setData4(a01.getData4() == null ? "" : a01.getData4());
                a01ExportExcelVos.add(a01ExportExcelVo);
            }
        }
    }

    private void setExportESFileValues(List data, List e01zcExportExcelVos) {
        setCommon(data,e01zcExportExcelVos,E01zcExportExcelVo.class);
    }

    private void setExportFileValues(List data, List e01z8ExportExcelVos) {
        if (data != null) {
            int i = 1;
            for (Object o : data) {
                JSONObject map = (JSONObject) o;
                String string = map.toJSONString();
                // 单独处理日期
                E01z8ExportExcelVo e01z8ExportExcelVo = JSONObject.parseObject(string, E01z8ExportExcelVo.class);
                e01z8ExportExcelVo.setXh(i);
                i++;
                e01z8ExportExcelVo.setCreatersTime(dateHandler(e01z8ExportExcelVo.getCreatersTime()));
                e01z8ExportExcelVo.setStartTime(dateHandler(e01z8ExportExcelVo.getStartTime()));
                e01z8ExportExcelVo.setEndTime(dateHandler(e01z8ExportExcelVo.getEndTime()));
                e01z8ExportExcelVo.setApprovTime(dateHandler(e01z8ExportExcelVo.getApprovTime()));
                e01z8ExportExcelVos.add(e01z8ExportExcelVo);
            }
        }
    }

    private void setExportFileLendValues(List data, List e01z9ExportExcelVos) {
        if (data != null) {
            int i = 1;
            for (Object o : data) {
                JSONObject map = (JSONObject) o;
                String string = map.toJSONString();
                // 单独处理日期
                E01z9ExportExcelVo e01z9ExportExcelVo = JSONObject.parseObject(string, E01z9ExportExcelVo.class);
                e01z9ExportExcelVo.setXh(i);
                i++;
                e01z9ExportExcelVo.setCreatersTime(dateHandler(e01z9ExportExcelVo.getCreatersTime()));
                e01z9ExportExcelVo.setStartTime(dateHandler(e01z9ExportExcelVo.getStartTime()));
                e01z9ExportExcelVo.setEndTime(dateHandler(e01z9ExportExcelVo.getEndTime()));
                e01z9ExportExcelVo.setApprovTime(dateHandler(e01z9ExportExcelVo.getApprovTime()));
                e01z9ExportExcelVos.add(e01z9ExportExcelVo);
            }
        }
    }

    private void setExportWfRuleValues(List data, List wfRulesExportVos) {
        setCommon(data,wfRulesExportVos,WfRuleExportExcelVo.class);
    }

    private void setExportWfTemplateValues(List data, List wfTemplateExportVos) {
        setCommon(data,wfTemplateExportVos,WfTemplateExportExcelVo.class);
    }

    private void setExportRoomValues(List data, List roomExportExcelVos) {
        setCommon(data,roomExportExcelVos,RoomExportExcelVo.class);
    }

    private void setExportRoomCheckValues(List data, List roomCheckExportExcelVos) {
        setCommon(data,roomCheckExportExcelVos,RoomCheckExportExcelVo.class);
    }

    private void setExportScatteredMaterialValues(List data, List scatteredMaterialExportExcelVos) {
        setCommon(data,scatteredMaterialExportExcelVos,ScatteredMaterialExportExcelVo.class);
    }

    private void setExportWholeFileValues(List data, List wholeFileExportExcelVos) {
        if (data != null) {
            int i =1;
            for (Object o : data) {
                JSONObject map = (JSONObject) o;
                String string = map.toJSONString();
                WholeFileExportExcelVo wholeFileExportExcelVo = JSONObject.parseObject(string, WholeFileExportExcelVo.class);
                wholeFileExportExcelVo.setXh(i);
                i++;
                // 处理性别
                if (wholeFileExportExcelVo.getA0104() != null) {
                    SysDictionary a0104 = queryDict("GB22611", wholeFileExportExcelVo.getA0104());
                    if (a0104 != null) {
                        wholeFileExportExcelVo.setA0104(a0104.getFullName());
                    }
                }
                wholeFileExportExcelVos.add(wholeFileExportExcelVo);
            }
        }
    }

    private void setExportDeptValues(List data, List deptExportExcelVos) {
        setCommon(data,deptExportExcelVos,DeptExportExcelVo.class);
    }

    private void setExportFileDeptValues(List data, List fileDeptExportExcelVos) {
        setCommon(data,fileDeptExportExcelVos,FileDeptExportExcelVo.class);
    }

    private void setExportRoleValues(List data, List roleExportExcelVos) {
        setCommon(data,roleExportExcelVos,RoleExportExcelVo.class);
    }

    private void setExportUserValues(List data, List userExportExcelVos) {
        setCommon(data,userExportExcelVos,UserExportExcelVo.class);
    }

    private void setExportDictValues(List data, List dictExportExcelVos) {
        setCommon(data,dictExportExcelVos,DictExportExcelVo.class);
    }

    private void setExportBackValues(List data, List backExportExcelVos) {
        setCommon(data,backExportExcelVos,BackExportExcelVo.class);
    }

    private void setExportStorageConfigValues(List data, List storageConfigExportExcelVos) {
        setCommon(data,storageConfigExportExcelVos, StorageConfigExportExcelVo.class);
    }
}
