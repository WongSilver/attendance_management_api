package edu.wong.attendance_management_api.common.lang;

import lombok.Data;

/**
 * 作用对返回结果进行统一封装，
 * 成功 200，只返回数据
 * 不成功 400，没有数据
 * 其他自定义
 */
@Data
public class ResponseFormat {
    private int code;
    private String msg;
    private Object data;

    //    操作成功调用
    public static ResponseFormat successful(Object data) {
        return operate(200, "操作成功", data);
    }

    //    操作失败调用
    public static ResponseFormat fail(String msg) {
        return operate(400, msg, null);
    }

    //    其他操作调用
    public static ResponseFormat operate(int code, String msg, Object data) {
        ResponseFormat util = new ResponseFormat();
        util.setCode(code);
        util.setMsg(msg);
        util.setData(data);
        return util;
    }

}
