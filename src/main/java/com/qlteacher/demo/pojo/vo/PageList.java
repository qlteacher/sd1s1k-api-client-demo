package com.qlteacher.demo.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 江立国 2024/8/15 10:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageList<T> implements Serializable {

    public PageList(List<T> list) {
        this.list = list;
    }

    /**
     * 当页数据列表
     */
    private List<T> list = new ArrayList<>();

    /**
     * 所有数据数
     */
    private Long count;

}
