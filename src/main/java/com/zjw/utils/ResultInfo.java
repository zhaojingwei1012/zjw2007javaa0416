package com.zjw.utils;

import lombok.Data;

import java.io.Serializable;

/**
 * @version 1.0
 * @author： 赵静薇
 * @date： 2021-04-09 18:34
 */
@Data
public class ResultInfo implements Serializable {

    private boolean flag;
    private String msg;

    public ResultInfo(boolean flag, String msg) {
        this.flag = flag;
        this.msg = msg;
    }

    public ResultInfo() {
    }
}
