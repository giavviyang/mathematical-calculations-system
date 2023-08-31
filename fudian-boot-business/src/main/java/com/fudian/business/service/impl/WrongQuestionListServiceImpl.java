package com.fudian.business.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudian.api.system.pojo.Students;
import com.fudian.api.system.pojo.WrongQuestion;
import com.fudian.api.system.pojo.WrongQuestionListDTO;
import com.fudian.api.system.pojo.WrongQuestionListVO;
import com.fudian.business.mapper.WrongQuestionListMapper;
import com.fudian.business.pojo.WrongQuestionList;
import com.fudian.business.service.WrongQuestionListService;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.common.utils.AppletUtils;
import com.fudian.common.utils.StringUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;


/**
 * 错题
 * @author xuchenghong
 */

@Service
public class WrongQuestionListServiceImpl extends ServiceImpl<WrongQuestionListMapper, WrongQuestionList> implements WrongQuestionListService {


    @Resource
    private WrongQuestionListMapper wrongQuestionListMapper;

    @Override
    public JSONObject queryListByDict(String dictId, Students student, String addr, String port) {
        if (StringUtils.isEmpty(dictId) || student == null || StringUtils.isEmpty(student.getStudentId())) {
            return null;
        }

        Map<String,Object> map = new HashMap<>();
        List<WrongQuestionListVO> voList = new ArrayList<>();
        List<WrongQuestionListVO.KnowledgeDTO> knows;
        List<WrongQuestion> wrongs;
        List<WrongQuestionListDTO> list = wrongQuestionListMapper.queryListByDict(dictId, student.getStudentId());
        String imgAddr = "https://" + addr + ":" + port + "/applet/file/ioReadImage?fileId=";
        for (WrongQuestionListDTO wq:list) {
            //验证算域是否存在
            WrongQuestionListVO vo = (WrongQuestionListVO) map.get(wq.getFullName());
            if (vo == null) {
                vo = new WrongQuestionListVO();
                vo.setAlgorithmId(wq.getAlgorithmId());
                vo.setFullName(wq.getFullName());
                vo.setImgAddr(imgAddr+wq.getAlgorithmMsg());
                knows = new ArrayList<>();
                vo.setKnowledgeDTOs(knows);
                voList.add(vo);
                map.put(wq.getFullName(), vo);
            }

            //验证知识点是否存在
            WrongQuestionListVO.KnowledgeDTO know = (WrongQuestionListVO.KnowledgeDTO) map.get(wq.getClassificationMsg());
            if (know == null) {
                know = new WrongQuestionListVO.KnowledgeDTO();
                know.setKnowledgeId(wq.getClassificationId());
                know.setClassificationMsg(wq.getClassificationMsg());
                know.setClassificationNickname(wq.getClassificationNickname());
                wrongs = new ArrayList<>();
                know.setErrorList(wrongs);
                know.setIsLock(wq.getIsLock());
                vo.getKnowledgeDTOs().add(know);
                map.put(wq.getClassificationMsg(), know);
            }
            //添加错题
            WrongQuestion wrong = new WrongQuestion();
            String errorContent = wq.getErrorContent();
            String[] split = errorContent.split("[,|]");
            wrong.setId(wq.getId());
            if (split != null && split.length > 0) {
                if (split.length > 0) {
                    wrong.setRight(AppletUtils.analysiErrorFormula(split[0]));
                }
                if (split.length > 1) {
                    wrong.setError(AppletUtils.analysiErrorFormula(split[1]));
                }
                if (split.length > 2) {
                    wrong.setText(split[2]);
                }
            }
            know.getErrorList().add(wrong);
        }
        //添加每个知识点的错误题数
        for (WrongQuestionListVO vo:voList) {
            List<WrongQuestionListVO.KnowledgeDTO> knowledgeDTOs = vo.getKnowledgeDTOs();
            for (WrongQuestionListVO.KnowledgeDTO know:knowledgeDTOs) {
                know.setKnowledgeCount(String.valueOf(know.getErrorList().size()));
            }
        }
        int error = wrongQuestionListMapper.isError(dictId, student.getStudentId());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("isError", error>0?"有":"没有");
        jsonObject.put("list", voList);
        return jsonObject;
    }

    /**
     * 分页查询错题
     *
     * @param wrongQuestionList
     * @return
     */
    @Override
    public CommonGridResult pagingSelectWrongQuestionList(WrongQuestionList wrongQuestionList) {
        LambdaQueryWrapper<WrongQuestionList> queryWrapper = new LambdaQueryWrapper<>();

        PageHelper.startPage(wrongQuestionList.getPageNum(), wrongQuestionList.getPageSize());
        List<WrongQuestionList> wrongQuestionLists = wrongQuestionListMapper.selectList(queryWrapper);

        Page page = (Page) wrongQuestionLists;
        long total = page.getTotal();
        CommonGridResult commonGridResult = new CommonGridResult(wrongQuestionList.getPageNum(), wrongQuestionList.getPageSize(), total, wrongQuestionLists);
        return commonGridResult;
    }

    /**
     * 查询错题
     *
     * @param wrongQuestionList
     * @return
     */
    @Override
    public List<WrongQuestionList> selectWrongQuestionList(WrongQuestionList wrongQuestionList) {
        LambdaQueryWrapper<WrongQuestionList> queryWrapper = new LambdaQueryWrapper<>();

        List<WrongQuestionList> wrongQuestionLists = wrongQuestionListMapper.selectList(queryWrapper);
        return wrongQuestionLists;
    }

    /**
     * 添加错题
     *
     * @param wrongQuestionList
     * @return
     */
    @Override
    public int insertWrongQuestionList(WrongQuestionList wrongQuestionList) {
        String uuid = UUID.randomUUID().toString();
        wrongQuestionList.setId(uuid);
        int insert = wrongQuestionListMapper.insert(wrongQuestionList);
        return insert;
    }

    /**
     * 修改错题
     *
     * @param wrongQuestionList
     * @return
     */
    @Override
    public int updateWrongQuestionListById(WrongQuestionList wrongQuestionList) {

        int i = wrongQuestionListMapper.updateById(wrongQuestionList);
        return i;
    }

    /**
     * 批量删除错题
     *
     * @param ids
     * @return
     */
    @Override
    public int deleteWrongQuestionListById(List<String> ids) {
        int insert = wrongQuestionListMapper.deleteBatchIds(ids);
        return insert;
    }


    /**
     * 删除错题
     *
     * @param id
     * @return
     */
    @Override
    public int deleteWrongQuestionListById(String id) {
        int insert = wrongQuestionListMapper.deleteById(id);
        return insert;
    }

    /**
     * @author xch
     * @param ids
     * @return
     */
    @Override
    public List<WrongQuestionList> getWrongQuestionByBatchId(List<String> ids) {
        List<WrongQuestionList> wrongQuestion = wrongQuestionListMapper.getWrongQuestionByBatchId(ids);
        return wrongQuestion;
    }
}
