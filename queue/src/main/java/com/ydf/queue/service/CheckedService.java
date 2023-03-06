package com.ydf.queue.service;

import com.ydf.common.domain.Checked;
import com.ydf.common.model.Result;

import java.util.List;

/**
 * @author ywb
 */
public interface CheckedService {

    /**
     * 添加受检人员
     * @param checked 受检人员
     * @return
     */
    public Result insertChecked(Checked checked);

    /**
     * 通过受检人员id查看受检人员详情
     * @param id
     * @return
     */
    public Result queryCheckedById(Integer id);

    /**
     * 通过用户id查看该账户的受检人列表
     * @param userId
     * @return
     */
    public Result queryCheck(Integer userId);

    /**
     * 生成排队二维码并上传到阿里云
     * @param checked
     * @return
     */
    public Result checkedQueue(Checked checked);

    /**
     * urltoRedis
     * @return
     */
    public Result urlRedis(Integer checkId,String url);

}
