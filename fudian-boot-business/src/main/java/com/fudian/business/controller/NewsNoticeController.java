package com.fudian.business.controller;

import com.alibaba.fastjson.JSON;
import com.fudian.api.system.pojo.SysUser;
import com.fudian.api.system.service.TokenApi;
import com.fudian.business.pojo.NewsNotice;
import com.fudian.business.pojo.StudentKnowledgeRelation;
import com.fudian.business.service.NewsNoticeService;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.common.pojo.CommonResult;
import com.fudian.common.utils.ServletUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 消息通知
 *
 * @author xuchenghong
 */
@RestController
@RequestMapping(value = "newsNotice")
public class NewsNoticeController {

    @Autowired
    private NewsNoticeService newsNoticeService;
    @Autowired
    private TokenApi tokenService;

    /**
     * 分页查询消息通知
     *
     * @return
     */
    @PostMapping(value = "/pagingQueryNewsNotice")
    public CommonGridResult pagingQueryNewsNotice(NewsNotice newsNotice) {
        HttpServletRequest request = ServletUtils.getRequest();
        SysUser user = tokenService.getLoginUser(request).getUser();
        newsNotice.setAddresseeId(user.getUserId());
        CommonGridResult commonGridResult = newsNoticeService.pagingSelectNewsNotice(newsNotice);
        return commonGridResult;
    }

    /**
     * 批量删除消息通知
     *
     * @return
     */
    @PostMapping(value = "/removeNewsNotice")
    public CommonResult<?> removeNewsNotice(@RequestBody String data) {
        try {
            List<NewsNotice> newsNotices = JSON.parseArray(data, NewsNotice.class);
            List<String> ids = newsNotices.stream().map(NewsNotice::getId).collect(Collectors.toList());
            newsNoticeService.deleteNewsNoticeById(ids);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.error();
        }
        return CommonResult.success();
    }
    /**
     * 批量已读消息通知
     *
     * @return
     */
    @PostMapping(value = "/readNewsNotice")
    public CommonResult<?> readNewsNotice(@RequestBody String data) {
        try {
            List<NewsNotice> newsNotices = JSON.parseArray(data, NewsNotice.class);
            for (NewsNotice newsNotice : newsNotices) {
                newsNotice.setIsRead(1);
                newsNoticeService.updateNewsNoticeById(newsNotice);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.error();
        }
        return CommonResult.success();
    }
}
