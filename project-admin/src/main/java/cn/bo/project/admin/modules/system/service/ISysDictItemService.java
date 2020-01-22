package cn.bo.project.admin.modules.system.service;

import cn.bo.project.admin.modules.system.entity.SysDictItem;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface ISysDictItemService extends IService<SysDictItem> {
    public List<SysDictItem> selectItemsByMainId(String mainId);
}
