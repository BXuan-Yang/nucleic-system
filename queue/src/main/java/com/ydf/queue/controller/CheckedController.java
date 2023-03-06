package com.ydf.queue.controller;

import com.ydf.common.domain.Checked;
import com.ydf.common.model.Result;
import com.ydf.common.util.ResultUtils;
import com.ydf.queue.domain.Oss;
import com.ydf.queue.service.CheckedService;
import com.ydf.queue.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author ywb
 * 控制器类
 */
@RestController
public class CheckedController {

    @Autowired
    private CheckedService checkedService;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 添加受检人
     * @param checked 受检人
     * @return Result
     */
    @PostMapping("/insertChecked")
    public Result insertChecked(@RequestBody Checked checked){
        return checkedService.insertChecked(checked);
    }

    /**
     * 排队功能，通过id查出受检人信息，生成二维码然后将二维码存储到阿里云oss对象中
     * @param checkedId 受检人id
     * @return Result
     */
    @GetMapping("/checkedQueue/{checkedId}")
    public Result checkedQueue(@PathVariable("checkedId") Integer checkedId){
        String url = (String) redisUtil.get("QRCode-checkedId-" + checkedId);

        if (url != null){
            return ResultUtils.successWithData(200,"从redis中取得",url);
        }

        Checked checked = null;
        Result result = checkedService.queryCheckedById(checkedId);

        if (result.getStatus() == 200){
            checked = (Checked) result.getData();
            Result ossUrlResult = checkedService.checkedQueue(checked);
            checkedService.urlRedis(checkedId, (String) ossUrlResult.getData());
            return ossUrlResult;
        }
        return null;
    }

    /**
     * 测试接口
     * @return
     */
    @GetMapping("/test")
    @ResponseBody
    public String test(){
        Result result = checkedService.queryCheckedById(1);
        System.out.println(result);
        return result.getData().toString();
    }

}
