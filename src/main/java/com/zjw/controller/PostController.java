package com.zjw.controller;

import com.zjw.entity.MenuBean;
import com.zjw.entity.PostBean;
import com.zjw.service.PostService;
import com.zjw.utils.Page;
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
 * @date： 2021-04-08 19:32
 */

/**
 * 职位的相关信息
 */
@RestController
@RequestMapping("post")
public class PostController {

    @Resource
    private PostService postService;

    //列表展示 + 分页 + 搜索
    @RequestMapping("getPostListConn")
    public Page<PostBean> getPostListConn(@RequestBody PostBean postBean,
                                          @RequestParam(defaultValue = "1") Integer pageNum,
                                          @RequestParam(defaultValue = "3") Integer pageSize){
        return postService.getPostListConn(postBean, pageNum, pageSize);
    }

    @RequestMapping("getMenuListById")
    public List<MenuBean> getMenuListById(@RequestParam("id") Long postid){
        return postService.getMenuListById(postid);
    }

    @RequestMapping("savePostMenu")
    public ResultInfo savePostMenu(Long postid, @RequestBody Long[] ids){
        try{
            postService.savePostMenu(postid, ids);
            return new ResultInfo(true, "保存成功");
        }catch(Exception e){
            return new ResultInfo(false, "保存失败");
        }
    }

}
