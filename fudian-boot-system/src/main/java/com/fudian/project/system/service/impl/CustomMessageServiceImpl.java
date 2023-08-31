package com.fudian.project.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudian.project.system.mapper.CustomMessageMapper;
import com.fudian.project.system.pojo.CustomMessage;
import com.fudian.project.system.service.CustomMessageService;
import org.springframework.stereotype.Service;

/**
* @Author: WangYuZhuo
* @Date: 2020/6/9 17:15
* @Desc:
*/
@Service
public class CustomMessageServiceImpl extends ServiceImpl<CustomMessageMapper, CustomMessage> implements CustomMessageService {

}
