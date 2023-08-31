package com.fudian.project.ams.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.common.pojo.CommonResult;
import com.fudian.project.ams.pojo.WfRule;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface WfRuleService extends IService<WfRule>{


    int updateBatch(List<WfRule> list);

    int updateBatchSelective(List<WfRule> list);

    int batchInsert(List<WfRule> list);

    int insertOrUpdate(WfRule record);

    int insertOrUpdateSelective(WfRule record);

    /**
     * 存入文件与规章制度信息
     * @param wfRule
     * @param files
     * @return
     */
    CommonResult insertData(WfRule wfRule, MultipartFile[] files);


    /**
     * 更新规章制度信息
     * @param wfRule
     * @param files
     * @return
     */
    CommonResult updateData(WfRule wfRule, MultipartFile[] files);

    /**
     * 根据ids删除多条规章制度
     * @param ids
     * @return
     */
    CommonResult delWfRulesByIds(String ids);

    /**
     * 获取规章制度列表
     * @param wfRule
     * @return
     */
    CommonGridResult getWfRulesList(WfRule wfRule);

    /**
     * 获取ftp路径
     */
    CommonResult getPdfFileByRuleId(String ruleId);
}
