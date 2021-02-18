package com.sunsharing.smartcampus.service.audit;

import com.alibaba.fastjson.JSONObject;
import com.sunsharing.smartcampus.model.pojo.audit.PdNode;
import com.sunsharing.smartcampus.model.pojo.audit.Pfhz;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sunsharing.smartcampus.model.vo.audit.AuditUserVo;

import java.util.Map;

/**
 * <p>
 * 评分汇总表 服务类
 * </p>
 *
 * @author CodeGenerator
 * @since 2020-08-13
 */
public interface PfhzService extends IService<Pfhz> {
    JSONObject queryPfhzList(JSONObject jsonObject);

    Map<String,Object> computeStarLevelByPfhz(String piId, PdNode pdNode);

    //{"score": "must","mustOn": ["11"]}
    //{"score":"can"}
    boolean checkMyPfhzCompleteByConfig(String piId, PdNode pdNode, String result,String userId);

    boolean checkMyScoreCompleteAllOnAgreeByConfig(String piId, PdNode pdNode, String result,String userId);

    boolean checkMyScoreCompleteAllOnAgreeOrNoAgreeByConfig(String piId, PdNode pdNode, String result, String userId);
}
