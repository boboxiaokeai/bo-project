package cn.bo.project.system.controller;

import cn.bo.project.common.constant.CacheConstant;
import cn.bo.project.common.constant.CommonConstant;
import cn.bo.project.common.core.LoginUser;
import cn.bo.project.common.core.api.CommonServiceApi;
import cn.bo.project.common.response.ResponseData;
import cn.bo.project.common.utils.PasswordUtil;
import cn.bo.project.common.utils.RedisUtil;
import cn.bo.project.common.utils.VerifyCodeUtils;
import cn.bo.project.shiro.JwtUtil;
import cn.bo.project.system.entity.SysMenu;
import cn.bo.project.system.entity.SysUser;
import cn.bo.project.system.model.RouterModel;
import cn.bo.project.system.model.param.LoginParam;
import cn.bo.project.system.service.SysMenuService;
import cn.bo.project.system.service.SysPermissionService;
import cn.bo.project.system.service.SysUserService;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.map.HashedMap;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author zhangbo
 * @Date 2020/8/7 13:30
 * @Description
 * @PackageName cn.bo.project.system.controller
 **/
@Api(tags = "登录Api")
@RestController
@RequestMapping("/sys")
public class SysLoginController {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private SysMenuService menuService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private CommonServiceApi commonServiceApi;
    @Autowired
    private SysPermissionService permissionService;

    @ApiOperation("登录接口")
    @PostMapping("/login")
    public ResponseData login(@RequestBody LoginParam loginParam){
        // 校验图片验证码
        String code = String.valueOf(redisUtil.get(CacheConstant.CAPTCHA_CODE_KEY+loginParam.getCodekey()));
        redisUtil.del(CacheConstant.CAPTCHA_CODE_KEY+loginParam.getCodekey());
        if (code==null){
            return ResponseData.error("验证码过期");
        }
        if (!code.equals(loginParam.getCode())){
            return ResponseData.error("验证码错误");
        }
        // 校验账户密码
        SysUser sysUser = sysUserService.selectUserByUserName(loginParam.getUserName());
        sysUserService.checkUserIsEffective(sysUser);
        String password = PasswordUtil.encrypt(loginParam.getUserName(), loginParam.getPassWord(), sysUser.getSalt());
        if (!sysUser.getPassword().equals(password)) {
            return ResponseData.error("用户名或密码错误");
        }
        commonServiceApi.addLog("用户: " + loginParam.getUserName() + ",登录成功！", 1, null);
        return userToken(sysUser);
    }

    private ResponseData userToken(SysUser sysUser) {
        // 生成token
        String token = JwtUtil.sign(sysUser.getUserName(), sysUser.getPassword());
        // 设置token缓存有效时间
        redisUtil.set(CacheConstant.USER_TOKEN + token, token);
        redisUtil.expire(CacheConstant.USER_TOKEN + token, JwtUtil.EXPIRE_TIME*2 / 1000);
        JSONObject object = new JSONObject();
        object.put("token", token);
        object.put("userInfo", sysUser);
        return ResponseData.success(object);
    }

    @ApiOperation("获取图片验证码")
    @GetMapping("/captchaImage")
    private ResponseData getCodeImage() throws IOException {
        Map<String,String> map = new HashMap(3);
        // 生成随机字串
        String verifyCode = RandomUtil.randomString(4);
        // 唯一标识
        String codekey = IdUtil.simpleUUID();
        redisUtil.set(CacheConstant.CAPTCHA_CODE_KEY+codekey,verifyCode,120);
        try {
            int w = 111, h = 36;
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            VerifyCodeUtils.outputImage(w, h, stream, verifyCode);
            map.put("codekey", codekey);
            map.put("img", Base64.encode(stream.toByteArray()));
            map.put("code", verifyCode);
            return ResponseData.success(map);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseData.error("获取验证码失败");
        }
    }

    @ApiOperation("获取用户信息")
    @GetMapping("/getUserInfo")
    public ResponseData getUserInfo() {
        LoginUser currentUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        SysUser sysUser = sysUserService.selectUserByUserName(currentUser.getUserName());
        Map<String, Set<String>> map = new HashedMap();
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(sysUser);
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(sysUser);
        map.put("roles", roles);
        map.put("permissions", permissions);
        return ResponseData.success(map);
    }

    @ApiOperation("获取路由信息")
    @GetMapping("/getRouters")
    public ResponseData getRouters() {
        LoginUser currentUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        // 路由信息
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(currentUser.getId());
        List<RouterModel> RouterModel = menuService.buildMenus(menus);
        JSONObject obj = new JSONObject();
        obj.put("menu", RouterModel);
        return ResponseData.success(obj);
    }

}
