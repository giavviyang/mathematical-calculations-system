package com.fudian.project.ams.pojo;

import java.util.ArrayList;
import java.util.List;

public class E01z8RelaExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public E01z8RelaExample() {
        oredCriteria = new ArrayList<>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(String value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLike(String value) {
            addCriterion("id like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotLike(String value) {
            addCriterion("id not like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andA0101IsNull() {
            addCriterion("a0101 is null");
            return (Criteria) this;
        }

        public Criteria andA0101IsNotNull() {
            addCriterion("a0101 is not null");
            return (Criteria) this;
        }

        public Criteria andA0101EqualTo(String value) {
            addCriterion("a0101 =", value, "a0101");
            return (Criteria) this;
        }

        public Criteria andA0101NotEqualTo(String value) {
            addCriterion("a0101 <>", value, "a0101");
            return (Criteria) this;
        }

        public Criteria andA0101GreaterThan(String value) {
            addCriterion("a0101 >", value, "a0101");
            return (Criteria) this;
        }

        public Criteria andA0101GreaterThanOrEqualTo(String value) {
            addCriterion("a0101 >=", value, "a0101");
            return (Criteria) this;
        }

        public Criteria andA0101LessThan(String value) {
            addCriterion("a0101 <", value, "a0101");
            return (Criteria) this;
        }

        public Criteria andA0101LessThanOrEqualTo(String value) {
            addCriterion("a0101 <=", value, "a0101");
            return (Criteria) this;
        }

        public Criteria andA0101Like(String value) {
            addCriterion("a0101 like", value, "a0101");
            return (Criteria) this;
        }

        public Criteria andA0101NotLike(String value) {
            addCriterion("a0101 not like", value, "a0101");
            return (Criteria) this;
        }

        public Criteria andA0101In(List<String> values) {
            addCriterion("a0101 in", values, "a0101");
            return (Criteria) this;
        }

        public Criteria andA0101NotIn(List<String> values) {
            addCriterion("a0101 not in", values, "a0101");
            return (Criteria) this;
        }

        public Criteria andA0101Between(String value1, String value2) {
            addCriterion("a0101 between", value1, value2, "a0101");
            return (Criteria) this;
        }

        public Criteria andA0101NotBetween(String value1, String value2) {
            addCriterion("a0101 not between", value1, value2, "a0101");
            return (Criteria) this;
        }

        public Criteria andA01IdIsNull() {
            addCriterion("a01_id is null");
            return (Criteria) this;
        }

        public Criteria andA01IdIsNotNull() {
            addCriterion("a01_id is not null");
            return (Criteria) this;
        }

        public Criteria andA01IdEqualTo(String value) {
            addCriterion("a01_id =", value, "a01Id");
            return (Criteria) this;
        }

        public Criteria andA01IdNotEqualTo(String value) {
            addCriterion("a01_id <>", value, "a01Id");
            return (Criteria) this;
        }

        public Criteria andA01IdGreaterThan(String value) {
            addCriterion("a01_id >", value, "a01Id");
            return (Criteria) this;
        }

        public Criteria andA01IdGreaterThanOrEqualTo(String value) {
            addCriterion("a01_id >=", value, "a01Id");
            return (Criteria) this;
        }

        public Criteria andA01IdLessThan(String value) {
            addCriterion("a01_id <", value, "a01Id");
            return (Criteria) this;
        }

        public Criteria andA01IdLessThanOrEqualTo(String value) {
            addCriterion("a01_id <=", value, "a01Id");
            return (Criteria) this;
        }

        public Criteria andA01IdLike(String value) {
            addCriterion("a01_id like", value, "a01Id");
            return (Criteria) this;
        }

        public Criteria andA01IdNotLike(String value) {
            addCriterion("a01_id not like", value, "a01Id");
            return (Criteria) this;
        }

        public Criteria andA01IdIn(List<String> values) {
            addCriterion("a01_id in", values, "a01Id");
            return (Criteria) this;
        }

        public Criteria andA01IdNotIn(List<String> values) {
            addCriterion("a01_id not in", values, "a01Id");
            return (Criteria) this;
        }

        public Criteria andA01IdBetween(String value1, String value2) {
            addCriterion("a01_id between", value1, value2, "a01Id");
            return (Criteria) this;
        }

        public Criteria andA01IdNotBetween(String value1, String value2) {
            addCriterion("a01_id not between", value1, value2, "a01Id");
            return (Criteria) this;
        }

        public Criteria andE01z8IdIsNull() {
            addCriterion("e01z8_id is null");
            return (Criteria) this;
        }

        public Criteria andE01z8IdIsNotNull() {
            addCriterion("e01z8_id is not null");
            return (Criteria) this;
        }

        public Criteria andE01z8IdEqualTo(String value) {
            addCriterion("e01z8_id =", value, "e01z8Id");
            return (Criteria) this;
        }

        public Criteria andE01z8IdNotEqualTo(String value) {
            addCriterion("e01z8_id <>", value, "e01z8Id");
            return (Criteria) this;
        }

        public Criteria andE01z8IdGreaterThan(String value) {
            addCriterion("e01z8_id >", value, "e01z8Id");
            return (Criteria) this;
        }

        public Criteria andE01z8IdGreaterThanOrEqualTo(String value) {
            addCriterion("e01z8_id >=", value, "e01z8Id");
            return (Criteria) this;
        }

        public Criteria andE01z8IdLessThan(String value) {
            addCriterion("e01z8_id <", value, "e01z8Id");
            return (Criteria) this;
        }

        public Criteria andE01z8IdLessThanOrEqualTo(String value) {
            addCriterion("e01z8_id <=", value, "e01z8Id");
            return (Criteria) this;
        }

        public Criteria andE01z8IdLike(String value) {
            addCriterion("e01z8_id like", value, "e01z8Id");
            return (Criteria) this;
        }

        public Criteria andE01z8IdNotLike(String value) {
            addCriterion("e01z8_id not like", value, "e01z8Id");
            return (Criteria) this;
        }

        public Criteria andE01z8IdIn(List<String> values) {
            addCriterion("e01z8_id in", values, "e01z8Id");
            return (Criteria) this;
        }

        public Criteria andE01z8IdNotIn(List<String> values) {
            addCriterion("e01z8_id not in", values, "e01z8Id");
            return (Criteria) this;
        }

        public Criteria andE01z8IdBetween(String value1, String value2) {
            addCriterion("e01z8_id between", value1, value2, "e01z8Id");
            return (Criteria) this;
        }

        public Criteria andE01z8IdNotBetween(String value1, String value2) {
            addCriterion("e01z8_id not between", value1, value2, "e01z8Id");
            return (Criteria) this;
        }

        public Criteria andE01z811IsNull() {
            addCriterion("e01z811 is null");
            return (Criteria) this;
        }

        public Criteria andE01z811IsNotNull() {
            addCriterion("e01z811 is not null");
            return (Criteria) this;
        }

        public Criteria andE01z811EqualTo(String value) {
            addCriterion("e01z811 =", value, "e01z811");
            return (Criteria) this;
        }

        public Criteria andE01z811NotEqualTo(String value) {
            addCriterion("e01z811 <>", value, "e01z811");
            return (Criteria) this;
        }

        public Criteria andE01z811GreaterThan(String value) {
            addCriterion("e01z811 >", value, "e01z811");
            return (Criteria) this;
        }

        public Criteria andE01z811GreaterThanOrEqualTo(String value) {
            addCriterion("e01z811 >=", value, "e01z811");
            return (Criteria) this;
        }

        public Criteria andE01z811LessThan(String value) {
            addCriterion("e01z811 <", value, "e01z811");
            return (Criteria) this;
        }

        public Criteria andE01z811LessThanOrEqualTo(String value) {
            addCriterion("e01z811 <=", value, "e01z811");
            return (Criteria) this;
        }

        public Criteria andE01z811Like(String value) {
            addCriterion("e01z811 like", value, "e01z811");
            return (Criteria) this;
        }

        public Criteria andE01z811NotLike(String value) {
            addCriterion("e01z811 not like", value, "e01z811");
            return (Criteria) this;
        }

        public Criteria andE01z811In(List<String> values) {
            addCriterion("e01z811 in", values, "e01z811");
            return (Criteria) this;
        }

        public Criteria andE01z811NotIn(List<String> values) {
            addCriterion("e01z811 not in", values, "e01z811");
            return (Criteria) this;
        }

        public Criteria andE01z811Between(String value1, String value2) {
            addCriterion("e01z811 between", value1, value2, "e01z811");
            return (Criteria) this;
        }

        public Criteria andE01z811NotBetween(String value1, String value2) {
            addCriterion("e01z811 not between", value1, value2, "e01z811");
            return (Criteria) this;
        }

        public Criteria andE01z814IsNull() {
            addCriterion("e01z814 is null");
            return (Criteria) this;
        }

        public Criteria andE01z814IsNotNull() {
            addCriterion("e01z814 is not null");
            return (Criteria) this;
        }

        public Criteria andE01z814EqualTo(String value) {
            addCriterion("e01z814 =", value, "e01z814");
            return (Criteria) this;
        }

        public Criteria andE01z814NotEqualTo(String value) {
            addCriterion("e01z814 <>", value, "e01z814");
            return (Criteria) this;
        }

        public Criteria andE01z814GreaterThan(String value) {
            addCriterion("e01z814 >", value, "e01z814");
            return (Criteria) this;
        }

        public Criteria andE01z814GreaterThanOrEqualTo(String value) {
            addCriterion("e01z814 >=", value, "e01z814");
            return (Criteria) this;
        }

        public Criteria andE01z814LessThan(String value) {
            addCriterion("e01z814 <", value, "e01z814");
            return (Criteria) this;
        }

        public Criteria andE01z814LessThanOrEqualTo(String value) {
            addCriterion("e01z814 <=", value, "e01z814");
            return (Criteria) this;
        }

        public Criteria andE01z814Like(String value) {
            addCriterion("e01z814 like", value, "e01z814");
            return (Criteria) this;
        }

        public Criteria andE01z814NotLike(String value) {
            addCriterion("e01z814 not like", value, "e01z814");
            return (Criteria) this;
        }

        public Criteria andE01z814In(List<String> values) {
            addCriterion("e01z814 in", values, "e01z814");
            return (Criteria) this;
        }

        public Criteria andE01z814NotIn(List<String> values) {
            addCriterion("e01z814 not in", values, "e01z814");
            return (Criteria) this;
        }

        public Criteria andE01z814Between(String value1, String value2) {
            addCriterion("e01z814 between", value1, value2, "e01z814");
            return (Criteria) this;
        }

        public Criteria andE01z814NotBetween(String value1, String value2) {
            addCriterion("e01z814 not between", value1, value2, "e01z814");
            return (Criteria) this;
        }

        public Criteria andCatalogIsNull() {
            addCriterion("`catalog` is null");
            return (Criteria) this;
        }

        public Criteria andCatalogIsNotNull() {
            addCriterion("`catalog` is not null");
            return (Criteria) this;
        }

        public Criteria andCatalogEqualTo(String value) {
            addCriterion("`catalog` =", value, "catalog");
            return (Criteria) this;
        }

        public Criteria andCatalogNotEqualTo(String value) {
            addCriterion("`catalog` <>", value, "catalog");
            return (Criteria) this;
        }

        public Criteria andCatalogGreaterThan(String value) {
            addCriterion("`catalog` >", value, "catalog");
            return (Criteria) this;
        }

        public Criteria andCatalogGreaterThanOrEqualTo(String value) {
            addCriterion("`catalog` >=", value, "catalog");
            return (Criteria) this;
        }

        public Criteria andCatalogLessThan(String value) {
            addCriterion("`catalog` <", value, "catalog");
            return (Criteria) this;
        }

        public Criteria andCatalogLessThanOrEqualTo(String value) {
            addCriterion("`catalog` <=", value, "catalog");
            return (Criteria) this;
        }

        public Criteria andCatalogLike(String value) {
            addCriterion("`catalog` like", value, "catalog");
            return (Criteria) this;
        }

        public Criteria andCatalogNotLike(String value) {
            addCriterion("`catalog` not like", value, "catalog");
            return (Criteria) this;
        }

        public Criteria andCatalogIn(List<String> values) {
            addCriterion("`catalog` in", values, "catalog");
            return (Criteria) this;
        }

        public Criteria andCatalogNotIn(List<String> values) {
            addCriterion("`catalog` not in", values, "catalog");
            return (Criteria) this;
        }

        public Criteria andCatalogBetween(String value1, String value2) {
            addCriterion("`catalog` between", value1, value2, "catalog");
            return (Criteria) this;
        }

        public Criteria andCatalogNotBetween(String value1, String value2) {
            addCriterion("`catalog` not between", value1, value2, "catalog");
            return (Criteria) this;
        }

        public Criteria andFlagIsNull() {
            addCriterion("flag is null");
            return (Criteria) this;
        }

        public Criteria andFlagIsNotNull() {
            addCriterion("flag is not null");
            return (Criteria) this;
        }

        public Criteria andFlagEqualTo(String value) {
            addCriterion("flag =", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagNotEqualTo(String value) {
            addCriterion("flag <>", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagGreaterThan(String value) {
            addCriterion("flag >", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagGreaterThanOrEqualTo(String value) {
            addCriterion("flag >=", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagLessThan(String value) {
            addCriterion("flag <", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagLessThanOrEqualTo(String value) {
            addCriterion("flag <=", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagLike(String value) {
            addCriterion("flag like", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagNotLike(String value) {
            addCriterion("flag not like", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagIn(List<String> values) {
            addCriterion("flag in", values, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagNotIn(List<String> values) {
            addCriterion("flag not in", values, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagBetween(String value1, String value2) {
            addCriterion("flag between", value1, value2, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagNotBetween(String value1, String value2) {
            addCriterion("flag not between", value1, value2, "flag");
            return (Criteria) this;
        }

        public Criteria andMarkIsNull() {
            addCriterion("mark is null");
            return (Criteria) this;
        }

        public Criteria andMarkIsNotNull() {
            addCriterion("mark is not null");
            return (Criteria) this;
        }

        public Criteria andMarkEqualTo(String value) {
            addCriterion("mark =", value, "mark");
            return (Criteria) this;
        }

        public Criteria andMarkNotEqualTo(String value) {
            addCriterion("mark <>", value, "mark");
            return (Criteria) this;
        }

        public Criteria andMarkGreaterThan(String value) {
            addCriterion("mark >", value, "mark");
            return (Criteria) this;
        }

        public Criteria andMarkGreaterThanOrEqualTo(String value) {
            addCriterion("mark >=", value, "mark");
            return (Criteria) this;
        }

        public Criteria andMarkLessThan(String value) {
            addCriterion("mark <", value, "mark");
            return (Criteria) this;
        }

        public Criteria andMarkLessThanOrEqualTo(String value) {
            addCriterion("mark <=", value, "mark");
            return (Criteria) this;
        }

        public Criteria andMarkLike(String value) {
            addCriterion("mark like", value, "mark");
            return (Criteria) this;
        }

        public Criteria andMarkNotLike(String value) {
            addCriterion("mark not like", value, "mark");
            return (Criteria) this;
        }

        public Criteria andMarkIn(List<String> values) {
            addCriterion("mark in", values, "mark");
            return (Criteria) this;
        }

        public Criteria andMarkNotIn(List<String> values) {
            addCriterion("mark not in", values, "mark");
            return (Criteria) this;
        }

        public Criteria andMarkBetween(String value1, String value2) {
            addCriterion("mark between", value1, value2, "mark");
            return (Criteria) this;
        }

        public Criteria andMarkNotBetween(String value1, String value2) {
            addCriterion("mark not between", value1, value2, "mark");
            return (Criteria) this;
        }

        public Criteria andFeedbackIsNull() {
            addCriterion("feedback is null");
            return (Criteria) this;
        }

        public Criteria andFeedbackIsNotNull() {
            addCriterion("feedback is not null");
            return (Criteria) this;
        }

        public Criteria andFeedbackEqualTo(String value) {
            addCriterion("feedback =", value, "feedback");
            return (Criteria) this;
        }

        public Criteria andFeedbackNotEqualTo(String value) {
            addCriterion("feedback <>", value, "feedback");
            return (Criteria) this;
        }

        public Criteria andFeedbackGreaterThan(String value) {
            addCriterion("feedback >", value, "feedback");
            return (Criteria) this;
        }

        public Criteria andFeedbackGreaterThanOrEqualTo(String value) {
            addCriterion("feedback >=", value, "feedback");
            return (Criteria) this;
        }

        public Criteria andFeedbackLessThan(String value) {
            addCriterion("feedback <", value, "feedback");
            return (Criteria) this;
        }

        public Criteria andFeedbackLessThanOrEqualTo(String value) {
            addCriterion("feedback <=", value, "feedback");
            return (Criteria) this;
        }

        public Criteria andFeedbackLike(String value) {
            addCriterion("feedback like", value, "feedback");
            return (Criteria) this;
        }

        public Criteria andFeedbackNotLike(String value) {
            addCriterion("feedback not like", value, "feedback");
            return (Criteria) this;
        }

        public Criteria andFeedbackIn(List<String> values) {
            addCriterion("feedback in", values, "feedback");
            return (Criteria) this;
        }

        public Criteria andFeedbackNotIn(List<String> values) {
            addCriterion("feedback not in", values, "feedback");
            return (Criteria) this;
        }

        public Criteria andFeedbackBetween(String value1, String value2) {
            addCriterion("feedback between", value1, value2, "feedback");
            return (Criteria) this;
        }

        public Criteria andFeedbackNotBetween(String value1, String value2) {
            addCriterion("feedback not between", value1, value2, "feedback");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}
