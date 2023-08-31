package com.fudian.filestorage.controller;

import com.fudian.api.system.pojo.WatermarkConfig;
import com.fudian.api.system.service.WatermarkConfigApi;
import com.fudian.common.pojo.CommonResult;
import com.fudian.common.utils.StringUtils;
import com.fudian.common.utils.data.DataStoreWrapper;
import com.fudian.filestorage.entity.StorageConfig;
import com.fudian.filestorage.enums.FileStorageType;
import com.fudian.filestorage.factory.FileStorageFactory;
import com.fudian.filestorage.factory.ImgDESFactory;
import com.fudian.filestorage.service.FileStorageService;
import net.coobird.thumbnailator.Thumbnails;
import net.sf.jmimemagic.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;

/**
 * @Description 文件存储Controller
 * @ClassName fileStorageController
 * @Author mr.hou
 * @Date 2019/12/5 6:13 下午
 * @Version 1.0.0
 **/
@RestController
@RequestMapping("fileStorage")
public class FileStorageController {


    @Autowired
    DataStoreWrapper redisCache;

    @Autowired
    private WatermarkConfigApi watermarkConfigService;


    /**
     * 文件上传
     *
     * @param file
     * @return 文件存储标识Id
     */
    @PostMapping("uploadFile")
    public String fileUpload(@RequestParam("file") MultipartFile file) throws IOException {
        FileStorageService fileStorageService = FileStorageFactory.getFileStorage(FileStorageType.valueOf(StorageConfig.fileStorageType));
        return fileStorageService.uploadFile(file);
    }

    /**
     * 文件上传（ftp需根据干部基本信息创建文件夹）
     *
     * @param file
     * @return 文件存储标识Id
     */
    @PostMapping("uploadFileWithFolder")
    public String fileUpload(@RequestParam("file") MultipartFile file, @RequestParam("folder") String folder) throws IOException {
        FileStorageService fileStorageService = FileStorageFactory.getFileStorage(FileStorageType.valueOf(StorageConfig.fileStorageType));

        return fileStorageService.uploadFile(file, folder);
    }

    /**
     * 文件上传
     *
     * @param fileBytes 文件二进制
     * @return 文件存储标识Id
     */
    @PostMapping("uploadBytes")
    public String fileUpload(@RequestParam("fileBytes") byte[] fileBytes) throws IOException {
        FileStorageService fileStorageService = FileStorageFactory.getFileStorage(FileStorageType.valueOf(StorageConfig.fileStorageType));

        return fileStorageService.uploadFile(fileBytes);
    }

    /**
     * 文件上传（ftp需根据干部基本信息创建文件夹）
     *
     * @param fileBytes 文件二进制
     * @param folder    文件存储目录
     * @param fileName  文件名
     * @return 文件存储标识Id
     */
    @PostMapping("uploadBytesWithFolderAndFileName")
    public String fileUpload(@RequestParam("fileBytes") byte[] fileBytes, @RequestParam("folder") String folder, @RequestParam("fileName") String fileName) throws IOException {
        FileStorageService fileStorageService = FileStorageFactory.getFileStorage(FileStorageType.valueOf(StorageConfig.fileStorageType));

        return fileStorageService.uploadFile(fileBytes, folder, fileName);
    }


    /**
     * 文件上传（ftp需根据干部基本信息创建文件夹）
     *
     * @param fileBytes 文件二进制
     * @param fileType  文件类型
     * @return 文件存储标识Id
     */
    @PostMapping("uploadBytesWithType")
    public String fileUpload(@RequestParam("fileBytes") byte[] fileBytes, @RequestParam("fileType") String fileType) throws IOException {
        FileStorageService fileStorageService = FileStorageFactory.getFileStorage(FileStorageType.valueOf(StorageConfig.fileStorageType));

        return fileStorageService.uploadFile(fileBytes, fileType);
    }

    /**
     * 文件删除
     *
     * @param fileId
     * @return true 删除成功  false: 失败
     */
    @DeleteMapping("delete")
    public Boolean filedelete(@RequestParam("fileId") String fileId) throws IOException {
        FileStorageService fileStorageService = FileStorageFactory.getFileStorage(FileStorageType.valueOf(StorageConfig.fileStorageType));

        return fileStorageService.deleteFile(fileId);
    }

