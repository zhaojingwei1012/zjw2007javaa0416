package com.zjw.controller;

import com.zjw.entity.MenuBean;
import com.zjw.entity.UserBean;
import com.zjw.service.UserService;
import com.zjw.utils.Page;
import com.zjw.utils.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @version 1.0
 * @author： 赵静薇
 * @date： 2021-04-07 11:29
 */
@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("getLogin")
    public ResultInfo getLogin(@RequestBody UserBean ub, HttpServletRequest request){
        UserBean userBean = userService.getLogin(ub);
        if(userBean == null){
            return new ResultInfo(false, "登录失败，用户名或密码有误");
        }else{
            request.getSession().setAttribute("userBean", userBean);
            return new ResultInfo(true, "登录成功");
        }
    }

    @RequestMapping("getMeunList")
    public List<MenuBean> getMeunList(){
        return userService.getMeunList();
    }

    //这个就是我们要把urls放进set里面然后再放进session里面。。

    @RequestMapping("getUserList")
    public List<UserBean> getUserList(){
        return userService.getUserList();
    }

    /**
     * 给用户选择科室
     */
    @RequestMapping("getUserVoById")
    public UserBean getUserVoById(Long id){
        return userService.getUserVoById(id);
    }

    @RequestMapping("saveUserDept")
    public ResultInfo saveUserDept(Long id, @RequestBody Long[] deptids){
        try{
            userService.saveUserDept(id, deptids);
            return new ResultInfo(true, "编辑成功");
        }catch(Exception e){
            return new ResultInfo(false, "编辑失败");
        }
    }

    /**
     * 给用户分配职位
     */
    @RequestMapping("getUserInfo")
    public UserBean getUserInfo(@RequestParam("id") Long userid){
        return userService.getUserInfo(userid);
    }

    @RequestMapping("saveUserPost")
    public ResultInfo saveUserPost(@RequestBody UserBean userBean){
        try{
            userService.saveUserPost(userBean);
            return new ResultInfo(true, "编辑成功");
        }catch(Exception e){
            return new ResultInfo(false, "编辑失败");
        }
    }

    @RequestMapping("getUserListConn")
    public Page<UserBean> getUserListConn(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "3") Integer pageSize){
        UserBean userBean = new UserBean();
        userBean.setUname("a");
        userBean.setAge(15);
        return userService.getUserListConn(userBean, pageNum, pageSize);
    }
}
