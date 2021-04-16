package com.zjw.mapper;

import com.zjw.entity.MenuBean;
import com.zjw.entity.MenuBeanExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuMapper {
    long countByExample(MenuBeanExample example);

    int deleteByExample(MenuBeanExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MenuBean record);

    int insertSelective(MenuBean record);

    List<MenuBean> selectByExample(MenuBeanExample example);

    MenuBean selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MenuBean record, @Param("example") MenuBeanExample example);

    int updateByExample(@Param("record") MenuBean record, @Param("example") MenuBeanExample example);

    int updateByPrimaryKeySelective(MenuBean record);

    int updateByPrimaryKey(MenuBean record);
}