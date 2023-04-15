package com.hero.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 分页结果类
 */
@Getter
@Setter
public class PageResult<T> {

    private Long total;//总记录数
    private List<T> rows;//记录

    public PageResult(Long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }
    public PageResult() { }
    //getter and setter ......
}
