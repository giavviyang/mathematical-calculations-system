package com.fudian.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fudian.business.pojo.Popup;
import com.fudian.common.pojo.CommonGridResult;

import java.util.List;

/**
 * 弹窗
 * @author xuchenghong
 */
public interface PopupService extends IService<Popup> {

    /**
     * 分页查询弹窗
     *
     * @param popup
     * @return
     */
    CommonGridResult pagingSelectPopup(Popup popup);

    /**
     * 查询弹窗
     *
     * @param popup
     * @return
     */
    List<Popup> selectPopup(Popup popup);

    /**
     * 添加弹窗
     *
     * @param popup
     * @return
     */
    int insertPopup(Popup popup);

    /**
     * 修改弹窗
     *
     * @param popup
     * @return
     */
    int updatePopupById(Popup popup);

    /**
     * 批量删除弹窗
     *
     * @param id
     * @return
     */
    int deletePopupById(String id);
    /**
     * 批量删除弹窗
     *
     * @param ids
     * @return
     */
    int deletePopupById(List<String> ids);
}
