package com.ydf.queue;

import cn.hutool.core.date.DateUtil;
import com.ydf.queue.domain.Oss;

import javax.annotation.Resource;
import java.util.Date;

public class Test {

    @Resource
    Oss oss;

    @org.junit.jupiter.api.Test
    public void main() {
        System.out.println(new Date().getTime());
    }
}
