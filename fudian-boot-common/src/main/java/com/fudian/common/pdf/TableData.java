package com.fudian.common.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;


/**
 * PDF 表格数据，每个表格一个对象
 *
 * PdfPTable  表格对象
 * PdfPCell  单元格对象
 * */
public class TableData {
    /**
     * 列表对象
     * */
    private PdfPTable T = null;

    /**
     * 设置列表整体样式
     * */
    public void setTableSize(float widthPercent, float beforeSpace, float afterSpace){
        this.T.setWidthPercentage(widthPercent);
        this.T.setSpacingBefore(beforeSpace);
        this.T.setSpacingAfter(afterSpace);
    }

    /**
     * 列标题样式
     * */
    private Font headerFont = null;
    private BaseColor headerBgColor = null;
    private float headerPadding = 0;
    private float headerBorder = 0;
    private BaseColor headerBdColor = null;
    /**
     * 指定列标题样式
     *
     * @param fontSize 像素，字体大小
     * @param bold     是否加粗
     * @param fontColor  字体颜色，传入空则不设置，支持：BLACK BLUE CYAN DARK_GRAY GRAY GREEN LIGHT_GRAY MAGENTA ORANGE PINK RED WHITE YELLOW
     * @param bgColor    字体底色，支持同上
     * @param padding    单元格子空隙，默认5pt
     * @param border     边框宽度
     * @param borderColor 边框颜色，传入空，则默认黑色
     * */
    public void setHeaderStyle(float fontSize, boolean bold, String fontColor,
            String bgColor, float padding, float border, String borderColor){
        int format = Font.NORMAL;
        if(bold){
            format = Font.BOLD;
        }
        if(null==fontColor||"".equals(fontColor)){
            this.headerFont = new Font(PdfExport.getCNFont(), fontSize, format);
        }else{
            this.headerFont = new Font(PdfExport.getCNFont(), fontSize, format, PdfExport.getColor(fontColor));
        }
        if(!(bgColor==null || "".equals(bgColor))){
            this.headerBgColor = PdfExport.getColor(bgColor);
        }
        this.headerPadding = padding;
        this.headerBorder = border;
        if(!(borderColor==null || "".equals(borderColor))){
            this.headerBdColor = PdfExport.getColor(borderColor);
        }
    }

    /**
     * 单元格样式
     * */
    private Font cellFont = null;
    private BaseColor cellBgColor = null;
    private float cellPadding= 0;
    private float cellBorder = 0;
    private BaseColor cellBdColor = null;
    /**
     * 指定单元格默认样式
     *
     * @param fontSize 像素，字体大小
     * @param bold     是否加粗
     * @param fontColor  字体颜色，支持：BLACK BLUE CYAN DARK_GRAY GRAY GREEN LIGHT_GRAY MAGENTA ORANGE PINK RED WHITE YELLOW
     * @param bgColor    字体底色，支持同上
     * @param padding    单元格子空隙，默认5pt
     * @param border     边框宽度
     * @param borderColor 边框颜色
     * */
    public void setCellStyle(float fontSize, boolean bold, String fontColor, String bgColor,
            float padding, float border, String borderColor){
        int format = Font.NORMAL;
        if(bold){
            format |= Font.BOLD;
        }
        if(null==fontColor||"".equals(fontColor)){
            this.cellFont = new Font(PdfExport.getCNFont(), fontSize, format);
        }else{
            this.cellFont = new Font(PdfExport.getCNFont(), fontSize, format, PdfExport.getColor(fontColor));
        }
        this.cellBgColor = PdfExport.getColor(bgColor);
        this.cellPadding = padding;
        this.cellBorder = border;
        this.cellBdColor = PdfExport.getColor(borderColor);
    }

    /**
     * 设置默认参数
     * */
    private void init(){
        setTableSize(100,10,10);

        this.headerFont = new Font(PdfExport.getCNFont(), 10f, Font.BOLD);
        this.headerBgColor = BaseColor.LIGHT_GRAY;
        this.headerBorder = 0.1f;
        this.headerBdColor = BaseColor.GRAY;
        this.headerPadding = 3;

        this.T.getDefaultCell().setPadding(this.headerPadding);
        this.T.getDefaultCell().setBorderWidth(this.headerBorder);
        this.T.getDefaultCell().setLeading(0f, 1.0f);

        this.cellFont = new Font(PdfExport.getCNFont(), 10f);
        this.cellPadding = 3;
        this.cellBorder = 0.1f;
        this.cellBgColor = null;
        this.cellBdColor = BaseColor.GRAY;
    }

    /**
     * 指定最多列数，之后采用列合并、行合并实现单元格合并
     * @param cols 列最大数目
     * */
    public TableData(int cols){
        this.T = new PdfPTable(cols);
//        this.T.setSplitLate(false);
        init();
    }
    /**
     * 指定最多列数，之后采用列合并、行合并实现单元格合并
     * @param colWidths 每列宽所占百分比， 和 <>100，将视为具体宽值，重新计算百分比
     * */
    public TableData(float[] colWidths){
        this.T = new PdfPTable(colWidths);
        init();
    }
    /**
     * 返回表格数据
     * */
    public PdfPTable getTable(){
        return this.T;
    }


