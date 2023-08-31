package com.fudian.common.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;


/**
 * PDF 文档输出类，每个类对象对应一个PDF文档的输出
 *
 * 注意：所有属性设置，应该在写入内容之前完成
 *
 * 几个基础类说明：
 * PdfWriter   书写器、用于往document中写入信息。
 * Document    一个PDF文档组件。
 * Chunk       文本块、这是可以写入文档的最小的组件。
 * Phrase      短语、由一系列Chunk组成。
 * Paragraph   段落、有一系列的Phrase或者Chunk组成。
 * TableWrapper   表格、处理简单表格的组件
 * PdfPTable      表格、处理复杂表格的组件
 * Image       图形处理组件
 * Rectangle   代表几何图形组件、可以通过此组件设置背景色、边框等样式，类似html里的Box
 * BaseColor   定义颜色组件、预装了很多常用颜色
 * Anchor      超链接，可以链接到外部URL地址，也可以指向内部另一个Anchor，放在Paragraph中
 * */
public class PdfExport {
    /**
     * PDF文档对象
     * */
    private Document DOC = null;
    /**
     * 二进制输出流
     * */
    private ByteArrayOutputStream BAOS = null;

    private static BaseFont baseFont = null;
    /**
     * 基础汉字库，统一使用***************
     * */
    public static BaseFont getCNFont(){
        if(baseFont == null){
            try{
                baseFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            }catch(Exception ex){
                System.out.println("init iText font failed, maybe itext-aisa.jar missed.");
                ex.printStackTrace();
            }
        }
        return baseFont;
    }

    /**
     * 构造器
     * */
    public PdfExport(){
    }

    //------------------------PDF输出属性设置值-----------------------
    /**
     * 常规字 20 point CYAN 蓝绿色，用于水印字体
     * */
    private int watermarkRepeat = 0;
    private String watermarkText = null;
    private byte[] watermarkImage = null;




    /**
     * 设置水印文本或图片
     *
     * @param text         水印文本，文本、图片二者不能都为null
     * @param image        水印图片，文本、图片二者不能都为null
     * @param repeatTimes  每页重复显示次数 1--6 有效
     * */
    public void setWatermark(String text, byte[] image, int repeatTimes){
        if(repeatTimes>0 && repeatTimes<7){
            this.watermarkText = text;
            this.watermarkImage = image;
            this.watermarkRepeat = repeatTimes;
        }
    }

    private String title = null;
    private String author =  null;
    private String subject = null;
    private String keywords = null;
    private String creator = null;
    /**
     * 设置文档基础属性，在写内容之后设置
     *
     * @param title    文档标题
     * @param author   文档作者
     * @param subject  文档摘要
     * @param keywords 关键字
     * @param creator  创建者
     * */
    public void setAttribute(String title, String author, String subject, String keywords, String creator){
        this.title = title;
        this.author = author;
        this.subject = subject;
        this.keywords = keywords;
        this.creator = creator;
    }

    private String headerLeft = null;
    private String headerCenter = null;
    private String headerRight = null;
    private int headerStart = 1;
    /**
     * 设置页眉信息 (特殊文本意义： P=数字页码； P)
     * @param left    页眉左侧
     * @param center  页眉中
     * @param right   页眉右侧
     * @param start   从第几页开始设置， start 默认 1
     * */
    public void setHeader(String left, String center, String right, int startPage){
        this.headerLeft = left;
        this.headerCenter = center;
        this.headerRight = right;
        this.headerStart = startPage;
    }

    private String footerLeft = null;
    private String footerCenter = null;
    private String footerRight = null;
    private int footerStart = 1;
    /***
     * 设置页脚信息   (特殊文本意义： P=数字页码； PC=第 X 页；PS=第 X 页 / 共 Y 页)
     * @param left    页脚左侧
     * @param center  页脚中
     * @param right   页脚右侧
     * @param start   从第几页开始设置， start 默认 1
     * */
    public void setFooter(String left, String center, String right, int startPage){
        this.footerLeft = left;
        this.footerCenter = center;
        this.footerRight = right;
        this.footerStart = startPage;
    }

