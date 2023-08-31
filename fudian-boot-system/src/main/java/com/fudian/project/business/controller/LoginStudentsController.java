package com.fudian.project.business.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.fudian.api.system.pojo.LoginUser;
import com.fudian.api.system.pojo.Students;
import com.fudian.api.system.pojo.SysDept;
import com.fudian.business.pojo.AppletFile;
import com.fudian.business.pojo.GoodsList;
import com.fudian.business.pojo.NewsNotice;
import com.fudian.business.pojo.UserAgreement;
import com.fudian.business.service.*;
import com.fudian.business.vo.StudentInfo;
import com.fudian.common.pojo.CommonResult;
import com.fudian.common.utils.ServletUtils;
import com.fudian.common.utils.StringUtils;
import com.fudian.common.utils.http.HttpUtils;
import com.fudian.log.annotation.OperInfo;
import com.fudian.log.annotation.OperTitle;
import com.fudian.project.system.security.service.TokenService;
import com.fudian.project.system.service.SysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@CrossOrigin(allowCredentials = "true",allowedHeaders = "*",origins = "*")
@RestController
@RequestMapping(value = "applet")
@OperTitle("学生管理")
public class LoginStudentsController {

    @Autowired
    private StudentsService studentsService;
    @Autowired
    private GoodsListService goodsListService;
    @Autowired
    private StudentGoodsRelationService studentGoodsRelationService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserAgreementService userAgreementService;
    @Autowired
    private StudentKnowledgeRelationService sKRService;
    @Autowired
    private StudentAnswerService studentAnswerService;
    @Autowired
    private SysDeptService sysDeptService;

    @PostMapping("login")//String user
    public CommonResult<Map<String, Object>> login(String username, String password) {

        CommonResult result = null;
        try {
            /*Map map = JSON.parseObject(user);
            String username = map.get("username").toString();
            String password = map.get("password").toString();*/
            //校验账号密码
            Students students = studentsService.selectStudentsByStudentNumber(username);

            //String encode = new BCryptPasswordEncoder().encode(password);

            //判断加密密码是否正确
            boolean matches = new BCryptPasswordEncoder().matches(password, students.getStudentCode());

            if (students == null) {
                result = CommonResult.error("账号不存在！");
                return result;
            }
            if (!matches) {
                result = CommonResult.error("密码错误！");
                return result;
            }
            students.setCumulativeLogin(students.getCumulativeLogin() + 1);
            studentsService.updateStudentByStudentId(students);
            HttpServletRequest request = ServletUtils.getRequest();
            String jsessionId = request.getSession().getId();
            LoginUser loginUser = new LoginUser();
            loginUser.setStudent(students);
            // 生成令牌
            String token = tokenService.createToken(loginUser);
            // 生成令牌

            result = CommonResult.success();

            result.put("token", token);
            result.put("JSESSIONID", jsessionId);
            return result;
        } catch (Exception e) {
            result = CommonResult.error("账号密码错误！");
            return result;
        }
    }


    /**
     * 重置密码
     *
     * @return 修改反馈
     */
    @PostMapping("restablecerContrasena")
    public CommonResult<?> restablecerContrasena(String username) {

        try {
            //根据学号查询学生信息
            Students students = studentsService.selectStudentsByStudentNumber(username);
            //修改密码
            String studentNumber = students.getStudentNumber();
            if (studentNumber != null) {
                String substring = studentNumber.substring(studentNumber.length() - 6, studentNumber.length());
                String encode = new BCryptPasswordEncoder().encode(substring);
                students.setStudentCode(encode);
                int i = studentsService.updateStudentByStudentNumber(students);
                if (i > 0) {
                    return CommonResult.success("重置成功！");
                } else {
                    return CommonResult.error("重置失败！");
                }
            } else {
                return CommonResult.error("账号不存在！");
            }
        } catch (Exception e) {
            return CommonResult.error("重置失败！");
        }
    }

