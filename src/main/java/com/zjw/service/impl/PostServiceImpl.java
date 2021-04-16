package com.zjw.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zjw.entity.MenuBean;
import com.zjw.entity.PostBean;
import com.zjw.entity.PostBeanExample;
import com.zjw.mapper.MenuMapper;
import com.zjw.mapper.PostMapper;
import com.zjw.service.PostService;
import com.zjw.utils.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @version 1.0
 * @author： 赵静薇
 * @date： 2021-04-08 19:33
 */
@Service
public class PostServiceImpl implements PostService {

    @Resource
    private PostMapper postMapper;
    @Resource
    private MenuMapper menuMapper;

    @Override
    public Page<PostBean> getPostListConn(PostBean postBean, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        PostBeanExample example = new PostBeanExample();
        PostBeanExample.Criteria criteria = example.createCriteria();
        if(postBean != null){
            if(postBean.getPostname() != null && postBean.getPostname().length() >= 1){
                criteria.andPostnameLike("%" + postBean.getPostname() + "%");
            }
        }
        List<PostBean> list = postMapper.selectByExample(example);
        PageInfo<PostBean> pageInfo = new PageInfo<>(list);
        Long total = pageInfo.getTotal();
        Page<PostBean> page = new Page<>(pageInfo.getPageNum() + "", total.intValue(), pageInfo.getPageSize() + "");
        page.setList(list);
        return page;
    }

    @Override
    public List<MenuBean> getMenuListById(Long postid) {
        //全查菜单
        List<MenuBean> menuBeans = menuMapper.selectByExample(null);
        //当前职位拥有哪些菜单，去中间表查询，只要id就好了
        List<Long> menuids = postMapper.getPostMenuids(postid);
        //要是原来有菜单的，需要回显，ztree回显，在后台直接回显就OK
        if(menuids != null && menuids.size() >= 1){
            for (Long menuid : menuids) {
                for (MenuBean menuBean : menuBeans) {
                    if(menuid.equals(menuBean.getId())){
                        menuBean.setChecked(true);
                        break;
                    }
                }
            }
        }
        return menuBeans;
    }

    @Override
    public void savePostMenu(Long postid, Long[] ids) {
        //先去删除 中间表的数据
        postMapper.deletePostMenuByPostid(postid);
        if(ids != null && ids.length >= 1){
            for (Long menuid : ids) {
                postMapper.savePosMenu(postid, menuid);
            }
        }
    }
}
