package com.zjw.service;

import com.zjw.entity.DeptBean;
import com.zjw.utils.Page;

/**
 * @version 1.0
 * @author： 赵静薇
 * @date： 2021-04-08 18:50
 */
public interface DeptService {
    Page<DeptBean> getDeptListConn(DeptBean deptBean, Integer pageNum, Integer pageSize);

    DeptBean getDeptByDeptid(Long deptid);

    void saveDeptPost(Long deptid, Long[] postids);
}
