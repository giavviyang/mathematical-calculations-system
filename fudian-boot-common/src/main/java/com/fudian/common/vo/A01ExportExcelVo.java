package com.fudian.common.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Description 干部信息导出Excel类
 * @ClassName A01ExportExcelVo
 * @Author mr.zhang
 * @Date 2020/4/1 14:54
 * @Version 1.0.0
 **/
@Data
@EqualsAndHashCode(callSuper = false)
public class A01ExportExcelVo implements java.io.Serializable {

    private static final long serialVersionUID = 2121L;

    /**
     * 序号
     */
    @Excel(name = "序号", width = 15 , type = 10)
    private Integer xh;

    /**
     * 姓名
     */
    @Excel(name = "干部姓名", width = 15)
    private String a0101;

    /**
     * 编号
     */
    //@Excel(name = "干部编号", width = 15)
    //private String number;

    /**
     * 部门
     */
    @Excel(name = "部门", width = 15)
    private String deptId;

    /**
     * 出生日期
     */
    @Excel(name = "出生日期", width = 15)
    private String a0107;

    /**
     * 参加组织日期
     */
    @Excel(name = "入党日期", width = 15)
    private String a0144;

    /**
     * 参加工作日期
     */
    @Excel(name = "参加工作日期", width = 15)
    private String a0134;

    /**
     * 学历（ZB64）
     */
    @Excel(name = "学历", width = 15)
    private String education;

    /**
     * 干部状态（FDA01State）
     */
    @Excel(name = "干部状态", width = 15)
    private String state;

    /**
     * 编制类别
     */
    //@Excel(name = "编制类别", width = 15)
    //private String data4;

    /*public void setData4(String data4) {
        switch (data4) {
            case "0":
                this.data4 = "海关";
                break;
            case "1":
                this.data4 = "缉私";
                break;
            case "2":
                this.data4 = "事业";
                break;
            case "3":
                this.data4 = "企业";
                break;
            case "4":
                this.data4 = "其他";
                break;
            default:
                this.data4 = data4;
        }
    }*/

    /**
     * 职务
     */
    @Excel(name = "职务", width = 15)
    private String duty;

    /**
     * 隶属组织机构
     */
    //@Excel(name = "隶属组织机构", width = 15)
    //private String orgId;

    /**
     * 个人身份(GB/T2261.4 GB22614)
     */
    @Excel(name = "个人身份", width = 15)
    private String a0151;

//    /**
//     * 序号
//     */
//    @Excel(name = "序号", width = 15)
//    private String showIndex;



    /**
     * 姓名拼音缩写(新增)
     */
    //@Excel(name = "姓名拼音缩写", width = 15)
    //private String a0102;

    /**
     * 性别(GB/T2261.1 GB22611)
     */
    //@Excel(name = "性别", width = 15)
    //private String a0104;



    /**
     * 籍贯名称
     */
    //@Excel(name = "籍贯名称", width = 15)
    //private String a0111a;

    /**
     * 籍贯代码(ZB01)
     */
    //@Excel(name = "籍贯代码", width = 15)
    //private String a0111b;

    /**
     * 出生地名称
     */
    //@Excel(name = "出生地名称", width = 15)
    //private String a0114a;

    /**
     * 出生地代码(ZB01)
     */
    //@Excel(name = "出生地代码", width = 15)
    //private String a0114b;

    /**
     * 成长地名称
     */
    //@Excel(name = "成长地名称", width = 15)
    //private String a0115a;

    /**
     * 成长地代码(ZB01)
     */
    //@Excel(name = "成长地代码", width = 15)
    //private String a0115b;

    /**
     * 民族(GB3304)
     */
    //@Excel(name = "名族", width = 15)
    //private String a0117;

    /**
     * 健康状况(GB22613)
     */
    //@Excel(name = "健康状况", width = 15)
    //private String a0127;

    /**
     * 健康状况描述
     */
    //@Excel(name = "健康状况描述", width = 15)
    //private String a0128;

    /**
     * 婚姻状况(GB22612)
     */
    //@Excel(name = "婚姻状况", width = 15)
    //private String a0131;



    /**
     * 工龄计算校正值
     */
    //@Excel(name = "工龄计算校正值", width = 15)
    //private String a0137;

    /**
     * 政治面貌(GB4762)
     */
    //@Excel(name = "政治面貌", width = 15)
    //private String a0141;


    /**
     * 现身份起始日期
     */
    //@Excel(name = "现身份起始日期", width = 15)
    //private String a0154;

    /**
     * 人事关系所在单位名称(编码修订)
     */
    //@Excel(name = "人事关系所在单位名称", width = 15)
    //private String a0157a;

    /**
     * 人事关系所在单位代码(编码修订)(ZB02)
     */
    //@Excel(name = "人事关系所在单位代码", width = 15)
    //private String a0157b;

    /**
     * 人事关系所在单位所在政区(ZB01)
     */
    //@Excel(name = "人事关系所在单位所在政区", width = 15)
    //private String a0161;

    /**
     * 人事关系所在单位隶属关系(ZB87)
     */
    //@Excel(name = "人事关系所在单位隶属关系", width = 15)
    //private String a0164;

    /**
     * 人事关系所在单位级别(ZB03)
     */
    //@Excel(name = "人事关系所在单位级别", width = 15)
    //private String a0167;

    /**
     * 人事关系所在单位性质类别(ZB04)
     */
    //@Excel(name = "人事关系所在单位性质类别", width = 15)
    //private String a0171;

    /**
     * 人事关系所在单位所属行业(GB/T4754)
     */
    //@Excel(name = "人事关系所在单位所属行业", width = 15)
    //private String a0174;

    /**
     * 户口性质(ZB07)
     */
    //@Excel(name = "户口性质", width = 15)
    //private String a0177;

    /**
     * 户籍所在地(ZB01)
     */
    //@Excel(name = "户籍所在地", width = 15)
    //private String a0181a;

    /**
     * 户籍所在地
     */
    //@Excel(name = "户籍所在地", width = 15)
    //private String a0181b;

    /**
     * 有效证件类型(ZB76)
     */
    //@Excel(name = "有效证件类型", width = 15)
    //private String a0183;

    /**
     * 证件号码
     */
    //@Excel(name = "证件号码", width = 15)
    //private String a0184;

    /**
     * 专长
     */
    //@Excel(name = "专长", width = 15)
    //private String a0187a;

    /**
     * 爱好
     */
    //@Excel(name = "爱好", width = 15)
    //private String a0187b;

    /**
     * 声音信息标识(AMS07)
     */
    //@Excel(name = "声音信息标识", width = 15)
    //private String a0191;

    /**
     * 信息标志(AMS08)
     */
    //@Excel(name = "信息标志", width = 15)
    //private String flag;





    /**
     * 级别
     */
    //@Excel(name = "级别", width = 15)
    //private String grade;






}
