package com.fudian.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudian.business.mapper.PopupMapper;
import com.fudian.business.pojo.Popup;
import com.fudian.business.service.PopupService;
import com.fudian.common.pojo.CommonGridResult;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;


/**
 * @author xuchenghong
 */

@Service
public class PopupServiceImpl extends ServiceImpl<PopupMapper, Popup> implements PopupService {


    @Resource
    private PopupMapper popupMapper;

    /**
     * 分页查询弹窗
     *
     * @param popup
     * @return
     */
    @Override
    public CommonGridResult pagingSelectPopup(Popup popup) {
        LambdaQueryWrapper<Popup> queryWrapper = new LambdaQueryWrapper<>();

        PageHelper.startPage(popup.getPageNum(), popup.getPageSize());
        List<Popup> popups = popupMapper.selectList(queryWrapper);

        Page page = (Page) popups;
        long total = page.getTotal();
        CommonGridResult commonGridResult = new CommonGridResult(popup.getPageNum(), popup.getPageSize(), total, popups);
        return commonGridResult;
    }

    /**
     * 查询弹窗
     *
     * @param popup
     * @return
     */
    @Override
    public List<Popup> selectPopup(Popup popup) {
        LambdaQueryWrapper<Popup> queryWrapper = new LambdaQueryWrapper<>();

        if (popup != null && popup.getClassificationId() != null){
            queryWrapper.eq(Popup::getClassificationId,popup.getClassificationId());
        }
        List<Popup> popups = popupMapper.selectList(queryWrapper);
        return popups;
    }

    /**
     * 添加弹窗
     *
     * @param popup
     * @return
     */
    @Override
    public int insertPopup(Popup popup) {
        String uuid = UUID.randomUUID().toString();
        popup.setId(uuid);
        int insert = popupMapper.insert(popup);
        return insert;
    }

    /**
     * 修改弹窗
     *
     * @param popup
     * @return
     */
    @Override
    public int updatePopupById(Popup popup) {

        int i = popupMapper.updateById(popup);
        return i;
    }

    /**
     * 批量删除弹窗
     *
     * @param ids
     * @return
     */
    @Override
    public int deletePopupById(List<String> ids) {
        int insert = popupMapper.deleteBatchIds(ids);
        return insert;
    }
    /**
     * 删除弹窗
     *
     * @param id
     * @return
     */
    @Override
    public int deletePopupById(String id) {
        int insert = popupMapper.deleteById(id);
        return insert;
    }
}
