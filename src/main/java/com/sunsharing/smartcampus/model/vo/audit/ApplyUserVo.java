package com.sunsharing.smartcampus.model.vo.audit;

import com.sunsharing.smartcampus.model.pojo.audit.ApplyUser;
import com.sunsharing.smartcampus.model.vo.apply.ApplyLoginUser;

import lombok.Data;

@Data
public class ApplyUserVo extends ApplyUser {
    private String cityBelong;

    private String schoolName;

    private String xzqh;


    public ApplyLoginUser toApplyLoginUser() {
        return new ApplyLoginUser().setCityBelong(this.cityBelong)
            .setId(this.getId()).setName(this.getName())
            .setSchoolName(this.schoolName).setSchoolType(this.getSchoolType())
            .setSchoolTypeName(this.getSchoolTypeName())
            .setState(this.getState()).setUnitCode(this.getUnitCode())
            .setXzqh(this.xzqh).setMaterial(this.getMaterial())
            .setOpinion(this.getOpinion());
    }

}
