package cn.bo.project.admin.modules.system.controller;

import cn.bo.project.admin.modules.system.entity.SysPost;
import cn.bo.project.admin.modules.system.service.ISysPostService;
import cn.bo.project.base.constant.UserConstants;
import cn.bo.project.base.response.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author zhangbo
 * @Date 2020/3/16 15:26
 * @Description 岗位控制器
 * @PackageName cn.bo.project.admin.modules.system.controller
 **/
@Slf4j
@Api(tags="岗位API")
@RestController
@RequestMapping("/sys/post")
public class SysPostController {
    @Autowired
    private ISysPostService sysPostService;

    @ApiOperation("岗位列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseData queryPageList(SysPost sysPost, @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                      @RequestParam(name="pageSize", defaultValue="10") Integer pageSize, HttpServletRequest req){
        List<SysPost> pageList = sysPostService.selectPostList(sysPost);
        return ResponseData.success(pageList);
    }

    @ApiOperation("岗位详情")
    @GetMapping(value = "/{postId}")
    public ResponseData getInfo(@PathVariable Long postId){
        return ResponseData.success(sysPostService.selectPostById(postId));
    }

    @ApiOperation("岗位新增")
    @PostMapping
    public ResponseData add(@Validated @RequestBody SysPost post){
        if (UserConstants.NOT_UNIQUE.equals(sysPostService.checkPostNameUnique(post))) {
            return ResponseData.error("新增岗位'" + post.getPostName() + "'失败，岗位名称已存在");
        }
        else if (UserConstants.NOT_UNIQUE.equals(sysPostService.checkPostCodeUnique(post))){
            return ResponseData.error("新增岗位'" + post.getPostName() + "'失败，岗位编码已存在");
        }
        post.setCreateBy("admin");
        return ResponseData.success(sysPostService.insertPost(post));
    }

    @ApiOperation("岗位编辑")
    @PutMapping
    public ResponseData edit(@Validated @RequestBody SysPost post){
        if (UserConstants.NOT_UNIQUE.equals(sysPostService.checkPostNameUnique(post))) {
            return ResponseData.error("修改岗位'" + post.getPostName() + "'失败，岗位名称已存在");
        }
        else if (UserConstants.NOT_UNIQUE.equals(sysPostService.checkPostCodeUnique(post))) {
            return ResponseData.error("修改岗位'" + post.getPostName() + "'失败，岗位编码已存在");
        }
        post.setCreateBy("admin");
        return ResponseData.success(sysPostService.updatePost(post));
    }

    @ApiOperation("岗位删除")
    @DeleteMapping("/{postIds}")
    public ResponseData remove(@PathVariable Long[] postIds){
        return ResponseData.success(sysPostService.deletePostByIds(postIds));
    }

    @ApiOperation("获取岗位选择框列表")
    @GetMapping("/select")
    public ResponseData select(){
        List<SysPost> posts = sysPostService.selectPostAll();
        return ResponseData.success(posts);
    }
}
