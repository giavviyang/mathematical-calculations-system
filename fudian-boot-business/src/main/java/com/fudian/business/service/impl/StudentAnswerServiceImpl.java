package com.fudian.business.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudian.business.mapper.StudentAnswerMapper;
import com.fudian.business.pojo.StudentAnswer;
import com.fudian.business.service.StudentAnswerService;
import com.fudian.business.vo.LearnPracticeVo;
import com.fudian.business.vo.SysRankingList;
import com.fudian.common.pojo.CommonGridResult;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @author xuchenghong
 */

@Service
public class StudentAnswerServiceImpl extends ServiceImpl<StudentAnswerMapper, StudentAnswer> implements StudentAnswerService {


    @Resource
    private StudentAnswerMapper studentAnswerMapper;

    /**
     * 分页查询弹窗
     *
     * @param studentAnswer
     * @return
     */
    @Override
    public CommonGridResult pagingSelectStudentAnswer(StudentAnswer studentAnswer) {
        LambdaQueryWrapper<StudentAnswer> queryWrapper = new LambdaQueryWrapper<>();

        PageHelper.startPage(studentAnswer.getPageNum(), studentAnswer.getPageSize());
        List<StudentAnswer> studentAnswers = studentAnswerMapper.selectList(queryWrapper);

        Page page = (Page) studentAnswers;
        long total = page.getTotal();
        CommonGridResult commonGridResult = new CommonGridResult(studentAnswer.getPageNum(), studentAnswer.getPageSize(), total, studentAnswers);
        return commonGridResult;
    }

    /**
     * 查询弹窗
     *
     * @param studentAnswer
     * @return
     */
    @Override
    public List<StudentAnswer> selectStudentAnswer(StudentAnswer studentAnswer) {
        LambdaQueryWrapper<StudentAnswer> queryWrapper = new LambdaQueryWrapper<>();

        if (studentAnswer != null && studentAnswer.getClassificationId() != null) {
            queryWrapper.eq(StudentAnswer::getClassificationId, studentAnswer.getClassificationId());
        }
        List<StudentAnswer> studentAnswers = studentAnswerMapper.selectList(queryWrapper);
        return studentAnswers;
    }

    /**
     * 查询学期正确率和总题数
     *
     * @param
     * @return
     */
    @Override
    public List<JSONObject> selectStudentCorrectRate(String studentId) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //获取当前时间
            Calendar now = Calendar.getInstance();
            int rear = now.get(Calendar.YEAR);
            int month = now.get(Calendar.MONTH) + 1;
            int day = now.get(Calendar.DAY_OF_MONTH);
            //计算开始时间 判断学期 //获取学期开始时间
            boolean bool = month >= 9 || month == 1 || month == 2 && day < 20;
            if (bool) {
                if (month == 1 || month == 2 && day < 20) {
                    rear = rear - 1;
                }
                now.set(rear, Calendar.SEPTEMBER, 1, 0, 0, 0);
            } else {
                now.set(rear, Calendar.FEBRUARY, 20, 0, 0, 0);
            }
            long leftTime = now.getTimeInMillis();
            Date leftTimes = format.parse(format.format(leftTime));

            List<JSONObject> diagrams = studentAnswerMapper.diagrams(leftTimes, studentId);

            return diagrams;
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 添加
     *
     * @param studentAnswer
     * @return
     */
    @Override
    public int insertStudentAnswer(StudentAnswer studentAnswer) {
        String uuid = UUID.randomUUID().toString();
        studentAnswer.setId(uuid);
        studentAnswer.setCreateTime(new Date());
        int insert = studentAnswerMapper.insert(studentAnswer);
        return insert;
    }

    /**
     * 修改弹窗
     *
     * @param studentAnswer
     * @return
     */
    @Override
    public int updateStudentAnswerById(StudentAnswer studentAnswer) {

        int i = studentAnswerMapper.updateById(studentAnswer);
        return i;
    }

    /**
     * 批量删除弹窗
     *
     * @param ids
     * @return
     */
    @Override
    public int deleteStudentAnswerById(List<String> ids) {
        int insert = studentAnswerMapper.deleteBatchIds(ids);
        return insert;
    }

    /**
     * 删除弹窗
     *
     * @param id
     * @return
     */
    @Override
    public int deleteStudentAnswerById(String id) {
        int insert = studentAnswerMapper.deleteById(id);
        return insert;
    }

