package com.fudian.project.business.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fudian.api.system.pojo.Students;
import com.fudian.api.system.pojo.SysDictionary;
import com.fudian.business.service.WrongQuestionListService;
import com.fudian.common.pojo.CommonResult;
import com.fudian.common.utils.ServletUtils;
import com.fudian.project.system.security.service.TokenService;
import com.fudian.project.system.service.SysDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin(allowCredentials = "true",allowedHeaders = "*",origins = "*")
@RestController
@RequestMapping(value = "applet/wrongQuestion")
public class WrongQuestionListController {

    @Autowired
    private WrongQuestionListService wrongQuestionListService;

    @Autowired
    private SysDictionaryService sysDictionaryService;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/queryListByDictId")
    public CommonResult queryListByDict(String dictId) {
        HttpServletRequest request = ServletUtils.getRequest();
        String serverName = request.getServerName();
        String serverPort = String.valueOf(request.getServerPort());
        Students student = tokenService.getLoginUser(request).getStudent();
        JSONObject obj = wrongQuestionListService.queryListByDict(dictId, student, serverName, serverPort);
        return CommonResult.success(obj);
    }

    /**
     * @return
     */
    @PostMapping("queryNumfield")
    public CommonResult queryNumfield() {
        List<SysDictionary> numfield = sysDictionaryService.queryByTypeAndEparent("numfield", "-");
        JSONArray jsonArray = new JSONArray();
        for (SysDictionary sysDictionary : numfield) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("numfieldId", sysDictionary.getDictType() + sysDictionary.getDictCode());
            jsonObject.put("numfieldName", sysDictionary.getFullName());
            jsonArray.add(jsonObject);
        }
        return CommonResult.success(jsonArray);
    }

}
