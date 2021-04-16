package com.zjw.entityvo;

import com.zjw.entity.DeptBean;
import com.zjw.entity.UserBean;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @version 1.0
 * @author： 赵静薇
 * @date： 2021-04-13 16:31
 */
@Data
public class UserVo extends UserBean implements Serializable {
    private Long[] deptids;
    private List<DeptBean> dlist;
}
