package com.fudian.business.vo;

import com.fudian.business.pojo.Popup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 知识点弹窗
 * @author xuchenghong
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class KnowledgePopup {
    /** 弹窗标题 */
    private String title;
    /** 集合 */
    private List<Example> list;
}
