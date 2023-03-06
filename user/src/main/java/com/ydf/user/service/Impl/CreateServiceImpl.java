package com.ydf.user.service.Impl;

import com.ydf.common.domain.SysUser;
import com.ydf.common.model.Result;
import com.ydf.common.util.ResultUtils;
import com.ydf.user.domain.LoginSysUser;
import com.ydf.user.domain.SysUserVo;
import com.ydf.user.mapper.CreateMapper;
import com.ydf.user.service.CreateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.UUID;

@Transactional
@Service
public class CreateServiceImpl implements CreateService {

    @Autowired
    private CreateMapper createMapper;

    @Override
    public Result createUser(Integer number,LoginSysUser loginSysUser) {

        LinkedList<SysUserVo> list = new LinkedList<>();
        LinkedList<SysUser> sysUsers = new LinkedList<>();
        LinkedList<BigInteger> ids = new LinkedList<>();

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        for (int i = 0; i < number; i++) {
            SysUser sysUser = new SysUser();
            SysUserVo sysUserVo = new SysUserVo();

            String account = UUID.randomUUID().toString().replaceAll("-", "");
            String password = UUID.randomUUID().toString().replaceAll("-", "");

            sysUserVo.setAccount(account);
            sysUserVo.setPassword(password);
            list.add(sysUserVo);

            sysUser.setAccount(account);
            sysUser.setPassword(encoder.encode(password));
            sysUsers.add(sysUser);
        }
        Integer i = createMapper.createSysUser(sysUsers);
        if (i == 0) {
            return ResultUtils.failureWithStatus(500, "生成账号密码失败");
        }

        for (SysUser sysUser : sysUsers) {
            ids.add(sysUser.getId());
        }
        if (loginSysUser.getPermissions().contains("createDoctor")) {
            Integer j = createMapper.insertDoctor(ids);
            if (j == 0) {
                return ResultUtils.failureWithStatus(500, "生成医护人员失败");
            }
            return ResultUtils.successWithData(200, "生成医护人员成功", list);
        }
        if (loginSysUser.getPermissions().contains("createVolunteer")) {
            Integer j = createMapper.insertVolunteer(ids);
            if (j == 0) {
                return ResultUtils.failureWithStatus(500, "生成志愿者失败");
            }
            return ResultUtils.successWithData(200, "生成志愿者成功", list);
        }
        return ResultUtils.failureWithStatus(500,"权限不足");
    }


}
