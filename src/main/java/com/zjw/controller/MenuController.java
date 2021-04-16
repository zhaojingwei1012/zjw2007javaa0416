package com.zjw.controller;

import com.zjw.entity.MenuBean;
import com.zjw.service.MenuService;
import com.zjw.utils.ResultInfo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @version 1.0
 * @author： 赵静薇
 * @date： 2021-04-08 21:22
 */

/**
 * 菜单的相关信息
 */
@RestController
@RequestMapping("menu")
public class MenuController {

    @Resource
    private MenuService menuService;

    //列表展示
    @RequestMapping("getMenuListByPid")
    public List<MenuBean> getMenuListByPid(@RequestParam(defaultValue = "1") Long pid){
        return menuService.getMenuListByPid(pid);
    }

    @RequestMapping("saveMenu")
    public ResultInfo vsaveMen(@RequestBody MenuBean menuBean){
        try{
            menuService.saveMenu(menuBean);
            return new ResultInfo(true, "编辑成功");
        }catch(Exception e){
            return new ResultInfo(false, "编辑失败");
        }
    }

    @RequestMapping("deleteMenuById")
    public ResultInfo deleteMenuById(Long id){
        try{
            menuService.deleteMenuById(id);
            return new ResultInfo(true, "删除成功");
        }catch(Exception e){
            return new ResultInfo(false, "删除失败");
        }
    }

}
