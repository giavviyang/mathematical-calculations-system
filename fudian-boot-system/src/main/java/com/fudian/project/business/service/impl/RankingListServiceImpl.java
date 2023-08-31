package com.fudian.project.business.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fudian.api.system.pojo.Students;
import com.fudian.business.service.GoodsListService;
import com.fudian.business.service.StudentAnswerService;
import com.fudian.business.service.StudentsService;
import com.fudian.common.utils.AppletUtils;
import com.fudian.common.utils.ServletUtils;
import com.fudian.common.utils.StringUtils;
import com.fudian.project.business.service.RankingListService;
import com.fudian.project.system.security.service.TokenService;
import com.fudian.project.system.service.SysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RankingListServiceImpl implements RankingListService {

    @Resource
    private StudentAnswerService studentAnswerService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private StudentsService studentsService;


    @Autowired
    private SysDeptService sysDeptService;
    @Autowired
    private GoodsListService goodsListService;

    /**
     * 勤奋榜
     *
     * @param type 0按周 1按学期
     * @return
     */
    @Override
    public JSONObject queryDiligenceRanking(int type) {
        HttpServletRequest request = ServletUtils.getRequest();
        //获取当前登录学生信息
        Students student = tokenService.getLoginUser(request).getStudent();

        //查询同年级班级
        List<String> ids = sysDeptService.queryGradeClassId(student.getClassRoomId());

        //查询同年级学生排名情况
        ids.add(student.getStudentId());
        List<Map<String, Object>> list = studentAnswerService.diligenceRanking(type, ids);

        //创建学生id集合
        List<String> studentIds = new ArrayList<>();
        //存储学生id
        list.forEach(map -> {
            studentIds.add(map.get("id").toString());
        });
        //获取学生信息，包含商品和班级名称
        List<Students> students = studentsService.batchSelectStudentsById(studentIds);
        student = studentsService.getById(student.getStudentId());
        //创建学生map，并存入学生信息
        Map<String, Students> studentMap = new HashMap<>();
        studentMap.put(student.getStudentId(), student);
        students.forEach(stu -> {
            studentMap.put(stu.getStudentId(), stu);
        });
        //处理学生信息
        JSONObject myRanking = setUpRanking(student.getStudentId(), studentMap);
        myRanking.put("isList", 0);
        if (list.size() == 0) {
            myRanking.put("disparity", 1);
        }
        JSONArray theirRanking = new JSONArray();
        int totalNum = 0;
        for (Map<String, Object> map : list) {
            //排名
            String numberStr = map.get("number").toString();
            if (numberStr.indexOf(".") != -1) {
                numberStr = numberStr.substring(0, numberStr.indexOf("."));
            }
            int numberInt = Integer.valueOf(numberStr);

            if (list.size() >= 20 && numberInt == 20) {
                totalNum = Integer.valueOf(map.get("totalNum").toString());
                myRanking.put("disparity", totalNum);
            } else if (list.size() < 20 && list.size() == numberInt) {
                totalNum = Integer.valueOf(map.get("totalNum").toString());
                myRanking.put("disparity", totalNum);
            }
            if (student.getStudentId().equals(map.get("id"))) {
                if (numberInt <= 20) {
                    myRanking.put("disparity", 0);
                    myRanking.put("isList", 1);
                } else {
                    int myTotalNum = Integer.valueOf(map.get("totalNum").toString());
                    myRanking.put("disparity", myTotalNum - totalNum);
                    myRanking.put("isList", 0);
                }
                //排名
                myRanking.put("ranking", numberInt);
                //答对题数
                myRanking.put("correcNum", map.get("totalNum"));
            }
            if (numberInt <= 20) {
                JSONObject jsonObject = setUpRanking(map.get("id").toString(), studentMap);
                //排名
                jsonObject.put("ranking", map.get("number"));
                //答对题数
                jsonObject.put("correcNum", map.get("totalNum"));
                theirRanking.add(jsonObject);
            }
        }
        myRanking.put("list", theirRanking);

        return myRanking;
    }

    /**
     * 进步榜
     *
     * @return
     */
    @Override
    public JSONObject queryProgressRanking() {
        HttpServletRequest request = ServletUtils.getRequest();

        //获取当前登录学生信息
        Students student = tokenService.getLoginUser(request).getStudent();

        //查询同年级班级
        List<String> ids = sysDeptService.queryGradeClassId(student.getClassRoomId());

        //查询同年级学生排名情况
        ids.add(student.getStudentId());
        List<Map<String, Object>> list = studentAnswerService.progressRanking(ids);
        //创建学生id集合
        List<String> studentIds = new ArrayList<>();
        //存储学生id
        list.forEach(map -> {
            studentIds.add(map.get("id").toString());
        });
        //获取学生信息，包含商品和班级名称
        List<Students> students = studentsService.batchSelectStudentsById(studentIds);
        student = studentsService.getById(student.getStudentId());
        //创建学生map，并存入学生信息
        Map<String, Students> studentMap = new HashMap<>();
        studentMap.put(student.getStudentId(), student);
        students.forEach(stu -> {
            studentMap.put(stu.getStudentId(), stu);
        });
        //处理学生信息erqweqqeg
        JSONObject myRanking = setUpRanking(student.getStudentId(), studentMap);
        myRanking.put("isList", 0);
        if (list.size() == 0) {
            myRanking.put("disparity", 0.01);
        }
        JSONArray theirRanking = new JSONArray();
        double progressRate = 0;
        for (Map<String, Object> map : list) {
            //排名
            String numberStr = map.get("number").toString();
            if (numberStr.indexOf(".") != -1) {
                numberStr = numberStr.substring(0, numberStr.indexOf("."));
            }
            int numberInt = Integer.valueOf(numberStr);

            if (list.size() >= 20 && numberInt == 20) {
                progressRate = Double.valueOf(map.get("progressRate").toString());
                myRanking.put("disparity", AppletUtils.retainTwoDecimal(progressRate) * 100);
            } else if (list.size() < 20 && list.size() == numberInt) {
                progressRate = Double.valueOf(map.get("progressRate").toString());
                myRanking.put("disparity", AppletUtils.retainTwoDecimal(progressRate) * 100);
            }
            if (student.getStudentId().equals(map.get("id"))) {
                //排名
                myRanking.put("ranking", map.get("number"));
                if (numberInt <= 20) {
                    myRanking.put("disparity", 0);
                    myRanking.put("isList", 1);
                } else {
                    double myProgressRate = Double.valueOf(map.get("progressRate").toString());
                    myRanking.put("disparity", progressRate - myProgressRate);
                    myRanking.put("isList", 0);
                }
                //排名
                myRanking.put("ranking", numberInt);
                //进步率
                myRanking.put("progressRate", map.get("progressRate"));
            }
            if (numberInt <= 20) {
                JSONObject jsonObject = setUpRanking(map.get("id").toString(), studentMap);
                //排名
                jsonObject.put("ranking", map.get("number"));
                //进步率
                jsonObject.put("progressRate", map.get("progressRate"));
                theirRanking.add(jsonObject);
            }
        }
        myRanking.put("list", theirRanking);

        return myRanking;
    }

    /**
     * 关卡榜
     *
     * @return
     */
    @Override
    public JSONObject queryLevelRanking() {
        HttpServletRequest request = ServletUtils.getRequest();

        //获取当前登录学生信息
        Students student = tokenService.getLoginUser(request).getStudent();

        //查询同年级班级
        List<String> ids = sysDeptService.queryGradeClassId(student.getClassRoomId());

        //查询同年级学生排名情况
        ids.add(student.getStudentId());
        List<Map<String, Object>> list = studentAnswerService.levelRanking(ids);


        //创建学生id集合
        List<String> studentIds = new ArrayList<>();
        //存储学生id
        list.forEach(map -> {
            studentIds.add(map.get("id").toString());
        });
        //获取学生信息，包含商品和班级名称
        List<Students> students = studentsService.batchSelectStudentsById(studentIds);
        student = studentsService.getById(student.getStudentId());
        //创建学生map，并存入学生信息
        Map<String, Students> studentMap = new HashMap<>();
        studentMap.put(student.getStudentId(), student);
        students.forEach(stu -> {
            studentMap.put(stu.getStudentId(), stu);
        });
        //处理学生信息
        JSONObject myRanking = setUpRanking(student.getStudentId(), studentMap);
        myRanking.put("isList", 0);
        if (list.size() == 0) {
            myRanking.put("disparity", 1);
        }
        JSONArray theirRanking = new JSONArray();
        double levelNum = 0;
        for (Map<String, Object> map : list) {
            //排名
            String numberStr = map.get("number").toString();
            if (numberStr.indexOf(".") != -1) {
                numberStr = numberStr.substring(0, numberStr.indexOf("."));
            }
            int numberInt = Integer.valueOf(numberStr);

            if (list.size() >= 20 && numberInt == 20) {
                levelNum = Integer.valueOf(map.get("count").toString());
                myRanking.put("disparity", levelNum);
            } else if (list.size() < 20 && list.size() == numberInt) {
                levelNum = Integer.valueOf(map.get("count").toString());
                myRanking.put("disparity", levelNum);
            }
            if (student.getStudentId().equals(map.get("id"))) {
                if (numberInt <= 20) {
                    myRanking.put("disparity", 0);
                    myRanking.put("isList", 1);
                } else {
                    int myLevelNum = Integer.valueOf(map.get("count").toString());
                    myRanking.put("disparity", levelNum - myLevelNum);
                    myRanking.put("isList", 0);
                }
                //排名
                myRanking.put("ranking", numberInt);
                //闯关数
                myRanking.put("levelNum", map.get("count"));
            }
            if (numberInt <= 20) {
                JSONObject jsonObject = setUpRanking(map.get("id").toString(), studentMap);
                //排名
                jsonObject.put("ranking", map.get("number"));
                //闯关数
                jsonObject.put("levelNum", map.get("count"));
                theirRanking.add(jsonObject);
            }
        }
        myRanking.put("list", theirRanking);

        return myRanking;
    }


/******************  分割线  ********************/
    /**
     * 生成勤奋榜单的数据
     *
     * @param studentId
     * @return
     */
    private JSONObject setUpRanking(String studentId, Map<String, Students> map) {

        HttpServletRequest request = ServletUtils.getRequest();
        //获取服务端口ip
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();

        Students students = map.get(studentId);
        JSONObject jsonObject = new JSONObject();
        //图片地址拼接+id
        if (StringUtils.isNotEmpty(students.getStudentPortrait())) {
            String studentPortrait = "https://" + serverName + ":" + serverPort + "/applet/file/ioReadImage?fileId=" + students.getStudentPortrait();
            jsonObject.put("studentPortrait", studentPortrait);
        } else {
            String studentPortrait = "https://" + serverName + ":" + serverPort + "/applet/file/ioReadImage?fileId=default";
            jsonObject.put("studentPortrait", studentPortrait);
        }
        //查询当前使用的头像框
        if (students.getGoodsId() != null && students.getGoodsId() != "") {
            String portraitFrame = "https://" + serverName + ":" + serverPort + "/applet/file/ioReadImage?fileId=" + students.getGoodsId();
            jsonObject.put("portraitFrame", portraitFrame);
        }
        if (StringUtils.isNotEmpty(students.getStudentNickname())) {
            //获取昵称
            jsonObject.put("studentNickname", students.getStudentNickname());
        } else {
            jsonObject.put("studentNickname", students.getStudentName());
        }
        //获取年级班级
        jsonObject.put("classRoom", students.getClassRoomId());

        return jsonObject;
    }
/*
    private JSONObject setUpRanking(String studentId) {

        HttpServletRequest request = ServletUtils.getRequest();
        //获取服务端口ip
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();

        Students students = studentsService.getById(studentId);
        JSONObject jsonObject = new JSONObject();
        //图片地址拼接+id
        if (StringUtils.isNotEmpty(students.getStudentPortrait())) {
            String studentPortrait = "https://" + serverName + ":" + serverPort + "/applet/file/ioReadImage?fileId=" + students.getStudentPortrait();
            jsonObject.put("studentPortrait", studentPortrait);
        } else {
            String studentPortrait = "https://" + serverName + ":" + serverPort + "/applet/file/ioReadImage?fileId=default";
            jsonObject.put("studentPortrait", studentPortrait);
        }
        //查询当前使用的头像框
        GoodsList goodsList = goodsListService.selectGoodsListByGoodsId(students.getGoodsId());
        String portraitFrame;
        if (goodsList != null) {
            portraitFrame = "https://" + serverName + ":" + serverPort + "/applet/file/ioReadImage?fileId=" + goodsList.getGoodsImge();
            jsonObject.put("portraitFrame", portraitFrame);
        }
        if (StringUtils.isNotEmpty(students.getStudentNickname())) {
            //获取昵称
            jsonObject.put("studentNickname", students.getStudentNickname());
        } else {
            jsonObject.put("studentNickname", students.getStudentName());
        }
        //获取年级班级
        jsonObject.put("classRoom", sysDeptService.selectRoomClass(students.getClassRoomId()));

        return jsonObject;
    }
*/

}
