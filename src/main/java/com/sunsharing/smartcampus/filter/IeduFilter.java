/*
 * @(#) SessionUserLoadFilter
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-03-21 14:40:14
 */

package com.sunsharing.smartcampus.filter;

import com.alibaba.fastjson.JSONObject;
import com.sunsharing.smartcampus.web.common.IeduUserController;

import org.apache.commons.lang3.CharSet;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class IeduFilter implements Filter {

    private List<String> excludePatterns = new ArrayList<>();

    protected static List<String> excludeRegexPatterns = new ArrayList();
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        excludePatterns = this.split(filterConfig.getInitParameter("excludePatterns"));
        excludeRegexPatterns = this.split(filterConfig.getInitParameter("excludeRegexPatterns"));
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("text/html;charset=utf-8");
        if(ignored(httpServletRequest)){
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        if (!IeduUserController.isNeedLoginSSO(httpServletRequest, httpServletResponse)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        if (isAjaxRequest(httpServletRequest)) {
            JSONObject resposeJson = new JSONObject();
            resposeJson.put("status", "1401");
            resposeJson.put("message", "PC端 i教育登录已失效，请退出后重新登录！");
            servletResponse.setContentType("application/json;charset=UTF-8");
            PrintWriter writer = servletResponse.getWriter();
            writer.write(resposeJson.toJSONString());
            writer.close();
            servletResponse.flushBuffer();
        } else {
            httpServletResponse.sendRedirect("https://www.xmedu.cn/door/index");
        }
    }

    @Override
    public void destroy() {
    }
    public static boolean isAjaxRequest(HttpServletRequest request) {
        String requestedWith = request.getHeader("x-requested-with");
        if (requestedWith != null && requestedWith.equalsIgnoreCase("XMLHttpRequest")) {
            return true;
        } else {
            return false;
        }
    }
    protected List<String> split(String patterns) {
        List<String> list = new ArrayList();
        if (!StringUtils.isBlank(patterns)) {
            String[] arr = patterns.replaceAll("\r\n", "").replaceAll("\n", "").replaceAll("\t", "").split("\\|");
            String[] var4 = arr;
            int var5 = arr.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                String s = var4[var6];
                if (!StringUtils.isBlank(s)) {
                    list.add(s.trim());
                }
            }
        }

        return list;
    }

    protected boolean ignored(HttpServletRequest request) {
        if ("true".equals(request.getHeader("Download-Resource"))) {
            return true;
        } else {
            String url = request.getRequestURI();
            if (url.indexOf(";") != -1) {
                url = url.substring(0, url.indexOf(";"));
            }

            Iterator var3 = excludePatterns.iterator();

            String tmp;
            do {
                if (!var3.hasNext()) {
                    var3 = excludeRegexPatterns.iterator();

                    do {
                        if (!var3.hasNext()) {
                            return false;
                        }

                        tmp = (String)var3.next();
                    } while(!url.matches(tmp));

                    return true;
                }

                tmp = (String)var3.next();
            } while(!url.endsWith(tmp));

            return true;
        }
    }
}
