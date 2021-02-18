package com.sunsharing.smartcampus;


import com.alibaba.fastjson.JSONObject;
import com.sunsharing.smartcampus.model.vo.common.IeduUserVo;
import com.sunsharing.smartcampus.service.audit.ProcInstService;
import com.sunsharing.smartcampus.web.common.IeduUserController;

//import com.sunsharing.smartcampus.service.audit.ProcInstService;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class UnitTest {
    //@Autowired
    private ProcInstService procInstService;
    //@Autowired
    IeduUserController ieduUserController;

    public IeduUserVo login(String idNum) throws Exception {
        JSONObject params = new JSONObject();
        params.put("idNum", idNum);
        JSONObject jsonObject = ieduUserController.realLogin(null, null, params);
        IeduUserVo iEduUserVo = jsonObject.getObject("iEduUserVo", IeduUserVo.class);
        return iEduUserVo;
    }

    //@org.junit.Test
    public void startOrRestartProcess() throws Exception {
        String idNum = "1111111";//申报
        String bussinessKey = "2432432";
        IeduUserVo iEduUserVo = this.login(idNum);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("bussinessKey", bussinessKey);
        jsonObject.put("varJson", "{}");
        jsonObject.put("iEduUserVo", iEduUserVo);
        JSONObject json = procInstService.startOrRestartProcess(jsonObject,iEduUserVo);
    }

    //@org.junit.Test
    public void enterProcess() throws Exception {
        String idNum = "1111111";//审核
        String bussinessKey = "2432432";
        IeduUserVo iEduUserVo = this.login(idNum);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("bussinessKey", bussinessKey);
        jsonObject.put("varJson", "{}");
        jsonObject.put("iEduUserVo", iEduUserVo);
        JSONObject json = procInstService.enterProcess(jsonObject);
        System.out.println("enterProcess:" + json);
    }
}
