package com.fudian.project.ams.pojo;

import java.util.ArrayList;
import java.util.List;

public class E01z9RelaExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public E01z9RelaExample() {
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

        public Criteria andE01z9IdIsNull() {
            addCriterion("e01z9_id is null");
            return (Criteria) this;
        }

        public Criteria andE01z9IdIsNotNull() {
            addCriterion("e01z9_id is not null");
            return (Criteria) this;
        }

        public Criteria andE01z9IdEqualTo(String value) {
            addCriterion("e01z9_id =", value, "e01z9Id");
            return (Criteria) this;
        }

        public Criteria andE01z9IdNotEqualTo(String value) {
            addCriterion("e01z9_id <>", value, "e01z9Id");
            return (Criteria) this;
        }

        public Criteria andE01z9IdGreaterThan(String value) {
            addCriterion("e01z9_id >", value, "e01z9Id");
            return (Criteria) this;
        }

        public Criteria andE01z9IdGreaterThanOrEqualTo(String value) {
            addCriterion("e01z9_id >=", value, "e01z9Id");
            return (Criteria) this;
        }

        public Criteria andE01z9IdLessThan(String value) {
            addCriterion("e01z9_id <", value, "e01z9Id");
            return (Criteria) this;
        }

        public Criteria andE01z9IdLessThanOrEqualTo(String value) {
            addCriterion("e01z9_id <=", value, "e01z9Id");
            return (Criteria) this;
        }

        public Criteria andE01z9IdLike(String value) {
            addCriterion("e01z9_id like", value, "e01z9Id");
            return (Criteria) this;
        }

        public Criteria andE01z9IdNotLike(String value) {
            addCriterion("e01z9_id not like", value, "e01z9Id");
            return (Criteria) this;
        }

        public Criteria andE01z9IdIn(List<String> values) {
            addCriterion("e01z9_id in", values, "e01z9Id");
            return (Criteria) this;
        }

        public Criteria andE01z9IdNotIn(List<String> values) {
            addCriterion("e01z9_id not in", values, "e01z9Id");
            return (Criteria) this;
        }

        public Criteria andE01z9IdBetween(String value1, String value2) {
            addCriterion("e01z9_id between", value1, value2, "e01z9Id");
            return (Criteria) this;
        }

