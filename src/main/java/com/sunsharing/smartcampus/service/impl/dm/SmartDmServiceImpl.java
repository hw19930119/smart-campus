/*
 * @(#) SmartDmServiceImpl
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-07-27 10:38:12
 */

package com.sunsharing.smartcampus.service.impl.dm;

import com.sunsharing.component.utils.base.MapHelper;
import com.sunsharing.share.common.collection.MapUtil;
import com.sunsharing.smartcampus.constant.enums.AttestStateEnum;
import com.sunsharing.smartcampus.constant.enums.FiledRequiredEnum;
import com.sunsharing.smartcampus.constant.enums.PcStateEnum;
import com.sunsharing.smartcampus.constant.enums.SchoolTypeEnum;
import com.sunsharing.smartcampus.mapper.common.DmDataMapper;
import com.sunsharing.smartcampus.model.vo.common.DmData;
import com.sunsharing.smartcampus.model.vo.common.IeduUserVo;
import com.sunsharing.smartcampus.model.vo.common.SchoolVo;
import com.sunsharing.smartcampus.service.dm.SmartDmService;
import com.sunsharing.smartcampus.web.common.IeduUserController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("smartDmService")
public class SmartDmServiceImpl implements SmartDmService {

    @Autowired
    DmDataMapper dmDataMapper;

    @Override
    public List<Map<String, Object>> loadRequiredDm() {
        List<Map<String, Object>> list = new ArrayList<>();
        FiledRequiredEnum[] filedRequiredEnum = FiledRequiredEnum.values();
        for (int i = 0; i < filedRequiredEnum.length; i++) {
            FiledRequiredEnum var1 = filedRequiredEnum[i];
            list.add(MapUtil.ofHashMap("id", var1.getValue(), "label", var1.getLabel()));
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> loadPcStateDm() {
        List<Map<String, Object>> list = new ArrayList<>();
        PcStateEnum[] pcStateEnums = PcStateEnum.values();
        for (int i = 0; i < pcStateEnums.length; i++) {
            PcStateEnum var1 = pcStateEnums[i];
            list.add(MapUtil.ofHashMap("id", var1.getValue(), "label", var1.getLabel()));
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> loadSchoolTypeDm() {
        List<Map<String, Object>> list = new ArrayList<>();
        SchoolTypeEnum[] schoolTypeEnums = SchoolTypeEnum.values();
        for (int i = 0; i < schoolTypeEnums.length; i++) {
            SchoolTypeEnum var1 = schoolTypeEnums[i];
            list.add(MapUtil.ofHashMap("id", var1.getValue(), "label", var1.getLabel()));
        }
        return list;
    }


    @Override
    public List<Map<String, Object>> loadSmartDm(String dmName) {
        List<DmData> list = dmDataMapper.queryStartDm(dmName);
        List rst = new ArrayList();
        list.forEach(item -> {
            rst.add(MapHelper.ofHashMap("id", item.getCode(), "label", item.getName()));
        });
        return rst;
    }

    @Override
    public List<Map<String, Object>> loadJcssZhSelectDm(String mark) {
        List<DmData> list = dmDataMapper.queryZhSelectDm(mark);
        List rst = new ArrayList();
        list.forEach(item -> {
            rst.add(MapHelper.ofHashMap("id", item.getCode(), "label", item.getName()));
        });
        return rst;
    }

    @Override
    public List<Map<String, Object>> loadAttestStateEnum() {
        List<Map<String, Object>> list = new ArrayList<>();
        AttestStateEnum[] attestStateEnums = AttestStateEnum.values();
        for (int i = 0; i < attestStateEnums.length; i++) {
            AttestStateEnum var1 = attestStateEnums[i];
            list.add(MapUtil.ofHashMap("id", var1.getValue(), "label", var1.getLabel()));
        }
        return list;
    }

    @Override
    public List<SchoolVo> loadLoginUserShoolDm() {
        IeduUserVo iEduUserVo = IeduUserController.load(null, null);
        String xzqh = "";
        if (iEduUserVo != null & iEduUserVo.getApplyUserVo() != null) {
            xzqh = iEduUserVo.getApplyUserVo().getXzqh();
        }
        if (iEduUserVo != null & iEduUserVo.getAuditUserVo() != null) {
            xzqh = iEduUserVo.getAuditUserVo().getXzqh();
        }
        List<SchoolVo> list = dmDataMapper.querySchoolDm(xzqh);
        return list;
    }

    @Override
    public List<SchoolVo> loadShoolDm(String xzqh) {
        List<SchoolVo> list = dmDataMapper.querySchoolDm(xzqh);
        return list;
    }

    @Override
    public List<Map<String, Object>> loadDpOpinion() {
        List<Map<String, Object>> list = dmDataMapper.queryDpOpinion();
        return list;
    }
}
