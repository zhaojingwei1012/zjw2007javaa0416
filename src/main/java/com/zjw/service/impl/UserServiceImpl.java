package com.zjw.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zjw.entity.*;
import com.zjw.mapper.DeptMapper;
import com.zjw.mapper.MenuMapper;
import com.zjw.mapper.UserMapper;
import com.zjw.service.UserService;
import com.zjw.utils.MD5key;
import com.zjw.utils.Page;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version 1.0
 * @author： 赵静薇
 * @date： 2021-04-07 11:29
 */
@Service

public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private DeptMapper deptMapper;

    @Override
    public List<UserBean> getUserList() {
        return userMapper.selectByExample(null);
    }

    @Override
    public Page<UserBean> getUserListConn(UserBean userBean, Integer pageNum, Integer pageSize) {
        //开启分页
        PageHelper.startPage(pageNum, pageSize);

        UserBeanExample example = new UserBeanExample();
        UserBeanExample.Criteria criteria = example.createCriteria();
        if(userBean != null){
            //按照用户名模糊查询
            if(userBean.getUname() != null && userBean.getUname().length() >= 1){
                criteria.andUnameLike("%" + userBean.getUname() + "%");
            }
            //按照年龄范围查询
            if(userBean.getAge() != null) {
                criteria.andAgeGreaterThanOrEqualTo(userBean.getAge());
            }
            if(userBean.getEage() != null){
                criteria.andAgeLessThanOrEqualTo(userBean.getEage());
            }
        }
        List<UserBean> list = userMapper.selectByExample(example);
        PageInfo<UserBean> pageInfo = new PageInfo<>(list);
        Long total = pageInfo.getTotal();
        Page page = new Page(pageInfo.getPageNum() + "", total.intValue(),pageInfo.getPageSize() + "");
        page.setList(list);
        return page;
    }

    @Override
    public List<MenuBean> getMeunList() {
        List<MenuBean> list = menuMapper.selectByExample(null);
        return list;
    }

    @Override
    public UserBean getUserVoById(Long id) {
        UserBean userBean = userMapper.selectByPrimaryKey(id);
        /**
         * 去查询这个用户有哪些部门，咱们的设计是一对多，所以页面待会儿要不用复选框接收，
         * 要不用下拉框接收，但是下拉框要支持多选
         */
        Long[] deptids = userMapper.getUserDeptidsById(id);
        userBean.setDeptids(deptids);
        List<DeptBean> dlist = deptMapper.selectByExample(null);
        userBean.setDlist(dlist);
        return userBean;
    }

    @Override
    public void saveUserDept(Long id, Long[] deptids) {
        /**
         * 修改科室
         * 要把该用户原来的科室删掉，再次新增
         * 还需要把原来的职位也删除掉(科室都变了，职位也就没了)
         */
        userMapper.deleteUserPostById(id);
        userMapper.deleteUserDeptById(id);
        if(deptids != null && deptids.length >= 1){
            for (Long deptid : deptids) {
                userMapper.insertUserDept(id, deptid);
            }
        }
    }

    @Override
    public UserBean getUserInfo(Long userid) {
        UserBean userBean = userMapper.selectByPrimaryKey(userid);
        /**
         * 开始去查询这个用户有没有科室
         */
        List<DeptBean> dlist = userMapper.getUserDeptById(userid);
        /**
         * 开始循环科室，查询科室里面的职位
         */
        if(dlist != null && dlist.size() >= 1){
            for (DeptBean deptBean : dlist) {
                List<PostBean> plist = deptMapper.getDeptPostList(deptBean.getId());
                Long[] postids = deptMapper.getUserPostByIdAndDeptid(userid, deptBean.getId());
                deptBean.setPostids(postids);
                deptBean.setPlist(plist);
            }
        }
        userBean.setDlist(dlist);
        return userBean;
    }

    @Override
    public void saveUserPost(UserBean userBean) {
        /**
         * 先去删除用户的职位
         */
        if(userBean != null){
            userMapper.deleteUserPostById(userBean.getId());
            if(userBean.getDlist() != null && userBean.getDlist().size() >= 1){
                for (DeptBean deptBean : userBean.getDlist()) {
                    if(deptBean.getPostids() != null && deptBean.getPostids().length >= 1){
                        for (Long postid : deptBean.getPostids()) {
                            userMapper.saveUserPost(userBean.getId(), postid);
                        }
                    }
                }
            }
        }
    }

    @Override
    public UserBean getLogin(UserBean ub) {
        //先用用户名或者手机号，都是用用户名接收的，有可能用户输入的手机号，都是string类型，无所谓
        if(ub != null){
            List<UserBean> list = userMapper.getLogin(ub);
            if(list != null && list.size() == 1){
                //到了这里表示用用户名或者手机号查询到了
                //开始比对密码，比对密码之前需要加盐加密
                //加密算法有很多，常用的md5，bscript等
                UserBean userBean = list.get(0);
                //页面用户输入的密码，进行加密加盐后和数据库里面的密码进行比较，相等正确，不相等就错误
                String pwd = ub.getPwd();

                //也就是这里的加盐加密规则和注册的要一致，就好了，都是一个人负责的
                pwd = userBean.getPwdsalt() + pwd + userBean.getPwdsalt();
                MD5key md5key = new MD5key();
                String newpwd = md5key.getkeyBeanofStr(pwd);

                //相等就返回，其它都是空
                if(newpwd.equals(userBean.getPwd())){
                    return userBean;
                }
            }
        }
        return null;
    }

    @Test
    public void test(){
        String pwd = "123456";
        pwd = "1234" + pwd + "1234";
        MD5key md5key = new MD5key();
        String newpwd = md5key.getkeyBeanofStr(pwd);
        System.out.println(newpwd);

    }

    public List<MenuBean> getMeunList2() {
        Long[] ids = {1L, 2L, 3L};
        List<MenuBean> list = menuMapper.selectByExample(null);
        for (Long id : ids) {
            for (MenuBean menuBean : list) {
                if(id.equals(menuBean.getId())){
                    menuBean.setChecked(true);
                    break;
                }
            }
        }
        return list;
    }
}
