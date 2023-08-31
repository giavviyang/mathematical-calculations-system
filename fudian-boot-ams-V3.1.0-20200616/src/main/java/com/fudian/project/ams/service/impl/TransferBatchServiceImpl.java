package com.fudian.project.ams.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudian.api.ams.pojo.A01;
import com.fudian.api.room.service.CustomPositionA01Api;
import com.fudian.api.system.pojo.SysFile;
import com.fudian.api.system.service.SysFileApi;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.common.pojo.CommonResult;
import com.fudian.filestorage.enums.FileStorageType;
import com.fudian.filestorage.factory.FileStorageFactory;
import com.fudian.filestorage.service.FileStorageService;
import com.fudian.project.ams.mapper.TransferBatchMapper;
import com.fudian.project.ams.pojo.TransferBatch;
import com.fudian.project.ams.pojo.TransferBill;
import com.fudian.project.ams.service.TransferBatchService;
import com.fudian.project.ams.service.TransferBillService;
import com.fudian.project.ams.utils.Map2Bean;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: WangYuZhuo
 * @Date: 2020/1/2 15:46
 * @Desc:
 */
@Service
public class TransferBatchServiceImpl extends ServiceImpl<TransferBatchMapper, TransferBatch> implements TransferBatchService {

    @Autowired
    private TransferBatchMapper transferBatchMapper;

    @Autowired
    private TransferBatchService transferBatchService;

    @Autowired
    private TransferBillService transferBillService;


    @Value("${fileStorage.type}")
    private String fileStorageType;

    @Autowired
    private SysFileApi sysFileService;

    //档案状态和干部状态
    public static String FLAG = "9"; //档案已转出
    public static String STATE = "7";     //干部已转出

    @Override
    public int deleteByPrimaryKey(String id) {
        return 0;
    }

    @Override
    public int insert(TransferBatch record) {
        return 0;
    }

    @Override
    public int insertSelective(TransferBatch record) {
        return 0;
    }

    @Override
    public TransferBatch selectByPrimaryKey(String id) {
        return null;
    }

