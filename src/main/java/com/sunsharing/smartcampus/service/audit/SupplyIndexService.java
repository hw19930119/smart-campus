package com.sunsharing.smartcampus.service.audit;

import com.alibaba.fastjson.JSONObject;
import com.sunsharing.smartcampus.model.pojo.audit.SupplyIndex;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 需要补充的指标 服务类
 * </p>
 *
 * @author CodeGenerator
 * @since 2020-08-02
 */
public interface SupplyIndexService extends IService<SupplyIndex> {
    void assignSupplyIndex(String bussinessKey,String scoreUserId,boolean counterSign);
    int updateIndexState(String businessKey,String mainIndexId, String status);

    boolean checkGivebackChangedFinish(String businessKey);


    JSONObject findSupplyIndex(JSONObject jsonObject);
}
