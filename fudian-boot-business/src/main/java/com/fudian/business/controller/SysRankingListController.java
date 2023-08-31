package com.fudian.business.controller;

import com.alibaba.fastjson.JSONArray;
import com.fudian.api.system.pojo.Students;
import com.fudian.api.system.pojo.SysUser;
import com.fudian.api.system.service.SysDeptApi;
import com.fudian.api.system.service.TokenApi;
import com.fudian.business.pojo.NewsNotice;
import com.fudian.business.service.NewsNoticeService;
import com.fudian.business.service.StudentAnswerService;
import com.fudian.business.service.StudentsService;
import com.fudian.business.vo.SysRankingList;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.common.pojo.CommonResult;
import com.fudian.common.utils.ServletUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

/**
 * 训练习题
 *
 * @author xuchenghong
 */
@RestController
@RequestMapping(value = "sysRanking")
public class SysRankingListController {

    @Autowired
    private StudentAnswerService studentAnswerService;
    @Autowired
    private TokenApi tokenService;
    @Autowired
    private SysDeptApi sysDeptService;
    @Autowired
    private StudentsService studentsService;


    /**
     * 勤奋榜
     *
     * @param
     * @return
     */
    @PostMapping("/diligenceRanking")
    public CommonGridResult diligenceRanking(SysRankingList sysRankingList) {

        //查询同年级学生排名情况
        CommonGridResult commonGridResult = studentAnswerService.sysDiligenceRanking(sysRankingList);

        return commonGridResult;
    }
    /**
     * 进步榜
     *
     * @param
     * @return
     */
    @PostMapping("/progressRanking")
    public CommonGridResult progressRanking(SysRankingList sysRankingList) {

        //查询同年级学生排名情况
        CommonGridResult commonGridResult = studentAnswerService.sysProgressRanking(sysRankingList);

        return commonGridResult;
    }


    /**
     * 进步榜
     *
     * @param
     * @return
     */
    /*@PostMapping("/progressRanking")
    public CommonResult<?> progressList(String deptId) {
        try {

            ArrayList<String> ids = new ArrayList<>();
            ids.add(deptId);
            //查询同年级学生排名情况
            List<Map<String, Object>> list = studentAnswerService.progressRanking(ids);
            if (list.size() == 0) {
                return CommonResult.success();
            }
            JSONArray theirRanking = new JSONArray();
            for (Map<String, Object> map : list) {
                SysRankingList jsonObject = setUpRanking(map);
                jsonObject.setProgressRate((double) map.get("progressRate"));
                //本周做题总数
                jsonObject.setTotalNum(Integer.valueOf(map.get("totalNum").toString()));
                //本周正确率
                jsonObject.setAverageCorrecRate((double) map.get("correcRate"));
                theirRanking.add(jsonObject);
            }
            return CommonResult.success(theirRanking);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.error();
        }
    }*/

    /*  */
    /**
     * 查询关卡榜
     *
     * @return
     *//*
    @PostMapping("levelRanking")
    public CommonResult<?> levelRanking(String deptId) {
        try {
            //查询同年级班级
            //List<String> ids = sysDeptService.queryGradeClassId(deptId);

            ArrayList<String> ids = new ArrayList<>();
            ids.add(deptId);
            //查询同年级学生排名情况
            List<Map<String, Object>> list = studentAnswerService.levelRanking(ids);
            if (list.size() == 0) {
                return CommonResult.success();
            }
            JSONObject myRanking = null;
            JSONArray theirRanking = new JSONArray();
            for (Map<String, Object> map : list) {
                //进步率
                SysRankingList jsonObject = setUpRanking(map);
                jsonObject.put("levelNum", map.get("count"));
                theirRanking.add(jsonObject);
            }
            myRanking.put("list", theirRanking);
            return CommonResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.error();
        }
    }*/

    @Autowired
    private NewsNoticeService newsNoticeService;

    /**
     * 送星星
     *
     * @param studentId
     * @param type      0勤奋之星 1进步之星
     * @return
     */
    @PostMapping("addStars")
    public CommonResult<?> addStars(String studentId, int type) {
        HttpServletRequest request = ServletUtils.getRequest();

        try {
            String relationName = "";
            SysUser user = tokenService.getLoginUser(request).getUser();
            Students students = studentsService.selectStudentsByStudentId(studentId);
            if (type == 0 || type == 1) {
                relationName = "勤奋之星";
                students.setDiligentStar(students.getDiligentStar() + 1);
            } else if (type == 2) {
                relationName = "进步之星";
                students.setProgressStar(students.getProgressStar() + 1);
            }

            NewsNotice newsNotice = new NewsNotice();
            newsNotice.setId(UUID.randomUUID().toString());
            newsNotice.setSenderId(user.getUserId());
            newsNotice.setSenderName(user.getUserName());
            newsNotice.setAddresseeId(students.getStudentId());
            newsNotice.setAddresseeName(students.getStudentName());
            newsNotice.setNewsType(type);
            newsNotice.setNewsContent(user.getUserName() + "老师给你发了" + relationName + "哦~");
            newsNotice.setNoticePortrait("systemImage");
            newsNotice.setCreateTime(new Date());
            newsNotice.setIsRead(0);
            newsNoticeService.save(newsNotice);
            studentsService.updateStudentByStudentId(students);
            return CommonResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.error();
        }
    }


    /**
     * 生成勤奋榜单的数据
     *
     * @param map
     * @return
     */
    private SysRankingList setUpRanking(Map<String, Object> map) {


        Students students = studentsService.getById((String) map.get("id"));
        SysRankingList jsonObject = new SysRankingList();
        //排名
        jsonObject.setNumber(map.get("number").toString());
        jsonObject.setStudentId(map.get("id").toString());
        //获取星星
        jsonObject.setProgressStar(students.getProgressStar());
        //获取
        jsonObject.setStudentName(students.getStudentName());
        //获取年级班级
        jsonObject.setDeptName(sysDeptService.selectRoomClass(students.getClassRoomId()));

        return jsonObject;
    }


    /**
     * 保留两位小数
     *
     * @param
     * @return
     */
    private double retainTwoDecimal(double dou) {
        BigDecimal b = new BigDecimal(dou);
        double decimal = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return decimal;
    }
}
