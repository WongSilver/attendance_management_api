package edu.wong.attendance_management_api.util;

import lombok.Data;

@Data
public class ResponseFormatUtil {
    private int code;
    private String msg;
    private Object data;

    public static ResponseFormatUtil successful(Object data) {
        return operate(200, "操作成功", data);
    }

    public static ResponseFormatUtil fail(String msg, Object data) {
        return operate(400, msg, data);
    }

    public static ResponseFormatUtil operate(int code, String msg, Object data) {
        ResponseFormatUtil util = new ResponseFormatUtil();
        util.setCode(code);
        util.setMsg(msg);
        util.setData(data);
        return util;
    }

}
