package com.fudian.project.ams.service;

import com.fudian.api.system.pojo.SysFile;
import com.fudian.common.pojo.CommonResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @Author: WangYuZhuo
 * @Date: 2020/3/16 10:32
 * @Desc:
 */
public interface ImgService {

    public List<SysFile> querySysFileByE01z1Id(String e01z1Id);

    public boolean deleteSysFileByE01z1Id(String e01z1Id, String index, Integer type);

    public byte[] createPdfBytes(String e01z1Id) throws IOException;


    public boolean fileUpload(MultipartFile uploadFile, Integer index, String a01Id, String e01z1Id) ;

    public boolean updateFileIndex(String fileId,Integer index);

    public boolean deleteFileByFileId(String fileId);
}
