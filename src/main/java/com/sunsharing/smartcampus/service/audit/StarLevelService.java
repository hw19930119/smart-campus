package com.sunsharing.smartcampus.service.audit;

import com.sunsharing.smartcampus.model.pojo.audit.PdNode;
import com.sunsharing.smartcampus.model.pojo.audit.StarLevel;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 节点评星 服务类
 * </p>
 *
 * @author CodeGenerator
 * @since 2020-08-13
 */
public interface StarLevelService extends IService<StarLevel> {
    void createStarLevel(String piId, String bussinessKey,PdNode pdNode,String star,String auditUserId,String reviewMaterials,Double avScore);
    Map listStarLevel(String businessKey);
}
