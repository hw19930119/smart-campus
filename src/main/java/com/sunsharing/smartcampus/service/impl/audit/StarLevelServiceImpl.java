package com.sunsharing.smartcampus.service.impl.audit;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.sunsharing.smartcampus.constant.enums.StarLevelWayEnum;
import com.sunsharing.smartcampus.model.pojo.audit.PdNode;
import com.sunsharing.share.common.collection.MapUtil;
import com.sunsharing.smartcampus.model.pojo.audit.StarLevel;
import com.sunsharing.smartcampus.mapper.audit.StarLevelMapper;
import com.sunsharing.smartcampus.service.audit.PfhzService;
import com.sunsharing.smartcampus.service.audit.StarLevelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.sunsharing.smartcampus.utils.ResultDataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Map;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 节点评星 服务实现类
 * </p>
 *
 * @author CodeGenerator
 * @since 2020-08-13
 */
@Service
@Transactional
public class StarLevelServiceImpl extends ServiceImpl<StarLevelMapper, StarLevel> implements StarLevelService {
    @Autowired
    PfhzService pfhzService;
    @Autowired
    StarLevelMapper starLevelMapper;

    @Override
    public void createStarLevel(String piId,String bussinessKey,PdNode pdNode, String star, String auditUserId,
                                String reviewMaterials,Double avScore) {
        String starLevelWay=pdNode.getStarLevelWay();
        if(StringUtils.equals(starLevelWay, StarLevelWayEnum.不要.getValue())){
            return ;
        }
        Integer starLevel=0;
        if(StringUtils.isNotBlank(star)){
            starLevel=Integer.parseInt(star);
        }
        boolean counterSign=StringUtils.equals(pdNode.getCounterSign(),"1")?true:false;
        if(counterSign==true){
            Map<String,Object> map=pfhzService.computeStarLevelByPfhz(piId,pdNode);
            avScore=(Double)map.get("avScore");
            starLevel=(Integer)map.get("starLevel");
        }
        if(avScore!=null){
            BigDecimal b=new BigDecimal(avScore);
            avScore=b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
        }
        //先删除再添加
        UpdateWrapper<StarLevel> updateWrapper=new UpdateWrapper();
        updateWrapper.eq("BUSSINESS_KEY",bussinessKey);
        updateWrapper.eq("NODE_ID",pdNode.getNodeId());
        starLevelMapper.delete(updateWrapper);

        //-------------------------
        String uuid=com.sunsharing.component.utils.base.StringUtils.generateUUID();;
        StarLevel starLevelDto=new StarLevel();
        starLevelDto.setId(uuid);
        starLevelDto.setUserId(auditUserId);
        starLevelDto.setBussinessKey(bussinessKey);
        starLevelDto.setNodeId(pdNode.getNodeId());
        starLevelDto.setAvScore(avScore);
        starLevelDto.setStarLevel(starLevel);
        starLevelDto.setReviewMaterials(reviewMaterials);
        starLevelDto.setCreateTime(new Date());
        starLevelDto.setCreatePerson(auditUserId);
        starLevelMapper.insert(starLevelDto);
    }

    public Map listStarLevel(String businessKey){
        List<Map> starLevelList = starLevelMapper.listStarLevel(businessKey);
        for(Map map:starLevelList){
            if(map.get("AV_SCORE")!=null){
                Double avSocre=(Double)map.get("AV_SCORE");
                DecimalFormat df=new DecimalFormat("#0.00");
                map.put("AV_SCORE",df.format(avSocre));
            }
        }
        return ResultDataUtils.packParams(MapUtil.ofHashMap("starLevelList",starLevelList),"查询成功");
    }
}
