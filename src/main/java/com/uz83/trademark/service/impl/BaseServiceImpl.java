package com.uz83.trademark.service.impl;

import com.uz83.trademark.mapper.MyBatisBaseDao;
import com.uz83.trademark.model.SysUser;
import com.uz83.trademark.service.IBaseService;
import com.uz83.trademark.util.UserUtil;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/*
 * @author panyh
 * @date 2018/8/17 20:47
 * 基础Service抽象类
 */
public abstract class BaseServiceImpl<Model, PK extends Serializable, E> implements IBaseService<Model, PK, E> {
    private Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);

    public abstract MyBatisBaseDao getBaseDao();

    @Override
    public long countByExample(E example) {
        return getBaseDao().countByExample(example);
    }

    @Override
    public int deleteByExample(E example) {
        return getBaseDao().deleteByExample(example);
    }

    @Override
    public int deleteByPrimaryKey(PK id) {
        return getBaseDao().deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Model record) {
        setCreator(record);
        return getBaseDao().insert(record);
    }

    @Override
    public int insertSelective(Model record) {
        setCreator(record);
        return getBaseDao().insertSelective(record);
    }

    @Override
    public List<Model> selectByExample(E example) {
        return getBaseDao().selectByExample(example);
    }

    @Override
    public Model selectByPrimaryKey(PK id) {
        return (Model) getBaseDao().selectByPrimaryKey(id);
    }

    @Override
    public int updateByExampleSelective(Model record, E example) {
        setAlter(record);
        return getBaseDao().updateByExampleSelective(record, example);
    }

    @Override
    public int updateByExample(Model record, E example) {
        setAlter(record);
        return getBaseDao().updateByExample(record, example);
    }

    @Override
    public int updateByPrimaryKeySelective(Model record) {
        setAlter(record);
        return getBaseDao().updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Model record) {
        setAlter(record);
        return getBaseDao().updateByPrimaryKey(record);
    }

    private void setCreator(Model record) {
        try {
            SysUser sysUser = UserUtil.getCurrentUser();
            if (sysUser != null) {
                // 判断是否有创建时间，有就自动赋值
                if (PropertyUtils.isWriteable(record, "createTime")) {
                    PropertyUtils.setProperty(record, "createTime", new Date());
                }
                // 判断是否有创建者ID，有就自动赋值
                if (PropertyUtils.isWriteable(record, "creatorId")) {
                    PropertyUtils.setProperty(record, "creatorId", sysUser.getId());
                }
            }
        } catch (Exception e) {
            logger.error("{}", e);
        }
    }

    private void setAlter(Model record) {
        try {
            SysUser sysUser = UserUtil.getCurrentUser();
            if (sysUser != null) {
                // 判断是否有创建时间，有就自动赋值
                if (PropertyUtils.isWriteable(record, "alterTime")) {
                    PropertyUtils.setProperty(record, "alterTime", new Date());
                }
                // 判断是否有创建者ID，有就自动赋值
                if (PropertyUtils.isWriteable(record, "alterorId")) {
                    PropertyUtils.setProperty(record, "alterorId", sysUser.getId());
                }
            }
        } catch (Exception e) {
            logger.error("{}", e);
        }
    }

}
