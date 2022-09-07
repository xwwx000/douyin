package com.xwwx.douyin.system.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xwwx.douyin.system.domain.SysUser;
import com.xwwx.douyin.system.domain.vo.SysUserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author: 可乐罐
 * @date: 2021/12/27 19:48
 * @description:用户mapper
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
    /**
     * 获取全部用户多表关联
     * @param page
     * @param userWrapper
     * @return
     */
    @Select("select a.*,b.dept_name,case when gender=0 then '保密' when gender=1 then '男' when gender=0 then '女' end AS genderInfo " +
            ",case when status=0 then '待激活' when status=1 then '正常' when status=99 then '禁用' end AS statusInfo " +
            ",case when user_type=10 then '操作员' when user_type=99 then '管理员' end AS userTypeInfo " +
            "from sys_user a left join sys_dept b on a.dept_id = b.id ${ew.customSqlSegment}")
    public Page<SysUserVO> getUserList(Page<SysUserVO> page, @Param(Constants.WRAPPER) Wrapper<SysUserVO> userWrapper);

    /**
     * 根据用户id取用户对象
     * @param userId
     * @return
     */
    @Select("select *" +
            ",case when gender=0 then '保密' when gender=1 then '男' when gender=0 then '女' end AS genderInfo" +
            ",case when status=0 then '待激活' when status=1 then '正常' when status=99 then '禁用' end AS statusInfo " +
            ",case when user_type=10 then '操作员' when user_type=99 then '管理员' end AS userTypeInfo " +
            " from sys_user where id = #{userId} and (user_type=10 or user_type=99)")
    public SysUserVO getUserByUserid(Integer userId);

    /**
     * 根据用户code取用户对象
     * @param userCode
     * @return
     */
    @Select("select *" +
            ",case when gender=0 then '保密' when gender=1 then '男' when gender=0 then '女' end AS genderInfo" +
            ",case when status=0 then '待激活' when status=1 then '正常' when status=99 then '禁用' end AS statusInfo " +
            ",case when user_type=10 then '操作员' when user_type=99 then '管理员' end AS userTypeInfo " +
            " from sys_user where user_code = #{userCode} and (user_type=10 or user_type=99)")
    public SysUserVO getUserByUserCode(String userCode);

    /**
     * 更新用户状态
     * @param id
     * @param status
     */
    @Update("update sys_user set status=#{status} where id=#{id}")
    public void setStatus(@Param("id") String id, @Param("status") Integer status);
}
