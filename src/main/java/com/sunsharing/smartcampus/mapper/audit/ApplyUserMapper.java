package com.sunsharing.smartcampus.mapper.audit;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sunsharing.smartcampus.model.pojo.audit.ApplyUser;
import com.sunsharing.smartcampus.model.vo.apply.ApplyLoginUser;
import com.sunsharing.smartcampus.model.vo.audit.ApplyUserVo;

import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 申请用户 Mapper 接口
 * </p>
 *
 * @author CodeGenerator
 * @since 2020-08-02
 */
public interface ApplyUserMapper extends BaseMapper<ApplyUser> {
    ApplyUserVo getApplyUserByIdNum(@Param("idNum") String idNum, @Param("userId") String userId);
    ApplyUserVo getApplyUserVoByUserId( @Param("userId") String userId);

    Integer updateTzhxySchool(@Param("schoolVo") ApplyLoginUser attestationVo);


    ApplyLoginUser queryLoginUser(String idNum);

    String queryForSbSwitch();
}