    /**
     * 获取文件流
     *
     * @param fileId
     * @return
     */
    @GetMapping("downFile")
    public byte[] downFile(@RequestParam("fileId") String fileId) throws IOException {
        FileStorageService fileStorageService = FileStorageFactory.getFileStorage(FileStorageType.valueOf(StorageConfig.fileStorageType));
        return fileStorageService.getFile(fileId);
    }

    /**
     * 获取文件流
     *
     * @param fileId
     * @return
     */
    @GetMapping("downFileTest")
    public void downFile(@RequestParam("fileId") String fileId, HttpServletResponse response) throws IOException {
        FileStorageService fileStorageService = FileStorageFactory.getFileStorage(FileStorageType.valueOf(StorageConfig.fileStorageType));
        byte[] bytes = fileStorageService.getFile(fileId);

        MagicMatch match = null;
        try {
            match = Magic.getMagicMatch(bytes);
        } catch (MagicParseException ex) {
            ex.printStackTrace();
        } catch (MagicMatchNotFoundException ex) {
            ex.printStackTrace();
        } catch (MagicException ex) {
            ex.printStackTrace();
        }
        String mimeType = match.getMimeType();
        // 设置响应的类型
        response.setContentType(mimeType);

        //禁止图像缓存。
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
        bos.write(bytes);

        bos.close();
    }

    /**
     * 获取图片流 (可压缩)
     *
     * @param fileId
     * @param compress 是否需要压缩  true为压缩  false为不压缩
     * @return
     */
    @GetMapping("downImage")
    public void downImage(@RequestParam("fileId") String fileId, @RequestParam("compress") Boolean compress, HttpServletResponse response) throws IOException {
        //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        //Date startDate = new Date();
        //System.err.println(dateFormat.format(startDate) + ":收到请求");
        FileStorageService fileStorageService = FileStorageFactory.getFileStorage(FileStorageType.valueOf(StorageConfig.fileStorageType));
        byte[] bytes = fileStorageService.getFile(fileId);
        //是否需要加解密
        if (ImgDESFactory.isDES()) {
            bytes = ImgDESFactory.getInstance().decode(bytes);
            System.out.println("图片已解密");
        }
        //Date downTime = new Date();
        //System.err.println(dateFormat.format(downTime) + ":从容器中拿出图片 花费了" +(downTime.getTime()-startDate.getTime()) + "毫秒");
        ByteArrayOutputStream outputStream = null;
        ByteArrayInputStream inputStream = null;
        try {
            //判断是否需要压缩图片
            if (compress != null && compress) {
                outputStream = new ByteArrayOutputStream();
                //字节转换为字节流放入压缩工具中
                inputStream = new ByteArrayInputStream(bytes);

                Thumbnails.of(inputStream)
                        .scale(0.1f) //值在0到1之间，1f就是原图大小，0.5就是原图的一半大小，大小是指图片的长宽
                        .outputQuality(0.1f) //图片的质量，值在0到1，越接近于1质量越好，越接近于0质量越差
                        .toOutputStream(outputStream);
                //Date comDate = new Date();
                //System.err.println(dateFormat.format(comDate) + ":压缩图片 花费了" +(comDate.getTime()-downTime.getTime()) + "毫秒");
                bytes = outputStream.toByteArray();
            } else {


                //原图添加水印
                WatermarkConfig watermarkConfig = new WatermarkConfig();
                watermarkConfig.setActive("1");
                watermarkConfig.setDiaphaneity(0.01);
                watermarkConfig.setRowLength(3000);
                watermarkConfig.setColLength(3000);
                List<WatermarkConfig> list = watermarkConfigService.select(watermarkConfig);
                if (list != null && list.size() > 0) {
                    WatermarkConfig config = list.get(0);
                    if ("0".equals(config.getType())) {
                        if (StringUtils.isNotBlank(config.getText())) {
                            bytes = writeTextWaterMark(bytes, config);
                        }
                    } else {
                        if (config.getImage() != null && config.getImage().length > 0) {
                            bytes = writeImageWaterMark(bytes, config);
                        }
                    }
                }
            }
            MagicMatch match = Magic.getMagicMatch(bytes);
            String mimeType = match.getMimeType();
            // 设置响应的类型格式为图片格式
            response.setContentType(mimeType);

            //禁止图像缓存。
            response.setHeader("Pragma", "no-cache");

            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);

            BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
            bos.write(bytes);

            bos.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }

