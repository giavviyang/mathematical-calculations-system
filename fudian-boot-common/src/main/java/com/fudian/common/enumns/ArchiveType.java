package com.fudian.common.enumns;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author lutao
 * @create 2020/1/7
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ArchiveType {
    /**
     * 1:电子档案查阅 2:实体档案查阅 3:档案借阅
     */
    ELECTRONIC("1","电子档案查阅"),ENTITY("2","实体档案查阅"),BORROWING("3","档案借阅");
    //String类型
    private String code;
    //描述
    private String desc;

}
