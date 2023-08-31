package com.fudian.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fudian.business.pojo.GoodsList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


/**
 * @author xuchenghong
 */

@Mapper
public interface GoodsListMapper extends BaseMapper<GoodsList> {
    List<Map<String,Object>> getGoodsList(GoodsList goodsList);
    @Select("SELECT * FROM goods_list where goods_id in (select goods_id from student_goods_relation WHERE student_id = #{studentId})")
    List<GoodsList> selectGoodsListByStudentId(String studentId);
}
