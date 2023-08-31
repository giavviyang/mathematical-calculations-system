package com.fudian.project.ams.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.common.pojo.CommonResult;
import com.fudian.project.ams.pojo.WfTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface WfTemplateService extends IService<WfTemplate>{


    int updateBatch(List<WfTemplate> list);

    int updateBatchSelective(List<WfTemplate> list);

    int batchInsert(List<WfTemplate> list);

    int insertOrUpdate(WfTemplate record);

    int insertOrUpdateSelective(WfTemplate record);

    /**
     * 新增常用模板
     * @param files
     * @param wfTemplate
     * @return
     */
    CommonResult insertTemplate(MultipartFile[] files, WfTemplate wfTemplate);


    /**
     * 更新常用模板
     * @param files
     * @param wfTemplate
     * @return
     */
    CommonResult updateTemplate(MultipartFile[] files, WfTemplate wfTemplate);


    /**
     * 删除常用模板
     * @param ids
     * @return
     */
    CommonResult delTemplatesByIds(String ids);


    /**
     * 获取常用模板列表
     * @param wfTemplate
     * @return
     */
    CommonGridResult getTemplateList(WfTemplate wfTemplate);

    /**
     * 根据常用模板id获取pdf地址
     * @param templateId
     * @return
     */
    CommonResult getPdfFileByTemplateId(String templateId);

    /**
     * 根据ids获取多个文件路径
     * @param templateIds
     * @return
     */
    CommonResult getWordFileByTemplateId(String templateIds);
}
