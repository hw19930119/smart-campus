package com.sunsharing.smartcampus.service.audit;

import com.alibaba.fastjson.JSONObject;
import com.sunsharing.smartcampus.model.pojo.audit.Score;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sunsharing.smartcampus.model.pojo.audit.SupplyIndex;
import com.sunsharing.smartcampus.model.vo.audit.AuditUserVo;
import com.sunsharing.smartcampus.model.vo.filed.FieldValueVo;

import java.util.Map;

/**
 * <p>
 * 审核端评分记录表 服务类
 * </p>
 *
 * @author CodeGenerator
 * @since 2020-08-14
 */
public interface ScoreService extends IService<Score> {
    JSONObject queryCategoryIdScoreDetail(JSONObject jsonObject);

    boolean queryExistCategoryIdGiveBackByMySelf(String bussinessKey, String myUserId, String roleId,boolean counterSign);

    JSONObject commitCategoryIdScore(JSONObject jsonObject);

    JSONObject cleanCategoryIdScore(JSONObject jsonObject);

    void deletePfhzByLoginUserAndPiId(AuditUserVo auditUserVo, String bussinessKey, String piId);

    FieldValueVo checkAllIndexCompelete(AuditUserVo auditUserVo, String piId, String bussinessKey);

    Map<String,Map<String,Object>> queryCurrentIndexScoreMap(String bussinessKey, String piId);

    Map<String,Double> querExpertIndexAvScoreMap(String bussinessKey);

    Map<String, String> queryFinalIndexScoreMap(String bussinessKey);

    JSONObject commitPfhz(JSONObject jsonObject);
}
