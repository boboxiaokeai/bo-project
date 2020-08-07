package cn.bo.project.shiro;


import cn.bo.project.common.constant.CacheConstant;
import cn.bo.project.common.core.LoginUser;
import cn.bo.project.common.core.api.CommonServiceApi;
import cn.bo.project.common.utils.HttpContextUtil;
import cn.bo.project.common.utils.IPUtils;
import cn.bo.project.common.utils.RedisUtil;
import cn.bo.project.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author zhangbo
 * @Date 2020/8/7 10:19
 * @Description
 * @PackageName cn.bo.project.shiro
 **/
@Slf4j
@Component
public class ShiroRealm extends AuthorizingRealm {

    @Lazy
    @Autowired
    private CommonServiceApi serviceApi;
    @Lazy
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 必须重写此方法，不然shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) { return token instanceof JwtToken; }

    /**
     * 触发检测用户权限时调用此方法
     * @param principalCollection 身份信息
     * @return simpleAuthorizationInfo 权限信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        if (principalCollection!=null){
            LoginUser currentUser = (LoginUser) principalCollection.getPrimaryPrincipal();
        }
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        // 设置用户拥有的角色集合
        Set<String> roleSet = new HashSet<>();
        roleSet.add("admin");
        simpleAuthorizationInfo.addRoles(roleSet);
        // 设置用户的权限集合
        Set<String> permissionSet = new HashSet<>();
        permissionSet.add("system:user:list");
        simpleAuthorizationInfo.addStringPermissions(permissionSet);
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String token = (String) authenticationToken.getCredentials();
        if (token == null) {
            log.info("————————身份认证失败——————————IP地址:  "+ IPUtils.getIpAddr(HttpContextUtil.getHttpServletRequest()));
            throw new AuthenticationException("token为空!");
        }
        LoginUser currentUser = this.AuthenticationUser(token);
        return new SimpleAuthenticationInfo(currentUser, token, currentUser.getUserName());
    }

    private LoginUser AuthenticationUser(String token) {
        String username = JwtUtil.getUsername(token);
        if (username == null) {
            throw new AuthenticationException("token无效!");
        }
        LoginUser loginUser = serviceApi.getUserByName(username);
        if (loginUser == null) {
            throw new AuthenticationException("用户不存在!");
        }
        if (!jwtTokenRefresh(token, username, loginUser.getPassword())) {
            throw new AuthenticationException("token失效，请重新登录!");
        }
        return loginUser;
    }

    /**
     * JWTToken刷新生命周期 （实现： 用户在线操作不掉线功能）
     * 1、登录成功后将用户的JWT生成的Token作为k、v存储到cache缓存里面(这时候k、v值一样)，缓存有效期设置为Jwt有效时间的2倍
     * 2、当该用户再次请求时，通过JWTFilter层层校验之后会进入到doGetAuthenticationInfo进行身份验证
     * 3、当该用户这次请求jwt生成的token值已经超时，但该token对应cache中的k还是存在，则表示该用户一直在操作只是JWT的token失效了，程序会给token对应的k映射的v值重新生成JWTToken并覆盖v值，该缓存生命周期重新计算
     * 4、当该用户这次请求jwt在生成的token值已经超时，并在cache中不存在对应的k，则表示该用户账户空闲超时，返回用户信息已失效，请重新登录。
     * 注意： 前端请求Header中设置Authorization保持不变，校验有效性以缓存中的token为准。
     *       用户过期时间 = Jwt有效时间 * 2。
     *
     * @param userName
     * @param passWord
     * @return
     */
    public boolean jwtTokenRefresh(String token, String userName, String passWord) {
        String cacheToken = String.valueOf(redisUtil.get(CacheConstant.USER_TOKEN + token));
        if (StringUtils.isNotEmpty(cacheToken)) {
            // 校验token有效性
            if (!JwtUtil.verify(cacheToken, userName, passWord)) {
                String newAuthorization = JwtUtil.sign(userName, passWord);
                // 设置超时时间
                redisUtil.set(CacheConstant.USER_TOKEN + token, newAuthorization);
                redisUtil.expire(CacheConstant.USER_TOKEN + token, JwtUtil.EXPIRE_TIME *2 / 1000);
            }
            return true;
        }
        return false;
    }

    /**
     * 清除当前用户的权限认证缓存
     * @param principals
     */
    @Override
    public void clearCache(PrincipalCollection principals) { super.clearCache(principals); }

}
