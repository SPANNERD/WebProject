package com.demon.dstudio.web.mybatis.provider;

import org.apache.ibatis.mapping.MappedStatement;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.mapperhelper.EntityHelper;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;
import tk.mybatis.mapper.mapperhelper.SqlHelper;

import java.util.Set;

/**
 * @author wd824
 */
public class BatchProvider extends MapperTemplate {

    public BatchProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    /**
     * 批量插入
     *
     * @param ms
     */
    public String insertList(MappedStatement ms) {
        final Class<?> entityClass = getEntityClass(ms);
        //开始拼sql
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.insertIntoTable(entityClass, tableName(entityClass)));
        sql.append(SqlHelper.insertColumns(entityClass, false, false, false));
        sql.append(" VALUES ");
        sql.append("<foreach collection=\"list\" item=\"record\" separator=\",\" >");
        sql.append("<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">");
        //获取全部列
        Set<EntityColumn> columnList = EntityHelper.getColumns(entityClass);
        //当某个列有主键策略时，不需要考虑他的属性是否为空，因为如果为空，一定会根据主键策略给他生成一个值
        for (EntityColumn column : columnList) {
            if (column.isInsertable()) {
                sql.append(column.getColumnHolder("record") + ",");
            }
        }
        sql.append("</trim>");
        sql.append("</foreach>");
        return sql.toString();
    }

    /**
     *
     * @param ms
     * @return
     */
    public String batchUpdateByPrimaryKey(MappedStatement ms) {
        final Class<?> entityClass = getEntityClass(ms);

        StringBuilder sql = new StringBuilder();

        sql.append("<foreach collection=\"list\" item=\"record\" separator=\";\">");
        sql.append(SqlHelper.updateTable(entityClass, tableName(entityClass)))
                .append(" SET ");
        sql.append("<trim suffixOverrides=\",\">");

        Set<EntityColumn> columnList = EntityHelper.getColumns(entityClass);

        for (EntityColumn column : columnList) {
            if (column.isUpdatable() && !column.isId()) {
                sql.append(SqlHelper.getIfNotNull("record", column,
                        column.getColumn() + "=" + column.getColumnHolder("record") + ", ", true));

            }
        }
        sql.append("</trim>");
        Set<EntityColumn> columns = EntityHelper.getPKColumns(entityClass);
        sql.append(" WHERE ");
        for (EntityColumn column : columns) {
            sql.append(column.getColumn()).append(" = ").append(column.getColumnHolder("record"));
        }
        sql.append("</foreach>");
        return sql.toString();
    }
}
