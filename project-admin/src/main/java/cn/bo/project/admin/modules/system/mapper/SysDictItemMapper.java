package cn.bo.project.admin.modules.system.mapper;

import cn.bo.project.admin.modules.system.entity.SysDictItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author zhangbo
 * @Date 2020/1/4 20:14
 * @Description 字典mapper接口
 * @PackageName cn.bo.project.admin.modules.system.mapper
 **/
public interface SysDictItemMapper extends BaseMapper<SysDictItem> {
    @Select("SELECT * FROM sys_dict_item WHERE DICT_ID = #{mainId} order by sort_order asc, item_value asc")
    public List<SysDictItem> selectItemsByMainId(String mainId);
}
