/*
 * @(#) FiledTypeEnum
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-07-21 11:41:51
 */

package com.sunsharing.smartcampus.constant.enums;

import com.sunsharing.share.common.base.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Created by wuxw on 2020/03/18
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum JcssDmEnum implements IEnum {

    WL_JRFS("WLJR","wlJrfs"),
    WL_FGFW("WLFW","wlFgfw"),
    WL_ZX("WLZX","wlZx"),
    XT_MHGL("MHGL","xtMhgl"),
    XT_SJGL("SJGL","xtSjgl"),
    XT_KFYJR("KFYJR","xtKfyjr"),
    RJ_JYGLXT("JYGLXT","rjJyglxt"),
    RJ_JYYYJGLXT("JYYYGLXT","rjJyyyjglxt"),
    RJ_XXGLXT("XXGLXT","rjXxglxt"),
    RJ_SSCZGLXT("SSCZGLXT","rjSsczglxt"),
    RJ_JYPT("JYPT","rjJypt"),
    RJ_JXHTPT("JXHTPT","rjJxhtpt"),
    ZY_SZTS("SZTS","zySzts"),
    ZY_XXKC("XXKC","zyXxkc"),
    ZY_XMKC("XMKC","zyXmkc"),
    ZY_GXHXXZY("GXHXXZY","zyGxhxxzy");



    private String value;
    private String name;


}
