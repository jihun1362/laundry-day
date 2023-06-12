package com.meta.laundry_day.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.meta.laundry_day.common.message.ErrorCode;
import com.meta.laundry_day.common.message.ResultCode;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDto<T> {
    private final String msg;
    private final int statusCode;
    private final T data;

    public ResponseDto(ErrorCode errorCode, T data) {
        this.msg = errorCode.getMsg();
        this.statusCode = errorCode.getStatusCode();
        this.data = data;
    }

    public ResponseDto(ResultCode resultCode, T data) {
        this.msg = resultCode.getMsg();
        this.statusCode = resultCode.getStatusCode();
        this.data = data;
    }
}
