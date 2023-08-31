package com.fudian.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudian.api.system.pojo.SysUser;
import com.fudian.api.system.service.TokenApi;
import com.fudian.business.mapper.AudienceMapper;
import com.fudian.business.mapper.AudienceMapper;
import com.fudian.business.pojo.Audience;
import com.fudian.business.pojo.TestKnowledgeRelation;
import com.fudian.business.pojo.Audience;
import com.fudian.business.service.TestKnowledgeRelationService;
import com.fudian.business.service.AudienceService;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.common.pojo.CommonResult;
import com.fudian.common.utils.ServletUtils;
import com.fudian.common.utils.StringUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


/**
 * @author xuchenghong
 */

@Service
public class AudienceServiceImpl extends ServiceImpl<AudienceMapper, Audience> implements AudienceService {


    @Resource
    private AudienceMapper audienceMapper;


}
