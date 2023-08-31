package com.fudian.common.pojo;

import com.fudian.common.enumns.Opt;

public class Condition {
    private String name;// 条件名
    private Opt Opertion;// 运算符
    private Object value;// 条件值

    public Condition() {
        super();
    }

    public Condition(Opt opertion, String name, Object value) {
        super();
        Opertion = opertion;
        this.name = name;
        this.setValue(value);
    }

    public static Condition eq(String propertyName, Object value) {
        return new Condition(Opt.Equal, propertyName, value);
    }

    public static Condition like(String propertyName, Object value) {
        return new Condition(Opt.Like, propertyName, value);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Opt getOpertion() {
        return Opertion;
    }

    public void setOpertion(Opt opertion) {
        Opertion = opertion;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        if (this.Opertion != null && this.Opertion.equals(Opt.Like)) {
            this.value = "%" + value + "%";
        } else {
            this.value = value;
        }
    }
}
