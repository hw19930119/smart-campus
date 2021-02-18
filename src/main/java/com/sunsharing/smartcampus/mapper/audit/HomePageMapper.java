package com.sunsharing.smartcampus.mapper.audit;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface HomePageMapper {

    Map countAuditByUser(@Param("roleId") String roleId,
                         @Param("xzqh")String xzqh);

    Map countAuditExpertByUser(@Param("userId") String userId);

    Map countAlreadyAuditByNode(@Param("xzqh")String xzqh);


    Map countAuditAll(@Param("pcNo") String pcNo,
                      @Param("year")String year,
                      @Param("pcState")String pcState);

    Map countAuditPassStar(@Param("pcNo") String pcNo,
                           @Param("year")String year,
                           @Param("pcState")String pcState);


    Map countAuditPassByXzqh(@Param("pcNo") String pcNo,
                             @Param("year")String year,
                             @Param("xzqh")String xzqh,
                             @Param("pcState")String pcState);

    List<Map> countAuditPassBySchoolType(@Param("pcNo") String pcNo,
                                         @Param("year")String year,
                                         @Param("pcState")String pcState);




}
