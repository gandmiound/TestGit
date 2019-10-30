package cc.mrbird.febs.common.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "qiniu")
public class ConstantQiniu {

    private String accessKey;

    private String secretKey;

    private String bucket;

    private String path;
}