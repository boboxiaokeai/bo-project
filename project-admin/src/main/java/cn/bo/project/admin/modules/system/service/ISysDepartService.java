package cn.bo.project.admin.modules.system.service;

import cn.bo.project.admin.modules.system.entity.SysDepart;
import cn.bo.project.admin.modules.system.model.DepartIdModel;
import cn.bo.project.admin.modules.system.model.SysDepartTreeModel;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * @Author zhangbo
 * @Date 2020/1/4 13:16
 * @Description 部门service接口
 * @PackageName cn.bo.project.admin.modules.system.service
 **/
public interface ISysDepartService extends IService<SysDepart>{


    /**
     * 查询所有部门信息,并分节点进行显示
     * @return
     */
    List<SysDepartTreeModel> queryTreeList();

    /**
     * 查询所有部门DepartId信息,并分节点进行显示
     * @return
     */
    public List<DepartIdModel> queryDepartIdTreeList();

    /**
     * 保存部门数据
     * @param sysDepart
     */
    void saveDepartData(SysDepart sysDepart, String username);

    /**
     * 更新depart数据
     * @param sysDepart
     * @return
     */
    Boolean updateDepartDataById(SysDepart sysDepart, String username);
    
    /**
     * 删除depart数据
     * @param id
     * @return
     */
	/* boolean removeDepartDataById(String id); */
    
    /**
     * 根据关键字搜索相关的部门数据
     * @param keyWord
     * @return
     */
    List<SysDepartTreeModel> searhBy(String keyWord);
    
    /**
     * 根据部门id删除并删除其可能存在的子级部门
     * @param id
     * @return
     */
    boolean delete(String id);
    
    /**
     * 查询SysDepart集合
     * @param userId
     * @return
     */
	public List<SysDepart> queryUserDeparts(String userId);

    /**
     * 根据用户名查询部门
     *
     * @param username
     * @return
     */
    List<SysDepart> queryDepartsByUsername(String username);

	 /**
     * 根据部门id批量删除并删除其可能存在的子级部门
     * @param ids
     * @return
     */
	void deleteBatchWithChildren(List<String> ids);
    
}
