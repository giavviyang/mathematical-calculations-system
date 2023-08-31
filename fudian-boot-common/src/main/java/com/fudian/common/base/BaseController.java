package com.fudian.common.base;

import cn.hutool.core.date.DateUtil;
import com.fudian.common.constant.CommonConstants;
import com.fudian.common.utils.ServletUtils;
import com.fudian.common.utils.sql.SqlUtil;
import com.fudian.common.vo.PageDomain;
import com.github.pagehelper.PageHelper;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;
import java.util.Date;

/**
 * @Description Controller基类
 * @ClassName BaseController
 * @Author mr.hou
 * @Date 2020/4/15 11:04 上午
 * @Version 1.0.0
 **/
public class BaseController {

    /**
     * 将前台传递过来的日期格式的字符串，自动转化为Date类型
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // Date 类型转换
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(DateUtil.parse(text));
            }
        });
    }

    /**
     * 设置请求分页数据
     */
    public static void startPage() {

        PageDomain pageDomain = PageDomain.builder()
                .pageNum(ServletUtils.getParameterToInt(CommonConstants.PAGE_NUM))
                .pageSize(ServletUtils.getParameterToInt(CommonConstants.PAGE_SIZE))
                .orderByColumn(ServletUtils.getParameter(CommonConstants.ORDER_BY_COLUMN))
                .isAsc(ServletUtils.getParameter(CommonConstants.IS_ASC))
                .build();


        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        if (pageNum != null && pageSize != null) {
            String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
            PageHelper.startPage(pageNum, pageSize, orderBy);
        }
    }



}
