package com.uz83.trademark.service;

import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

/*
 * @author panyh
 * @date 2018/8/17 20:47
 */
public interface IBaseService<Model, PK extends Serializable, E> {

    /**
     * 根据条件查询数量
     *
     * @param example
     * @return
     */
    long countByExample(E example);

    /**
     * 根据条件删除数据
     *
     * @param example
     * @return
     */
    int deleteByExample(E example);

    /**
     * 根据主键删除数据
     *
     * @param id
     * @return
     */
    int deleteByPrimaryKey(PK id);

    /**
     * 增加数据（整个实体）
     *
     * @param record
     * @return
     */
    int insert(Model record);

    /**
     * 增加数据（选择性，即增加有数据的属性）
     *
     * @param record
     * @return
     */
    int insertSelective(Model record);

    /**
     * 根据条件查询数据
     *
     * @param example
     * @return
     */
    List<Model> selectByExample(E example);

    /**
     * 根据主键查询数据
     *
     * @param id
     * @return
     */
    Model selectByPrimaryKey(PK id);

    /**
     * 根据条件更新数据（选择性，即更新有数据的属性）
     *
     * @param record
     * @param example
     * @return
     */
    int updateByExampleSelective(@Param("record") Model record, @Param("example") E example);

    /**
     * 根据条件更新数据（整个实体）
     *
     * @param record
     * @param example
     * @return
     */
    int updateByExample(@Param("record") Model record, @Param("example") E example);

    /**
     * 根据主键更新数据（选择性，即更新有数据的属性）
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Model record);

    /**
     * 根据主键更新数据（整个实体）
     *
     * @param record
     * @return
     */
    int updateByPrimaryKey(Model record);

}
