package com.sunsharing.smartcampus.service.audit;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sunsharing.smartcampus.model.pojo.audit.ApplyUser;
import com.sunsharing.smartcampus.model.vo.apply.ApplyLoginUser;
import com.sunsharing.smartcampus.model.vo.audit.ApplyUserVo;

import java.util.Map;

/**
 * <p>
 * 申请用户 服务类
 * </p>
 *
 * @author CodeGenerator
 * @since 2020-08-02
 */
public interface ApplyUserService extends IService<ApplyUser> {

    /**
     *  通过unitcode 判断学校是否已有关联账号
     * @param jsonObject
     * @return
     */
    JSONObject queryUnitCodeExitsOtherUser(JSONObject jsonObject);

    JSONObject auditApplyUser(JSONObject jsonObject);

    /**
     * 提交认证
     * @param userVo
     * @param attestationVo
     */
    Map<String, Object> submitAttestation(ApplyUser userVo, ApplyLoginUser attestationVo);

    /**
     * 查询登录用户信息
     * @param idNum
     * @return
     */
    ApplyLoginUser queryLoginUser(String idNum);

    /**
     *  撤销认证
     * @param applyUserVo
     * @param attestationVo
     * @return
     */
    Map<String, Object> revokeAttestation(ApplyUserVo applyUserVo, ApplyLoginUser attestationVo);

    /**
     * 获取申报开关
     * @return
     */
    String queryForSbSwitch();
}
