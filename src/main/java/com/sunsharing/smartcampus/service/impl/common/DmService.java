/*
 * @(#) DmService
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-07-21 17:24:59
 */

/**
 * 基础 - 代码管理
 * dmService - 1439
 */

package com.sunsharing.smartcampus.service.impl.common;

import com.alibaba.fastjson.JSONObject;
import com.sunsharing.eos.client.rpc.DynamicRpc;
import com.sunsharing.eos.common.ServiceRequest;

import java.util.List;
import java.util.Map;

public class DmService {

    private static final String appId = "auth";
    private static final String serviceId = "dmService";
    public static JSONObject getDefaultReturnJSON(){
        JSONObject result=new JSONObject();
        result.put("status",true);
        result.put("msg","成功");
        return result;
    }
    /**
     * @return
     *  ${success}   如果找到返回名称，没有找到返回Key值
     *     正确
     */
    public static String getLabelByKey(String dmName, String key) {
        ServiceRequest.Builder builder = new ServiceRequest.Builder(
            appId, serviceId, "getLabelByKey", "1.0");
        builder.setParameter("dmName", dmName);
        builder.setParameter("key", key);
        return DynamicRpc.invoke(builder.build(), String.class);
    }

    /**
     * @return
     *  ${success}
     *     [{"code":"01","label":"名族"}]
     */
    public static List getAllData(String dmName) {
        ServiceRequest.Builder builder = new ServiceRequest.Builder(
            appId, serviceId, "getAllData", "1.0");
        builder.setParameter("dmName", dmName);
        return DynamicRpc.invoke(builder.build(), List.class);
    }

    /**
     * @return
     *  ${success}
     *     {"01":"汉族","02":"满族"}
     */
    public static Map getMultiLabels(String dmName, String[] keys) {
        ServiceRequest.Builder builder = new ServiceRequest.Builder(
            appId, serviceId, "getMultiLabels", "1.0");
        builder.setParameter("dmName", dmName);
        builder.setParameter("keys", keys);
        return DynamicRpc.invoke(builder.build(), Map.class);
    }

    /**
     * @return
     *  ${success}
     *     [{"N":"DM_GLLX","C":"关联类型"}]
     */
    public static List getAllTables() {
        ServiceRequest.Builder builder = new ServiceRequest.Builder(
            appId, serviceId, "getAllTables", "1.0");
        return DynamicRpc.invoke(builder.build(), List.class);
    }

    /**
     * @return
     *  ${success}   如果找到返回对应的 Code,找不到则返回 null
     *
     */
    public static String getCodeByLabel(String dmName, String label) {
        ServiceRequest.Builder builder = new ServiceRequest.Builder(
            appId, serviceId, "getCodeByLabel", "1.0");
        builder.setParameter("dmName", dmName);
        builder.setParameter("label", label);
        return DynamicRpc.invoke(builder.build(), String.class);
    }

    /**
     * @return
     *  ${success}
     *     {"01":"汉族","02":"满族"}
     */
    public static Map getAllData2Map(String dmName) {
        ServiceRequest.Builder builder = new ServiceRequest.Builder(
            appId, serviceId, "getAllData2Map", "1.0");
        builder.setParameter("dmName", dmName);
        return DynamicRpc.invoke(builder.build(), Map.class);
    }

    /**
     * @return
     *  ${success}
     *     {"01":"汉族","02":"满族"}
     */
    public static Map getMultiLabelByKeyList(String dmName, List keys) {
        ServiceRequest.Builder builder = new ServiceRequest.Builder(
            appId, serviceId, "getMultiLabelByKeyList", "1.0");
        builder.setParameter("dmName", dmName);
        builder.setParameter("keys", keys);
        return DynamicRpc.invoke(builder.build(), Map.class);
    }

    /**
     * @return
     *  ${success}
     *     {"MZ":{"01":"汉族"},"XB":{"01":"男"}}
     */
    public static Map getMultiLabelByKeyListMap(List<Map<String, Object>> keys, String keyColumn) {
        ServiceRequest.Builder builder = new ServiceRequest.Builder(
            appId, serviceId, "getMultiLabelByKeyListMap", "1.0");
        builder.setParameter("keys", keys);
        builder.setParameter("keyColumn", keyColumn);
        return DynamicRpc.invoke(builder.build(), Map.class);
    }

    /**
     * @return
     *  ${void}  null
     *
     */
    public static void refreshBm(String dmName) {
        ServiceRequest.Builder builder = new ServiceRequest.Builder(
            appId, serviceId, "refreshBM", "1.0");
        builder.setParameter("dmName", dmName);
        DynamicRpc.invoke(builder.build(), Object.class);
    }

    /**
     * @return
     *  ${void}  null
     *
     */
    public static Object loadData() {
        ServiceRequest.Builder builder = new ServiceRequest.Builder(
            appId, serviceId, "loadData", "1.0");
        return DynamicRpc.invoke(builder.build(), Object.class);
    }


}