    private float pageWidth = 0;
    private float pageHeight = 0;
    private int pageDirection = 0;
    /**
     * 设置页面属性（纸张背景默认颜色，不设置）,写内容之前设置
     *
     * @param pageSize 纸张大小，支持 A4, A3, B5 三种
     * @param rotate   纸张方向，默认 false 高>宽， true 高<宽
     * */
    public void setPage(String pageSize, boolean rotate){
        if(pageSize == null || "".equals(pageSize) || "A4".equals(pageSize)){
            this.pageWidth = PageSize.A4.getWidth();
            this.pageHeight = PageSize.A4.getHeight();
        }else if("A3".equals(pageSize)){
            this.pageWidth = PageSize.A3.getWidth();
            this.pageHeight = PageSize.A3.getHeight();
        }else if("B5".equals(pageSize)){
            this.pageWidth = PageSize.B5.getWidth();
            this.pageHeight = PageSize.B5.getHeight();
        }

        if(rotate){
            this.pageDirection = 1;
        }
    }
    private byte[] userPassword = null;
    private byte[] ownerPassword = null;
    private int permisstions = 0;
    /**
     * 设置用户打开文档的密码和拥有者的密码
     *
     * @param userPwd     用户打开文档的密码，不设置传入null
     * @param ownerPwd    拥有者的保护密码，不设置传入null
     * @param permission  权限位:
     *  0 = 最低权限，只能设备查看，不能复制文本和图片、不能打印、不能标注、不能修改内容和增删页面；
     *  1 = 允许打印、允许复制文本和图片，不能标注、不能修改内容和增删页面；（默认选项）
     *  2 = 允许打印、复制、标注、修改、增删改页面等
     *  示例调用 setSecret(null, "verysecretpassword", 1);
     * */
    public void setSecrety(String userPwd, String ownerPwd,int permission){
        if( !( null==userPwd || "".equals(userPwd) ) ){
            this.userPassword = userPwd.getBytes();
        }
        if( !( null==ownerPwd || "".equals(ownerPwd) ) ){
            this.ownerPassword = ownerPwd.getBytes();
        }

        /**
         * 各标识的意义：
         * PdfWriter.STANDARD_ENCRYPTION_40 = 0  采用标准40bit加密方式
         * PdfWriter.STANDARD_ENCRYPTION_128 = 1 采用标准128bit加密方式
         * PdfWriter.ENCRYPTION_AES_128 = 2      采用标准128bit AES加密方式
         * PdfWriter.ENCRYPTION_AES_256 = 3      采用标准256bit AES加密方式
         * PdfWriter.DO_NOT_ENCRYPT_METADATA = 8 不加密元数据，其他人将可以导出
         *
         * PdfWriter.ALLOW_DEGRADED_PRINTING = 4 允许打印，但不能保证和 PdfWriter.Allow-Printing 一样的质量。
         * PdfWriter.ALLOW_MODIFY_CONTENTS = 8   修改内容许可，比如改变页面内容、或者插入删除页面
         * PdfWriter.ALLOW_COPY =16              拷贝或者提取文本或者图像的许可。
         * PdfWriter.ALLOW_MODIFY_ANNOTATIONS=32 填写注释许可
         * PdfWriter.ALLOW_FILL_IN = 256         填充表单域许可
         * PdfWriter.ALLOW_SCREENREADERS = 512   只能提供给显示器文本和图形
         * PdfWriter.ALLOW_ASSEMBLY = 1024       插入、删除、旋转页面、加入书签的允许。内容不能被改变、除非PdfWriter.ALLOW_MODIFY_CONTENTS设置了许可。
         * PdfWriter.ALLOW_PRINTING = 2052       允许打印文档
         * */

        this.permisstions = PdfWriter.ALLOW_SCREENREADERS;
        if(permission == 1){
            this.permisstions = PdfWriter.ALLOW_SCREENREADERS | PdfWriter.ALLOW_PRINTING | PdfWriter.ALLOW_COPY;
        }else if(permission == 0){
            this.permisstions = PdfWriter.ALLOW_SCREENREADERS;
        }else if(permission == 2){
            this.permisstions = PdfWriter.ALLOW_DEGRADED_PRINTING | PdfWriter.ALLOW_ASSEMBLY | PdfWriter.ALLOW_COPY
                 | PdfWriter.ALLOW_FILL_IN | PdfWriter.ALLOW_MODIFY_ANNOTATIONS | PdfWriter.ALLOW_MODIFY_CONTENTS
                 | PdfWriter.ALLOW_PRINTING | PdfWriter.ALLOW_SCREENREADERS;
        }

    }



