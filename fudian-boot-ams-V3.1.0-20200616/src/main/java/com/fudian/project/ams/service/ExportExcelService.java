package com.fudian.project.ams.service;

import com.alibaba.fastjson.JSONArray;

import javax.servlet.http.HttpServletResponse;

/**
 * @author ZYGisComputer
 */
public interface ExportExcelService {

//    void exportPersonInfo(String jsonData, HttpServletResponse response);

//    void exportPersonInfoParam(String data, HttpServletResponse response);

    void exportESFileSeeApply(JSONArray params, HttpServletResponse response);

    void exportFileSeeApply(JSONArray params, HttpServletResponse response);

    void exportFileLendApply(JSONArray params, HttpServletResponse response);

    void exportCacheESFileSeeCheck(JSONArray params, HttpServletResponse response);

    void exportESFileSeeCheck(JSONArray params, HttpServletResponse response);

    void exportFileSeeCheck(JSONArray params, HttpServletResponse response);

    void exportFileLendCheck(JSONArray params, HttpServletResponse response);

    void exportRules(JSONArray params, HttpServletResponse response);

    void exportTemplate(JSONArray params, HttpServletResponse response);

    void exportStorageConfig(JSONArray params, HttpServletResponse response);

    void exportFileReceiveLog(String params, HttpServletResponse response);

    void exportRoom(JSONArray params, HttpServletResponse response);

    void exportRoomCheck(JSONArray params, HttpServletResponse response);

    void exportScatteredMaterial(JSONArray params, HttpServletResponse response);

    void exportWholeFile(JSONArray params, HttpServletResponse response);

    void exportDept(JSONArray params, HttpServletResponse response);

    void exportFileDept(JSONArray params, HttpServletResponse response);

    void exportRole(JSONArray params, HttpServletResponse response);

    void exportUser(JSONArray params, HttpServletResponse response);

    void exportDict(JSONArray params, HttpServletResponse response);

    void exportBack(JSONArray params, HttpServletResponse response);

}
