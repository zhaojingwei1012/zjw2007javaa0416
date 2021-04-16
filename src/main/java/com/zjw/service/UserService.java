package com.zjw.service;

import com.zjw.entity.MenuBean;
import com.zjw.entity.UserBean;
import com.zjw.utils.Page;

import java.util.List;

/**
 * @version 1.0
 * @author： 赵静薇
 * @date： 2021-04-07 11:29
 */
public interface UserService {
    List<UserBean> getUserList();

    Page<UserBean> getUserListConn(UserBean userBean, Integer pageNum, Integer pageSize);

    List<MenuBean> getMeunList();

    UserBean getUserVoById(Long id);

    void saveUserDept(Long id, Long[] deptids);

    UserBean getUserInfo(Long userid);

    void saveUserPost(UserBean userBean);

    UserBean getLogin(UserBean ub);
}
