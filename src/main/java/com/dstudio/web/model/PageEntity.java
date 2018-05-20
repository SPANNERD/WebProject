package com.dstudio.web.model;

/**
 * Created by wd824 on 2017-06-26.
 */
public class PageEntity
{
    private String pageIndex;

    private String pageSize;

    private String sortField;

    private String sortOrder;

    public PageEntity()
    {
    }

    public PageEntity(String pageIndex, String pageSize, String sortField, String sortOrder)
    {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }

    public PageEntity(String pageIndex, String pageSize)
    {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }

    public String getPageIndex()
    {
        return Integer.valueOf(pageIndex) + 1 + "";
    }

    public void setPageIndex(String pageIndex)
    {
        this.pageIndex = pageIndex;
    }

    public String getPageSize()
    {
        return pageSize;
    }

    public void setPageSize(String pageSize)
    {
        this.pageSize = pageSize;
    }

    public String getSortField()
    {
        return sortField;
    }

    public void setSortField(String sortField)
    {
        this.sortField = sortField;
    }

    public String getSortOrder()
    {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder)
    {
        this.sortOrder = sortOrder;
    }

    @Override
    public String toString() {
        return "PageEntity{" +
                "pageIndex='" + pageIndex + '\'' +
                ", pageSize='" + pageSize + '\'' +
                ", sortField='" + sortField + '\'' +
                ", sortOrder='" + sortOrder + '\'' +
                '}';
    }
}