        public Criteria andE01z9IdNotBetween(String value1, String value2) {
            addCriterion("e01z9_id not between", value1, value2, "e01z9Id");
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

        public Criteria andMaterialsIsNull() {
            addCriterion("materials is null");
            return (Criteria) this;
        }

        public Criteria andMaterialsIsNotNull() {
            addCriterion("materials is not null");
            return (Criteria) this;
        }

        public Criteria andMaterialsEqualTo(Integer value) {
            addCriterion("materials =", value, "materials");
            return (Criteria) this;
        }

        public Criteria andMaterialsNotEqualTo(Integer value) {
            addCriterion("materials <>", value, "materials");
            return (Criteria) this;
        }

        public Criteria andMaterialsGreaterThan(Integer value) {
            addCriterion("materials >", value, "materials");
            return (Criteria) this;
        }

        public Criteria andMaterialsGreaterThanOrEqualTo(Integer value) {
            addCriterion("materials >=", value, "materials");
            return (Criteria) this;
        }

        public Criteria andMaterialsLessThan(Integer value) {
            addCriterion("materials <", value, "materials");
            return (Criteria) this;
        }

        public Criteria andMaterialsLessThanOrEqualTo(Integer value) {
            addCriterion("materials <=", value, "materials");
            return (Criteria) this;
        }

        public Criteria andMaterialsIn(List<Integer> values) {
            addCriterion("materials in", values, "materials");
            return (Criteria) this;
        }

        public Criteria andMaterialsNotIn(List<Integer> values) {
            addCriterion("materials not in", values, "materials");
            return (Criteria) this;
        }

        public Criteria andMaterialsBetween(Integer value1, Integer value2) {
            addCriterion("materials between", value1, value2, "materials");
            return (Criteria) this;
        }

        public Criteria andMaterialsNotBetween(Integer value1, Integer value2) {
            addCriterion("materials not between", value1, value2, "materials");
            return (Criteria) this;
        }

        public Criteria andTotalPagesIsNull() {
            addCriterion("total_pages is null");
            return (Criteria) this;
        }

        public Criteria andTotalPagesIsNotNull() {
            addCriterion("total_pages is not null");
            return (Criteria) this;
        }

        public Criteria andTotalPagesEqualTo(Integer value) {
            addCriterion("total_pages =", value, "totalPages");
            return (Criteria) this;
        }

        public Criteria andTotalPagesNotEqualTo(Integer value) {
            addCriterion("total_pages <>", value, "totalPages");
            return (Criteria) this;
        }

        public Criteria andTotalPagesGreaterThan(Integer value) {
            addCriterion("total_pages >", value, "totalPages");
            return (Criteria) this;
        }

        public Criteria andTotalPagesGreaterThanOrEqualTo(Integer value) {
            addCriterion("total_pages >=", value, "totalPages");
            return (Criteria) this;
        }

        public Criteria andTotalPagesLessThan(Integer value) {
            addCriterion("total_pages <", value, "totalPages");
            return (Criteria) this;
        }

        public Criteria andTotalPagesLessThanOrEqualTo(Integer value) {
            addCriterion("total_pages <=", value, "totalPages");
            return (Criteria) this;
        }

        public Criteria andTotalPagesIn(List<Integer> values) {
            addCriterion("total_pages in", values, "totalPages");
            return (Criteria) this;
        }

        public Criteria andTotalPagesNotIn(List<Integer> values) {
            addCriterion("total_pages not in", values, "totalPages");
            return (Criteria) this;
        }

        public Criteria andTotalPagesBetween(Integer value1, Integer value2) {
            addCriterion("total_pages between", value1, value2, "totalPages");
            return (Criteria) this;
        }

        public Criteria andTotalPagesNotBetween(Integer value1, Integer value2) {
            addCriterion("total_pages not between", value1, value2, "totalPages");
            return (Criteria) this;
        }

        public Criteria andModifiedIsNull() {
            addCriterion("modified is null");
            return (Criteria) this;
        }

        public Criteria andModifiedIsNotNull() {
            addCriterion("modified is not null");
            return (Criteria) this;
        }

        public Criteria andModifiedEqualTo(String value) {
            addCriterion("modified =", value, "modified");
            return (Criteria) this;
        }

        public Criteria andModifiedNotEqualTo(String value) {
            addCriterion("modified <>", value, "modified");
            return (Criteria) this;
        }

        public Criteria andModifiedGreaterThan(String value) {
            addCriterion("modified >", value, "modified");
            return (Criteria) this;
        }

        public Criteria andModifiedGreaterThanOrEqualTo(String value) {
            addCriterion("modified >=", value, "modified");
            return (Criteria) this;
        }

        public Criteria andModifiedLessThan(String value) {
            addCriterion("modified <", value, "modified");
            return (Criteria) this;
        }

        public Criteria andModifiedLessThanOrEqualTo(String value) {
            addCriterion("modified <=", value, "modified");
            return (Criteria) this;
        }

        public Criteria andModifiedLike(String value) {
            addCriterion("modified like", value, "modified");
            return (Criteria) this;
        }

        public Criteria andModifiedNotLike(String value) {
            addCriterion("modified not like", value, "modified");
            return (Criteria) this;
        }

        public Criteria andModifiedIn(List<String> values) {
            addCriterion("modified in", values, "modified");
            return (Criteria) this;
        }

        public Criteria andModifiedNotIn(List<String> values) {
            addCriterion("modified not in", values, "modified");
            return (Criteria) this;
        }

        public Criteria andModifiedBetween(String value1, String value2) {
            addCriterion("modified between", value1, value2, "modified");
            return (Criteria) this;
        }

        public Criteria andModifiedNotBetween(String value1, String value2) {
            addCriterion("modified not between", value1, value2, "modified");
            return (Criteria) this;
        }

        public Criteria andIsBreakIsNull() {
            addCriterion("is_break is null");
            return (Criteria) this;
        }

        public Criteria andIsBreakIsNotNull() {
            addCriterion("is_break is not null");
            return (Criteria) this;
        }

        public Criteria andIsBreakEqualTo(String value) {
            addCriterion("is_break =", value, "isBreak");
            return (Criteria) this;
        }

        public Criteria andIsBreakNotEqualTo(String value) {
            addCriterion("is_break <>", value, "isBreak");
            return (Criteria) this;
        }

        public Criteria andIsBreakGreaterThan(String value) {
            addCriterion("is_break >", value, "isBreak");
            return (Criteria) this;
        }

        public Criteria andIsBreakGreaterThanOrEqualTo(String value) {
            addCriterion("is_break >=", value, "isBreak");
            return (Criteria) this;
        }

        public Criteria andIsBreakLessThan(String value) {
            addCriterion("is_break <", value, "isBreak");
            return (Criteria) this;
        }

        public Criteria andIsBreakLessThanOrEqualTo(String value) {
            addCriterion("is_break <=", value, "isBreak");
            return (Criteria) this;
        }

        public Criteria andIsBreakLike(String value) {
            addCriterion("is_break like", value, "isBreak");
            return (Criteria) this;
        }

        public Criteria andIsBreakNotLike(String value) {
            addCriterion("is_break not like", value, "isBreak");
            return (Criteria) this;
        }

        public Criteria andIsBreakIn(List<String> values) {
            addCriterion("is_break in", values, "isBreak");
            return (Criteria) this;
        }

        public Criteria andIsBreakNotIn(List<String> values) {
            addCriterion("is_break not in", values, "isBreak");
            return (Criteria) this;
        }

        public Criteria andIsBreakBetween(String value1, String value2) {
            addCriterion("is_break between", value1, value2, "isBreak");
            return (Criteria) this;
        }

        public Criteria andIsBreakNotBetween(String value1, String value2) {
            addCriterion("is_break not between", value1, value2, "isBreak");
            return (Criteria) this;
        }

        public Criteria andBrokenIsNull() {
            addCriterion("broken is null");
            return (Criteria) this;
        }

        public Criteria andBrokenIsNotNull() {
            addCriterion("broken is not null");
            return (Criteria) this;
        }

        public Criteria andBrokenEqualTo(String value) {
            addCriterion("broken =", value, "broken");
            return (Criteria) this;
        }

        public Criteria andBrokenNotEqualTo(String value) {
            addCriterion("broken <>", value, "broken");
            return (Criteria) this;
        }

        public Criteria andBrokenGreaterThan(String value) {
            addCriterion("broken >", value, "broken");
            return (Criteria) this;
        }

        public Criteria andBrokenGreaterThanOrEqualTo(String value) {
            addCriterion("broken >=", value, "broken");
            return (Criteria) this;
        }

        public Criteria andBrokenLessThan(String value) {
            addCriterion("broken <", value, "broken");
            return (Criteria) this;
        }

        public Criteria andBrokenLessThanOrEqualTo(String value) {
            addCriterion("broken <=", value, "broken");
            return (Criteria) this;
        }

        public Criteria andBrokenLike(String value) {
            addCriterion("broken like", value, "broken");
            return (Criteria) this;
        }

        public Criteria andBrokenNotLike(String value) {
            addCriterion("broken not like", value, "broken");
            return (Criteria) this;
        }

        public Criteria andBrokenIn(List<String> values) {
            addCriterion("broken in", values, "broken");
            return (Criteria) this;
        }

        public Criteria andBrokenNotIn(List<String> values) {
            addCriterion("broken not in", values, "broken");
            return (Criteria) this;
        }

        public Criteria andBrokenBetween(String value1, String value2) {
            addCriterion("broken between", value1, value2, "broken");
            return (Criteria) this;
        }

        public Criteria andBrokenNotBetween(String value1, String value2) {
            addCriterion("broken not between", value1, value2, "broken");
            return (Criteria) this;
        }

        public Criteria andIsLoseIsNull() {
            addCriterion("is_lose is null");
            return (Criteria) this;
        }

        public Criteria andIsLoseIsNotNull() {
            addCriterion("is_lose is not null");
            return (Criteria) this;
        }

        public Criteria andIsLoseEqualTo(String value) {
            addCriterion("is_lose =", value, "isLose");
            return (Criteria) this;
        }

        public Criteria andIsLoseNotEqualTo(String value) {
            addCriterion("is_lose <>", value, "isLose");
            return (Criteria) this;
        }

        public Criteria andIsLoseGreaterThan(String value) {
            addCriterion("is_lose >", value, "isLose");
            return (Criteria) this;
        }

        public Criteria andIsLoseGreaterThanOrEqualTo(String value) {
            addCriterion("is_lose >=", value, "isLose");
            return (Criteria) this;
        }

        public Criteria andIsLoseLessThan(String value) {
            addCriterion("is_lose <", value, "isLose");
            return (Criteria) this;
        }

        public Criteria andIsLoseLessThanOrEqualTo(String value) {
            addCriterion("is_lose <=", value, "isLose");
            return (Criteria) this;
        }

        public Criteria andIsLoseLike(String value) {
            addCriterion("is_lose like", value, "isLose");
            return (Criteria) this;
        }

        public Criteria andIsLoseNotLike(String value) {
            addCriterion("is_lose not like", value, "isLose");
            return (Criteria) this;
        }

        public Criteria andIsLoseIn(List<String> values) {
            addCriterion("is_lose in", values, "isLose");
            return (Criteria) this;
        }

        public Criteria andIsLoseNotIn(List<String> values) {
            addCriterion("is_lose not in", values, "isLose");
            return (Criteria) this;
        }

        public Criteria andIsLoseBetween(String value1, String value2) {
            addCriterion("is_lose between", value1, value2, "isLose");
            return (Criteria) this;
        }

        public Criteria andIsLoseNotBetween(String value1, String value2) {
            addCriterion("is_lose not between", value1, value2, "isLose");
            return (Criteria) this;
        }

        public Criteria andInstructionsIsNull() {
            addCriterion("instructions is null");
            return (Criteria) this;
        }

        public Criteria andInstructionsIsNotNull() {
            addCriterion("instructions is not null");
            return (Criteria) this;
        }

        public Criteria andInstructionsEqualTo(String value) {
            addCriterion("instructions =", value, "instructions");
            return (Criteria) this;
        }

        public Criteria andInstructionsNotEqualTo(String value) {
            addCriterion("instructions <>", value, "instructions");
            return (Criteria) this;
        }

        public Criteria andInstructionsGreaterThan(String value) {
            addCriterion("instructions >", value, "instructions");
            return (Criteria) this;
        }

        public Criteria andInstructionsGreaterThanOrEqualTo(String value) {
            addCriterion("instructions >=", value, "instructions");
            return (Criteria) this;
        }

        public Criteria andInstructionsLessThan(String value) {
            addCriterion("instructions <", value, "instructions");
            return (Criteria) this;
        }

        public Criteria andInstructionsLessThanOrEqualTo(String value) {
            addCriterion("instructions <=", value, "instructions");
            return (Criteria) this;
        }

        public Criteria andInstructionsLike(String value) {
            addCriterion("instructions like", value, "instructions");
            return (Criteria) this;
        }

        public Criteria andInstructionsNotLike(String value) {
            addCriterion("instructions not like", value, "instructions");
            return (Criteria) this;
        }

        public Criteria andInstructionsIn(List<String> values) {
            addCriterion("instructions in", values, "instructions");
            return (Criteria) this;
        }

        public Criteria andInstructionsNotIn(List<String> values) {
            addCriterion("instructions not in", values, "instructions");
            return (Criteria) this;
        }

        public Criteria andInstructionsBetween(String value1, String value2) {
            addCriterion("instructions between", value1, value2, "instructions");
            return (Criteria) this;
        }

        public Criteria andInstructionsNotBetween(String value1, String value2) {
            addCriterion("instructions not between", value1, value2, "instructions");
            return (Criteria) this;
        }

        public Criteria andRemindIsNull() {
            addCriterion("remind is null");
            return (Criteria) this;
        }

        public Criteria andRemindIsNotNull() {
            addCriterion("remind is not null");
            return (Criteria) this;
        }

        public Criteria andRemindEqualTo(String value) {
            addCriterion("remind =", value, "remind");
            return (Criteria) this;
        }

        public Criteria andRemindNotEqualTo(String value) {
            addCriterion("remind <>", value, "remind");
            return (Criteria) this;
        }

        public Criteria andRemindGreaterThan(String value) {
            addCriterion("remind >", value, "remind");
            return (Criteria) this;
        }

        public Criteria andRemindGreaterThanOrEqualTo(String value) {
            addCriterion("remind >=", value, "remind");
            return (Criteria) this;
        }

        public Criteria andRemindLessThan(String value) {
            addCriterion("remind <", value, "remind");
            return (Criteria) this;
        }

        public Criteria andRemindLessThanOrEqualTo(String value) {
            addCriterion("remind <=", value, "remind");
            return (Criteria) this;
        }

        public Criteria andRemindLike(String value) {
            addCriterion("remind like", value, "remind");
            return (Criteria) this;
        }

        public Criteria andRemindNotLike(String value) {
            addCriterion("remind not like", value, "remind");
            return (Criteria) this;
        }

        public Criteria andRemindIn(List<String> values) {
            addCriterion("remind in", values, "remind");
            return (Criteria) this;
        }

        public Criteria andRemindNotIn(List<String> values) {
            addCriterion("remind not in", values, "remind");
            return (Criteria) this;
        }

        public Criteria andRemindBetween(String value1, String value2) {
            addCriterion("remind between", value1, value2, "remind");
            return (Criteria) this;
        }

        public Criteria andRemindNotBetween(String value1, String value2) {
            addCriterion("remind not between", value1, value2, "remind");
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