    ///------------------------------内容写入---------------------------------

    /**
     * 写入文本内容，重复调用，内容依次向后累加
     *
     * @param listParagraphAndTable  对象包括两种类型：ParagraphData 和 TableData 内容
     * @return 成功返回true； 失败返回 false
     * */
    public byte[] writeContent(ArrayList<Object> listParagraphAndTable){
        try{
            // 设定纸张
            if(this.pageWidth == 0 || this.pageHeight == 0){
                this.pageWidth = PageSize.A4.getWidth();
                this.pageHeight = PageSize.A4.getHeight();
            }
            Rectangle rect = new Rectangle(this.pageWidth, this.pageHeight);
            if(this.pageDirection == 1){
                this.DOC = new Document(rect.rotate());
            }else{
                this.DOC = new Document(rect);
            }
            //设置页面边距
            this.DOC.setMargins(0,0,0,0);
            // 设定输出流
            this.BAOS = new ByteArrayOutputStream();
            // 写入器
            PdfWriter writer = PdfWriter.getInstance(this.DOC, this.BAOS);
            writer.setPdfVersion(PdfWriter.VERSION_1_4);
            // 水印设置
            if(this.watermarkRepeat > 0){ // 需要水印
                if(! (null==this.watermarkText || "".equals(this.watermarkText) )){
                    writer.setPageEvent(new PdfTextWatermarkPageEvent(this.watermarkText, this.watermarkRepeat, 45));
                }
                if(null != watermarkImage){
                    writer.setPageEvent(new PdfPictureWatermarkPageEvent(this.watermarkImage, this.watermarkRepeat, 45));
                }
            }
            // 属性设置
            if(!(this.title==null || "".equals(this.title))){
                this.DOC.addTitle(this.title);
            }
            if(!(this.author==null || "".equals(this.author))){
                this.DOC.addAuthor(this.author);
            }
            if(!(this.subject==null || "".equals(this.subject))){
                this.DOC.addSubject(this.subject);
            }
            if(!(this.creator==null || "".equals(this.creator))){
                this.DOC.addCreator(this.creator);
            }
            if(!(this.keywords==null || "".equals(this.keywords))){
                this.DOC.addKeywords(this.keywords);
            }
            // 页眉页脚设置
            if(this.headerLeft==null || this.headerCenter==null || this.headerRight==null || this.footerLeft==null || this.footerCenter==null || this.footerRight==null){
                writer.setPageEvent(new PdfHeaderFooterPageEvent(this.headerLeft, this.headerCenter, this.headerRight, this.headerStart, this.footerLeft, this.footerCenter, this.footerRight, this.footerStart));
            }
            // 设置安全属性
            if( this.userPassword!=null || this.ownerPassword!=null){
                writer.setEncryption(this.userPassword, this.ownerPassword, this.permisstions, PdfWriter.STANDARD_ENCRYPTION_128);
            }

            // 写入内容(段落+表格)
            this.DOC.open();
            this.DOC.setMarginMirroringTopBottom(true);
            for(Object o : listParagraphAndTable){
                if(null == o){
                    this.DOC.newPage();
                }else if(o instanceof ParagraphData){
                    this.DOC.add( ((ParagraphData)o).getData() );
                }else if(o instanceof TableData){
                    this.DOC.add( ((TableData)o).getTable());
                }
            }
            this.DOC.close();
            return this.BAOS.toByteArray();
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }




    ///------------------------------页面事件类---------------------------------

    /**
     * 为PDF分页时添加“文本”水印的事件
     * 初始化时指定水印文本内容
     */
    private class PdfTextWatermarkPageEvent extends PdfPageEventHelper{
        private String text;
        private int repeat;
        private int degree;
        public PdfTextWatermarkPageEvent(String watermarkText, int repeatTimes, int rotateDegree){
            this.text = watermarkText;
            this.repeat = repeatTimes;
            this.degree = rotateDegree;
        }
        public void onEndPage(PdfWriter writer, Document document) {
            try{
                // 获取PDF内容正文页面宽度
                float pageWidth = document.right()+document.left();
                // 获取PDF内容正文页面高度
                float pageHeight = document.top()+document.bottom();
                // 设置水印字体格式
                PdfContentByte waterMarkPdfContent = writer.getDirectContentUnder();
                Font waterFont = new Font(getCNFont(), 20, Font.BOLD, BaseColor.CYAN);
                Phrase phrase = new Phrase(this.text, waterFont);

                // 位置的文字信息，可调整
                if(this.repeat == 1){ // 显示一次，则只居中倾斜45度显示
                    ColumnText.showTextAligned(waterMarkPdfContent, Element.ALIGN_CENTER, phrase, pageWidth*0.45f, pageHeight*0.5f, this.degree);
                }else if(this.repeat == 2){ // 居中，上下均匀显示两次
                    ColumnText.showTextAligned(waterMarkPdfContent, Element.ALIGN_CENTER, phrase, pageWidth*0.45f, pageHeight*0.3f, this.degree);
                    ColumnText.showTextAligned(waterMarkPdfContent, Element.ALIGN_CENTER, phrase, pageWidth*0.45f, pageHeight*0.7f, this.degree);
                }else if(this.repeat == 3){ // 居中，上下均匀显示三次
                    ColumnText.showTextAligned(waterMarkPdfContent, Element.ALIGN_CENTER, phrase, pageWidth*0.45f, pageHeight*0.25f, this.degree);
                    ColumnText.showTextAligned(waterMarkPdfContent, Element.ALIGN_CENTER, phrase, pageWidth*0.45f, pageHeight*0.50f, this.degree);
                    ColumnText.showTextAligned(waterMarkPdfContent, Element.ALIGN_CENTER, phrase, pageWidth*0.45f, pageHeight*0.75f, this.degree);
                }else if(this.repeat == 4){ // 阵列显示4个
                    ColumnText.showTextAligned(waterMarkPdfContent, Element.ALIGN_CENTER, phrase, pageWidth*0.25f, pageHeight*0.3f, this.degree);
                    ColumnText.showTextAligned(waterMarkPdfContent, Element.ALIGN_CENTER, phrase, pageWidth*0.25f, pageHeight*0.7f, this.degree);
                    ColumnText.showTextAligned(waterMarkPdfContent, Element.ALIGN_CENTER, phrase, pageWidth*0.65f, pageHeight*0.3f, this.degree);
                    ColumnText.showTextAligned(waterMarkPdfContent, Element.ALIGN_CENTER, phrase, pageWidth*0.65f, pageHeight*0.7f, this.degree);
                }else if(this.repeat == 5){ // 中间一个，四角4个
                    ColumnText.showTextAligned(waterMarkPdfContent, Element.ALIGN_CENTER, phrase, pageWidth*0.45f, pageHeight*0.5f, this.degree);
                    ColumnText.showTextAligned(waterMarkPdfContent, Element.ALIGN_CENTER, phrase, pageWidth*0.25f, pageHeight*0.3f, this.degree);
                    ColumnText.showTextAligned(waterMarkPdfContent, Element.ALIGN_CENTER, phrase, pageWidth*0.25f, pageHeight*0.7f, this.degree);
                    ColumnText.showTextAligned(waterMarkPdfContent, Element.ALIGN_CENTER, phrase, pageWidth*0.65f, pageHeight*0.3f, this.degree);
                    ColumnText.showTextAligned(waterMarkPdfContent, Element.ALIGN_CENTER, phrase, pageWidth*0.65f, pageHeight*0.7f, this.degree);
                }else if(this.repeat == 6){
                    ColumnText.showTextAligned(waterMarkPdfContent, Element.ALIGN_CENTER, phrase, pageWidth*0.25f, pageHeight*0.25f, this.degree);
                    ColumnText.showTextAligned(waterMarkPdfContent, Element.ALIGN_CENTER, phrase, pageWidth*0.25f, pageHeight*0.50f, this.degree);
                    ColumnText.showTextAligned(waterMarkPdfContent, Element.ALIGN_CENTER, phrase, pageWidth*0.25f, pageHeight*0.75f, this.degree);
                    ColumnText.showTextAligned(waterMarkPdfContent, Element.ALIGN_CENTER, phrase, pageWidth*0.65f, pageHeight*0.25f, this.degree);
                    ColumnText.showTextAligned(waterMarkPdfContent, Element.ALIGN_CENTER, phrase, pageWidth*0.65f, pageHeight*0.50f, this.degree);
                    ColumnText.showTextAligned(waterMarkPdfContent, Element.ALIGN_CENTER, phrase, pageWidth*0.65f, pageHeight*0.75f, this.degree);
                }
            }catch(Exception de) {
                de.printStackTrace();
            }
        }
    }

    /**
     * 为PDF分页时添加“图片”水印的事件
     * 初始化时指定水印图片二进制数据
     */
    private class PdfPictureWatermarkPageEvent extends PdfPageEventHelper{
        private byte[] pic;
        private int repeat;
        private int rotate;
        private Image waterMarkImage = null;

        public PdfPictureWatermarkPageEvent(byte[] picture, int repeatTimes, int rotateDegree){
            this.pic = picture;
            this.repeat = repeatTimes;
            this.rotate = rotateDegree;
            try{
                this.waterMarkImage = Image.getInstance(this.pic);
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        // 这些参数可调
        public Image getWaterMarkImage(float xPosition,float yPosition){
            // 坐标
            this.waterMarkImage.setAbsolutePosition(xPosition, yPosition);
            // 旋转 弧度
            this.waterMarkImage.setRotation(-20);
            // 旋转 角度
            this.waterMarkImage.setRotationDegrees(this.rotate);
            // 依照比例缩放
            this.waterMarkImage.scalePercent(100);
            // 以底图方式显示
            this.waterMarkImage.setAlignment(Image.UNDERLYING);
            return this.waterMarkImage;
        }
        public void onEndPage(PdfWriter writer, Document document) {
            try{
                // 获取 PDF 内容正文页面宽度
                float pageWidth = document.right()+document.left();
                // 获取 PDF 内容正文页面高度
                float pageHeight = document.top()+document.bottom();
                PdfContentByte waterMarkPdfContent = writer.getDirectContentUnder();
                // 水印图片，位置可调整
                if(this.repeat == 1){ // 显示一次，则只居中倾斜45度显示
                    waterMarkPdfContent.addImage(getWaterMarkImage(pageWidth*0.45f, pageHeight*0.5f));
                }else if(this.repeat == 2){ // 居中，上下均匀显示两次
                    waterMarkPdfContent.addImage(getWaterMarkImage(pageWidth*0.4f, pageHeight*0.3f));
                    waterMarkPdfContent.addImage(getWaterMarkImage(pageWidth*0.4f, pageHeight*0.7f));
                }else if(this.repeat == 3){ // 居中，上下均匀显示三次
                    waterMarkPdfContent.addImage(getWaterMarkImage(pageWidth*0.4f, pageHeight*0.25f));
                    waterMarkPdfContent.addImage(getWaterMarkImage(pageWidth*0.4f, pageHeight*0.50f));
                    waterMarkPdfContent.addImage(getWaterMarkImage(pageWidth*0.4f, pageHeight*0.75f));
                }else if(this.repeat == 4){ // 阵列显示4个
                    waterMarkPdfContent.addImage(getWaterMarkImage(pageWidth*0.25f, pageHeight*0.3f));
                    waterMarkPdfContent.addImage(getWaterMarkImage(pageWidth*0.25f, pageHeight*0.7f));
                    waterMarkPdfContent.addImage(getWaterMarkImage(pageWidth*0.65f, pageHeight*0.3f));
                    waterMarkPdfContent.addImage(getWaterMarkImage(pageWidth*0.65f, pageHeight*0.7f));
                }else if(this.repeat == 5){ // 中间一个，四角4个
                    waterMarkPdfContent.addImage(getWaterMarkImage(pageWidth*0.25f, pageHeight*0.3f));
                    waterMarkPdfContent.addImage(getWaterMarkImage(pageWidth*0.25f, pageHeight*0.7f));
                    waterMarkPdfContent.addImage(getWaterMarkImage(pageWidth*0.4f, pageHeight*0.45f));
                    waterMarkPdfContent.addImage(getWaterMarkImage(pageWidth*0.65f, pageHeight*0.3f));
                    waterMarkPdfContent.addImage(getWaterMarkImage(pageWidth*0.65f, pageHeight*0.7f));
                }else if(this.repeat == 6){
                    waterMarkPdfContent.addImage(getWaterMarkImage(pageWidth*0.25f, pageHeight*0.25f));
                    waterMarkPdfContent.addImage(getWaterMarkImage(pageWidth*0.25f, pageHeight*0.50f));
                    waterMarkPdfContent.addImage(getWaterMarkImage(pageWidth*0.25f, pageHeight*0.75f));
                    waterMarkPdfContent.addImage(getWaterMarkImage(pageWidth*0.65f, pageHeight*0.25f));
                    waterMarkPdfContent.addImage(getWaterMarkImage(pageWidth*0.65f, pageHeight*0.50f));
                    waterMarkPdfContent.addImage(getWaterMarkImage(pageWidth*0.65f, pageHeight*0.75f));
                }
                PdfGState gs = new PdfGState();
                //设置透明度为0.2
                gs.setFillOpacity(0.2f);
                waterMarkPdfContent.setGState(gs);
            }catch(Exception de) {
                de.printStackTrace();
            }
        }
    }

    /**
     * 创建页眉、页脚的分页事件辅助类
     *
     * @param h1  页眉，左侧，null 表示留空
     * @param h2  页眉，中间，null 表示留空
     * @param h3  页眉，右侧，null 表示留空
     * @param f1  页脚，左侧，null 表示留空
     * @param f2  页脚，中间，null 表示留空
     * @param f3  页脚，右侧，null 表示留空
     * 以上参数中，输入 P = 显示页码数字， PC = 显示 第 X 页， PS = 显示 第 X 页 ，共 S 页
     * ****输入 P/PC 应提供 start 值，默认 1，表示从第一页开始计算页码（从1开始），开始页适用其他样式的页眉和页脚
     * ****输入 PS 应事先计算好总页数，传入pageAll，PDF 填充时无法自动计算和更新总页数，所以不建议采用PS
     * */
    private class PdfHeaderFooterPageEvent extends PdfPageEventHelper{
        private String h1 = null;
        private String h2 = null;
        private String h3 = null;
        private int hs = 1;
        private String f1 = null;
        private String f2 = null;
        private String f3 = null;
        private int fs = 1;
        private PdfTemplate total = null;
        private int psPos = 0; // 总页码位置：0 无  1 页眉 2 页脚
        // 全部参数
        public PdfHeaderFooterPageEvent(String headerLeft, String headerCenter, String headerRight, int headerStart,
                String footerLeft, String footerCenter, String footerRight, int footerStart){
            this.h1 = headerLeft;
            this.h2 = headerCenter;
            this.h3 = headerRight;
            this.hs = headerStart;

            this.f1 = footerLeft;
            this.f2 = footerCenter;
            this.f3 = footerRight;
            this.fs = footerStart;
        }
        public void onOpenDocument(PdfWriter writer,Document document){
            // 有需要显示 共 Y 页时才创建模板， 模板足够宽，容许三位数字
            if("PS".equals(f2) || "PS".equals(f3) || "PS".equals(f1)){
                total = writer.getDirectContent().createTemplate(50, 16);
                psPos = 2;
            }else if("PS".equals(h2) || "PS".equals(h1) || "PS".equals(h3)){
                total = writer.getDirectContent().createTemplate(50, 16);
                psPos = 1;
            }
        }
        public void onCloseDocument(PdfWriter writer,Document document){
            try{
                // 最后填充模板数据
                if(total != null){
                    total.beginText();
                    total.setFontAndSize(getCNFont(), 8);
                    int showPage = writer.getPageNumber() - 1;
                    if(psPos == 2){
                        showPage = writer.getPageNumber() - fs;
                    }else if(psPos == 1){
                        showPage = writer.getPageNumber() - hs;
                    }
                    String yYe = String.format("%d 页", showPage);
                    total.showText(yYe);
                    total.endText();
                    total.closePath();
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        public void showText(PdfContentByte cb, String text, int align, int curentPage, int startPage, float posX, float posY, float rotate){
            try{
                // 条件判断
                if(text==null || "".equals(text)){
                    return;
                }
                if(curentPage < startPage){
                    return;
                }
                // align=0 居左，1 居中，2 居右
                // posX, posY 的意义，居左时，传入左侧点, 居中时，传入中心点，居右时，传入右侧点
                // 因此写入模板时，需要根据字符串长度适当做偏移调整。
                // ************ 共 Y 页，目前该方法可以显示，但输出的文件打开时提示“ 本页面上存在错误”，原因不明
                int showPage = curentPage-startPage+1;
                if("P".equals(text)){
                    String result = String.format("- %d -", showPage);
                    cb.showTextAligned(align, result, posX, posY, rotate);
                }else if("PC".equals(text)){
                    String result = String.format("第 %d 页", showPage);
                    cb.showTextAligned(align, result, posX, posY, rotate);
                }else if("PS".equals(text)){
                    String result = String.format("第 %d 页 / 共 ", showPage);
                    float len = getCNFont().getWidthPoint(result, 8);
                    if(align == 0){
                        cb.showTextAligned(PdfContentByte.ALIGN_LEFT, result, posX, posY, rotate);
                        cb.addTemplate(total, posX + len, posY);
                    }else if(align == 1){
                        cb.showTextAligned(PdfContentByte.ALIGN_LEFT, result, posX-50, posY, rotate);
                        cb.addTemplate(total, posX-50+len, posY);
                    }else if(align == 2){
                        cb.showTextAligned(PdfContentByte.ALIGN_LEFT, result, posX-len-60, posY, rotate);
                        cb.addTemplate(total, posX-60, posY);
                    }
                }else{ // 原样输出
                    cb.showTextAligned(align, text, posX, posY, rotate);
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        public void onEndPage(PdfWriter writer, Document document) {
            try{
                int curPage = writer.getPageNumber();
                PdfContentByte cb = writer.getDirectContent();
                cb.saveState();
                cb.beginText();
                cb.setFontAndSize(getCNFont(), 8);
                // Header
                float topY = document.top() + 20;
                // Header left
                showText(cb, h1, PdfContentByte.ALIGN_LEFT, curPage, hs, document.left(), topY, 0);
                // Header center
                showText(cb, h2, PdfContentByte.ALIGN_CENTER, curPage, hs,  (document.right() + document.left())/2, topY, 0);
                //  Header right
                showText(cb, h1, PdfContentByte.ALIGN_RIGHT, curPage, hs, document.right(), topY, 0);
                // Footer
                float footY = document.bottom() - 20;
                // Footer left
                showText(cb, f1, PdfContentByte.ALIGN_LEFT, curPage, fs, document.left(), footY, 0);
                // Footer center
                showText(cb, f2, PdfContentByte.ALIGN_CENTER, curPage, fs, (document.right() + document.left())/2, footY, 0);
                // Footer right
                showText(cb, f3, PdfContentByte.ALIGN_RIGHT, curPage, fs, document.right(), footY, 0);
                // 写入完毕
                cb.endText();
                cb.restoreState();
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }


    /**
     * 传入值不得为空
     * */
    public static BaseColor getColor(String v){
        // check
        if(null==v||"".equals(v)){
            return null;
        }
        //
        BaseColor bc = BaseColor.BLACK;
        // BLACK BLUE CYAN DARK_GRAY GRAY GREEN LIGHT_GRAY MAGENTA ORANGE PINK RED WHITE YELLOW
        switch(v){
            case "BLACK":
                bc = BaseColor.BLACK;
                break;
            case "BLUE":
                bc =  BaseColor.BLUE;
                break;
            case "CYAN":
                bc =  BaseColor.CYAN;
                break;
            case "DARK_GRAY":
                bc =  BaseColor.DARK_GRAY;
                break;
            case "GRAY":
                bc =  BaseColor.GRAY;
                break;
            case "GREEN":
                bc =  BaseColor.GREEN;
                break;
            case "LIGHT_GRAY":
                bc =  BaseColor.LIGHT_GRAY;
                break;
            case "MAGENTA":
                bc =  BaseColor.MAGENTA;
                break;
            case "ORANGE":
                bc =  BaseColor.ORANGE;
                break;
            case "PINK":
                bc =  BaseColor.PINK;
                break;
            case "RED":
                bc =  BaseColor.RED;
                break;
            case "WHITE":
                bc =  BaseColor.WHITE;
                break;
            case "YELLOW":
                bc =  BaseColor.YELLOW;
                break;
            default:
                bc = BaseColor.BLACK;
                break;
        }
        return bc;
    }
}