        //Date returnTime = new Date();
        //System.err.println(dateFormat.format(returnTime) + ":返回图片流");

    }

    /**
     * 获取文件流
     *
     * @param name
     * @return
     */
    @GetMapping("TestDownFile")
    public byte[] TestDownFile(String name) throws IOException {
        FileStorageService fileStorageService = FileStorageFactory.getFileStorage(FileStorageType.valueOf(StorageConfig.fileStorageType));
        String path = "/ams/" + name + ".jpg";
        return fileStorageService.getFile(path);
    }

    /**
     * 获取文件流
     *
     * @param fileId
     * @return
     */
    @GetMapping("downFileByCheckId")
    public byte[] downFileByCheckId(@RequestParam("fileId") String fileId) throws IOException {
        String id = redisCache.getCacheObject(fileId);
        FileStorageService fileStorageService = FileStorageFactory.getFileStorage(FileStorageType.valueOf(StorageConfig.fileStorageType));
        byte[] bytes = fileStorageService.getFile(id);
        return bytes;
    }

    /**
     * 获取文件流
     *
     * @param fileSource
     * @return
     */
    @GetMapping("downFileByFileSource")
    public byte[] downFileByFileSource(@RequestParam("fileSource") String fileSource) throws IOException {
        FileStorageService fileStorageService = FileStorageFactory.getFileStorage(FileStorageType.valueOf(StorageConfig.fileStorageType));
        byte[] bytes = fileStorageService.getFile(fileSource);
        return bytes;
    }

    /**
     * 验证是否能够连接
     *
     * @param
     * @return
     */
    @GetMapping("testIsConnect")
    public boolean testIsConnect(@RequestParam("ip") String ip, @RequestParam("port") int port, @RequestParam("userName") String userName, @RequestParam("password") String password) {
        return false;
    }

    /**
     * 根据redisId获取文件流  下载时使用
     *
     * @param redisId
     * @return
     */
    @GetMapping("getFileBytes")
    public void getFileBytes(String redisId, String fileName, HttpServletResponse response) throws UnsupportedEncodingException {
        byte[] bytes = redisCache.getCacheObject(redisId);
        redisCache.deleteObject(redisId);
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Disposition", "attachment;fileName=" +
                java.net.URLEncoder.encode(fileName, "UTF-8"));
        try {
            OutputStream outputStream = response.getOutputStream();
            outputStream.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据redisId获取文件流  浏览时使用
     *
     * @param redisId
     * @return
     */
    @GetMapping("getFileBytesToImg")
    public byte[] getFileBytesToImg(String redisId) {
        byte[] bytes = redisCache.getCacheObject(redisId);
        return bytes;
    }

    @GetMapping("getFileToImage")
    public CommonResult getFileToImage(String redisId, HttpServletResponse response) throws IOException {
        byte [] bytes = redisCache.getCacheObject(redisId);
        redisCache.deleteObject(redisId);
        return CommonResult.success("获取成功",bytes);
    }

    @GetMapping("getTestFileBytes")
    public byte[] getTestFileBytes() {
//        File file = new File("F:/aliyunSvn/巴托尼亚.pdf");

        byte[] bytes = getBytesByFile("F:/aliyunSvn/巴托尼亚.pdf");
        //redisCache.deleteObject(redisId);
        return bytes;
    }

    /**
     * 添加图片水印
     *
     * @param bytes
     * @return
     */
    public  byte[] writeImageWaterMark(byte[] bytes, WatermarkConfig config) {
        try {
            //将图片流转换为图片对象
            ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
            BufferedImage bufferedImage = ImageIO.read(inputStream);
            //将水印图片流转换为图片对象
            ByteArrayInputStream watermarkInputStream = new ByteArrayInputStream(config.getImage());
            BufferedImage watermarkImage = ImageIO.read(watermarkInputStream);

            //添加水印
            bufferedImage = writeImageWaterMark(bufferedImage, watermarkImage);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "jpg", outputStream);
            return outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 操纵图片水印
     */
    public BufferedImage writeImageWaterMark(BufferedImage image, BufferedImage waterMark) {

        //水印行间距
        int row = 200;
        //水印列间距
        int col = 150;
        //差值
        int dif = (waterMark.getWidth() + row) / 4;
        //倾斜度
        double rotate = 0.4;
        //透明度
        float luc = 0.2F;

        Graphics2D g = image.createGraphics();

        //设置透明度  0-1之间  越小越透明
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, luc));
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int x = image.getWidth();
        int y = -image.getHeight();

        //旋转图片
        g.rotate(rotate);
        while (y < image.getHeight()) {
            while (x > -image.getWidth()) {
                g.drawImage(waterMark, null, x, y);
                x = x - waterMark.getWidth() - row;
            }
            y = y + waterMark.getWidth() + col;
            x = image.getWidth() + dif;
            dif = -dif;
        }
        //还原图片
        g.rotate(-rotate);

        g.dispose();
        return image;
    }

    /**
     * 添加文字水印
     *
     * @param bytes
     * @param config
     * @return
     */
    public byte[] writeTextWaterMark(byte[] bytes, WatermarkConfig config) {
        ByteArrayInputStream byteArrayInputStream = null;
        ByteArrayOutputStream outputStream = null;
        try {
            byteArrayInputStream = new ByteArrayInputStream(bytes);
            BufferedImage image = ImageIO.read(byteArrayInputStream);
            BufferedImage bufferedImage = writeTextWaterMark(image, config);
            outputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "jpg", outputStream);
            return outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (outputStream != null) {
                try {
                    byteArrayInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (byteArrayInputStream != null) {
                try {
                    byteArrayInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 文字型水印
     */
    private BufferedImage writeTextWaterMark(BufferedImage image, WatermarkConfig config) {

        String content = config.getText();
        //字体大小
        Integer fontSize = 80;
        //字体属性
        Font font = new Font("宋体", Font.BOLD, fontSize);
        //水印行间距
        int row = 400;
        //水印列间距
        int col = 300;
        //差值
        int dif = (content.length() * fontSize + row) / 4;
        //倾斜度
        double rotate = 0.4;
        //透明度
        float luc = 0.2F;

        Graphics2D g = image.createGraphics();

        //设置透明度  0-1之间  越小越透明
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, luc));
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //设置字体颜色
        String color = config.getColor();
        if (StringUtils.isNotBlank(color)) {
            String[] rgbs = color.split(",");
            if (rgbs.length != 3) {
                g.setColor(Color.GRAY);
            } else {
                g.setColor(new Color(Integer.parseInt(rgbs[0]), Integer.parseInt(rgbs[1]), Integer.parseInt(rgbs[2])));
            }
        } else {
            g.setColor(Color.GRAY);
        }
        g.setFont(font);

        int x = image.getWidth() * 2;
        int y = -image.getHeight();

        if (StringUtils.isNotBlank(content)) {
            //旋转图片
            g.rotate(rotate);
            while (y < image.getHeight() * 2) {
                while (x > -image.getWidth()) {
                    g.drawString(content, x, y);
                    x = x - content.length() * fontSize - row;
                }
                y = y + fontSize + col;
                x = image.getWidth() * 2 + dif;
                dif = -dif;
            }
            //还原图片
            g.rotate(-rotate);
        }

        g.dispose();
        return image;
    }

    public byte[] getBytesByFile(String filePath) {
        try {
            File file = new File(filePath);
            //获取输入流
            FileInputStream fis = new FileInputStream(file);

            //新的 byte 数组输出流，缓冲区容量1024byte
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
            //缓存
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            //改变为byte[]
            byte[] data = bos.toByteArray();
            //
            bos.close();
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    @PostMapping("getSystemUnit")
    public CommonResult getSystemUnit(){
        ClassPathResource resource = new ClassPathResource("/static/plug.exe");
        try {
            String uuid = StringUtils.uuid();
            InputStream inputStream = resource.getInputStream();
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int n = 0;
            while (-1 != (n = inputStream.read(buffer))) {
                output.write(buffer, 0, n);
            }
            redisCache.setCacheObject(uuid,output.toByteArray());
//            Map<String,String> returnMap = new HashMap<>();
//            returnMap.put("data",uuid);
//            returnMap.put("fileName","plug.exe");
            CommonResult commonResult = new CommonResult();
            commonResult.setCode(200);
            commonResult.setData(uuid);
            commonResult.put("fileName","plug.exe");
            return commonResult;

        } catch (IOException e) {
            e.printStackTrace();
            return CommonResult.error(500,"文件不存在");
        }

    }
}
