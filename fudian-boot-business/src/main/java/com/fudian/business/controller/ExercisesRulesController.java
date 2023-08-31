package com.fudian.business.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fudian.business.pojo.ExercisesRules;
import com.fudian.business.service.ExercisesRulesService;
import com.fudian.common.pojo.CommonResult;
import com.fudian.common.utils.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;


/**
 * 当前数据为数据生成规则表展示的数据
 */
@RestController
@RequestMapping(value = "er")
public class ExercisesRulesController {

    @Resource
    private ExercisesRulesService exercisesRulesService;

    /**
     * 新增生成规则
     * @param rules
     * @return
     */
    @PostMapping("insertEr")
    public  CommonResult  insertExercisesRules(String rules){
        ExercisesRules exercisesRules= JSONObject.parseObject(rules, ExercisesRules.class);
        if (StringUtils.isEmpty(exercisesRules.getExcerciseRuleId())){
            String uuid = UUID.randomUUID().toString().replace("-","");
            exercisesRules.setExcerciseRuleId(uuid);
        }
        boolean b = exercisesRulesService.saveOrUpdate(exercisesRules);
        return new CommonResult(200,"保存成功",b);
//        return  null;
    }


    /**
     * 更新生成规则
     * @param rules
     * @return
     */
    @PutMapping("updateEr")
    public  CommonResult  updateExercisesRules(ExercisesRules rules){
        return new CommonResult(200,"新增成功",exercisesRulesService.updateById(rules));
    }


    /**
     * 删除生成规则
     * @param rules
     * @return
     */
    @DeleteMapping("deleteEr")
    public  boolean  deleteExercisesRules(ExercisesRules rules){
        return exercisesRulesService.removeById(rules.getExcerciseRuleId());
    }

    /**
     * 依据父级id查询当前的
     * @param pid
     * @return
     */
    @GetMapping("selectErByPid")
    public CommonResult  selectExercisesRulesByPid(String pid){
        LambdaQueryWrapper<ExercisesRules>  rulesLambdaQueryWrapper = new LambdaQueryWrapper<>();
        rulesLambdaQueryWrapper.eq(ExercisesRules::getPid,pid);
        List<ExercisesRules> list = exercisesRulesService.list(rulesLambdaQueryWrapper);
        return  new CommonResult(200,"",list);
    }

    @GetMapping("selectErById")
    public  CommonResult  selectErById(String  classifyId){
        LambdaQueryWrapper<ExercisesRules>  wrapper  = new LambdaQueryWrapper<>();
        wrapper.eq(ExercisesRules::getPid,classifyId);
        ExercisesRules one = exercisesRulesService.getOne(wrapper);
        return  new CommonResult(200,"规则数据",one);
    }

    /**
     * 根据分类项知识点生成题目
     * @param classfitionId  分类id
     * @return
     */
    @PostMapping("generateExercises")
    public   CommonResult  generateExercisesById(String  classfitionId){
       CommonResult  commonResult = exercisesRulesService.generateExercises(classfitionId,null);
       return commonResult;
    }


}
