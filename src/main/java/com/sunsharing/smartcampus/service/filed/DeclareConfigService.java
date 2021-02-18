/*
 * @(#) DeclareConfigService
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-07-22 19:17:54
 */

package com.sunsharing.smartcampus.service.filed;

import com.sunsharing.smartcampus.field.FormField;
import com.sunsharing.smartcampus.model.vo.audit.ApplyUserVo;
import com.sunsharing.smartcampus.model.vo.filed.CategoryConfigDto;
import com.sunsharing.smartcampus.model.vo.filed.FieldConfigDto;

import java.util.List;
import java.util.Map;

public interface DeclareConfigService {

    Map getTreePreviewByTemplateId(String templateId);
    Map getTreePreview(String templateId,String declareId,String piId,boolean isShenBao,String isExpertCommentPage);

    Map findBasicFieldList(String templateId,String g_id);

    List<FormField> generalFormFiledList(List<FieldConfigDto> fieldConfigDtoList);

}
