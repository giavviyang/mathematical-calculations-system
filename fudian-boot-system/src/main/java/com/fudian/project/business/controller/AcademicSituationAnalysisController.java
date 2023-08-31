package com.fudian.project.business.controller;

import com.fudian.business.service.AcademicSituationAnalysisService;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.common.pojo.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "analysis")
public class AcademicSituationAnalysisController {

    @Autowired
    private AcademicSituationAnalysisService analysisServicea;

    @PostMapping("/getAlgorithmAmount")
    public CommonResult getAlgorithmAmount(String studentId, String numberType) {
        return analysisServicea.getAlgorithmAmount(studentId,numberType);
    }

    @PostMapping("/getKnowledgeAmount")
    public CommonResult getKnowledgeAmount(String studentId, String numberType,String algorithm) {
        return analysisServicea.getKnowledgeAmount(studentId,numberType,algorithm);
    }

    @PostMapping("/getMasterSituation")
    public CommonResult getMasterSituation(String studentId) {
        return analysisServicea.getMasterSituation(studentId);
    }

    @PostMapping("/getKnowledgeAnalysis")
    public CommonGridResult getKnowledgeAnalysis(@RequestParam Map<String,Object> map) {
        return analysisServicea.getKnowledgeAnalysis(map);
    }

    @PostMapping("/getBreakThroughAnalysis")
    public CommonResult getBreakThroughAnalysis(String studentId) {
        return analysisServicea.getBreakThroughAnalysis(studentId);
    }

}
