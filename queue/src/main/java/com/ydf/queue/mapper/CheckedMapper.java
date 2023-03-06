package com.ydf.queue.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ydf.common.domain.Checked;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CheckedMapper extends BaseMapper<Checked> {

    /**
     * 新增受检人员
     * @param checked 受检人
     * @return
     */
    public Integer insertChecked(Checked checked);

    /**
     * 通过受检人员id查看受检人员详情
     * @param id 受检人id
     * @return
     */
    public Checked queryCheckedById(Integer id);

    /**
     * 通过用户id查看该账户的受检人列表
     * @param userId
     * @return
     */
    public List<Checked> queryCheck(Integer userId);

}
