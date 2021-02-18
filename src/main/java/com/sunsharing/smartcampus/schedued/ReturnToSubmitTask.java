/*
 * @(#) ReturnToSubmitTask
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-11-17 17:15:54
 */

package com.sunsharing.smartcampus.schedued;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sunsharing.component.utils.base.DateUtils;
import com.sunsharing.smartcampus.constant.enums.BizResultEnum;
import com.sunsharing.smartcampus.model.pojo.apply.TzhxyBaseInfo;
import com.sunsharing.smartcampus.model.vo.apply.SubmitParamVo;
import com.sunsharing.smartcampus.service.apply.TzhxyBaseInfoService;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class ReturnToSubmitTask {

    @Autowired
    TzhxyBaseInfoService tzhxyBaseInfoService;

    static String msg = "提交已回退申报数据定时任务：{}";

    static final String mack = "0";

    @Scheduled(cron = "${share.sys.cron}")
    public void restDeclareSubmit() {
        //获取系统设置的截止提交时间与处理标识
        Map<String, Object> sysMap = tzhxyBaseInfoService.querySystemConfigBackEndTime();
        String backMack = MapUtils.getString(sysMap, "backMack", "1");
        String endTime = MapUtils.getString(sysMap, "endTime", "");
        if (StringUtils.isBlank(endTime)) {
            log.info(msg, "未设置截止时间，不执行提交功能");
            return;
        }
        //判断设置的实际格式是否为12位
        if (endTime.length() != 12) {
            log.info(msg, "设置的时间格式错误，精确到分，格式为yyyyMMddHHmm");
        }
        //获取系统当前时间
        String nowTime = DateUtils.transFormat(new Date(), "yyyyMMddHHmm");
        //判断系统时间与当前时间的大小
        Long endTimeL = Long.valueOf(endTime);
        Long nowTimeL = Long.valueOf(nowTime);
        if (endTimeL <= nowTimeL && StringUtils.equals(mack, backMack)) {//执行任务
            LambdaQueryWrapper<TzhxyBaseInfo> queryWrapper = new LambdaQueryWrapper();
            queryWrapper.eq(TzhxyBaseInfo::getDel, "0")
                .in(TzhxyBaseInfo::getState, new String[]{
                    BizResultEnum.专家退回补充材料.getValue(),
                    BizResultEnum.市教育局终审退回补充.getValue(),
                    BizResultEnum.受理中心复审退回补充.getValue(),
                    BizResultEnum.受理中心退回补充.getValue(),
                    BizResultEnum.区教育局退回补充材料.getValue()
                });
            List<TzhxyBaseInfo> list = this.tzhxyBaseInfoService.list(queryWrapper);
            List<String> finalErrList = new ArrayList<>();
            list.forEach(item -> {
                SubmitParamVo paramVo = new SubmitParamVo();
                paramVo.setDeclareId(item.getDeclareId());
                paramVo.setSfshttj(true);//回退提交标识
                try {
                    tzhxyBaseInfoService.submitDeclare(paramVo);
                } catch (RuntimeException e) {
                    log.info(msg, e);
                    finalErrList.add(item.getDeclareId());
                }
            });
            //处理标识
            if (finalErrList.size() > 0) {//全部处理
                log.info(msg, "有部分申报提交异常" + finalErrList.toString() + "");
            }
            tzhxyBaseInfoService.updateSysConfigMack();
        } else {
            log.info(msg, "本次已执行过提交任务，如有未提交成功的，请联系管理员处理");
        }
    }

}
