package com.sunsharing.smartcampus.mapper.audit;

import com.sunsharing.smartcampus.model.pojo.audit.ProcInst;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sunsharing.smartcampus.model.vo.audit.PdNodeVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 流程实例 Mapper 接口
 * </p>
 *
 * @author CodeGenerator
 * @since 2020-08-02
 */
public interface ProcInstMapper extends BaseMapper<ProcInst> {
    void clearAudit(@Param("bussinessKey") String bussinessKey);
    List<PdNodeVo> queryProcessNodeList(String bussinessKey);

    int countDoneByRole(String roleId);

}
