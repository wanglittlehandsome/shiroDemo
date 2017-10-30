package com.shiro.example.shirodemo.config;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Service;

@Service
public class ShiroRealm extends AuthorizingRealm {

    private static final String[] users={"wangyao","123","456"};
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        SimpleAuthenticationInfo simpleAuthenticationInfo = null;
        //UsernamePasswordToken对象用来存放提交的登录信息
        UsernamePasswordToken token=(UsernamePasswordToken) authenticationToken;
        System.out.print("name:"+token.getUsername()+">>pwd:"+token.getPassword()+">>host:"+token.getHost());
        boolean success=false;
        for (int i=0;i<users.length;i++){
            if(users[i].equals(token.getUsername())){
                success=true;
                break;
            }
        }
        if(success){
            simpleAuthenticationInfo = new SimpleAuthenticationInfo(token.getUsername(), // 用户账号
                    token.getUsername(), // 密码
                    getName()// realm name
            );
        }else{
            return null;
        }
        return simpleAuthenticationInfo;
    }
}