    /**
     * 获取用户协议
     *
     * @return
     */
    @PostMapping(value = "queryUserAgreement")
    public CommonResult<?> queryUserAgreement() {
        UserAgreement userAgreement = userAgreementService.selectUserAgreement();

        return CommonResult.success("查询成功！", userAgreement.getContent());
    }


    /**
     * 获取学生信息 个人中心学生信息
     *
     * @return 获取学生信息
     */
    @PostMapping("getStudentsInfo")
    public CommonResult<Students> getStudentsInfo() {

        try {
            HttpServletRequest request = ServletUtils.getRequest();
            //通过token获取学生信息
            Students student = tokenService.getLoginUser(request).getStudent();
            Students students = studentsService.selectStudentsByStudentId(student.getStudentId());
            StudentInfo studentInfo = new StudentInfo();

            String serverName = request.getServerName();
            int serverPort = request.getServerPort();
            //图片地址拼接+id
            if (student.getStudentPortrait() != null) {
                String studentPortrait = "https://" + serverName + ":" + serverPort + "/applet/file/ioReadImage?fileId=" + students.getStudentPortrait();
                studentInfo.setStudentPortrait(studentPortrait);
            } else {
                String studentPortrait = "https://" + serverName + ":" + serverPort + "/applet/file/ioReadImage?fileId=default";
                studentInfo.setStudentPortrait(studentPortrait);
            }
            //查询当前使用的头像框
            GoodsList goodsList = goodsListService.selectGoodsListByGoodsId(students.getGoodsId());
            String portraitFrame = "";
            if (goodsList != null) {
                portraitFrame = "https://" + serverName + ":" + serverPort + "/applet/file/ioReadImage?fileId=" + goodsList.getGoodsImge();
            }
            studentInfo.setPortraitFrame(portraitFrame);
            if (StringUtils.isNotEmpty(students.getStudentNickname())) {
                studentInfo.setStudentNickname(students.getStudentNickname());
            } else {
                studentInfo.setStudentNickname(students.getStudentName());
            }

            studentInfo.setStudentId(students.getStudentNumber());

            //查询学生所在学校
            SysDept sysDept = sysDeptService.selectRoomClassSchool(students.getClassRoomId());
            studentInfo.setSchoolName(sysDept.getDeptName());

            studentInfo.setStudentGrade(sysDeptService.selectRoomClass(students.getClassRoomId()));
            studentInfo.setDiligence(students.getDiligentStar());
            studentInfo.setProgress(students.getProgressStar());
            studentInfo.setCumulativeLogin(students.getCumulativeLogin());

            //查询知识点和闯关掌握率
            studentInfo.setKnowledgeNumber(sKRService.selectKnowledgeNum(students.getStudentId(), 0));
            studentInfo.setLevelNumber(sKRService.selectKnowledgeNum(students.getStudentId(), 1));
            //查询学期内正确率和习题数 周为单位
            List<JSONObject> jsonObjects = studentAnswerService.selectStudentCorrectRate(students.getStudentId());
            //正确率和做题数量 转换为规定格式
            //studentInfo.setCorrectRate(corrects);
            //studentInfo.setExercisesNumber(numbers);
            studentInfo.setGrowthCurve(jsonObjects);

            studentInfo.setAcoustics(students.getAcoustics());
            studentInfo.setBackgroundMusic(students.getBackgroundMusic());
            studentInfo.setGoldNumber(students.getCoinsNum());
            return CommonResult.success(studentInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.error();
        }
    }


    /**
     * 修改昵称
     *
     * @return 修改反馈
     */
    @PostMapping("modifyNickname")
    public CommonResult<?> modifyNickname(String nickname) {

        try {
            //通过token获取学生信息
            //根据学号 或者 id 查询学生信息
            Students student = tokenService.getLoginUser(ServletUtils.getRequest()).getStudent();
            Students students = studentsService.selectStudentsByStudentId(student.getStudentId());

            //修改昵称
            students.setStudentNickname(nickname);
            int i = studentsService.updateStudentByStudentNumber(students);
            if (i > 0) {
                return CommonResult.success("修改成功！");
            } else {
                return CommonResult.error("修改失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.error("修改失败！");
        }
    }

    @Autowired
    private AppletFileService appletFileService;

    /**
     * 修改头像
     *
     * @return 修改反馈
     */
    @PostMapping("modifyPortrait")
    public CommonResult<?> modifyPortrait(MultipartFile file) throws IOException {

        try {
            //通过token获取学生信息
            //根据学号 或者 id 查询学生信息
            Students student = tokenService.getLoginUser(ServletUtils.getRequest()).getStudent();
            Students students = studentsService.selectStudentsByStudentId(student.getStudentId());
            if (StringUtils.isEmpty(students.getStudentPortrait()) || students.getStudentPortrait().equals("default")) {
                String uuid = UUID.randomUUID().toString();
                students.setStudentPortrait(uuid);
                studentsService.updateStudentByStudentId(students);
            }
            //修改头像
            AppletFile appletFile = new AppletFile();
            appletFile.setId(students.getStudentPortrait());
            byte[] bytes = file.getBytes();
            appletFile.setFileData(bytes);
            appletFileService.saveOrUpdate(appletFile);
            return CommonResult.success("修改成功！");
        } catch (IOException e) {
            e.printStackTrace();
            return CommonResult.error("修改失败！");
        }


    }


    /**
     * 查询使用中的头像框
     *
     * @return
     */
    @PostMapping("portraitBoxInUse")
    public CommonResult<?> portraitBoxInUse() {

        try {
            JSONObject jsonObject = new JSONObject();
            //通过token获取学生信息
            //根据学号 或者 id 查询学生信息
            Students student = tokenService.getLoginUser(ServletUtils.getRequest()).getStudent();
            Students students = studentsService.selectStudentsByStudentId(student.getStudentId());

            GoodsList goodsList = goodsListService.selectGoodsListByGoodsId(students.getGoodsId());


            if (goodsList != null) {
                HttpServletRequest request = ServletUtils.getRequest();
                String serverName = request.getServerName();
                int serverPort = request.getServerPort();
                //图片地址拼接+id
                if (goodsList.getGoodsImge() != null) {
                    String goods = "https://" + serverName + ":" + serverPort + "/applet/file/ioReadImage?fileId=" + goodsList.getGoodsImge();
                    jsonObject.put("image", goods);
                }

                jsonObject.put("id", goodsList.getGoodsId());
            }

            return CommonResult.success(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.error();
        }
    }

    /**
     * 修改头像框
     *
     * @return 修改反馈
     */
    @PostMapping("modifyPortraitFrame")
    public CommonResult<?> modifyPortraitFrame(String id) {

        try {
            //通过token获取学生信息
            //根据学号 或者 id 查询学生信息
            Students student = tokenService.getLoginUser(ServletUtils.getRequest()).getStudent();
            Students students = studentsService.selectStudentsByStudentId(student.getStudentId());

            students.setGoodsId(id);
            int i = studentsService.updateStudentByStudentId(students);

            if (i == 1) {
                return CommonResult.success();
            } else {
                return CommonResult.error();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.error();
        }
    }


    /**
     * 获取我的头像框列表
     *
     * @return 反馈
     */
    @PostMapping("queryMyAvatarFrame")
    public CommonResult<?> queryMyAvatarFrame() {

        try {
            //通过token获取学生信息
            //根据学号 或者 id 查询学生信息
            Students student = tokenService.getLoginUser(ServletUtils.getRequest()).getStudent();

            List<GoodsList> goodsLists = goodsListService.selectGoodsListByStudentId(student.getStudentId());

            JSONArray jsonArray = new JSONArray();
            HttpServletRequest request = ServletUtils.getRequest();
            String serverName = request.getServerName();
            int serverPort = request.getServerPort();
            for (GoodsList goods : goodsLists) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", goods.getGoodsId());
                //图片地址拼接+id
                String img = "https://" + serverName + ":" + serverPort + "/applet/file/ioReadImage?fileId=" + goods.getGoodsImge();

                jsonObject.put("image", img);
                jsonObject.put("name", goods.getGoodsName());
                jsonObject.put("describe", goods.getGoodsDescribe());
                jsonArray.add(jsonObject);
            }
            return CommonResult.success(jsonArray);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.error();
        }
    }

    /**
     * 修改密码
     *
     * @return 修改反馈
     */
    @PostMapping("modifyPassword")
    public CommonResult<?> modifyPassword(String oldPassword, String newPassword) {

        try {
            //通过token获取学生信息
            //根据学号 或者 id 查询学生信息
            Students student = tokenService.getLoginUser(ServletUtils.getRequest()).getStudent();
            Students students = studentsService.selectStudentsByStudentId(student.getStudentId());
            //判断加密密码是否正确
            boolean matches = new BCryptPasswordEncoder().matches(oldPassword, students.getStudentCode());

            //判断密码是否正确
            if (matches) {
                //修改昵称
                String encode = new BCryptPasswordEncoder().encode(newPassword);
                students.setStudentCode(encode);
                studentsService.updateStudentByStudentNumber(students);
                return CommonResult.success("修改成功！");
            } else {
                return CommonResult.error("密码错误，修改失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.error();
        }
    }

    @Autowired
    private NewsNoticeService newsNoticeService;

    @PostMapping("queryNewsNotice")
    public CommonResult<?> queryNewsNotice() {
        try {
            HttpServletRequest request = ServletUtils.getRequest();

            String serverName = request.getServerName();
            int serverPort = request.getServerPort();

            Students student = tokenService.getLoginUser(request).getStudent();
            List<NewsNotice> list = newsNoticeService.selectNewsNotice(student.getStudentId());
            JSONArray jsonArray = new JSONArray();
            for (NewsNotice newsNotice : list) {
                JSONObject jsonObject = new JSONObject();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                String time = format.format(newsNotice.getCreateTime());
                //通知时间
                jsonObject.put("noticeTime", time);
                if (newsNotice.getNoticePortrait() != null) {
                    //通知头像
                    String portrait = "https://" + serverName + ":" + serverPort + "/applet/file/ioReadImage?fileId=" + newsNotice.getNoticePortrait();
                    jsonObject.put("noticePortrait", portrait);
                }
                //通知内容
                jsonObject.put("noticeContent", newsNotice.getNewsContent());
                jsonArray.add(jsonObject);
            }
            return CommonResult.success(jsonArray);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.error();
        }
    }

    /**
     * 退出登录
     *
     * @param request
     * @return
     */
    @PostMapping("logout")
    public CommonResult logout(HttpServletRequest request) {
        LoginUser loginUser = tokenService.getLoginUser(request);
        try {
            if (StringUtils.isNotNull(loginUser)) {
                // 删除用户缓存记录
                tokenService.delLoginUser(loginUser.getToken());
            }
        } catch (Exception e) {
            return CommonResult.error("退出失败");
        }

        return CommonResult.success("退出成功");
    }

    /*
    *根据学生id修改背景音乐/音效
     */
    @PutMapping("setStudentSound")
    @OperInfo(info="根据学生id修改背景音乐")
    public CommonResult setStudentSound(HttpServletRequest request,String studentId,String acoustics,String backgroundMusic){
        Students student = tokenService.getLoginUser(request).getStudent();
        LambdaUpdateWrapper<Students> studentsLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        studentsLambdaUpdateWrapper.eq(Students::getStudentId,student.getStudentId());
        if (StringUtils.isNotEmpty(acoustics)){
            studentsLambdaUpdateWrapper.set(Students::getAcoustics,acoustics);
        }
        if (StringUtils.isNotEmpty(backgroundMusic)){
            studentsLambdaUpdateWrapper.set(Students::getBackgroundMusic,backgroundMusic);
        }
        boolean update = studentsService.update(studentsLambdaUpdateWrapper);
        if (update){
            return CommonResult.success();
        }
        return CommonResult.error();
    }
}
