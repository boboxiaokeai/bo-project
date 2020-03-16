package cn.bo.project.admin.modules.system.controller;

import cn.bo.project.admin.modules.system.entity.SysPost;
import cn.bo.project.admin.modules.system.entity.SysUser;
import cn.bo.project.admin.modules.system.service.ISysPostService;
import cn.bo.project.base.api.ResultBean;
import cn.bo.project.base.core.query.QueryGenerator;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author zhangbo
 * @Date 2020/3/16 15:26
 * @Description 岗位控制器
 **/
@Slf4j
@Api("岗位API")
@RestController
@RequestMapping("/sys/post")
public class SysPostController {
    @Autowired
    private ISysPostService sysPostService;

    @ApiOperation("岗位列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResultBean<List<SysPost>> queryPageList(SysPost sysPost, @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                                    @RequestParam(name="pageSize", defaultValue="10") Integer pageSize, HttpServletRequest req){
        ResultBean<List<SysPost>> ResultBean = new ResultBean<List<SysPost>>();
       /* QueryWrapper<SysPost> queryWrapper = QueryGenerator.initQueryWrapper(sysPost, req.getParameterMap());
        Page<SysPost> page = new Page<SysPost>(pageNo, pageSize);*/
        List<SysPost> pageList = sysPostService.selectPostList(sysPost);
        ResultBean.setSuccess(true);
        ResultBean.setResult(pageList);
        return ResultBean;
    }
}
