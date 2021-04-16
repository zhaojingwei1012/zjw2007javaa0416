package com.zjw.mapper;

import com.zjw.entity.DeptBean;
import com.zjw.entity.UserBean;
import com.zjw.entity.UserBeanExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    long countByExample(UserBeanExample example);

    int deleteByExample(UserBeanExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UserBean record);

    int insertSelective(UserBean record);

    List<UserBean> selectByExample(UserBeanExample example);

    UserBean selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UserBean record, @Param("example") UserBeanExample example);

    int updateByExample(@Param("record") UserBean record, @Param("example") UserBeanExample example);

    int updateByPrimaryKeySelective(UserBean record);

    int updateByPrimaryKey(UserBean record);

    Long[] getUserDeptidsById(@Param("userid") Long id);

    void deleteUserPostById(@Param("userid") Long id);

    void deleteUserDeptById(@Param("userid") Long id);

    void insertUserDept(@Param("userid") Long id, @Param("deptid") Long deptid);

    List<DeptBean> getUserDeptById(@Param("userid") Long userid);

    void saveUserPost(@Param("userid") Long id, @Param("postid") Long postid);

    List<UserBean> getLogin(UserBean userBean);
}