    /**
     * 查询勤奋榜单
     *
     * @param type
     * @param list 学生id
     * @return
     */
    @Override
    public List<Map<String, Object>> diligenceRanking(int type, List<String> list) {
        List<Map<String, Object>> map = null;
        try {
            //获取当前时间
            Calendar now = Calendar.getInstance();
            //获取当前年
            int rear = now.get(Calendar.YEAR);
            //获取当前月
            int month = now.get(Calendar.MONTH) + 1;
            //获取当前几号
            int day = now.get(Calendar.DAY_OF_MONTH);
            //获取当前周几
            int week = now.get(Calendar.DAY_OF_WEEK) - 1;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            Date date = null;
            if (type == 0) {
                //获取当前周几
                if (week == 0) week = 7;
                week = week - 1;
                String dateString = rear + "-" + month + "-" + day + " " + "00" + ":" + "00" + ":" + "00";
                Date parse = dateFormat.parse(dateString);
                long time = parse.getTime();
                long range = week * 1000 * 60 * 60 * 24;
                range = time - range;
                date = dateFormat.parse(dateFormat.format(range));
            } else if (type == 1) {

                if (month >= 9 || month == 1) {
                    if (month == 1) {
                        rear = rear - 1;
                    }
                    month = 9;
                    day = 1;
                } else if (month == 2 && day < 20) {
                    rear = rear - 1;
                    month = 9;
                    day = 1;
                } else {
                    month = 2;
                    day = 20;
                }
                String dateString = rear + "-" + month + "-" + day + " " + "00" + ":" + "00" + ":" + "00";
                date = dateFormat.parse(dateString);

            }
            map = studentAnswerMapper.diligenceRanking(date, list);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 查询进步榜单
     *
     * @param list 学生id
     * @return
     */
    @Override
    public List<Map<String, Object>> progressRanking(List<String> list) {
        List<Map<String, Object>> map = null;
        try {
            //获取当前时间
            Calendar now = Calendar.getInstance();
            //获取当前年
            int rear = now.get(Calendar.YEAR);
            //获取当前月
            int month = now.get(Calendar.MONTH) + 1;
            //获取当前几号
            int day = now.get(Calendar.DAY_OF_MONTH);
            //获取当前周几
            int week = now.get(Calendar.DAY_OF_WEEK) - 1;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            //获取当前周几
            if (week == 0) week = 7;
            week = week - 1;
            String dateString = rear + "-" + month + "-" + day + " " + "00" + ":" + "00" + ":" + "00";
            Date parse = dateFormat.parse(dateString);
            long time = parse.getTime();
            long range = week * 1000 * 60 * 60 * 24;
            range = time - range;
            Date thisWeek = dateFormat.parse(dateFormat.format(range));
            Date lastWeek = dateFormat.parse(dateFormat.format(range - (7 * 1000 * 60 * 60 * 24)));

            map = studentAnswerMapper.progressRanking(thisWeek, lastWeek, list);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 查询闯关榜单
     *
     * @param list 学生id
     * @return
     */
    @Override
    public List<Map<String, Object>> levelRanking(List<String> list) {
        List<Map<String, Object>> map = null;
        try {
            //获取当前时间
            Calendar now = Calendar.getInstance();
            //获取当前年
            int rear = now.get(Calendar.YEAR);
            //获取当前月
            int month = now.get(Calendar.MONTH) + 1;
            //获取当前几号
            int day = now.get(Calendar.DAY_OF_MONTH);
            //获取当前周几
            int week = now.get(Calendar.DAY_OF_WEEK) - 1;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            if (month >= 9 || month == 1) {
                if (month == 1) {
                    rear = rear - 1;
                }
                month = 9;
                day = 1;
            } else if (month == 2 && day < 20) {
                rear = rear - 1;
                month = 9;
                day = 1;
            } else {
                month = 2;
                day = 20;
            }
            String dateString = rear + "-" + month + "-" + day + " " + "00" + ":" + "00" + ":" + "00";
            Date date = dateFormat.parse(dateString);
            map = studentAnswerMapper.levelRanking(date, list);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 查询勤奋榜单
     *
     * @param sysRankingList
     * @return
     */
    @Override
    public CommonGridResult sysDiligenceRanking(SysRankingList sysRankingList) {

        sysRankingList = diligenceRankingTime(sysRankingList);
        int total = studentAnswerMapper.sysDiligenceRankingCount(sysRankingList);
        List<SysRankingList> sysRankingLists = studentAnswerMapper.sysDiligenceRanking(sysRankingList);

        CommonGridResult map = new CommonGridResult(sysRankingList.getPageNum(), sysRankingList.getPageSize(), total, sysRankingLists);
        return map;
    }

    /**
     * 系统查询进步榜单
     *
     * @param sysRankingList
     * @return
     */
    @Override
    public CommonGridResult sysProgressRanking(SysRankingList sysRankingList) {

        sysRankingList = progressRankingTime(sysRankingList);

        int total = studentAnswerMapper.sysProgressRankingCount(sysRankingList);
        List<SysRankingList> sysRankingLists = studentAnswerMapper.sysProgressRanking(sysRankingList);

        CommonGridResult map = new CommonGridResult(sysRankingList.getPageNum(), sysRankingList.getPageSize(), total, sysRankingLists);
        return map;
    }


    /**
     * 查询正确率/用时排名
     *
     * @return
     */
    @Override
    public Double correctRateOnTimeRanking(StudentAnswer studentAnswer) {
        return studentAnswerMapper.correctRateOnTimeRanking(studentAnswer);
    }

    /**
     * 查询正确率/用时排名
     *
     * @return
     */
    @Override
    public Double correctRateRanking(StudentAnswer studentAnswer) {
        return studentAnswerMapper.correctRateRanking(studentAnswer);
    }

    /**
     * 查询正确率/用时排名
     *
     * @return
     */
    @Override
    public Double correctTimeRanking(StudentAnswer studentAnswer) {
        return studentAnswerMapper.correctTimeRanking(studentAnswer);
    }

    /**
     * 随学随练
     *
     * @param learnPracticeVo
     * @return
     */
    @Override
    public CommonGridResult getLearnPractice(LearnPracticeVo learnPracticeVo) {
        int total = studentAnswerMapper.getLearnPracticeTotal(learnPracticeVo);

        List<LearnPracticeVo> learnPractice = studentAnswerMapper.getLearnPractice(learnPracticeVo);
        CommonGridResult commonGridResult = new CommonGridResult(learnPracticeVo.getPageNum(), learnPracticeVo.getPageSize(), total, learnPractice);
        return commonGridResult;
    }

    /**
     * 随学随练
     *
     * @param learnPracticeVo
     * @return
     */
    @Override
    public CommonGridResult getLearnPracticeByClassify(LearnPracticeVo learnPracticeVo) {
        int total = studentAnswerMapper.getLearnPracticeByClassifyTotal(learnPracticeVo);
        List<Map<String, Object>> maps = null;
        if (learnPracticeVo.getIsRandom() == 0) {
            maps = baseMapper.getLearnPracticeByClassify(learnPracticeVo);
        } else if (learnPracticeVo.getIsRandom() == 1) {
            maps = baseMapper.getLearnPracticeByClassifyUnified(learnPracticeVo);
        }

        CommonGridResult commonGridResult = new CommonGridResult(learnPracticeVo.getPageNum(), learnPracticeVo.getPageSize(), total, maps);
        return commonGridResult;
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


    /**
     * 获取勤奋榜单需要的时间
     *
     * @param sysRankingList
     * @return
     */
    private SysRankingList diligenceRankingTime(SysRankingList sysRankingList) {
        try {
            //获取当前时间
            Calendar now = Calendar.getInstance();
            //获取当前年
            int rear = now.get(Calendar.YEAR);
            //获取当前月
            int month = now.get(Calendar.MONTH) + 1;
            //获取当前几号
            int day = now.get(Calendar.DAY_OF_MONTH);
            //获取当前周几
            int week = now.get(Calendar.DAY_OF_WEEK) - 1;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            Date date = null;
            if (sysRankingList.getType() == 0) {
                //获取当前周几
                if (week == 0) week = 7;
                week = week - 1;
                String dateString = rear + "-" + month + "-" + day + " " + "00" + ":" + "00" + ":" + "00";
                Date parse = dateFormat.parse(dateString);
                long time = parse.getTime();
                long range = week * 1000 * 60 * 60 * 24;
                range = time - range;
                date = dateFormat.parse(dateFormat.format(range));
            } else if (sysRankingList.getType() == 1) {

                if (month >= 9 || month == 1) {
                    if (month == 1) {
                        rear = rear - 1;
                    }
                    month = 9;
                    day = 1;
                } else if (month == 2 && day < 20) {
                    rear = rear - 1;
                    month = 9;
                    day = 1;
                } else {
                    month = 2;
                    day = 20;
                }
                String dateString = rear + "-" + month + "-" + day + " " + "00" + ":" + "00" + ":" + "00";
                date = dateFormat.parse(dateString);
            }
            sysRankingList.setBeginTime(date);
            return sysRankingList;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sysRankingList;
    }


    /**
     * 获取进步榜单需要的时间
     *
     * @param sysRankingList
     * @return
     */
    private SysRankingList progressRankingTime(SysRankingList sysRankingList) {
        try {
            //获取当前时间
            Calendar now = Calendar.getInstance();
            //获取当前年
            int rear = now.get(Calendar.YEAR);
            //获取当前月
            int month = now.get(Calendar.MONTH) + 1;
            //获取当前几号
            int day = now.get(Calendar.DAY_OF_MONTH);
            //获取当前周几
            int week = now.get(Calendar.DAY_OF_WEEK) - 1;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            //获取当前周几
            if (week == 0) week = 7;
            week = week - 1;
            String dateString = rear + "-" + month + "-" + day + " " + "00" + ":" + "00" + ":" + "00";
            Date parse = dateFormat.parse(dateString);
            long time = parse.getTime();
            long range = week * 1000 * 60 * 60 * 24;
            range = time - range;
            Date thisWeek = dateFormat.parse(dateFormat.format(range));
            Date lastWeek = dateFormat.parse(dateFormat.format(range - (7 * 1000 * 60 * 60 * 24)));
            sysRankingList.setBeginTime(lastWeek);
            sysRankingList.setEndTime(thisWeek);
            return sysRankingList;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sysRankingList;
    }
}
