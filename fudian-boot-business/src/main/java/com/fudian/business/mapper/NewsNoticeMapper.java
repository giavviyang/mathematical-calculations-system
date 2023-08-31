package com.fudian.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fudian.business.pojo.NewsNotice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 消息通知mapper
 * @author xuchenghong
 */

@Mapper
public interface NewsNoticeMapper extends BaseMapper<NewsNotice> {

    /**
     * 分页查询消息通知
     * @param newsNotice
     * @return
     */
    List<NewsNotice> pagingQueryNewsNotice(@Param("newsNotice") NewsNotice newsNotice);
}
