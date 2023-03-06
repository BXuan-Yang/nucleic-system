package com.ydf.user.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ydf.user.domain.LoginSysUser;
import com.ydf.common.domain.SysUser;
import com.ydf.user.mapper.SysMenuMapper;
import com.ydf.user.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        //根据账号查询用户信息
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getAccount,account).eq(SysUser::getIsDeleted,0);
        SysUser sysUser = sysUserMapper.selectOne(wrapper);
        //如果查询不到数据就通过抛出异常来给出提示
        if(Objects.isNull(sysUser)){
            throw new RuntimeException("账号或密码错误");
        }
        //根据用户查询角色信息 添加到LoginUser中
        List<String> permissionKeyList =  sysMenuMapper.selectPermsByUserId(sysUser.getId());
        //封装成UserDetails对象返回
        return new LoginSysUser(sysUser,permissionKeyList);
    }
}
