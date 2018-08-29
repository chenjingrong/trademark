package com.uz83.trademark.service.impl;

import com.uz83.trademark.mapper.MyBatisBaseDao;
import com.uz83.trademark.mapper.SysUserDAO;
import com.uz83.trademark.model.SysUser;
import com.uz83.trademark.model.SysUserExample;
import com.uz83.trademark.service.ISysUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/*
 * @author panyh
 * @date 2018/8/16 13:59
 */
@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUser, Integer, SysUserExample> implements ISysUserService {
    @Resource
    private SysUserDAO userDAO;

    @Override
    public MyBatisBaseDao getBaseDao() {
        return userDAO;
    }

    @Override
    public SysUser getByUsername(String username) throws Exception {
        SysUserExample example = new SysUserExample();
        example.createCriteria().andUsernameEqualTo(username); //  根据用户名查询数据
        List<SysUser> list = userDAO.selectByExample(example);
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }


}
