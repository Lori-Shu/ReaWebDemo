package com.ggl.entity;

import lombok.Data;

@Data
public class CommonResult<T> {
    private int code;
    private T data;
    private CommonResult(){

    }
    public static<T> CommonResult<T> success(T data){
        CommonResult<T> res = new CommonResult<T>();
        res.setCode(200);
        res.setData(data);
        return res;
    }
    
    public static <T> CommonResult<T> error(T data) {
        CommonResult<T> res = new CommonResult<T>();
        res.setCode(400);
        res.setData(data);
        return res;
    }
}
