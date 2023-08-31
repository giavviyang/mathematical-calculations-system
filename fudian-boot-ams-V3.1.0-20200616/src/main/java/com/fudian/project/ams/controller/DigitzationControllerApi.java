package com.fudian.project.ams.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fudian.api.system.service.SysFileApi;
import com.fudian.common.constant.HttpStatus;
import com.fudian.common.pojo.CommonResult;
import com.fudian.common.utils.StringUtils;
import com.fudian.filestorage.controller.FileStorageController;
import com.fudian.log.annotation.OperInfo;
import com.fudian.log.annotation.OperTitle;
import com.fudian.api.ams.pojo.E01z1;
import com.fudian.project.ams.pojo.WfTask;
import com.fudian.project.ams.pojo.WfTaskE01z1;
import com.fudian.project.ams.service.E01z1Service;
import com.fudian.project.ams.service.WfTaskE01z1Service;
import com.fudian.project.ams.service.WfTaskService;
import com.fudian.api.system.pojo.SysFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

/**
 * @Description 数字化微服务
 * @ClassName DigitzationControllerApi
 * @Author mr.zhang
 * @Date 2019/12/27 18:27
 * @Version 1.0.0
 **/
@RestController
@RequestMapping("/digitzation")
@OperTitle("数字化微服务")
public class DigitzationControllerApi {

    @Autowired
    @Qualifier("fileStorageController")
    private FileStorageController fileStorageClient;

    @Autowired
    private SysFileApi sysFileServiceImpl;

    @Autowired
    private E01z1Service e01z1ServiceImpl;

    @Autowired
    private WfTaskE01z1Service wfTaskE01z1ServiceImpl;

    @Autowired
    private WfTaskService wfTaskServiceImpl;

    /**
     * 根据任务ID查询任务列表
     *
     * @param taskIds
     * @return
     */
    @GetMapping("/queryWfTaskE01z1ByTaskIds")
    @OperInfo(info = "根据任务ID查询任务列表", params = {"taskIds"})
    public CommonResult<List<Map<String, String>>> queryWfTaskE01z1ByTaskIds(String[] taskIds) {
        CommonResult<List<Map<String, String>>> result = new CommonResult<>();
        List<Map<String, String>> resultList = new ArrayList<>();
        Set<String> e01Ids = new HashSet<>();
        try {
            if (null == taskIds || 0 == taskIds.length) {
                result.setCode(HttpStatus.BAD_REQUEST);
                result.setMsg("参数taskIds为空");
            } else {
                QueryWrapper<WfTaskE01z1> queryWrapper;
                for (String taskId : taskIds) {
                    queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("task_id", taskId);
                    List<WfTaskE01z1> wfTaskE01z1s = wfTaskE01z1ServiceImpl.getBaseMapper().selectList(queryWrapper);
                    wfTaskE01z1s.forEach(x -> {
                        e01Ids.add(x.getE01z1Id());
                    });
                }
                e01Ids.forEach(x -> {
                    Map<String, String> map = wfTaskE01z1ServiceImpl.selectE01z1ViewById(x);
                    resultList.add(map);
                });
                result.setCode(HttpStatus.SUCCESS);
                result.setMsg("查询材料列表成功！");
                result.setData(resultList);
            }
        } catch (Exception e) {
            result.setCode(HttpStatus.ERROR);
            result.setMsg("后端报错");
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 数字化上传
     *
     * @param file    电子文件
     * @param sysFile 电子文件对象
     * @return 通用返回对象
     */
    @PostMapping("/insertSysFileById")
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    @OperInfo(info = "数字化上传", params = {"sysFile","flag"})
    public CommonResult<Object> insertSysFileById(@RequestParam("file") MultipartFile file, SysFile sysFile, @RequestParam(defaultValue = "true") boolean flag) {
        CommonResult<Object> result = new CommonResult();
        if (null == file || null == sysFile) {
            result.setCode(HttpStatus.BAD_REQUEST);
            result.setMsg("参数file或档案材料ID为空");
        } else {
            String storageId = "";
            try {
                storageId = fileStorageClient.fileUpload(file);
                if (StringUtils.isEmpty(storageId)) {
                    result.setCode(HttpStatus.ERROR);
                    result.setMsg("上传电子文件失败");
                } else {
                    synchronized (this){
                        LambdaQueryWrapper<SysFile> lambdaQueryWrapper = new LambdaQueryWrapper<>();
                        lambdaQueryWrapper.eq(SysFile::getBindingId,sysFile.getBindingId());
                        lambdaQueryWrapper.orderByDesc(SysFile::getFileIndex);
                        List<SysFile> sysFiles;
                        sysFiles = sysFileServiceImpl.list(lambdaQueryWrapper);
                        int index = sysFiles.size()==0?1:(sysFiles.get(0).getFileIndex() + 1);
                        sysFile.setFileIndex(index);
                        sysFile.setFileName(file.getOriginalFilename());
                        sysFile.setId(UUID.randomUUID().toString().replaceAll("-", ""));
                        sysFile.setFileType(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")));
                        sysFile.setFileSource(storageId);
                        //暂时写死
                        sysFile.setFileStorageMode("1");
                        int i = sysFileServiceImpl.save(sysFile)?1:0;
                        if (i == 1) {
                            if (flag) {
                                String bindingId = sysFile.getBindingId();
                                QueryWrapper<E01z1> queryWrapper = new QueryWrapper<>();
                                E01z1 one = e01z1ServiceImpl.getOne(queryWrapper);
                                if (0 == one.getE01z114() || null == one.getE01z114()) {
                                    one.setE01z114(1);
                                } else {
                                    one.setE01z114(one.getE01z114() + 1);
                                }
                                boolean update = e01z1ServiceImpl.update(one, queryWrapper);
                                if (!update) {
                                    result.setCode(HttpStatus.ERROR);
                                    result.setMsg("更新E01z1表失败！");
                                    return result;
                                }
                            }
                            result.setCode(HttpStatus.SUCCESS);
                            result.setMsg("上传电子文件成功");
                        } else {
                            result.setCode(HttpStatus.ERROR);
                            result.setMsg("上传电子文件失败");
                        }
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
                result.setCode(HttpStatus.ERROR);
                result.setMsg("后端报错");
            }
        }
        return result;
    }

    /**
     * 修改任务状态
     *
     * @param taskIds
     * @return
     */
    @PutMapping("/updateWfTask")
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    @OperInfo(info = "修改任务状态", params = {"taskIds"})
    public CommonResult<Object> updateWfTask(String[] taskIds) {
        CommonResult<Object> result = new CommonResult<>();
        try {
            if (null == taskIds || 0 == taskIds.length) {
                result.setCode(HttpStatus.BAD_REQUEST);
                result.setMsg("参数taskIds为空");
            } else {
                QueryWrapper<WfTask> queryWrapper;
                for (String taskId : taskIds) {
                    queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("id", taskId);
                    WfTask one = wfTaskServiceImpl.getOne(queryWrapper);
                    one.setStatus(2);
                    boolean update = wfTaskServiceImpl.update(one, queryWrapper);
                    if(!update){
                        result.setCode(HttpStatus.ERROR);
                        result.setMsg("关闭任务失败！");
                        return result;
                    }
                }
                result.setCode(HttpStatus.SUCCESS);
                result.setMsg("关闭任务成功！");
            }
        } catch (Exception e) {
            result.setCode(HttpStatus.ERROR);
            result.setMsg("后端报错");
            e.printStackTrace();
        }
        return result;
    }


}
