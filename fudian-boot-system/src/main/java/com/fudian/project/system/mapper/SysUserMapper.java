package com.fudian.project.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fudian.api.system.pojo.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
    int updateBatch(List<SysUser> list);

    int updateBatchSelective(List<SysUser> list);

    int batchInsert(@Param("list") List<SysUser> list);

    int insertOrUpdate(SysUser record);

    int insertOrUpdateSelective(SysUser record);

    List<SysUser> queryUserByPermissionId(String permissionId);

    List<SysUser> queryUserByRoleId(String roleId);

    /**
     * 依据map查询数据
     *
     * @param map 参数 当中有roleId 还有 userName参数
     * @return List<SysUser>
     */
    List<SysUser> queryUserListByParamters(Map<String, String> map);

    /*
    * 根据用户名和部门id获取用户信息
    *
    * */
    List<Map> querySysUserByNameAndDeptId(SysUser sysUser);
    /*
    * 根据用户名和部门id获取用户信息
    *
    * */
    List<SysUser> querySysUserByClassId(String classId);
}
