package com.fudian.common.utils.sql;

import com.fudian.common.pojo.Condition;

import java.util.ArrayList;
import java.util.List;

public class SqlHandle {

    private StringBuffer sql;
    private List<Condition> list = new ArrayList<Condition>();

    public SqlHandle() {
    }

    public SqlHandle(String sql) {
        this.sql = new StringBuffer(sql);
    }

    public static SqlHandle getInstance(String sql) {
        return new SqlHandle(sql);
    }

    /**
     *
     * @类名称:add
     * @类描述:添加条件
     * @param condition
     */
    public void add(Condition condition) {
        this.list.add(condition);
    }

    public String getSql() {
        return this.sql.append(this.getwhereString()).toString();
    }

    public void printSql() {
        System.out.println("生成的sql语句:" + this.sql.toString() + this.getwhereString());
    }

    /*
     * 得到where子句，以？为占位符。
     */
    /**
     *
     * @类名称:getwhereString
     * @类描述:得到where子句，以？为占位符。通过条件集合中所存储的条件进行and拼接的where子句
     * @注意：不能与getwhereOrString同时使用
     * @return：拼接好的where子句
     */
    public String getwhereString() {
        StringBuffer sb = new StringBuffer();
        if (list.size() <= 0) {
            return "";
        } else {
            sb.append(" where 1=1 and");
            for (Condition item : list) {
                sb.append(" " + item.getName());
                switch (item.getOpertion()) {
                    case Equal:
                        sb.append(" = ");
                        break;
                    case UnEqual:
                        sb.append(" <> ");
                        break;
                    case GreatThan:
                        sb.append(" >= ");
                        break;
                    case LessThan:
                        sb.append(" <= ");
                        break;
                    case Like:
                        sb.append(" like ");
                        break;
                }
                sb.append("? and");
            }
            if (sb.toString().endsWith("and")) {
                sb.delete(sb.length() - 3, sb.length());
            }
            return sb.toString();
        }
    }

    /**
     *
     * @类名称：getwhereOrString
     * @类描述：得到where子句，以？为占位符。通过条件集合中所存储的条件进行or拼接的where子句
     * @注意：不能与getwhereString同时使用
     * @return：拼接好的where子句
     * @创建时间2014/4/16
     */
    public String getwhereOrString() {
        String result = this.getwhereString();
        return result.replace("and", "or");
    }

    /**
     *
     * @类名称：getwhereNotWithWhere
     * @类描述：得到where子句，以？为占位符。通过条件集合中所存储的条件进行and拼接的where子句,并且去掉where关键字
     * @注意：不能与其他getwhere方法同时使用
     * @return：拼接好的where子句，去掉where关键字
     * @创建时间2014/4/16
     */
    public String getwhereNotWithWhere() {
        String result = this.getwhereString();
        return result.replace(" where ", " ");
    }

    /**
     *
     * @类名称:getwhereValues
     * @类描述:通过条件集合中的条件得到与where字句位置对应的object集合。存储所有的值
     * @return：
     */
    public Object[] getwhereValues() {
        Object[] obj = new Object[this.list.size()];
        for (int i = 0; i < this.list.size(); i++) {
            obj[i] = this.list.get(i).getValue();
        }
        return obj;
    }

}
