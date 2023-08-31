package com.fudian.project.ams.utils;


import com.fudian.api.ams.pojo.A01;
import com.fudian.api.ams.pojo.E01z1;
import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.*;
import java.text.DecimalFormat;
import java.text.Format;
import java.util.List;

public class CreatePersonXml {

    /**
     * 根据A01及材料信息生成xml
     * @param a01
     * @param list
     * @param folder 生成后存放路径
     * @return
     */
    public static boolean createXmlPersonAndFolder(A01 a01, List<E01z1> list, String folder){
        try{
            // 创建Document
            Document document = DocumentHelper.createDocument();
//            list.stream().sorted();
            document.setXMLEncoding("UTF-8");
            // 添加根节点
            Element root = document.addElement("数字档案","http://www.w3.org/2001/XMLSchema");

            // 在根节点下添加第一个子节点
            Element oneChildElement= root.addElement("人员基本信息");

            oneChildElement.addElement("姓名").addText(a01.getA0101());
            oneChildElement.addElement("性别").addText(a01.getA0104());
            oneChildElement.addElement("民族").addText(a01.getA0117());
            oneChildElement.addElement("出生日期").addText(a01.getA0107());
            oneChildElement.addElement("公民身份号码").addText(a01.getA0184()==null?"":a01.getA0184());


            if(list!=null&&list.size()>0) {
                // 在根节点下添加第二个子节点
                Element twoChildElement= root.addElement("目录信息");
                for (E01z1 e01z1 : list) {
                    Element twoChildElementChild = twoChildElement.addElement("档案目录条目");
                    if(e01z1.getE01z101().contains("-")){
                        twoChildElementChild.addElement("类号").addText(e01z1.getE01z101());
                    }else {
                        twoChildElementChild.addElement("类号").addText(ChineseNumToArabicNumUtil.arabicNumToChineseNum(Integer.parseInt(e01z1.getE01z101())));
                    }
                    twoChildElementChild.addElement("序号").addText(e01z1.getE01z104().toString()==null?"":e01z1.getE01z104().toString());
                    twoChildElementChild.addElement("材料名称").addText(e01z1.getE01z111a()==null?"":e01z1.getE01z111a());
                    twoChildElementChild.addElement("材料形成时间").addText(e01z1.getE01z117()==null?"":e01z1.getE01z117());
                    twoChildElementChild.addElement("页数").addText(e01z1.getE01z114()!=null?e01z1.getE01z114().toString():"0");
                    twoChildElementChild.addElement("备注").addText(e01z1.getE01z121()!=null?e01z1.getE01z121():"");
                    if(e01z1.getE01z114()!=null) {
                        for (int i = 0; i < e01z1.getE01z114(); i++) {
                            Format f1 = new DecimalFormat("000");
                            twoChildElementChild.addElement("原始图像数据_Text").addText(e01z1.getE01z101() + "-" + e01z1.getE01z104().toString() + "-" + f1.format(i) + ".JPG");
                        }
                    }
                }
            }
            OutputFormat format = OutputFormat.createPrettyPrint();
            XMLWriter writer = new XMLWriter( new FileOutputStream(folder+"/"+a01.getA0101()+a01.getA0184()+".xml"), format);
            writer.write(document);
            writer.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void createDom4j(File file){
        try{
            // 创建Document
            Document document = DocumentHelper.createDocument();
            document.setXMLEncoding("UTF-8");
            // 添加根节点
            Element root = document.addElement("数字档案","");

            // 在根节点下添加第一个子节点
            Element oneChildElement= root.addElement("人员基本信息");

            oneChildElement.addElement("姓名")
                    .addText("梁健华");
            oneChildElement.addElement("性别")
                    .addText("男");

            // 在根节点下添加第二个子节点
            Element twoChildElement= root.addElement("目录信息");
            Element twoChildElementChild = twoChildElement.addElement("档案目录条目");
            twoChildElementChild.addElement("类号").addText("一");
            twoChildElementChild.addElement("序号").addText("1");
            twoChildElementChild.addElement("材料名称").addText("干部履历表");
            twoChildElementChild.addElement("材料形成时间").addText("198807");
            twoChildElementChild.addElement("页数").addText("15");
            twoChildElementChild.addElement("备注").addText("123213");
            for(int i=0;i<10;i++){
                Format f1 = new DecimalFormat("000");
                twoChildElementChild.addElement("原始图像数据_Text").addText("1-1-"+f1.format(i)+".JPG");
            }
            OutputFormat format = OutputFormat.createPrettyPrint();
            XMLWriter writer = new XMLWriter( new FileOutputStream(file), format);
            writer.write(document);

            System.out.println("dom4j CreateDom4j success!");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
