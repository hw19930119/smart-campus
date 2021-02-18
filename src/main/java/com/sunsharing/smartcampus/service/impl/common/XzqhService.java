/*
 * @(#) XzqhService
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-07-22 16:51:56
 */

/**
 * 基础 - 行政区划获取服务
 * xzqhService - a2c612b1fc844326bccde78d8712fa4c
 */

package com.sunsharing.smartcampus.service.impl.common;

import com.sunsharing.eos.client.rpc.DynamicRpc;
import com.sunsharing.eos.common.ServiceRequest;

import java.util.List;
import java.util.Map;

public class XzqhService {

    private static final String APP_ID = "auth";
    private static final String SERVICE_ID = "xzqhService";

    /**
     * @return
     *  ${success}
     *     [{ "WGBH":"", "WGMC":"","X_2000":"","Y_2000":"","BZMID":"","BZID":"","X_MKT":"Y_MKT","SSJWH":""}]
     */
    public static List getDzXzqWg(String xzqh) {
        ServiceRequest.Builder builder = new ServiceRequest.Builder(
            APP_ID, SERVICE_ID, "getDzXzqWg", "1.0");
        builder.setParameter("xzqh", xzqh);
        return DynamicRpc.invoke(builder.build(), List.class);
    }

    /**
     * @return
     *  ${success}
     *     350206001000
     */
    public static String getParentXzqh(String xzqh) {
        ServiceRequest.Builder builder = new ServiceRequest.Builder(
            APP_ID, SERVICE_ID, "getParentXzqh", "1.0");
        builder.setParameter("xzqh", xzqh);
        return DynamicRpc.invoke(builder.build(), String.class);
    }

    public static Map getXzqhTree(String baseXzqh) {
        ServiceRequest.Builder builder = new ServiceRequest.Builder(
            APP_ID, SERVICE_ID, "getXzqhTree", "1.0");
        builder.setParameter("baseXzqh", baseXzqh);
        return DynamicRpc.invoke(builder.build(), Map.class);
    }

    /**
     * @return
     *  ${success}
     *     [{"wgmc": "网格名称","X_2000": "CGCS2000坐标X","Y_2000": "CGCS2000坐标Y","bzmId": "标注面ID","bzId": "标注ID","X": "系统使用坐标X",
     *     "Y": "系统使用坐标Y","mktX": "墨卡托坐标X","mktY": "墨卡托坐标Y","ssjwh": "所属居（村）委会"}]
     */
    public static Object getDzDywgInfoByBzmId() {
        ServiceRequest.Builder builder = new ServiceRequest.Builder(
            APP_ID, SERVICE_ID, "getDzDywgInfoByBzmId", "1.0");
        return DynamicRpc.invoke(builder.build(), Object.class);
    }

    /**
     * @return
     *  ${success}
     *     [{ "XZQH":"", "XZQMC":"","X_2000":"","Y_2000":"","BZMID":"","BZID":"","X_MKT":"Y_MKT"}]
     */
    public static List getDzXzq(String[] xzqhs) {
        ServiceRequest.Builder builder = new ServiceRequest.Builder(
            APP_ID, SERVICE_ID, "getDzXzq", "1.0");
        builder.setParameter("xzqhs", xzqhs);
        return DynamicRpc.invoke(builder.build(), List.class);
    }

    public static List getXzqhIgnoreLoginUser(String baseXzqh, String level) {
        ServiceRequest.Builder builder = new ServiceRequest.Builder(
            APP_ID, SERVICE_ID, "getXzqhIgnoreLoginUser", "1.0");
        builder.setParameter("baseXzqh", baseXzqh);
        builder.setParameter("level", level);
        return DynamicRpc.invoke(builder.build(), List.class);
    }

    public static List getXzqh(String baseXzqh, String level) {
        ServiceRequest.Builder builder = new ServiceRequest.Builder(
            APP_ID, SERVICE_ID, "getXzqh", "1.0");
        builder.setParameter("baseXzqh", baseXzqh);
        builder.setParameter("level", level);
        return DynamicRpc.invoke(builder.build(), List.class);
    }

    /**
     * @return
     *  ${success}
     *     {"parent":[{ "name":"福建省","xzqh":"350000000000","detail":"福建省","grade":1},
     *     { "name":"厦门市","xzqh":"350200000000","detail":"福建省厦门市","grade":2},
     *     { "name":"湖里区","xzqh":"350206000000","detail":"福建省厦门市湖里区","grade":3},
     *     { "name":"金山街道","xzqh":"350206005000","detail":"福建省厦门市湖里区金山街道","grade":4},
     *     { "name":"金山社区居委会","xzqh":"350206005021","detail":"福建省厦门市湖里区金山街道金山社区居委会","grade":5}],
     *     "fullName":"福建省厦门市湖里区金山街道金山社区居委会"}
     */
    public static Map getJsXzqhDetail(String xzqh) {
        ServiceRequest.Builder builder = new ServiceRequest.Builder(
            APP_ID, SERVICE_ID, "getJsXzqhDetail", "1.0");
        builder.setParameter("xzqh", xzqh);
        return DynamicRpc.invoke(builder.build(), Map.class);
    }

    /**
     * @return
     *  ${success}
     *     350206001
     */
    public static String formatXzqh(String xzqh) {
        ServiceRequest.Builder builder = new ServiceRequest.Builder(
            APP_ID, SERVICE_ID, "formatXzqh", "1.0");
        builder.setParameter("xzqh", xzqh);
        return DynamicRpc.invoke(builder.build(), String.class);
    }

    /**
     * @return
     *  ${success}
     *     {"id":"","pid":"根节点为0","name":"","type":"xzqh or dept"}
     */
    public static List getXzqhDeptTree(String baseXzqh) {
        ServiceRequest.Builder builder = new ServiceRequest.Builder(
            APP_ID, SERVICE_ID, "getXzqhDeptTree", "1.0");
        builder.setParameter("baseXzqh", baseXzqh);
        return DynamicRpc.invoke(builder.build(), List.class);
    }

    /**
     * @return
     *  ${success}   如果找到返回名称，没有找到返回xzqh值
     *     湖里社区居委会
     */
    public static String getXzqhName(String xzqh) {
        ServiceRequest.Builder builder = new ServiceRequest.Builder(
            APP_ID, SERVICE_ID, "getXzqhName", "1.0");
        builder.setParameter("xzqh", xzqh);
        return DynamicRpc.invoke(builder.build(), String.class);
    }

    /**
     * @return
     *  ${success}
     *     {"id":"","pid":"根节点为0","name":"","type":"xzqh or dept"}
     */
    public static List getDeptTree(String baseXzqh) {
        ServiceRequest.Builder builder = new ServiceRequest.Builder(
            APP_ID, SERVICE_ID, "getDeptTree", "1.0");
        builder.setParameter("baseXzqh", baseXzqh);
        return DynamicRpc.invoke(builder.build(), List.class);
    }

    /**
     * @return
     *  ${success}
     *     [{ "name":"名称","xzqh":"行政区划代码","detail":"全称","grade":1}]
     */
    public static List findJsXzqhChildren(String xzqh) {
        ServiceRequest.Builder builder = new ServiceRequest.Builder(
            APP_ID, SERVICE_ID, "findJsXzqhChildren", "1.0");
        builder.setParameter("xzqh", xzqh);
        return DynamicRpc.invoke(builder.build(), List.class);
    }


}
