package com.demon.dstudio.web.util;

import java.util.UUID;

/**
 * @author wd824
 */
public class SqlUtil {

    private final static String PAGE_SQL = "select * from( select rownum num, * from ( ${sql} ))where " +
            "num >=({pageIndex}-1)*{pageSize}+1 and num<={pageIndex}*{pageSize}";

    private final static String PAGE_COUNT_SQL = "select convert(int,count(1)) as num from ( ${sql} )";

    public final static String getPageSql(String sql, String pageIndex, String pageSize) {
        return PAGE_SQL.replace("${sql}", sql)
                .replace("{pageIndex}", pageIndex)
                .replace("{pageSize}", pageSize);
    }

    public final static String getPageCountSql(String sql) {
        return PAGE_COUNT_SQL.replace("${sql}", sql);
    }


    public static String newGUID() {
        return UUID.randomUUID().toString().toUpperCase();
    }

    /**
     * 防SQL注入
     * @param strHand 参数
     * @return
     */
    public static String filterSqlChar(String strHand)
    {
        StringBuffer regVal = new StringBuffer();
        regVal.append("and|exec|insert|select|delete|update|count|");
        regVal.append("chr|mid|master|truncate|char|declare|or|from|");
        regVal.append("*|'|%|^|&|?|;|-|+|<|>|{|}|\\|:|@");
        try
        {
            if ((strHand != null) &&
                    (!"".equals(strHand)) && (strHand.length() > 0))
            {
                strHand = strHand.toLowerCase();
                String[] arrayVal = regVal.toString().split("\\|");
                for (int i = 0; i < arrayVal.length; i++) {
                    if (strHand.contains(arrayVal[i])) {
                        strHand = strHand.replace(arrayVal[i], "");
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return strHand;
    }

//    /**
//     * JSONObject转SQL
//     * 要求JSONObject的字段与表的字段一一对应
//     * @param jsonObject
//     * @param tableName 完整表名
//     * @param type update 或 inseret
//     * @param filter 过滤条件
//     * @return
//             * @throws Exception
//     */
//    public static String convertJsonToSql(JSONObject jsonObject, String tableName, String type, String filter) throws Exception
//    {
//        StringBuffer sb = new StringBuffer();
//        Iterator<String> iterator = jsonObject.keys();
//        if ("insert".equals(type))
//        {
//            sb.append("INSERT INTO ").append(tableName).append(" (");
//            String fields = "";
//            String values = "";
//            while (iterator.hasNext())
//            {
//                String key = iterator.next().toString();
//                String value = jsonObject.getString(key);
//                fields = fields + key + ",";
//                values = values + "'" + value + "',";
//            }
//            if (fields.endsWith(",")) {
//                fields = fields.substring(0, fields.length() - 1);
//                values = values.substring(0, values.length() - 1);
//            }
//            sb.append(fields).append(") VALUES (").append(values).append(")");
//        }
//        else if ("update".equals(type))
//        {
//            sb.append("UPDATE ").append(tableName).append(" SET ");
//            String set = "";
//            while (iterator.hasNext())
//            {
//                String key = iterator.next().toString();
//                String value = jsonObject.getString(key);
//                set = set + key + "='" + value + "',";
//            }
//            if (set.endsWith(","))
//            {
//                set = set.substring(0, set.length() - 1);
//            }
//            sb.append(set).append(filter);
//        }
//        return sb.toString();
//    }
//
//
//
//    /**
//     * 通过实体类生成INSERT或UPDATE语句
//     * 与Controller由请求参数转换的实体类配合使用
//     * 实体类需在私有属性上使用@JsonProperty注解，值为属性对应的列名
//     * @param tableName 完整的表名称
//     * @param object  bean
//     * @return
//     */
//    public static String getInsertSql(String tableModule, String tableName, Object object) {
//        Field[] fields = object.getClass().getDeclaredFields();
//        StringBuffer sql = new StringBuffer();
//        sql.append("INSERT INTO ").append(tableModule).append(".").append(tableName);
//        StringBuffer keys = new StringBuffer();
//        StringBuffer values = new StringBuffer();
//        for (Field field : fields) {
//            if (field.isAnnotationPresent(Column.class)) {
//                Column column  = field.getAnnotation(Column.class);
//                String key = column.name();
//                try {
//                    field.setAccessible(true);
//                    Object valueObject = field.get(object);
//                    if (null != valueObject || "REV_".equals(key)) {
//                        keys.append(key).append(",");
//                        if ("REV_".equals(key)) {
//                            values.append(tableModule).append(".DISPINDEX.NEXTVAL,");
//                        } else {
//                            values.append("'").append(valueObject.toString()).append("',");
//                        }
//                    }
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        String strKey = keys.toString();
//        String strValue = values.toString();
//        if (strKey.endsWith(",")) {
//            strKey = strKey.substring(0, strKey.length() - 1);
//            strValue = strValue.substring(0, strValue.length() - 1);
//        }
//        sql.append("(").append(strKey).append(") VALUES (").append(strValue).append(")");
//        return sql.toString();
//    }
//
//    public static String getUpdateSql(String tableModule, String tableName, Object object, String primaryKey, String filter)
//            throws Exception {
//        Field[] fields = object.getClass().getDeclaredFields();
//        StringBuffer sql = new StringBuffer();
//        sql.append("UPDATE ").append(tableModule).append(".").append(tableName).append(" SET ");
//        StringBuffer keyValue = new StringBuffer();
//        for (Field field : fields) {
//            if (field.isAnnotationPresent(Column.class)) {
//                Column column = field.getAnnotation(Column.class);
//                String key = column.name();
//                try {
//                    field.setAccessible(true);
//                    Object valueObject = field.get(object);
//                    if (null != valueObject && !key.equals(primaryKey)) {
//                        keyValue.append(key).append(" = '").append(valueObject.toString()).append("',");
//                    }
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        String strKeyValue = keyValue.toString();
//        if (strKeyValue.endsWith(",")) {
//            strKeyValue = strKeyValue.substring(0, strKeyValue.length() - 1);
//        }
//        if ("".equals(filter)) {
//            throw new APPErrorException("UPDATE语句缺少过滤条件！");
//        }
//        sql.append(strKeyValue).append(" WHERE ").append(filter);
//        return sql.toString();
//    }



    public static void main(String[] args) {
        System.out.println(filterSqlChar("ce's"));
    }
}
