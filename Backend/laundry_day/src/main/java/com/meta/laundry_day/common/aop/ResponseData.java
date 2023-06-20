package com.meta.laundry_day.common.aop;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseData {
    String timestamp;
    String hostname;
    String hostIp;
    String clientIp;
    String clientUrl;
    String callFunction;
    String type;
    String parameter;
}
