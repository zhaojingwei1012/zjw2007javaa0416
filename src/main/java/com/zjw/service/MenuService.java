package com.zjw.service;

import com.zjw.entity.MenuBean;

import java.util.List;

/**
 * @version 1.0
 * @author： 赵静薇
 * @date： 2021-04-08 21:22
 */
public interface MenuService {
    List<MenuBean> getMenuListByPid(Long pid);

    void saveMenu(MenuBean menuBean);

    void deleteMenuById(Long id);
}
