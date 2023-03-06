package com.ydf.queue.domain;

import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 阿里云对象存储oss
 */
@Component
@Data
@ToString
public class Oss {

    @Value("${oss.endpoint}")
    public String  endpoint;

    @Value("${oss.accessKeyId}")
    private String accessKeyId;

    @Value("${oss.accessKeySecret}")
    public String  secretAccessKey;

    @Value("${oss.bucketName}")
    public String  bucketName;

    @Value("${oss.fileDir}")
    private String fileDir;

}