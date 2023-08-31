package com.fudian.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudian.api.system.pojo.Students;
import com.fudian.api.system.pojo.SysDept;
import com.fudian.api.system.service.SysDeptApi;
import com.fudian.business.mapper.NewsNoticeMapper;
import com.fudian.business.pojo.NewsNotice;
import com.fudian.business.service.NewsNoticeService;
import com.fudian.business.service.StudentsService;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.common.utils.StringUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


/**
 * 消息通知service
 *
 * @author xuchenghong
 */

@Service
public class NewsNoticeServiceImpl extends ServiceImpl<NewsNoticeMapper, NewsNotice> implements NewsNoticeService {


    @Resource
    private NewsNoticeMapper newsNoticeMapper;
    @Resource
    private SysDeptApi sysDeptApi;
    @Resource
    private StudentsService studentsService;

    /**
     * 分页查询消息通知
     *
     * @param newsNotice
     * @return
     */
    @Override
    public CommonGridResult pagingSelectNewsNotice(NewsNotice newsNotice) {


        PageHelper.startPage(newsNotice.getPageNum(), newsNotice.getPageSize());
        List<NewsNotice> newsNotices = newsNoticeMapper.pagingQueryNewsNotice(newsNotice);

        Page page = (Page) newsNotices;
        long total = page.getTotal();
        CommonGridResult commonGridResult = new CommonGridResult(newsNotice.getPageNum(), newsNotice.getPageSize(), total, newsNotices);
        return commonGridResult;
    }

    /**
     * 查询消息通知查询最近两周的消息 根据学生id
     *
     * @param studentId
     * @return
     */
    @Override
    public List<NewsNotice> selectNewsNotice(String studentId) {
        List<NewsNotice> newsNotices = null;

        try {
            //获取当前时间
            Calendar now = Calendar.getInstance();
            //获取当前年
            int rear = now.get(Calendar.YEAR);
            //获取当前月
            int month = now.get(Calendar.MONTH) + 1;
            //获取当前几号
            int day = now.get(Calendar.DAY_OF_MONTH);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateString = rear + "-" + month + "-" + day + " " + "00" + ":" + "00" + ":" + "00";
            Date parse = dateFormat.parse(dateString);
            long range = 14 * 1000 * 60 * 60 * 24;
            range = parse.getTime() - range;
            parse = dateFormat.parse(dateFormat.format(range));

            LambdaQueryWrapper<NewsNotice> queryWrapper = new LambdaQueryWrapper<>();
            //根据收件人id查询
            queryWrapper.eq(NewsNotice::getAddresseeId, studentId);

            queryWrapper.ge(NewsNotice::getCreateTime, parse);
            queryWrapper.orderByAsc(NewsNotice::getCreateTime);
            newsNotices = newsNoticeMapper.selectList(queryWrapper);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newsNotices;
    }

    /**
     * 添加消息通知
     *
     * @param newsNotice
     * @return
     */
    @Override
    public int insertNewsNotice(NewsNotice newsNotice) {
        String uuid = UUID.randomUUID().toString();
        newsNotice.setId(uuid);
        int insert = newsNoticeMapper.insert(newsNotice);
        return insert;
    }

    /**
     * 修改消息通知
     *
     * @param newsNotice
     * @return
     */
    @Override
    public int updateNewsNoticeById(NewsNotice newsNotice) {

        int i = newsNoticeMapper.updateById(newsNotice);
        return i;
    }

    /**
     * 批量删除消息通知
     *
     * @param ids
     * @return
     */
    @Override
    public int deleteNewsNoticeById(List<String> ids) {
        int insert = newsNoticeMapper.deleteBatchIds(ids);
        return insert;
    }

    /**
     * 删除消息通知
     *
     * @param id
     * @return
     */
    @Override
    public int deleteNewsNoticeById(String id) {
        int insert = newsNoticeMapper.deleteById(id);
        return insert;
    }
}
