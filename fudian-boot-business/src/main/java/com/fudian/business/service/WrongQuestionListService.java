package com.fudian.business.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fudian.api.system.pojo.Students;
import com.fudian.api.system.pojo.WrongQuestionListVO;
import com.fudian.business.pojo.WrongQuestionList;
import com.fudian.common.pojo.CommonGridResult;

import java.util.List;
import java.util.Map;

/**
 * 错题
 * @author xuchenghong
 */
public interface WrongQuestionListService extends IService<WrongQuestionList> {

    /**
     * @describe 根据 代码类型和代码（整数、小数、分数）,学生信息 获取相关错题本数据
     * @param dictId 字典id，dict_type+dict_code
     * @return com.fudian.common.pojo.CommonResult
     */
    JSONObject queryListByDict(String dictId, Students student, String addr, String port);

    /**
     * 分页查询错题
     *
     * @param wrongQuestionList
     * @return
     */
    CommonGridResult pagingSelectWrongQuestionList(WrongQuestionList wrongQuestionList);

    /**
     * 查询错题
     *
     * @param wrongQuestionList
     * @return
     */
    List<WrongQuestionList> selectWrongQuestionList(WrongQuestionList wrongQuestionList);

    /**
     * 添加错题
     *
     * @param wrongQuestionList
     * @return
     */
    int insertWrongQuestionList(WrongQuestionList wrongQuestionList);

    /**
     * 修改错题
     *
     * @param wrongQuestionList
     * @return
     */
    int updateWrongQuestionListById(WrongQuestionList wrongQuestionList);

    /**
     * 批量删除错题
     *
     * @param id
     * @return
     */
    int deleteWrongQuestionListById(String id);
    /**
     * 批量删除错题
     *
     * @param ids
     * @return
     */
    int deleteWrongQuestionListById(List<String> ids);

    /**
     * @author xch
     * @param ids
     * @return
     */
    List<WrongQuestionList> getWrongQuestionByBatchId(List<String> ids);
}