    /**
     * 添加列标题
     * @param colName  列标题
     * @param colSpan  合并列数，1表示无合并，>1 合并列数
     * @param rowSpan  合并行数，1表示无合并，>1 合并行数
     * */
    public void addHeaderCell(String colName, int colSpan, int rowSpan,int border){
        String text = colName;
        if(null==colName||"".equals(colName)){
            text = " ";
        }
        PdfPCell cell = new PdfPCell();
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        cell.setBackgroundColor(this.headerBgColor);
        cell.setNoWrap(false);
        cell.setPadding(this.headerPadding);
        cell.setBorderWidth(this.headerBorder);
        cell.setBorderColor(this.headerBdColor);
        cell.disableBorderSide(border);
        Phrase p = new Phrase();
        p.setFont(this.headerFont);
        p.add(text);
        cell.setPhrase(p);
        if(colSpan > 1){
            cell.setColspan(colSpan);
        }
        if(rowSpan > 1){
            cell.setRowspan(rowSpan);
        }
        this.T.addCell(cell);
    }/**
     * 添加列标题
     * @param colName  列标题
     * @param colSpan  合并列数，1表示无合并，>1 合并列数
     * @param rowSpan  合并行数，1表示无合并，>1 合并行数
     * */
    public void addHeaderCell(String colName, int colSpan, int rowSpan){
        addHeaderCell(colName,colSpan,rowSpan,0);
    }
    /**
     * 设置表头所占行数
     *
     * 设置了这个，将使得表格跨页时自动每页具有表头
     * */
    public void setHeaderRows(int rows){
        this.T.setHeaderRows(rows);
    }

    /**
     * 添加单元格文本
     *
     * @param data     单元格文本
     * @param align    1 水平居左，2 水平居中 3 水平居右
     * */
    public void addCellText(String value, int align){
        addCellText(value, 1, 1, align);
    }
    /**
     * 添加单元格文本
     *
     * @param data     单元格文本
     * @param colSpan  合并列数，1表示无合并，>1 合并列数
     * @param rowSpan  合并行数，1表示无合并，>1 合并行数
     * @param align    1 水平居左，2 水平居中 3 水平居右
     * */
    public void addCellText(String value, int colSpan, int rowSpan, int align){
       this.addCellText(value, colSpan, rowSpan, align, null);
    }


    public void addCellText(String value, int colSpan, int rowSpan, int align,int... border){
    	this.addCellText(0, value, colSpan, rowSpan, align, border);
    }


    public void addCellText(int rotation,int height,String value, int colSpan, int rowSpan, int align,int... border){
        String text = value;
        if(null==value||"".equals(value)){
            text = " ";
        }

        PdfPCell cell = new PdfPCell();
        if(align == 1){
            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        }else if(align == 2){
            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        }else{
            cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        }
        cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        cell.setBackgroundColor(this.cellBgColor);
        cell.setNoWrap(false);

        cell.setRotation(rotation);

        cell.setPadding(this.cellPadding);
        cell.setFixedHeight(height);
        cell.setBorderWidth(this.cellBorder);
        if(border!=null) {
        	for(int b:border) {
        		cell.disableBorderSide(b);
        	}

        }

        if(this.cellBdColor != null){
            cell.setBorderColor(this.cellBdColor);
        }
        Phrase p = new Phrase();
        p.setFont(this.cellFont);

        p.add(text);
        cell.setPhrase(p);
        if(colSpan > 1){
            cell.setColspan(colSpan);
        }
        if(rowSpan > 1){
            cell.setRowspan(rowSpan);
        }
//        T.add
        this.T.addCell(cell);
    }


    /**
     * 添加单元格文本
     *
     * @param data     单元格文本
     * @param colSpan  合并列数，1表示无合并，>1 合并列数
     * @param rowSpan  合并行数，1表示无合并，>1 合并行数
     * @param align    1 水平居左，2 水平居中 3 水平居右
     * @param border 隐藏的边框 1为上边框 2为下边框 4为左边框 8为右边框
     * */
    public void addCellText(int rotation,String value, int colSpan, int rowSpan, int align,int... border){
        String text = value;
        if(null==value||"".equals(value)){
            text = " ";
        }

        PdfPCell cell = new PdfPCell();
        if(align == 1){
            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        }else if(align == 2){
            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        }else{
            cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        }
        cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        cell.setBackgroundColor(this.cellBgColor);
        cell.setNoWrap(false);

        cell.setRotation(rotation);

        cell.setPadding(this.cellPadding);

        cell.setBorderWidth(this.cellBorder);
        if(border!=null) {
        	for(int b:border) {
        		cell.disableBorderSide(b);
        	}

        }

        if(this.cellBdColor != null){
            cell.setBorderColor(this.cellBdColor);
        }
        Phrase p = new Phrase();
        p.setFont(this.cellFont);
        p.add(text);
        cell.setPhrase(p);
        if(colSpan > 1){
            cell.setColspan(colSpan);
        }
        if(rowSpan > 1){
            cell.setRowspan(rowSpan);
        }
//        T.add
        this.T.addCell(cell);
    }


