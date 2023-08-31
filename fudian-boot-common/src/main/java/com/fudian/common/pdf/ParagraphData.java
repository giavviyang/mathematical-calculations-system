package com.fudian.common.pdf;


import com.itextpdf.text.*;


/**
 * PDF 的一个段落
 *
 * 段落可以包含的内容有：纯文本、带格式的块、标题短语、链接、图片等。
 * 说明：如果图片希望独占一行，应单独作为一个段落对象；否则图片将是与其他文本等内容混排。
 * */
public class ParagraphData {
    /**
     * 具体段落对象数据
     * */
    private Paragraph P = null;


    /**
     * 默认结构化
     * */
    public ParagraphData(){
        init();
    }
    /**
     * 构造函数，并添加默认段落文本，最简单的一个段落构造
     * */
    public ParagraphData(String text){
        init();
        this.P.add(text);
    }

    /**
     * 初始化
     * */
    private void init(){
        try{
            this.P = new Paragraph();
            // 段落默认字体
            Font fontDefault = new Font(PdfExport.getCNFont(), 10, Font.NORMAL, BaseColor.BLACK);
            this.P.setFont(fontDefault);
            // 默认缩进两个汉字的宽度，具体缩进量和字号有关系
            this.P.setFirstLineIndent(21f);


            // 段落默认居左
            this.P.setAlignment(Element.ALIGN_LEFT);
        }catch(Exception ex){
            System.out.println("create chinese font failed, the itext-asian.jar may be missed.");
            ex.printStackTrace();
        }
    }

    /**
     * 返回段落数据
     * */
    public Paragraph getData(){
        return this.P;
    }

    /**
     * 修改段落首行缩进量（默认两个汉字宽度）
     * @param indent 缩进量
     * */
    public void setFirstIndent(float indent){
        this.P.setFirstLineIndent(indent);
    }

    /**
     * 修改段落默认排列方式(默认居左)
     * @param align   1=居左； 2=居中； 3=居右； 4=两边
     * */
    public void setAlign(int align){
        if(align == 1){
            this.P.setAlignment(Element.ALIGN_LEFT);
        }else if(align == 2){
            this.P.setAlignment(Element.ALIGN_CENTER);
        }else if(align == 3){
            this.P.setAlignment(Element.ALIGN_RIGHT);
        }else if(align == 4){
            this.P.setAlignment(Element.ALIGN_JUSTIFIED);
        }else {
            this.P.setAlignment(Element.ALIGN_LEFT);
        }
    }

    /**
     * 修改段落默认字体，宋体不可修改
     *
     * @param fontSize  字号 points， 默认10
     * @param bold      是否加粗，true 加粗，false 不加粗（默认）
     * @param color     字体颜色，默认（BLACK） BLACK BLUE CYAN DARK_GRAY GRAY GREEN LIGHT_GRAY MAGENTA ORANGE PINK RED WHITE YELLOW
     * @param underline 是否有下划线，true 有，false 无（默认）
     * @param italic    是否斜体，true 斜体， false 不倾斜（默认）
     * */
    public void setFont(int fontSize, boolean bold, String color, boolean underline, boolean italic){
        int format = Font.NORMAL;
        if(bold){
            format |= Font.BOLD;
        }
        if(italic){
            format |= Font.ITALIC;
        }
        if(underline){
            format |= Font.UNDERLINE;
        }
        Font fontDefault = null;
        if(null==color||"".equals(color)){
            fontDefault = new Font(PdfExport.getCNFont(), fontSize, format);
        }else{
            fontDefault = new Font(PdfExport.getCNFont(), fontSize, format, PdfExport.getColor(color));
        }
        this.P.setFont(fontDefault);
    }

    /**
     * 段落前后空白高度（默认由PDF自动控制）
     *
     * @param before  段落前空白高度
     * @param after   段落后空白高度
     * @param leading 段落内行间距
     * @param left    段落左侧整体缩进
     * @param right   段落整体右侧缩进
     * */
    public void setSpacing(float before, float after, float leading, float left, float right){
        this.P.setSpacingAfter(after);
        this.P.setSpacingBefore(before);
        this.P.setLeading(leading);
        this.P.setIndentationLeft(left);
        this.P.setIndentationRight(right);
    }

    /**
     * 添加默认格式文本
     *
     * @param value 文本内容
     * */
    public void appendText(String text){
        this.P.add(text);
    }

