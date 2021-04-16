package com.zjw.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zjw.entity.DeptBean;
import com.zjw.entity.DeptBeanExample;
import com.zjw.entity.PostBean;
import com.zjw.mapper.DeptMapper;
import com.zjw.mapper.PostMapper;
import com.zjw.service.DeptService;
import com.zjw.utils.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @version 1.0
 * @author： 赵静薇
 * @date： 2021-04-08 18:50
 */
@Service
public class DeptServiceImpl implements DeptService {

    @Resource
    private DeptMapper deptMapper;
    @Resource
    private PostMapper postMapper;

    @Override
    public Page<DeptBean> getDeptListConn(DeptBean deptBean, Integer pageNum, Integer pageSize) {
        //没有默认值的话，这里容易出现空指针
        PageHelper.startPage(pageNum, pageSize);

        //条件查询
        DeptBeanExample example = new DeptBeanExample();
        DeptBeanExample.Criteria criteria = example.createCriteria();
        //前台是否输入条件
        if(deptBean != null){
            if(deptBean.getDeptname() != null && deptBean.getDeptname().length() >= 1){
                criteria.andDeptnameLike("%" + deptBean.getDeptname() + "%");
            }
        }
        List<DeptBean> list = deptMapper.selectByExample(example);
        //分页，PageHelper的分页
        PageInfo<DeptBean> pageInfo = new PageInfo<>(list);
        //总记录数查询出来，类型转换一下，分页助手的long类型，我们要转成Integer类型
        Long total = pageInfo.getTotal();
        //使用自己的分页工具类，我们接收的值类型不一样，接收的是String类型，转化一下
        Page<DeptBean> page = new Page(pageInfo.getPageNum() + "", total.intValue(), pageInfo.getPageSize() + "");
        page.setList(list);
        return page;
    }

    @Override
    public DeptBean getDeptByDeptid(Long deptid) {
        DeptBean deptBean = deptMapper.selectByPrimaryKey(deptid);
        /**
         * 查询这个部门原来的职位（需要在中间表）
         * 还要查询职位列表（直接使用职位的mapper去查询就OK啦）
         */
        List<PostBean> plist = postMapper.selectByExample(null);
        deptBean.setPlist(plist);
        Long[] postids = deptMapper.getDeptPostIds(deptid);
        deptBean.setPostids(postids);
        return deptBean;
    }

    @Override
    public void saveDeptPost(Long deptid, Long[] postids) {
        /**
         * 先删除后新增
         */
        deptMapper.deleteDeptPost(deptid);
        if(postids !=  null && postids.length >= 1){
            for (Long postid : postids) {
                deptMapper.saveDeptPost(deptid, postid);
            }
        }
    }

}
