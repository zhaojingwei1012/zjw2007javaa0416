package com.zjw.service.impl;

import com.zjw.entity.MenuBean;
import com.zjw.entity.MenuBeanExample;
import com.zjw.mapper.MenuMapper;
import com.zjw.service.MenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * @author： 赵静薇
 * @date： 2021-04-08 21:23
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Resource
    private MenuMapper menuMapper;

    @Override
    public List<MenuBean> getMenuListByPid(Long pid) {
        MenuBeanExample example = new MenuBeanExample();
        MenuBeanExample.Criteria criteria = example.createCriteria();
        criteria.andPidEqualTo(pid);
        List<MenuBean> list = menuMapper.selectByExample(example);
        return list;
    }

    @Override
    public void saveMenu(MenuBean menuBean) {
        if(menuBean != null){
            if(menuBean.getId() != null){
                //修改
                /**
                 * updateByPrimaryKeySelective：这个修改是遇到实体类字段值为空的时候，不修改，一般密码等。
                 * updateByPrimaryKey：实体类传递过来是什么，把数据库中全部修改为实体类中的值，即便实体类中为空，也把数据库中修改为空。
                 */
                menuMapper.updateByPrimaryKeySelective(menuBean);
            }else{
                //添加
                menuMapper.insertSelective(menuBean);
            }
        }
    }

    List<Long> ids = new ArrayList<>();
    @Override
    public void deleteMenuById(Long id) {
        /**
         * 按照id等于pid去查询的方法，在service中已经有了，我们可以直接使用吗？
         * 当然使用了也没什么大问题，但是不符合面向对象的设计原则：
         * 单一职责原则(一个接口一个类一个方法只干一件事情)、开闭原则(对写好的方法拓展开修改闭，自己写好的尽量不要修改、不要乱用别人的)、
         * 接口隔离原则(接口与接口之间不能互相调用，可以写一个依赖于你的方法)、依赖倒置原则(controller调用service，service调用mapper)、
         * 里氏替换原则。
         */
        getMenuListByPidToDelete(id);

        //在这里拿到ids，然后去删除
        //System.out.println(ids);
        for (Long id1 : ids) {
            menuMapper.deleteByPrimaryKey(id1);
        }

    }
    private void getMenuListByPidToDelete(Long pid) {
        ids.add(pid);
        MenuBeanExample example = new MenuBeanExample();
        MenuBeanExample.Criteria criteria = example.createCriteria();
        criteria.andPidEqualTo(pid);
        List<MenuBean> list = menuMapper.selectByExample(example);
        /**
         * 和我们阶乘的思想有点不一样，阶乘是到了最后满足条件了结束
         * 不满足条件的时候，一直执行
         *
         * 只要满足条件就执行，不满足条件了结束。
         */
        if(list != null && list.size() >= 1){
            for (MenuBean menuBean : list) {
                getMenuListByPidToDelete(menuBean.getId());
            }
        }
    }

}
