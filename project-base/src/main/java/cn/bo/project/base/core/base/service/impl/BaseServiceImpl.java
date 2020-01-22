package cn.bo.project.base.core.base.service.impl;

import cn.bo.project.base.core.base.entity.BaseEntity;
import cn.bo.project.base.core.base.service.BaseService;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;


/**
 * @Author zhangbo
 * @Date 2020/1/3 9:21
 * @Description ServiceImpl基类
 * @PackageName cn.bo.project.base.core.base.entity
 **/
@Slf4j
public class BaseServiceImpl<M extends BaseMapper<T>, T extends BaseEntity> extends ServiceImpl<M, T> implements BaseService<T> {

}