    /**
     * 添加单元格图片
     * @param img    图片二进制数据
     * @param align  图片排列方式 1=居左； 2=居中； 3=居右
     * */
    public void addCellImage(byte[] image, int align){
        addCellImage(image, 2, 1, 0, 0, 0, "BLACK", 0, 1, 1);
    }

    /**
     * 添加单元格图片
     * @param img    图片二进制数据
     * @param align  图片排列方式 1=居左； 2=居中； 3=居右
     * @param wrap   图片是否文字环绕， 1=文字环绕图形； 2=图形作为文字背景
     * @param width  图片宽，0表示保持原来尺寸，此时 height 也该设为0
     * @param height 图片高，0表示保持原来尺寸，此时 width 也该设为0
     * @param border 边框宽度，0表示无边框
     * @param borderColor   边框颜色，border > 0 时有效
     * @param rotateDegree  图片旋转角度
     * @param colSpan  合并列数，1表示无合并，>1 合并列数
     * @param rowSpan  合并行数，1表示无合并，>1 合并行数
     * */
    public void addCellImage(byte[] image, int align, int wrap, float width, float height, int boder, String borderColor, int rotateDegree, int colSpan, int rowSpan){
        try{
            Image img = Image.getInstance(image);
            // 参数 alignment 为 Image.RIGHT、Image.MIDDLE、Image.LEFT 分别指右对齐、居中、左对齐
            // 参数 alignment 为 Image.TEXTWRAP、Image.UNDERLYING 分别指文字绕图形显示、图形作为文字的背景显示
            int aa = 0;
            if(align == 2){
                aa = Image.MIDDLE;
            }else if(align == 3){
                aa = Image.RIGHT;
            }else{ // 默认
                aa = Image.LEFT;
            }
            if(wrap == 2){
                aa |= Image.UNDERLYING;
            }else{ // 默认
                aa |= Image.TEXTWRAP;
            }
            img.setAlignment(aa);

            if(width>0 && height>0){
                img.scaleToFit(width, height);
            }

            if(boder>0){
                img.setBorder(Image.BOX);
                img.setBorderWidth(boder);
                img.setBorderColor(PdfExport.getColor(borderColor));
            }

            if(rotateDegree != 0){
                img.setRotationDegrees(rotateDegree);
            }
            PdfPCell cell = new PdfPCell(img);
            if(align == 1){
                cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            }else if(align == 2){
                cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            }else{
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            }
            cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell.setBackgroundColor(this.cellBgColor);
            cell.setNoWrap(false);
            cell.setPadding(this.cellPadding);
            cell.setBorderWidth(this.cellBorder);
            cell.setBorderColor(this.cellBdColor);
            if(colSpan > 1){
                cell.setColspan(colSpan);
            }
            if(rowSpan > 1){
                cell.setRowspan(rowSpan);
            }
            this.T.addCell(cell);
        }catch(Exception ex){
            System.out.println("********** appendImage failed. **********");
            ex.printStackTrace();
        }
    }

    /**
     * 添加单元格超链接
     *
     * @param text  链接文字
     * @param name  链接名称，用于内部跳转，无跳转可以设置为null， 譬如： test
     * @param url   链接地址，可以是一个 http 地址， 也可以是上面 name 值，譬如： #test
     * @param align    1 水平居左，2 水平居中 3 水平居右
     * @param colSpan  合并列数，1表示无合并，>1 合并列数
     * @param rowSpan  合并行数，1表示无合并，>1 合并行数
     * */
    public void addCellAnchor(String text, String name, String url, int align, int colSpan, int rowSpan){
        // 需要创建一个新的字体******
        Font font = new Font(PdfExport.getCNFont());
        font.setColor(BaseColor.BLUE);
        font.setSize(this.cellFont.getSize());
        Anchor a = new Anchor(text, font);
        if(!(null==name || "".equals(name))){
            a.setName(name);
        }
        if(!(null==url || "".equals(url))){
            a.setReference(url);
        }
        PdfPCell cell = new PdfPCell(a);
        if(align == 1){
            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        }else if(align == 2){
            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        }else{
            cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        }
        cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        cell.setBackgroundColor(this.cellBgColor);
        cell.setNoWrap(false);
        cell.setPadding(this.cellPadding);
        cell.setBorderWidth(this.cellBorder);
        cell.setBorderColor(this.cellBdColor);
        if(colSpan > 1){
            cell.setColspan(colSpan);
        }
        if(rowSpan > 1){
            cell.setRowspan(rowSpan);
        }
        this.T.addCell(cell);
    }

}
