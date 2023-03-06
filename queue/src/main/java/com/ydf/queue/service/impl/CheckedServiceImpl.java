package com.ydf.queue.service.impl;

import cn.hutool.json.JSONUtil;
import com.ydf.common.domain.Checked;
import com.ydf.common.model.Result;
import com.ydf.common.util.ResultUtils;
import com.ydf.queue.domain.Oss;
import com.ydf.queue.mapper.CheckedMapper;
import com.ydf.queue.service.CheckedService;
import com.ydf.queue.utils.BarCodeUtils;
import com.ydf.queue.utils.RedisUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CheckedServiceImpl implements CheckedService {

    @Resource
    private CheckedMapper checkedMapper;

    /**
     * 使用阿里云对象存储Oss
     */
    @Resource
    Oss oss;

    /**
     * Redis工具类
     */
    @Resource
    RedisUtil redisUtil;

    /**
     * 插入检测点
     * @param checked 受检人员
     * @return Result
     */
    @Override
    public Result insertChecked(Checked checked) {
        System.out.println(checked);
        int insert = checkedMapper.insertChecked(checked);
        System.out.println("insert---->"+insert);
        if (insert == 1){
            return ResultUtils.successWithStatus(200,"插入成功");
        }else{
            return ResultUtils.failureWithStatus(500,"插入失败");
        }
    }

    /**
     * 根据id查询检测点
     * @param id
     * @return
     */
    @Override
    public Result queryCheckedById(Integer id) {
        Checked checked = checkedMapper.queryCheckedById(id);
        if (checked != null){
            return ResultUtils.successWithData(200,"查找成功",checked);
        }else {
            return ResultUtils.successWithData(500,"查找失败",null);
        }

    }

    @Override
    public Result queryCheck(Integer userId) {
        List<Checked> checkedList = checkedMapper.queryCheck(userId);
        if (checkedList != null){
            return ResultUtils.successWithData(200,"查找成功",checkedList);
        }else {
            return ResultUtils.successWithData(500,"查找失败",null);
        }
    }

    @Override
    public Result checkedQueue(Checked checked) {
        String fileName = "checkedId-"+checked.getId()+"time"+ System.currentTimeMillis();
        String checkedJson = JSONUtil.toJsonStr(checked);
        String url = BarCodeUtils.uploadFileToOss(BarCodeUtils.generateBarcodeWithoutWhite(
                checkedJson, null), oss, fileName);
        if (url != null){
            return ResultUtils.successWithData(200,"上传成功",url);
        }else {
            return ResultUtils.successWithData(500,"上传失败",url);
        }


    }

    /**
     * 将url存到Redis中
     * @param checkId 检测点id
     * @param url
     * @return
     */
    @Override
    public Result urlRedis(Integer checkId, String url) {
        redisUtil.set("QRCode-checkedId-"+checkId,url);
        redisUtil.expire("QRCode-checkedId-"+checkId,1000*30*60);
        return ResultUtils.successWithStatus(200);
    }

}
