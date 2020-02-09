package cn.bo.project.admin.modules.system.mapper;


import cn.bo.project.admin.modules.system.entity.SysUser;
import cn.bo.project.admin.modules.system.entity.SysUserPost;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;


/**
 * @Author zhangbo
 * @Date 2020/1/4 13:12
 * @Description 用户与岗位关联表mapper接口
 * @PackageName cn.bo.project.admin.modules.system.mapper
 **/
public interface SysUserPostMapper{

    /**
     * 通过用户ID删除用户和岗位关联
     * 
     * @param userId 用户ID
     * @return 结果
     */
    public int deleteUserPostByUserId(Long userId);

    /**
     * 通过岗位ID查询岗位使用数量
     * 
     * @param postId 岗位ID
     * @return 结果
     */
    public int countUserPostById(Long postId);

    /**
     * 批量删除用户和岗位关联
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteUserPost(Long[] ids);

    /**
     * 批量新增用户岗位信息
     * 
     * @param userPostList 用户角色列表
     * @return 结果
     */
    public int batchUserPost(List<SysUserPost> userPostList);
}
