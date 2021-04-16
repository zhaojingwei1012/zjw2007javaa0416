package com.zjw.service;

import com.zjw.entity.MenuBean;
import com.zjw.entity.PostBean;
import com.zjw.utils.Page;

import java.util.List;

/**
 * @version 1.0
 * @author： 赵静薇
 * @date： 2021-04-08 19:33
 */
public interface PostService {
    Page<PostBean> getPostListConn(PostBean postBean, Integer pageNum, Integer pageSize);

    List<MenuBean> getMenuListById(Long postid);

    void savePostMenu(Long postid, Long[] ids);
}
