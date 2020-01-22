package cn.bo.project.admin.modules.system.service.impl;

import cn.bo.project.admin.modules.system.entity.SysDict;
import cn.bo.project.admin.modules.system.entity.SysDictItem;
import cn.bo.project.admin.modules.system.mapper.SysDictItemMapper;
import cn.bo.project.admin.modules.system.mapper.SysDictMapper;
import cn.bo.project.admin.modules.system.model.TreeSelectModel;
import cn.bo.project.admin.modules.system.service.ISysDictService;
import cn.bo.project.base.constant.CacheConstant;
import cn.bo.project.base.constant.CommonConstant;
import cn.bo.project.base.core.model.DictModel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author zhangbo
 * @Date 2020/1/13 20:14
 * @Description 字典service实现类
 * @PackageName cn.bo.project.admin.modules.system.service.impl
 **/
@Service
@Slf4j
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements ISysDictService {

    @Autowired
    private SysDictMapper sysDictMapper;
    @Autowired
    private SysDictItemMapper sysDictItemMapper;

	/**
	 * 通过查询指定code 获取字典
	 * @param code
	 * @return
	 */
	@Override
	@Cacheable(value = CacheConstant.SYS_DICT_CACHE,key = "#code")
	public List<DictModel> queryDictItemsByCode(String code) {
		log.info("无缓存dictCache的时候调用这里！");
		return sysDictMapper.queryDictItemsByCode(code);
	}

	/**
	 * 通过查询指定code 获取字典值text
	 * @param code
	 * @param key
	 * @return
	 */

	@Override
	@Cacheable(value = CacheConstant.SYS_DICT_CACHE,key = "#code+':'+#key")
	public String queryDictTextByKey(String code, String key) {
		log.info("无缓存dictText的时候调用这里！");
		return sysDictMapper.queryDictTextByKey(code, key);
	}

	/**
	 * 通过查询指定table的 text code 获取字典
	 * dictTableCache采用redis缓存有效期10分钟
	 * @param table
	 * @param text
	 * @param code
	 * @return
	 */
	@Override
	//@Cacheable(value = CacheConstant.SYS_DICT_TABLE_CACHE)
	public List<DictModel> queryTableDictItemsByCode(String table, String text, String code) {
		log.info("无缓存dictTableList的时候调用这里！");
		return sysDictMapper.queryTableDictItemsByCode(table,text,code);
	}

	@Override
	public List<DictModel> queryTableDictItemsByCodeAndFilter(String table, String text, String code, String filterSql) {
		log.info("无缓存dictTableList的时候调用这里！");
		return sysDictMapper.queryTableDictItemsByCodeAndFilter(table,text,code,filterSql);
	}
	
	/**
	 * 通过查询指定table的 text code 获取字典值text
	 * dictTableCache采用redis缓存有效期10分钟
	 * @param table
	 * @param text
	 * @param code
	 * @param key
	 * @return
	 */
	@Override
	@Cacheable(value = CacheConstant.SYS_DICT_TABLE_CACHE)
	public String queryTableDictTextByKey(String table,String text,String code, String key) {
		log.info("无缓存dictTable的时候调用这里！");
		return sysDictMapper.queryTableDictTextByKey(table,text,code,key);
	}

	/**
	 * 通过查询指定table的 text code 获取字典，包含text和value
	 * dictTableCache采用redis缓存有效期10分钟
	 * @param table
	 * @param text
	 * @param code
	 * @param keyArray
	 * @return
	 */
	@Override
	@Cacheable(value = CacheConstant.SYS_DICT_TABLE_CACHE)
	public List<String> queryTableDictByKeys(String table, String text, String code, String[] keyArray) {
		List<DictModel> dicts = sysDictMapper.queryTableDictByKeys(table, text, code, keyArray);
		List<String> texts = new ArrayList<>(dicts.size());
		// 查询出来的顺序可能是乱的，需要排个序
		for (String key : keyArray) {
			for (DictModel dict : dicts) {
				if (key.equals(dict.getValue())) {
					texts.add(dict.getText());
					break;
				}
			}
		}
		return texts;
	}

    /**
     * 根据字典类型id删除关联表中其对应的数据
     */
    @Override
    public boolean deleteByDictId(SysDict sysDict) {
        sysDict.setDelFlag(CommonConstant.DEL_FLAG_1);
        return  this.updateById(sysDict);
    }

    @Override
    @Transactional
    public void saveMain(SysDict sysDict, List<SysDictItem> sysDictItemList) {

        sysDictMapper.insert(sysDict);
        if (sysDictItemList != null) {
            for (SysDictItem entity : sysDictItemList) {
                entity.setDictId(sysDict.getId());
                sysDictItemMapper.insert(entity);
            }
        }
    }

	@Override
	public List<DictModel> queryAllDepartBackDictModel() {
		return baseMapper.queryAllDepartBackDictModel();
	}

	@Override
	public List<DictModel> queryAllUserBackDictModel() {
		return baseMapper.queryAllUserBackDictModel();
	}
	
	@Override
	public List<DictModel> queryTableDictItems(String table, String text, String code, String keyword) {
		return baseMapper.queryTableDictItems(table, text, code, "%"+keyword+"%");
	}

	@Override
	public List<TreeSelectModel> queryTreeList(Map<String, String> query, String table, String text, String code, String pidField, String pid, String hasChildField) {
		return baseMapper.queryTreeList(query,table, text, code, pidField, pid,hasChildField);
	}

	@Override
	public void deleteOneDictPhysically(String id) {
		this.baseMapper.deleteOneById(id);
		this.sysDictItemMapper.delete(new LambdaQueryWrapper<SysDictItem>().eq(SysDictItem::getDictId,id));
	}

	@Override
	public void updateDictDelFlag(int delFlag, String id) {
		baseMapper.updateDictDelFlag(delFlag,id);
	}

	@Override
	public List<SysDict> queryDeleteList() {
		return baseMapper.queryDeleteList();
	}
}
