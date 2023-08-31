package com.fudian.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fudian.business.pojo.NewsNotice;
import com.fudian.common.pojo.CommonGridResult;

import java.util.List;

/**
 * 消息通知
 * @author xuchenghong
 */
public interface NewsNoticeService extends IService<NewsNotice> {

    /**
     * 分页查询消息通知
     *
     * @param newsNotice
     * @return
     */
    CommonGridResult pagingSelectNewsNotice(NewsNotice newsNotice);

    /**
     * 查询消息通知查询最近两周的消息 根据学生id
     *
     * @param studentId
     * @return
     */
    List<NewsNotice> selectNewsNotice(String studentId);

    /**
     * 添加消息通知
     *
     * @param newsNotice
     * @return
     */
    int insertNewsNotice(NewsNotice newsNotice);

    /**
     * 修改消息通知
     *
     * @param newsNotice
     * @return
     */
    int updateNewsNoticeById(NewsNotice newsNotice);

    /**
     * 删除消息通知
     *
     * @param id
     * @return
     */
    int deleteNewsNoticeById(String id);
    /**
     * 批量删除消息通知
     *
     * @param ids
     * @return
     */
    int deleteNewsNoticeById(List<String> ids);
}
