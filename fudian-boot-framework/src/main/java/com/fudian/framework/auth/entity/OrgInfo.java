package com.fudian.framework.auth.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description 全宗授权实体类
 * @ClassName OrgInfo
 * @Author mr.hou
 * @Date 2019/12/30 1:59 下午
 * @Version 1.0.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrgInfo {
    private String orgId;

    /**
     * 全宗名称
     */
    private String orgName;

    /**
     * 机器码
     */
    private String licenseKey;

    /**
     * 起始时间
     */
    private long startTime;

    /**
     * 过期时间
     */
    private long endTime;

    public OrgInfo(String orgId, String orgName,String licenseKey) {
        this.orgId = orgId;
        this.orgName = orgName;
        this.licenseKey = licenseKey;
    }
}
