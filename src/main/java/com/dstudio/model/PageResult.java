package com.dstudio.model;

import java.util.List;

/**
 * @author wd824
 */
public class PageResult<T> {

    private int total;

    private List<T> data;

    public PageResult(int total, List<T> data) {
        this.total = total;
        this.data = data;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
