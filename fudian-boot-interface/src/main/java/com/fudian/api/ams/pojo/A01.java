package com.fudian.api.ams.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fudian.common.pojo.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: WangYuZhuo
 * @Date: 2020/3/25 13:51
 * @Desc:
 */
@Data
@TableName(value = "a01")
public class A01 extends BaseEntity implements Serializable {
    public static final String COL_DATA4 = "data4";
    public static final String COL_ID = "id";
    public static final String COL_ORG_ID = "org_id";
    public static final String COL_SHOW_INDEX = "show_index";
    public static final String COL_A0101 = "a0101";
    public static final String COL_A0102 = "a0102";
    public static final String COL_A0104 = "a0104";
    public static final String COL_A0107 = "a0107";
    public static final String COL_A0111A = "a0111a";
    public static final String COL_A0111B = "a0111b";
    public static final String COL_A0114A = "a0114a";
    public static final String COL_A0114B = "a0114b";
    public static final String COL_A0115A = "a0115a";
    public static final String COL_A0115B = "a0115b";
    public static final String COL_A0117 = "a0117";
    public static final String COL_A0127 = "a0127";
    public static final String COL_A0128 = "a0128";
    public static final String COL_A0131 = "a0131";
    public static final String COL_A0134 = "a0134";
    public static final String COL_A0137 = "a0137";
    public static final String COL_A0141 = "a0141";
    public static final String COL_A0144 = "a0144";
    public static final String COL_A0151 = "a0151";
    public static final String COL_A0154 = "a0154";
    public static final String COL_A0157A = "a0157a";
    public static final String COL_A0157B = "a0157b";
    public static final String COL_A0161 = "a0161";
    public static final String COL_A0164 = "a0164";
    public static final String COL_A0167 = "a0167";
    public static final String COL_A0171 = "a0171";
    public static final String COL_A0174 = "a0174";
    public static final String COL_A0177 = "a0177";
    public static final String COL_A0181A = "a0181a";
    public static final String COL_A0181B = "a0181b";
    public static final String COL_A0183 = "a0183";
    public static final String COL_A0184 = "a0184";
    public static final String COL_A0187A = "a0187a";
    public static final String COL_A0187B = "a0187b";
    public static final String COL_A0191 = "a0191";
    public static final String COL_FLAG = "flag";
    public static final String COL_DEPT_ID = "dept_id";
    public static final String COL_DUTY = "duty";
    public static final String COL_GRADE = "grade";
    public static final String COL_STATE = "state";
    public static final String COL_EDUCATION = "education";
    public static final String COL_NUMBER = "number";
    /**
     * 唯一标识(UUID)
     */
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    /**
     * 隶属组织机构
     */
    @TableField(value = "org_id")
    private String orgId;

    /**
     * 序号
     */
    @TableField(value = "show_index")
    private Integer showIndex;

    /**
     * 姓名
     */
    @TableField(value = "a0101")
    private String a0101;

    /**
     * 姓名拼音缩写(新增)
     */
    @TableField(value = "a0102")
    private String a0102;

    /**
     * 性别(GB/T2261.1 GB22611)
     */
    @TableField(value = "a0104")
    private String a0104;

    /**
     * 出生日期
     */
    @TableField(value = "a0107")
    private String a0107;

    /**
     * 籍贯名称
     */
    @TableField(value = "a0111a")
    private String a0111a;

    /**
     * 籍贯代码(ZB01)
     */
    @TableField(value = "a0111b")
    private String a0111b;

    /**
     * 出生地名称
     */
    @TableField(value = "a0114a")
    private String a0114a;

    /**
     * 出生地代码(ZB01)
     */
    @TableField(value = "a0114b")
    private String a0114b;

    /**
     * 成长地名称
     */
    @TableField(value = "a0115a")
    private String a0115a;

    /**
     * 成长地代码(ZB01)
     */
    @TableField(value = "a0115b")
    private String a0115b;

    /**
     * 民族(GB3304)
     */
    @TableField(value = "a0117")
    private String a0117;

    /**
     * 健康状况(GB22613)
     */
    @TableField(value = "a0127")
    private String a0127;

    /**
     * 健康状况描述
     */
    @TableField(value = "a0128")
    private String a0128;

    /**
     * 婚姻状况(GB22612)
     */
    @TableField(value = "a0131")
    private String a0131;

    /**
     * 参加工作日期
     */
    @TableField(value = "a0134")
    private String a0134;

    /**
     * 工龄计算校正值
     */
    @TableField(value = "a0137")
    private String a0137;

    /**
     * 政治面貌(GB4762)
     */
    @TableField(value = "a0141")
    private String a0141;

    /**
     * 参加组织日期
     */
    @TableField(value = "a0144")
    private String a0144;

    /**
     * 个人身份(GB/T2261.4 GB22614)
     */
    @TableField(value = "a0151")
    private String a0151;

    /**
     * 现身份起始日期
     */
    @TableField(value = "a0154")
    private String a0154;

    /**
     * 人事关系所在单位名称(编码修订)
     */
    @TableField(value = "a0157a")
    private String a0157a;

    /**
     * 人事关系所在单位代码(编码修订)(ZB02)
     */
    @TableField(value = "a0157b")
    private String a0157b;

    /**
     * 人事关系所在单位所在政区(ZB01)
     */
    @TableField(value = "a0161")
    private String a0161;

    /**
     * 人事关系所在单位隶属关系(ZB87)
     */
    @TableField(value = "a0164")
    private String a0164;

    /**
     * 人事关系所在单位级别(ZB03)
     */
    @TableField(value = "a0167")
    private String a0167;

    /**
     * 人事关系所在单位性质类别(ZB04)
     */
    @TableField(value = "a0171")
    private String a0171;

    /**
     * 人事关系所在单位所属行业(GB/T4754)
     */
    @TableField(value = "a0174")
    private String a0174;

    /**
     * 户口性质(ZB07)
     */
    @TableField(value = "a0177")
    private String a0177;

    /**
     * 户籍所在地(ZB01)
     */
    @TableField(value = "a0181a")
    private String a0181a;

    /**
     * 户籍所在地
     */
    @TableField(value = "a0181b")
    private String a0181b;

    /**
     * 有效证件类型(ZB76)
     */
    @TableField(value = "a0183")
    private String a0183;

    /**
     * 证件号码
     */
    @TableField(value = "a0184")
    private String a0184;

    /**
     * 专长
     */
    @TableField(value = "a0187a")
    private String a0187a;

    /**
     * 爱好
     */
    @TableField(value = "a0187b")
    private String a0187b;

    /**
     * 声音信息标识(AMS07)
     */
    @TableField(value = "a0191")
    private String a0191;

    /**
     * 信息标志(AMS08)
     */
    @TableField(value = "flag")
    private String flag;

    /**
     * 部门
     */
    @TableField(value = "dept_id")
    private String deptId;

    /**
     * 职务
     */
    @TableField(value = "duty")
    private String duty;

    /**
     * 级别
     */
    @TableField(value = "grade")
    private String grade;

    /**
     * 干部状态（FDA01State）
     */
    @TableField(value = "state")
    private String state;

    /**
     * 学历（ZB64）
     */
    @TableField(value = "education")
    private String education;

    /**
     * 编号
     */
    @TableField(value = "number")
    private String number;

    /**
     * 编制类别
     */
    @TableField(value = "data4")
    private String data4;

    private static final long serialVersionUID = 1L;
}
