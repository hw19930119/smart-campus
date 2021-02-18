package com.sunsharing.smartcampus.constant.constants;

/**
 * 程序中使用的常量
 *
 * @author wangqh
 */
public class Constants {
    public static final String PROJECT_NAME = "smart-campus";
    //cookie
    public static final String iEduCookieName = "my.com.trs.idm.coSessionId";
    //cache
    public static final String iEduCacheKeyPrefix = PROJECT_NAME+":"+"iEduCacheKeyPrefix";
    public static final String iEduIdNumCacheKeyPrefix = PROJECT_NAME+":"+"iEduIdNumCacheKeyPrefix";
    public static final String iEduCacheKeyToIdNum = PROJECT_NAME+":"+"iEduCacheKeyToIdNum";


    public static final String iEduCacheKeyAccessToken = PROJECT_NAME+":"+"iEduCacheKeyAccessToken";

    //选中16进制码，方块带勾
    public static final int[] XZ_CODE = {0x00FE};
    //未选中16进制码，方块不带勾
    public static final int[] WXZ_CODE = {0x00A8};
    public static final String XZ_SYMBOL = new String(XZ_CODE,0,1);
    public static final String WXZ_SYMBOL = new String(WXZ_CODE,0,1);
}
