package com.zjw.controller;

import com.zjw.entity.DeptBean;
import com.zjw.service.DeptService;
import com.zjw.utils.Page;
import com.zjw.utils.ResultInfo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @version 1.0
 * @author： 赵静薇
 * @date： 2021-04-08 18:30
 */

/**
 * 科室的相关信息
 */
@RestController
@RequestMapping("dept")
public class DeptController {

    @Resource
    private DeptService deptService;

    //列表展示 + 分页 + 搜索
    @RequestMapping("getDeptListConn")
    public Page<DeptBean> getDeptListConn(@RequestBody DeptBean deptBean,
                                          @RequestParam(defaultValue = "1") Integer pageNum,
                                          @RequestParam(defaultValue = "3") Integer pageSize){
        return deptService.getDeptListConn(deptBean, pageNum, pageSize);
    }

    @RequestMapping("getDeptByDeptid")
    public DeptBean getDeptByDeptid(@RequestParam("id") Long deptid){
        return deptService.getDeptByDeptid(deptid);
    }

    /**
     * 保存科室分配职位
     */
    @RequestMapping("saveDeptPost")
    public ResultInfo saveDeptPost(Long deptid, @RequestBody Long[] postids){
        try{
            deptService.saveDeptPost(deptid, postids);
            return new ResultInfo(true, "编辑成功");
        }catch(Exception e){
            return new ResultInfo(false, "编辑失败");
        }
    }

}
