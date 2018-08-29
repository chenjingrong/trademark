package com.uz83.trademark.security.auth;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.uz83.trademark.util.DesignUtil;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;

/*
 * @author panyh
 * @date 2018/8/16 22:03
 * 最核心的地方，就是提供某个资源对应的权限定义，即getAttributes方法返回的结果。 此类在初始化时，应该取到所有资源及其对应角色的定义。
 */
@Service
public class CustomInvocationSecurityMetadataSourceService implements FilterInvocationSecurityMetadataSource {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private JSONArray resource = null;

    private PathMatcher pathMatcher = new AntPathMatcher();

    @PostConstruct // 被@PostConstruct修饰的方法会在服务器加载Servle的时候运行，并且只会被服务器执行一次。PostConstruct在构造函数之后执行,init()方法之前执行。
    public void loadResourceDefine() {   // 一定要加上@PostConstruct注解
        // 加载权限数据
        resource = loadFile("classpath:config/auth.json");
        DesignUtil.authArray = resource;
        // 加载菜单数据
        DesignUtil.menuArray = loadFile("classpath:config/menu.json");

        // 加载权限映射
        if (resource == null) {
            throw new RuntimeException("未加载到任何权限：config/auth.json");
        }
    }

    /**
     * 加载文件数据
     *
     * @param path
     * @return
     */
    private JSONArray loadFile(String path) {
        FileReader fr = null;
        BufferedReader reader = null;
        JSONArray array = null;
        try {
            File file = ResourceUtils.getFile(path);
            if (file != null && file.exists() && file.isFile()) {
                fr = new FileReader(file);
                reader = new BufferedReader(fr);
                StringBuffer buf = new StringBuffer();
                String str = null;
                while ((str = reader.readLine()) != null) {
                    buf.append(str);
                }
                if (logger.isDebugEnabled()) {
                    logger.debug("=====" + path + "====\r\n" + buf);
                }
                array = JSON.parseArray(buf.toString());
            }
        } catch (Exception e) {
            logger.error("{}", e);
        } finally {
            IOUtils.closeQuietly(fr);
            IOUtils.closeQuietly(reader);
        }
        return array;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return new ArrayList<ConfigAttribute>();
    }

    // 根据URL，找到相关的权限配置。
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        if (logger.isDebugEnabled()) {
            logger.debug(object.toString());
        }

        // object 是一个URL，被用户请求的url。
        FilterInvocation filterInvocation = (FilterInvocation) object;
        if (resource == null) {
            loadResourceDefine();
        }

        String url = filterInvocation.getHttpRequest().getRequestURI();
        String auths = null;
        for (int i = 0; i < resource.size(); i++) {
            JSONObject model = resource.getJSONObject(i);
            // 匹配模块的权限路径
            if (pathMatcher.match(model.getString("url"), url)) {
                // 取模块的访问权限
                if (model.containsKey("auth")) {
                    auths = model.getString("auth");
                }

                // 如果模块匹配了，再查询具体的请求
                if (model.containsKey("request")) {
                    JSONArray requests = model.getJSONArray("request");
                    for (int j = 0; j < requests.size(); j++) {
                        JSONObject request = requests.getJSONObject(j);
                        String pattern = StringUtils.replaceAll(model.getString("url"), "[*]", "") + request.getString("url").substring(1);
                        if (pathMatcher.match(pattern, url)) {
                            // 如果具体的请求也匹配了，就取此URL的访问权限
                            auths = request.getString("auth");
                            // 找到第一个就返回
                            break;
                        }
                    }
                }
            }
            if (auths != null) {
                // 找到第一个就返回
                break;
            }
        }

        Collection<ConfigAttribute> attrs = null;
        if (auths != null) {
            attrs = new ArrayList<ConfigAttribute>();
            String[] temp = auths.split("\\s*[,;]\\s*");
            for (String auth : temp) {
                attrs.add(new SecurityConfig(auth));
            }
        }

        return attrs;
    }

    @Override
    public boolean supports(Class<?> arg0) {
        return true;
    }

}
