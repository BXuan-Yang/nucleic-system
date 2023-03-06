package com.ydf.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ydf.common.domain.SysUser;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigInteger;
import java.util.LinkedList;

@Mapper
public interface CreateMapper extends BaseMapper<SysUser> {

    Integer createSysUser(LinkedList<SysUser> list);

    Integer insertDoctor(LinkedList<BigInteger> ids);

    Integer insertVolunteer(LinkedList<BigInteger> ids);
}