    /**
     * 添加文档标题或章节标题
     * 特点：独占一行、居中或居左，需要单独一个段落
     * @param text   标题文本
     * @param align  1=居左，2=居中，3=居右
     * @param points 字体大小，14  12 等
     * @param bold   是否加粗，true 默认
     * @param color  颜色，BLACK BLUE CYAN DARK_GRAY GRAY GREEN LIGHT_GRAY MAGENTA ORANGE PINK RED WHITE YELLOW
     * @param bgcolor 背景色，默认 null
     * */
    public void appendTitle(String text, int align, int points, boolean bold, String color, String bgcolor){
        if(null==text || "".equals(text)){
            return;
        }

        try{
            int format = Font.NORMAL;
            if(bold){
                format |= Font.BOLD;
            }
            Font font = null;
            if(null==color||"".equals(color)){
                font = new Font(PdfExport.getCNFont(), points, format);
            }else{
                font = new Font(PdfExport.getCNFont(), points, format, PdfExport.getColor(color));
            }
            Chunk ct = new Chunk(text, font);
            if(!(null==bgcolor||"".equals(bgcolor))){
                ct.setBackground(PdfExport.getColor(bgcolor),1f, 0.5f, 1f, 1.5f);
            }
            this.P.add(ct);
            this.P.setFirstLineIndent(0);
            if(align == 1){
                this.P.setAlignment(Element.ALIGN_LEFT);
            }else if(align == 2){
                this.P.setAlignment(Element.ALIGN_CENTER);
            }else if(align == 3){
                this.P.setAlignment(Element.ALIGN_RIGHT);
            }else{
                this.P.setAlignment(Element.ALIGN_LEFT);
            }
            this.P.setSpacingAfter(15f);
            this.P.setSpacingBefore(15f);
        }catch(Exception ex){
            System.out.println(text);
            ex.printStackTrace();
        }
    }

    /**
     * 添加指定样式的文本块、词语
     *
     * @param text    文本内容
     * @param points  像素，字体大小
     * @param bold    是否加粗
     * @param color   字体颜色，支持：BLACK BLUE CYAN DARK_GRAY GRAY GREEN LIGHT_GRAY MAGENTA ORANGE PINK RED WHITE YELLOW
     * @param bgcolor 字体底色，支持同上
     * @param underline  是否下划线
     * @param italic     是否斜体
     * */
    public void appendFormatText(String text, int points, boolean bold, String color, String bgcolor, boolean underline, boolean italic){
        if(null==text || "".equals(text)){
            return;
        }
        try{
            int format = Font.NORMAL;
            if(bold){
                format |= Font.BOLD;
            }
            if(italic){
                format |= Font.ITALIC;
            }
            if(underline){
                format |= Font.UNDERLINE;
            }
            Font font = null;
            if(null==color||"".equals(color)){
                font = new Font(PdfExport.getCNFont(), points, format);
            }else{
                font = new Font(PdfExport.getCNFont(), points, format, PdfExport.getColor(color));
            }
            Chunk ct = new Chunk(text, font);
            if(!(null==bgcolor||"".equals(bgcolor))){
                ct.setBackground(PdfExport.getColor(bgcolor),1f, 0.5f, 1f, 1.5f);
            }
            this.P.add(ct);
        }catch(Exception ex){
            System.out.println(text);
            ex.printStackTrace();
        }
    }

    /**
     * 添加超链接
     * @param text  链接文字
     * @param name  链接名称，用于内部跳转，无跳转可以设置为null， 譬如： test
     * @param url   链接地址，可以是一个 http 地址， 也可以是上面 name 值，譬如： #test
     * */
    public void appendAnchor(String text, String name, String url){
        // 需要创建一个新的字体******
        Font font = new Font(PdfExport.getCNFont());
        font.setColor(BaseColor.BLUE);
        font.setSize(this.P.getFont().getSize());
        Anchor a = new Anchor(text, font);
        if(!(null==name || "".equals(name))){
            a.setName(name);
        }
        if(!(null==url || "".equals(url))){
            a.setReference(url);
        }
        this.P.add(a);
    }

    /**
     * 添加一个图片
     * @param image  图片二进制数据
     * @param align  图片排列方式 1=居左； 2=居中； 3=居右
     * @param wrap   图片是否文字环绕， 1=文字环绕图形； 2=图形作为文字背景
     * @param width  图片宽，0表示保持原来尺寸，此时 height 也该设为0
     * @param height 图片高，0表示保持原来尺寸，此时 width 也该设为0
     * @param border 边框宽度，0表示无边框
     * @param borderColor   边框颜色，border > 0 时有效
     * @param rotateDegree  图片旋转角度
     * */
    public void appendImage(byte[] image, int align, int wrap, float width, float height, int boder, String borderColor, int rotateDegree){
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
            }
            if(!( null==borderColor || "".equals(borderColor) )){
                img.setBorderColor(PdfExport.getColor(borderColor));
            }

            if(rotateDegree != 0){
                img.setRotationDegrees(rotateDegree);
            }

            this.P.add(img);
        }catch(Exception ex){
            System.out.println("********** appendImage failed. **********");
            ex.printStackTrace();
        }
    }

    /**
     * 强制换行
     * @param num >=1 表示插入空行的数量
     * */
    public void appendNewline(int num){
        for(int i=0; i<num; i++){
            this.P.add(Chunk.NEWLINE);
        }
    }



}
