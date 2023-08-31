package com.fudian.project.ams.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudian.project.ams.mapper.IssueCertificateMapper;
import com.fudian.project.ams.pojo.IssueCertificate;
import com.fudian.project.ams.service.IssueCertificateService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
  *@author lutao
  *@create 2020/3/16
*/

@Service
public class IssueCertificateServiceImpl extends ServiceImpl<IssueCertificateMapper, IssueCertificate> implements IssueCertificateService {
    @Autowired
    private IssueCertificateMapper issueCertificateMapper;

    @Override
    public int updateByPrimaryKeySelective(IssueCertificate record) {
        return issueCertificateMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(IssueCertificate record) {
        return issueCertificateMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateBatch(List<IssueCertificate> list) {
        return issueCertificateMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<IssueCertificate> list) {
        return issueCertificateMapper.batchInsert(list);
    }

    @Override
    public List<IssueCertificate> queryList(IssueCertificate issueCertificate) {
        LambdaQueryWrapper<IssueCertificate> wrapper=new LambdaQueryWrapper<>();
        if (issueCertificate.getProveType()!=null&&!"".equals(issueCertificate.getProveType())){
            wrapper.eq(IssueCertificate::getProveType,issueCertificate.getProveType());
        }
        if (issueCertificate.getA0101()!=null&&!"".equals(issueCertificate.getA0101())){
            wrapper.like(IssueCertificate::getA0101,issueCertificate.getA0101());
        }
        if (issueCertificate.getA0184()!=null&&!"".equals(issueCertificate.getA0184())){
            wrapper.like(IssueCertificate::getA0184,issueCertificate.getA0184());
        }
        if (issueCertificate.getState()!=null&&!"".equals(issueCertificate.getState())){
            wrapper.eq(IssueCertificate::getState,issueCertificate.getState());
        }
        PageHelper.startPage(issueCertificate.getPageNum(),issueCertificate.getPageSize());
        List<IssueCertificate> list=issueCertificateMapper.selectList(wrapper);
        return list;
    }

    @Override
    public int removeById(String id) {
        int i=issueCertificateMapper.deleteById(id);
        return i;
    }

    @Override
    public int saveIssueCertificate(IssueCertificate issueCertificate) {
        int i=0;
        if (issueCertificate.getId()!=null&&!"".equals(issueCertificate.getId())){
            i=issueCertificateMapper.updateByPrimaryKey(issueCertificate);
        }else {
            String uuid= UUID.randomUUID().toString();
            issueCertificate.setId(uuid);
            i=issueCertificateMapper.insert(issueCertificate);
        }
        return i;
    }
}
