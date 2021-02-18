/*
 * @(#) CommentConfigController
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-10-29 17:26:59
 */

package com.sunsharing.smartcampus.web.comment;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sunsharing.common.utils.StringUtils;
import com.sunsharing.share.common.collection.MapUtil;
import com.sunsharing.share.webex.annotation.ShareRest;
import com.sunsharing.smartcampus.model.pojo.comment.TzhxyDpConfig;
import com.sunsharing.smartcampus.service.comment.TzhxyDpConfigService;

import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import lombok.extern.log4j.Log4j2;

@RestController
@ShareRest
@RequestMapping("/comment/config")
@Log4j2
public class CommentConfigController {

    @Autowired
    TzhxyDpConfigService tzhxyDpConfigService;

    @GetMapping("/queryConfigTree")
    public Map<String, Object> quyerCofigInitData(@RequestParam("templateId") String templateId) {
        Map<String, Object> result = tzhxyDpConfigService.queryTreeForCategory(templateId);
        return result;
    }

    @PostMapping("/saveCommentConfig")
    public Map<String, Object> saveCommentConfig(@RequestBody Map<String, Object> reqMap) {
        String pcNo = MapUtils.getString(reqMap, "pcNo");
        String templateId = MapUtils.getString(reqMap, "templateId");
        String expertId = MapUtils.getString(reqMap, "expertId");
        String fieldIds = MapUtils.getString(reqMap, "fieldIds");
        if (StringUtils.isBlank(pcNo)
            || StringUtils.isBlank(templateId)
            || StringUtils.isBlank(expertId)) {
            return MapUtil.ofHashMap("result", false, "msg", "入参不能为空");
        }
        try {
            Map<String, Object> result = this.tzhxyDpConfigService.saveCommentConfig(pcNo, templateId, expertId, fieldIds);
            return result;
        } catch (RuntimeException e) {
            log.error("点评分配专家发生异常", e);
            return MapUtil.ofHashMap("result", false, "msg", "该专家已分配过指标不能重复分配");
        }
    }

    @GetMapping("/queryExpertField")
    public Map<String, Object> queryExpertField(@RequestParam("templateId") String templateId,
                                                @RequestParam("expertId") String expertId) {
        LambdaQueryWrapper<TzhxyDpConfig> dpCofigWrapper = new LambdaQueryWrapper<>();
        dpCofigWrapper.eq(TzhxyDpConfig::getDel, "0")
            .eq(TzhxyDpConfig::getTemplateId, templateId)
            .eq(TzhxyDpConfig::getExpertId, expertId);
        try {
            List<TzhxyDpConfig> repeatList = this.tzhxyDpConfigService.list(dpCofigWrapper);
            List<String> fieldList = Arrays.asList(repeatList.get(0).getFieldIds().split(","));
            return MapUtil.ofHashMap("result", true, "msg", "查询成功", "ids", fieldList);
        } catch (RuntimeException e) {
            return MapUtil.ofHashMap("result", false, "msg", "查询失败");
        }

    }
}