    @Override
    public int updateByPrimaryKeySelective(TransferBatch record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(TransferBatch record) {
        return 0;
    }

    @Override
    public TransferBatch queryByCondition(Map<String, Object> map) {
        QueryWrapper<TransferBatch> queryWrapper = new QueryWrapper<>();
        if (map != null && map.size() > 0) {
            queryWrapper.allEq(map);
            return transferBatchMapper.selectOne(queryWrapper);
        }
        return null;
    }



    @Override
    public CommonGridResult queryListByTransferBatch(TransferBatch transferBatch, String personName) {
        Map<String, Object> map = new HashMap<>();
        if (personName != null && !"".equals(personName)) {
            personName = personName.toLowerCase();
            map.put("personName", personName);
        }
        if(transferBatch.getSearchValue()!=null&&!"".equals(transferBatch.getSearchValue())){
            map.put("orgName",transferBatch.getSearchValue());
        }
        if (transferBatch.getBeginTime() != null && transferBatch.getEndTime() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            map.put("beginTime", sdf.format(transferBatch.getBeginTime()));
            map.put("endTime", sdf.format(transferBatch.getEndTime()));
        }
        if (transferBatch.getId() != null && !"".equals(transferBatch.getId())) {
            map.put("transId", transferBatch.getId());
        }
        PageHelper.startPage(transferBatch.getPageNum(), transferBatch.getPageSize());
        List<TransferBatch> transferBatches = transferBatchMapper.selectByMaps(map);
        Page<TransferBatch> page = (Page) transferBatches;
        //拼接bill条件
        LambdaQueryWrapper<TransferBill> transferBillQueryWrapper = new LambdaQueryWrapper<>();
        List<String> sysBingIds = new ArrayList<>();
        if (transferBatches != null) {
            for (TransferBatch transferBatch1 : transferBatches) {
                if (transferBatch1 != null) {
                    sysBingIds.add(transferBatch1.getId());
                    transferBillQueryWrapper.or().eq(TransferBill::getTransId, transferBatch1.getId());
                }
            }
        } else {
            return null;
        }
        List<SysFile> sysFiles = null;
        if (sysBingIds.size() > 0) {
            LambdaQueryWrapper<SysFile> sysFileWrapper = new LambdaQueryWrapper<>();
            sysFileWrapper.in(SysFile::getBindingId, sysBingIds);
            sysFiles = sysFileService.list(sysFileWrapper);
        }
        Map<String, SysFile> sysFileMap = null;
        if (sysFiles != null && sysFiles.size() > 0) {
            sysFileMap = sysFiles.stream().collect(Collectors.toMap(SysFile::getBindingId, a -> a, (k, v) -> k));
        }
        List<TransferBill> transferBills = transferBillService.list(transferBillQueryWrapper);

        CommonGridResult commonGridResult = new CommonGridResult();
        commonGridResult.setRows(page.getResult());
        commonGridResult.setTotal(page.getTotal());
        commonGridResult.setPageNum(page.getPageNum());
        commonGridResult.setPageSize(page.getPageSize());
        //制作返回参数
        List<Map<String, Object>> returnList = new ArrayList<>();
        for (TransferBatch transferBatch1 : page.getResult()) {
            Map<String, Object> map1 = null;
            try {
                map1 = Map2Bean.bean2map(transferBatch1);
                if (transferBatch1.getE01z701() != null && !"".equals(transferBatch1.getE01z701())) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                    Date date = sdf.parse(transferBatch1.getE01z701());
                    map1.put("e01z701", date);
                }
                if (transferBatch1.getE01z727() != null && !"".equals(transferBatch1.getE01z727())) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                    Date date = sdf.parse(transferBatch1.getE01z727());
                    map1.put("e01z727", date);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (transferBills != null) {
                List<TransferBill> transferBillList = transferBills.stream()
                        .filter(transferBill -> transferBill.getTransId().equals(transferBatch1.getId())).collect(Collectors.toList());
                int sum = 0;
                for (TransferBill bill : transferBillList) {
                    sum += bill.getE01z711();
                }
                map1.put("children", transferBillList);
                map1.put("sum",sum);

                if (sysFileMap != null && sysFileMap.size() > 0) {
                    map1.put("isReceipt", sysFileMap.get(transferBatch1.getId()) == null ? "无" : "有");
                } else {
                    map1.put("isReceipt", "无");
                }

            }
            returnList.add(map1);
        }

        commonGridResult.setRows(returnList);
        return commonGridResult;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBatchAndBill(String jsonStr) {
        String[] ids = jsonStr.split("-");
        FileStorageService fileStorageService = FileStorageFactory.getFileStorage(FileStorageType.valueOf(fileStorageType));
        LambdaQueryWrapper<SysFile> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(SysFile::getBindingId, ids);
        List<SysFile> gSysFile = sysFileService.list(lambdaQueryWrapper);
        transferBillService.removeByIds(Arrays.asList(ids));
        this.removeByIds(Arrays.asList(ids));
        if (gSysFile != null && gSysFile.size() > 0) {
            List<String> sysFileIdList = gSysFile.stream().map(e -> e.getId()).collect(Collectors.toList());
            sysFileService.removeByIds(sysFileIdList);
        }
        if (gSysFile != null && gSysFile.size() > 0) {
            //数据库删除后删除电子文件
            try {
                for (int i = 0; i < gSysFile.size(); i++) {
                    fileStorageService.deleteFile(gSysFile.get(i).getFileSource());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    @Override
    public byte[] getFileByBatchId(String batchId) {
        FileStorageService fileStorageService = FileStorageFactory.getFileStorage(FileStorageType.valueOf(fileStorageType));
//        SysFile sysFile = new SysFile();
//        sysFile.setBindingId(batchId);
        SysFile returnSysFile = sysFileService.getOne(new LambdaQueryWrapper<SysFile>().eq(SysFile::getBindingId,batchId));
        if (returnSysFile != null) {
            try {
                Map<String,Object> file = new HashMap<>();
                file.put("fileName",returnSysFile.getFileName());
                return fileStorageService.getFile(returnSysFile.getFileSource());
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public boolean uploadFileAndInsertSysFile(MultipartFile part, String batchId) {
        FileStorageService fileStorageService = FileStorageFactory.getFileStorage(FileStorageType.valueOf(fileStorageType));
        SysFile sysfile = new SysFile();
        sysfile.setBindingId(batchId);
        SysFile sysFile1 = sysFileService.queryBySysFile(sysfile);
        if (sysFile1 != null) {//已存在回执单文件
            //先删除原有电子文件
            try {
                fileStorageService.deleteFile(sysFile1.getFileSource());
            } catch (Exception e) {
                e.printStackTrace();
            }

            String fileSourceId = null;
            try {
                fileSourceId = fileStorageService.uploadFile(part);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (fileSourceId == null) {
                return false;
            }
            sysFile1.setFileSize((int) part.getSize());
            sysFile1.setFileName(part.getOriginalFilename());
            sysFile1.setFileType(part.getOriginalFilename().split("\\.")[1]);
            sysFile1.setFileSource(fileSourceId);
            boolean flag = sysFileService.updateById(sysFile1);
            if (!flag) {
                return false;
            }
        } else {
            String fileSourceId = null;
            try {
                fileSourceId = fileStorageService.uploadFile(part);
            } catch (Exception e) {
                e.printStackTrace();
            }
            long size = part.getSize();
            String fileName = part.getOriginalFilename();
            sysfile.setFileSize((int) size);
            sysfile.setFileName(fileName);
            sysfile.setFileType(part.getOriginalFilename().split("\\.")[1]);
            sysfile.setFileSource(fileSourceId);
            sysfile.setId(UUID.randomUUID().toString().replace("-", ""));
            int i = sysFileService.save(sysfile)?1:0;
            if (i < 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Map<String, String> queryIdAndDate() {
        LambdaQueryWrapper<TransferBatch> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(TransferBatch::getId);
        List<TransferBatch> transfer = transferBatchService.list(queryWrapper);
        String id = "";
        if (transfer != null && transfer.size() > 0) {
            id = transfer.get(0).getId();
            id = String.valueOf(Long.parseLong(id, 10) + 1);
        }
        Map result = new HashMap();
        result.put("id", id);
        result.put("e01z701", new Date());
        result.put("orgId", "001");//TODO 获取user信息
        return result;
    }

    @Override
    public int updateBatch(List<TransferBatch> list) {
        return 0;
    }

    @Override
    public int batchInsert(List<TransferBatch> list) {
        return 0;
    }

    @Override
    public int insertOrUpdate(TransferBatch record) {
        return 0;
    }

    @Override
    public int insertOrUpdateSelective(TransferBatch record) {
        return 0;
    }

    @Override
    public CommonResult deleteFileByBatchId(String batchId) {
        FileStorageService fileStorageService = FileStorageFactory.getFileStorage(FileStorageType.valueOf(fileStorageType));
        LambdaQueryWrapper<SysFile> sysFileLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sysFileLambdaQueryWrapper.eq(SysFile::getBindingId, batchId);
        SysFile sysFile = sysFileService.getOne(sysFileLambdaQueryWrapper);
        boolean b = false;
        if (sysFile != null) {
            String fileSource = sysFile.getFileSource();
            try {
                b = fileStorageService.deleteFile(fileSource);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (b) {
                sysFileService.removeById(sysFile.getId());
                return CommonResult.success("删除成功");
            } else {
                return CommonResult.error(400, "电子文件删除失败");
            }
        } else {
            return CommonResult.error(400, "未找到电子文件");
        }

    }

    @Override
    public int updateBatchSelective(List<TransferBatch> list) {
        return baseMapper.updateBatchSelective(list);
    }

    @Override
    public List<Map<String, Object>> summaryTransferBathc() {
        return baseMapper.summaryTransferBathc();
    }
}

