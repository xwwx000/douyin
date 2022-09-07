package com.xwwx.douyin.system.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.xwwx.douyin.system.domain.Module;
import com.xwwx.douyin.system.domain.vo.ModuleVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author: 可乐罐
 * @date: 2022/3/18 11:53
 * @description:模块数据库映射
 */
@Mapper
public interface ModuleMapper extends BaseMapper<Module> {
    //根据用户获取全部菜单功能
    @Select("select distinct * from sys_module a join (select module_id,user_id" +
            "           from sys_role_module rm right join sys_user_role ur" +
            "            on rm.role_id=ur.role_id where user_id in (select id from sys_user where user_code=#{usercode})) b on a.id = b.module_id and a.is_show = 1 and a.deleted=0")
    public List<Module> getAllModuleByUser(@Param("usercode") String usercode);

    //获取菜单
    @Select("select distinct id,pid,module_name AS name,url AS path,icon,sort from sys_module a join (select module_id,user_id" +
            "             from sys_role_module rm right join sys_user_role ur" +
            "             on rm.role_id=ur.role_id where user_id in (select id from sys_user where user_code=#{usercode})) b on a.id = b.module_id and a.type=1 and a.is_show=1 and a.deleted=0")
    public List<ModuleVO> getModuleByUser(@Param("usercode") String usercode);

    //获取菜单与模块树
    @Select("select distinct id,pid,module_name AS name,url AS path,icon,sort,level,type,is_show from sys_module where deleted=0")
    public List<ModuleVO> getModuleFunctionTree();

    //根据角色id取菜单 角色权限
    @Select("select c.id,c.pid,c.module_name AS name,c.url AS path,c.icon,c.sort," +
            "case when d.id IS NULL then 0 when d.id IS NOT NULL then 1 end status," +
            "case when c.type=1 then c.module_name || ' -- 菜单' when c.type=2 then c.module_name || ' -- 功能' end remark " +
            "from sys_module c left join " +
            "(select a.* from sys_module a join (select * from sys_role_module where role_id = #{roleId}) b on a.id = b.module_id) d " +
            "on c.id = d.id and c.deleted=0 order by c.id")
    public List<ModuleVO> getModuleByRoleId(@Param("roleId") String roleId);

    /**
     * 取url拦截队列
     * @param moduleWrapper
     * @return
     */
    @Select("select * from sys_module ${ew.customSqlSegment}")
    public List<Module> getUrlInterceptorModuleList(@Param(Constants.WRAPPER) Wrapper<Module> moduleWrapper);

    /**
     * 删除模块
     * @param id
     */
    @Delete("update sys_module set deleted=1 where id = #{id}")
    public void deleteModule(String id);

    @Update("update sys_module set is_show=#{isshow} where id=#{id}")
    public void isShowModule(@Param("id")String id,@Param("isshow")Integer isshow);
}
