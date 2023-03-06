package com.ydf.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ydf.common.domain.SysMenu;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigInteger;
import java.util.List;

@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {
    List<String> selectPermsByUserId(BigInteger userId);
}
