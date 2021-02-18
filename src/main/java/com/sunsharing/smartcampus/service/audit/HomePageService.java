package com.sunsharing.smartcampus.service.audit;

import java.util.Map;

/**
 * <p>
 * 申请用户 服务类
 * </p>
 *
 * @author CodeGenerator
 * @since 2020-08-02
 */
public interface HomePageService{
    /**
     * 通过当前用户获取待审和已审统计值
     * @return 结果
     */
    Map countAuditByUser();
    /**
     * 分节点统计已经处理过的数据
     * @return 结果
     */
    Map countAlreadyAuditByNode();
    /**
     * 厦门市审核数据数字统计值和星级统计值
     * @return 结果
     */
    Map countAuditAll(String pcNo,String year);
    /**
     * 厦门市审核通过分值和安学校类型统计
     * @return 结果
     */
    Map countAuditPass(String pcNo,String year);


}
