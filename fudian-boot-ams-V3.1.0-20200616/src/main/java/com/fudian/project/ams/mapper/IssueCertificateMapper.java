package com.fudian.project.ams.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fudian.project.ams.pojo.IssueCertificate;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
  *@author lutao
  *@create 2020/3/16
*/

public interface IssueCertificateMapper extends BaseMapper<IssueCertificate> {
    int updateByPrimaryKeySelective(IssueCertificate record);

    int updateByPrimaryKey(IssueCertificate record);

    int updateBatch(List<IssueCertificate> list);

    int batchInsert(@Param("list") List<IssueCertificate> list);
}
