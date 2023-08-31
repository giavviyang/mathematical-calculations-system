package com.fudian.common.utils.sql;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOpExpr;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOperator;
import com.alibaba.druid.sql.ast.statement.SQLSelect;
import com.alibaba.druid.sql.ast.statement.SQLSelectQueryBlock;
import com.alibaba.druid.sql.ast.statement.SQLSelectStatement;
import com.alibaba.druid.sql.dialect.postgresql.visitor.PGSchemaStatVisitor;
import com.alibaba.druid.sql.parser.SQLExprParser;
import com.alibaba.druid.sql.parser.SQLParserUtils;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import com.alibaba.druid.stat.TableStat;
import com.alibaba.druid.util.JdbcConstants;
import com.alibaba.druid.util.JdbcUtils;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DataSqlHandle {


    /**
     * 处理sql工具
     * @param sql 原有sql语句
     * @param sx 1部门权限  2人员权限  3分类权限   多个以逗号分割
     * @param dataMap
     * @return 拼接条件后的sql语句
     */
    public static String sqlTransformation(String sql,String sx,Map<String,Object> dataMap){

        sqlHandle(sql,sx,dataMap);
        String[] types = sx.split(",");
        String whereSql = "";
        for(String type:types){

        }
        return sql;
    }

    private static void sqlHandle(String sql,String type,Map<String,Object> map) {
        String dbType = JdbcConstants.MYSQL;
        //格式化输出
        String result = SQLUtils.format(sql, dbType);
        sql=contactConditions(sql,type,map);
        System.out.println(result); // 缺省大写格式
        List<SQLStatement> stmtList = SQLUtils.parseStatements(sql, dbType);

        //解析出的独立语句的个数
        System.out.println("size is:" + stmtList.size());
        for (int i = 0; i < stmtList.size(); i++) {

            SQLStatement stmt = stmtList.get(i);

            PGSchemaStatVisitor visitor = new PGSchemaStatVisitor();
            stmt.accept(visitor);
            Set<TableStat.Column> groupby_col = visitor.getGroupByColumns();
            //
            for (Iterator iterator = groupby_col.iterator(); iterator.hasNext(); ) {
                TableStat.Column column = (TableStat.Column) iterator.next();
                System.out.println("[GROUP]" + column.toString());
            }
            //获取表名称
            System.out.println("table names:");
            Map<TableStat.Name, TableStat> tabmap = visitor.getTables();
            for (Iterator iterator = tabmap.keySet().iterator(); iterator.hasNext(); ) {
                TableStat.Name name = (TableStat.Name) iterator.next();
                System.out.println(name.toString() + " - " + tabmap.get(name).toString());
            }
            //System.out.println("Tables : " + visitor.getCurrentTable());
            //获取操作方法名称,依赖于表名称
            System.out.println("Manipulation : " + visitor.getTables());
            //获取字段名称
            System.out.println("fields : " + visitor.getColumns());
        }
    }


    private static String contactConditions(String sql, String type,Map<String, Object> columnMap) {
        SQLStatementParser parser = SQLParserUtils.createSQLStatementParser(sql, JdbcUtils.MYSQL);
        List<SQLStatement> stmtList = parser.parseStatementList();
        SQLStatement stmt = stmtList.get(0);
        if (stmt instanceof SQLSelectStatement) {
            StringBuffer constraintsBuffer = new StringBuffer();
            Set<String> keys = columnMap.keySet();
            Iterator<String> keyIter = keys.iterator();
            if (keyIter.hasNext()) {
                String key = keyIter.next();
                constraintsBuffer.append(key).append(" = " + getSqlByClass(columnMap.get(key)));
            }
            while (keyIter.hasNext()) {
                String key = keyIter.next();
                constraintsBuffer.append(" AND ").append(key).append(" = " + getSqlByClass(columnMap.get(key)));
            }
            SQLExprParser constraintsParser = SQLParserUtils.createExprParser(constraintsBuffer.toString(), JdbcUtils.MYSQL);
            SQLExpr constraintsExpr = constraintsParser.expr();

            SQLSelectStatement selectStmt = (SQLSelectStatement) stmt;
            // 拿到SQLSelect
            SQLSelect sqlselect = selectStmt.getSelect();
            SQLSelectQueryBlock query = (SQLSelectQueryBlock) sqlselect.getQuery();
            SQLExpr whereExpr = query.getWhere();
            // 修改where表达式
            if (whereExpr == null) {
                query.setWhere(constraintsExpr);
            } else {
                SQLBinaryOpExpr newWhereExpr = new SQLBinaryOpExpr(whereExpr, SQLBinaryOperator.BooleanAnd, constraintsExpr);
                query.setWhere(newWhereExpr);
            }
            sqlselect.setQuery(query);
            return sqlselect.toString();

        }

        return sql;
    }
    private static String getSqlByClass(Object value){

        if(value instanceof Number){
            return value + "";
        }else if(value instanceof String){
            return "'" + value + "'";
        }

        return "'" + value.toString() + "'";
    }

}
