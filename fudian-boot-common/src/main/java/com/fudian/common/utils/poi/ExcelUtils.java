package com.fudian.common.utils.poi;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import com.alibaba.fastjson.JSONObject;
import com.fudian.common.utils.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description Excel工具类
 * @ClassName ExcelUtils
 * @Author mr.zhang
 * @Date 2020/4/6 15:57
 * @Version 1.0.0
 **/
public class ExcelUtils {


    /**
     * 导出Excel 动态列
     *
     * @param colTitle     动态列头
     * @param colWidth     对应列宽
     * @param dataList     数据集合
     * @param tableTitle   表头
     * @param sheetTitle   sheet头
     * @param response     response
     * @param downLoadName 下载文件名
     * @throws IOException
     */
    public static byte[] exportDynamicExcel(String colTitle, String colWidth, String dataList, String tableTitle, String sheetTitle, HttpServletResponse response, String downLoadName) throws IOException {

        // 解析列头 在这里用LinkedHashMap 做有序列
        Map colTitleMap = JSONObject.parseObject(colTitle, LinkedHashMap.class);
        // 列宽就不需要有序了
        Map colWidthMap = JSONObject.parseObject(colWidth, Map.class);

        // 列头容器 考虑到前后添加列 选用LinkedList
        LinkedList<ExcelExportEntity> colList = new LinkedList<>();
        // 列头遍历 采用Foreach
        colTitleMap.forEach((k, v) -> {
            ExcelExportEntity excelExportEntity = null;
            // 考虑列宽容器为空
            if (null == colWidthMap) {
                excelExportEntity = new ExcelExportEntity(v.toString(), k, 15);
            } else {
                // 考虑只指定了一些字段的列宽
                if (null == colWidthMap.get(k)) {
                    excelExportEntity = new ExcelExportEntity(v.toString(), k, 15);
                }else{
                    try {
                        Integer width = Integer.valueOf(colWidthMap.get(k).toString());
                        excelExportEntity = new ExcelExportEntity(v.toString(), k, width);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        excelExportEntity = new ExcelExportEntity(v.toString(), k, 15);
                    }
                }
            }
            colList.add(excelExportEntity);
        });

        // 添加序号列
        colList.addFirst(new ExcelExportEntity("序号","xh",15));

        // 解析数据 保证数据顺序
        List<LinkedHashMap> jsonArray = JSONObject.parseArray(dataList,LinkedHashMap.class);

        // 因为使用foreach 所以采用原子操作
        AtomicInteger number = new AtomicInteger(1);

        // 序号列赋值
        jsonArray.forEach(x -> {
            x.put("xh", number.getAndIncrement());
        });

        // 调用EasyPoi 返回workbook
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(tableTitle, sheetTitle), colList, jsonArray);
        if (workbook != null) {
            return writeToWeb(workbook, response, downLoadName);
        }
        return null;

    }

    private static byte[] writeToWeb(Workbook sheets, HttpServletResponse response, String fileName) throws IOException {
        String id = StringUtils.uuid();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        sheets.write(outputStream);

        return outputStream.toByteArray();
    }

}
