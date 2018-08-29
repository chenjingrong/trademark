package com.uz83.trademark.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

/*
 * @author panyh
 * @date 2018/8/18 9:31
 * 系统设计器工具类，支持权限设计，菜单设计
 */
public class DesignUtil {
    /**
     * 权限集
     */
    public static JSONArray authArray = null;

    /**
     * 菜单集
     */
    public static JSONArray menuArray = null;

    /**
     * 保存所有权限值
     */
    private static List<String> allRoleAliass = null;

    /**
     * 获取所有定义的权限
     */
    public static List<String> getAllRoleAliass() {
        if (allRoleAliass == null) {
            allRoleAliass = new ArrayList<String>();
            if (authArray != null && authArray.size() > 0) {
                for (int i = 0; i < authArray.size(); i++) {
                    JSONObject model = authArray.getJSONObject(i);
                    if (model.containsKey("roles")) {
                        allRoleAliass.addAll(model.getJSONArray("roles").toJavaList(String.class));
                    }
                }
            }
        }
        return allRoleAliass;
    }


}
