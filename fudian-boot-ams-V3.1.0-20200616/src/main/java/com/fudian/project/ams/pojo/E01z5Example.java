package com.fudian.project.ams.pojo;

import java.util.ArrayList;
import java.util.List;

public class E01z5Example {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public E01z5Example() {
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

        public Criteria andOrgIdIsNull() {
            addCriterion("org_id is null");
            return (Criteria) this;
        }

        public Criteria andOrgIdIsNotNull() {
            addCriterion("org_id is not null");
            return (Criteria) this;
        }

        public Criteria andOrgIdEqualTo(String value) {
            addCriterion("org_id =", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdNotEqualTo(String value) {
            addCriterion("org_id <>", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdGreaterThan(String value) {
            addCriterion("org_id >", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdGreaterThanOrEqualTo(String value) {
            addCriterion("org_id >=", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdLessThan(String value) {
            addCriterion("org_id <", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdLessThanOrEqualTo(String value) {
            addCriterion("org_id <=", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdLike(String value) {
            addCriterion("org_id like", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdNotLike(String value) {
            addCriterion("org_id not like", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdIn(List<String> values) {
            addCriterion("org_id in", values, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdNotIn(List<String> values) {
            addCriterion("org_id not in", values, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdBetween(String value1, String value2) {
            addCriterion("org_id between", value1, value2, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdNotBetween(String value1, String value2) {
            addCriterion("org_id not between", value1, value2, "orgId");
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

        public Criteria andE01z501IsNull() {
            addCriterion("e01z501 is null");
            return (Criteria) this;
        }

        public Criteria andE01z501IsNotNull() {
            addCriterion("e01z501 is not null");
            return (Criteria) this;
        }

        public Criteria andE01z501EqualTo(String value) {
            addCriterion("e01z501 =", value, "e01z501");
            return (Criteria) this;
        }

        public Criteria andE01z501NotEqualTo(String value) {
            addCriterion("e01z501 <>", value, "e01z501");
            return (Criteria) this;
        }

        public Criteria andE01z501GreaterThan(String value) {
            addCriterion("e01z501 >", value, "e01z501");
            return (Criteria) this;
        }

        public Criteria andE01z501GreaterThanOrEqualTo(String value) {
            addCriterion("e01z501 >=", value, "e01z501");
            return (Criteria) this;
        }

        public Criteria andE01z501LessThan(String value) {
            addCriterion("e01z501 <", value, "e01z501");
            return (Criteria) this;
        }

        public Criteria andE01z501LessThanOrEqualTo(String value) {
            addCriterion("e01z501 <=", value, "e01z501");
            return (Criteria) this;
        }

        public Criteria andE01z501Like(String value) {
            addCriterion("e01z501 like", value, "e01z501");
            return (Criteria) this;
        }

        public Criteria andE01z501NotLike(String value) {
            addCriterion("e01z501 not like", value, "e01z501");
            return (Criteria) this;
        }

        public Criteria andE01z501In(List<String> values) {
            addCriterion("e01z501 in", values, "e01z501");
            return (Criteria) this;
        }

        public Criteria andE01z501NotIn(List<String> values) {
            addCriterion("e01z501 not in", values, "e01z501");
            return (Criteria) this;
        }

        public Criteria andE01z501Between(String value1, String value2) {
            addCriterion("e01z501 between", value1, value2, "e01z501");
            return (Criteria) this;
        }

        public Criteria andE01z501NotBetween(String value1, String value2) {
            addCriterion("e01z501 not between", value1, value2, "e01z501");
            return (Criteria) this;
        }

        public Criteria andE01z504IsNull() {
            addCriterion("e01z504 is null");
            return (Criteria) this;
        }

        public Criteria andE01z504IsNotNull() {
            addCriterion("e01z504 is not null");
            return (Criteria) this;
        }

        public Criteria andE01z504EqualTo(String value) {
            addCriterion("e01z504 =", value, "e01z504");
            return (Criteria) this;
        }

        public Criteria andE01z504NotEqualTo(String value) {
            addCriterion("e01z504 <>", value, "e01z504");
            return (Criteria) this;
        }

        public Criteria andE01z504GreaterThan(String value) {
            addCriterion("e01z504 >", value, "e01z504");
            return (Criteria) this;
        }

        public Criteria andE01z504GreaterThanOrEqualTo(String value) {
            addCriterion("e01z504 >=", value, "e01z504");
            return (Criteria) this;
        }

        public Criteria andE01z504LessThan(String value) {
            addCriterion("e01z504 <", value, "e01z504");
            return (Criteria) this;
        }

        public Criteria andE01z504LessThanOrEqualTo(String value) {
            addCriterion("e01z504 <=", value, "e01z504");
            return (Criteria) this;
        }

        public Criteria andE01z504Like(String value) {
            addCriterion("e01z504 like", value, "e01z504");
            return (Criteria) this;
        }

        public Criteria andE01z504NotLike(String value) {
            addCriterion("e01z504 not like", value, "e01z504");
            return (Criteria) this;
        }

        public Criteria andE01z504In(List<String> values) {
            addCriterion("e01z504 in", values, "e01z504");
            return (Criteria) this;
        }

        public Criteria andE01z504NotIn(List<String> values) {
            addCriterion("e01z504 not in", values, "e01z504");
            return (Criteria) this;
        }

        public Criteria andE01z504Between(String value1, String value2) {
            addCriterion("e01z504 between", value1, value2, "e01z504");
            return (Criteria) this;
        }

        public Criteria andE01z504NotBetween(String value1, String value2) {
            addCriterion("e01z504 not between", value1, value2, "e01z504");
            return (Criteria) this;
        }

        public Criteria andE01z507aIsNull() {
            addCriterion("e01z507a is null");
            return (Criteria) this;
        }

        public Criteria andE01z507aIsNotNull() {
            addCriterion("e01z507a is not null");
            return (Criteria) this;
        }

        public Criteria andE01z507aEqualTo(String value) {
            addCriterion("e01z507a =", value, "e01z507a");
            return (Criteria) this;
        }

        public Criteria andE01z507aNotEqualTo(String value) {
            addCriterion("e01z507a <>", value, "e01z507a");
            return (Criteria) this;
        }

        public Criteria andE01z507aGreaterThan(String value) {
            addCriterion("e01z507a >", value, "e01z507a");
            return (Criteria) this;
        }

        public Criteria andE01z507aGreaterThanOrEqualTo(String value) {
            addCriterion("e01z507a >=", value, "e01z507a");
            return (Criteria) this;
        }

        public Criteria andE01z507aLessThan(String value) {
            addCriterion("e01z507a <", value, "e01z507a");
            return (Criteria) this;
        }

        public Criteria andE01z507aLessThanOrEqualTo(String value) {
            addCriterion("e01z507a <=", value, "e01z507a");
            return (Criteria) this;
        }

        public Criteria andE01z507aLike(String value) {
            addCriterion("e01z507a like", value, "e01z507a");
            return (Criteria) this;
        }

        public Criteria andE01z507aNotLike(String value) {
            addCriterion("e01z507a not like", value, "e01z507a");
            return (Criteria) this;
        }

        public Criteria andE01z507aIn(List<String> values) {
            addCriterion("e01z507a in", values, "e01z507a");
            return (Criteria) this;
        }

        public Criteria andE01z507aNotIn(List<String> values) {
            addCriterion("e01z507a not in", values, "e01z507a");
            return (Criteria) this;
        }

        public Criteria andE01z507aBetween(String value1, String value2) {
            addCriterion("e01z507a between", value1, value2, "e01z507a");
            return (Criteria) this;
        }

        public Criteria andE01z507aNotBetween(String value1, String value2) {
            addCriterion("e01z507a not between", value1, value2, "e01z507a");
            return (Criteria) this;
        }

        public Criteria andE01z507bIsNull() {
            addCriterion("e01z507b is null");
            return (Criteria) this;
        }

        public Criteria andE01z507bIsNotNull() {
            addCriterion("e01z507b is not null");
            return (Criteria) this;
        }

        public Criteria andE01z507bEqualTo(String value) {
            addCriterion("e01z507b =", value, "e01z507b");
            return (Criteria) this;
        }

        public Criteria andE01z507bNotEqualTo(String value) {
            addCriterion("e01z507b <>", value, "e01z507b");
            return (Criteria) this;
        }

        public Criteria andE01z507bGreaterThan(String value) {
            addCriterion("e01z507b >", value, "e01z507b");
            return (Criteria) this;
        }

        public Criteria andE01z507bGreaterThanOrEqualTo(String value) {
            addCriterion("e01z507b >=", value, "e01z507b");
            return (Criteria) this;
        }

        public Criteria andE01z507bLessThan(String value) {
            addCriterion("e01z507b <", value, "e01z507b");
            return (Criteria) this;
        }

        public Criteria andE01z507bLessThanOrEqualTo(String value) {
            addCriterion("e01z507b <=", value, "e01z507b");
            return (Criteria) this;
        }

        public Criteria andE01z507bLike(String value) {
            addCriterion("e01z507b like", value, "e01z507b");
            return (Criteria) this;
        }

        public Criteria andE01z507bNotLike(String value) {
            addCriterion("e01z507b not like", value, "e01z507b");
            return (Criteria) this;
        }

        public Criteria andE01z507bIn(List<String> values) {
            addCriterion("e01z507b in", values, "e01z507b");
            return (Criteria) this;
        }

        public Criteria andE01z507bNotIn(List<String> values) {
            addCriterion("e01z507b not in", values, "e01z507b");
            return (Criteria) this;
        }

        public Criteria andE01z507bBetween(String value1, String value2) {
            addCriterion("e01z507b between", value1, value2, "e01z507b");
            return (Criteria) this;
        }

        public Criteria andE01z507bNotBetween(String value1, String value2) {
            addCriterion("e01z507b not between", value1, value2, "e01z507b");
            return (Criteria) this;
        }

        public Criteria andE01z511IsNull() {
            addCriterion("e01z511 is null");
            return (Criteria) this;
        }

        public Criteria andE01z511IsNotNull() {
            addCriterion("e01z511 is not null");
            return (Criteria) this;
        }

        public Criteria andE01z511EqualTo(Integer value) {
            addCriterion("e01z511 =", value, "e01z511");
            return (Criteria) this;
        }

        public Criteria andE01z511NotEqualTo(Integer value) {
            addCriterion("e01z511 <>", value, "e01z511");
            return (Criteria) this;
        }

        public Criteria andE01z511GreaterThan(Integer value) {
            addCriterion("e01z511 >", value, "e01z511");
            return (Criteria) this;
        }

        public Criteria andE01z511GreaterThanOrEqualTo(Integer value) {
            addCriterion("e01z511 >=", value, "e01z511");
            return (Criteria) this;
        }

        public Criteria andE01z511LessThan(Integer value) {
            addCriterion("e01z511 <", value, "e01z511");
            return (Criteria) this;
        }

        public Criteria andE01z511LessThanOrEqualTo(Integer value) {
            addCriterion("e01z511 <=", value, "e01z511");
            return (Criteria) this;
        }

        public Criteria andE01z511In(List<Integer> values) {
            addCriterion("e01z511 in", values, "e01z511");
            return (Criteria) this;
        }

        public Criteria andE01z511NotIn(List<Integer> values) {
            addCriterion("e01z511 not in", values, "e01z511");
            return (Criteria) this;
        }

        public Criteria andE01z511Between(Integer value1, Integer value2) {
            addCriterion("e01z511 between", value1, value2, "e01z511");
            return (Criteria) this;
        }

        public Criteria andE01z511NotBetween(Integer value1, Integer value2) {
            addCriterion("e01z511 not between", value1, value2, "e01z511");
            return (Criteria) this;
        }

        public Criteria andE01z514IsNull() {
            addCriterion("e01z514 is null");
            return (Criteria) this;
        }

        public Criteria andE01z514IsNotNull() {
            addCriterion("e01z514 is not null");
            return (Criteria) this;
        }

        public Criteria andE01z514EqualTo(Integer value) {
            addCriterion("e01z514 =", value, "e01z514");
            return (Criteria) this;
        }

        public Criteria andE01z514NotEqualTo(Integer value) {
            addCriterion("e01z514 <>", value, "e01z514");
            return (Criteria) this;
        }

        public Criteria andE01z514GreaterThan(Integer value) {
            addCriterion("e01z514 >", value, "e01z514");
            return (Criteria) this;
        }

        public Criteria andE01z514GreaterThanOrEqualTo(Integer value) {
            addCriterion("e01z514 >=", value, "e01z514");
            return (Criteria) this;
        }

        public Criteria andE01z514LessThan(Integer value) {
            addCriterion("e01z514 <", value, "e01z514");
            return (Criteria) this;
        }

        public Criteria andE01z514LessThanOrEqualTo(Integer value) {
            addCriterion("e01z514 <=", value, "e01z514");
            return (Criteria) this;
        }

        public Criteria andE01z514In(List<Integer> values) {
            addCriterion("e01z514 in", values, "e01z514");
            return (Criteria) this;
        }

        public Criteria andE01z514NotIn(List<Integer> values) {
            addCriterion("e01z514 not in", values, "e01z514");
            return (Criteria) this;
        }

        public Criteria andE01z514Between(Integer value1, Integer value2) {
            addCriterion("e01z514 between", value1, value2, "e01z514");
            return (Criteria) this;
        }

        public Criteria andE01z514NotBetween(Integer value1, Integer value2) {
            addCriterion("e01z514 not between", value1, value2, "e01z514");
            return (Criteria) this;
        }

        public Criteria andE01z517IsNull() {
            addCriterion("e01z517 is null");
            return (Criteria) this;
        }

        public Criteria andE01z517IsNotNull() {
            addCriterion("e01z517 is not null");
            return (Criteria) this;
        }

        public Criteria andE01z517EqualTo(String value) {
            addCriterion("e01z517 =", value, "e01z517");
            return (Criteria) this;
        }

        public Criteria andE01z517NotEqualTo(String value) {
            addCriterion("e01z517 <>", value, "e01z517");
            return (Criteria) this;
        }

        public Criteria andE01z517GreaterThan(String value) {
            addCriterion("e01z517 >", value, "e01z517");
            return (Criteria) this;
        }

        public Criteria andE01z517GreaterThanOrEqualTo(String value) {
            addCriterion("e01z517 >=", value, "e01z517");
            return (Criteria) this;
        }

        public Criteria andE01z517LessThan(String value) {
            addCriterion("e01z517 <", value, "e01z517");
            return (Criteria) this;
        }

        public Criteria andE01z517LessThanOrEqualTo(String value) {
            addCriterion("e01z517 <=", value, "e01z517");
            return (Criteria) this;
        }

        public Criteria andE01z517Like(String value) {
            addCriterion("e01z517 like", value, "e01z517");
            return (Criteria) this;
        }

        public Criteria andE01z517NotLike(String value) {
            addCriterion("e01z517 not like", value, "e01z517");
            return (Criteria) this;
        }

        public Criteria andE01z517In(List<String> values) {
            addCriterion("e01z517 in", values, "e01z517");
            return (Criteria) this;
        }

        public Criteria andE01z517NotIn(List<String> values) {
            addCriterion("e01z517 not in", values, "e01z517");
            return (Criteria) this;
        }

        public Criteria andE01z517Between(String value1, String value2) {
            addCriterion("e01z517 between", value1, value2, "e01z517");
            return (Criteria) this;
        }

        public Criteria andE01z517NotBetween(String value1, String value2) {
            addCriterion("e01z517 not between", value1, value2, "e01z517");
            return (Criteria) this;
        }

        public Criteria andE01z521IsNull() {
            addCriterion("e01z521 is null");
            return (Criteria) this;
        }

        public Criteria andE01z521IsNotNull() {
            addCriterion("e01z521 is not null");
            return (Criteria) this;
        }

        public Criteria andE01z521EqualTo(String value) {
            addCriterion("e01z521 =", value, "e01z521");
            return (Criteria) this;
        }

        public Criteria andE01z521NotEqualTo(String value) {
            addCriterion("e01z521 <>", value, "e01z521");
            return (Criteria) this;
        }

        public Criteria andE01z521GreaterThan(String value) {
            addCriterion("e01z521 >", value, "e01z521");
            return (Criteria) this;
        }

        public Criteria andE01z521GreaterThanOrEqualTo(String value) {
            addCriterion("e01z521 >=", value, "e01z521");
            return (Criteria) this;
        }

        public Criteria andE01z521LessThan(String value) {
            addCriterion("e01z521 <", value, "e01z521");
            return (Criteria) this;
        }

        public Criteria andE01z521LessThanOrEqualTo(String value) {
            addCriterion("e01z521 <=", value, "e01z521");
            return (Criteria) this;
        }

        public Criteria andE01z521Like(String value) {
            addCriterion("e01z521 like", value, "e01z521");
            return (Criteria) this;
        }

        public Criteria andE01z521NotLike(String value) {
            addCriterion("e01z521 not like", value, "e01z521");
            return (Criteria) this;
        }

        public Criteria andE01z521In(List<String> values) {
            addCriterion("e01z521 in", values, "e01z521");
            return (Criteria) this;
        }

        public Criteria andE01z521NotIn(List<String> values) {
            addCriterion("e01z521 not in", values, "e01z521");
            return (Criteria) this;
        }

        public Criteria andE01z521Between(String value1, String value2) {
            addCriterion("e01z521 between", value1, value2, "e01z521");
            return (Criteria) this;
        }

        public Criteria andE01z521NotBetween(String value1, String value2) {
            addCriterion("e01z521 not between", value1, value2, "e01z521");
            return (Criteria) this;
        }

        public Criteria andE01z524IsNull() {
            addCriterion("e01z524 is null");
            return (Criteria) this;
        }

        public Criteria andE01z524IsNotNull() {
            addCriterion("e01z524 is not null");
            return (Criteria) this;
        }

        public Criteria andE01z524EqualTo(String value) {
            addCriterion("e01z524 =", value, "e01z524");
            return (Criteria) this;
        }

        public Criteria andE01z524NotEqualTo(String value) {
            addCriterion("e01z524 <>", value, "e01z524");
            return (Criteria) this;
        }

        public Criteria andE01z524GreaterThan(String value) {
            addCriterion("e01z524 >", value, "e01z524");
            return (Criteria) this;
        }

        public Criteria andE01z524GreaterThanOrEqualTo(String value) {
            addCriterion("e01z524 >=", value, "e01z524");
            return (Criteria) this;
        }

        public Criteria andE01z524LessThan(String value) {
            addCriterion("e01z524 <", value, "e01z524");
            return (Criteria) this;
        }

        public Criteria andE01z524LessThanOrEqualTo(String value) {
            addCriterion("e01z524 <=", value, "e01z524");
            return (Criteria) this;
        }

        public Criteria andE01z524Like(String value) {
            addCriterion("e01z524 like", value, "e01z524");
            return (Criteria) this;
        }

        public Criteria andE01z524NotLike(String value) {
            addCriterion("e01z524 not like", value, "e01z524");
            return (Criteria) this;
        }

        public Criteria andE01z524In(List<String> values) {
            addCriterion("e01z524 in", values, "e01z524");
            return (Criteria) this;
        }

        public Criteria andE01z524NotIn(List<String> values) {
            addCriterion("e01z524 not in", values, "e01z524");
            return (Criteria) this;
        }

        public Criteria andE01z524Between(String value1, String value2) {
            addCriterion("e01z524 between", value1, value2, "e01z524");
            return (Criteria) this;
        }

        public Criteria andE01z524NotBetween(String value1, String value2) {
            addCriterion("e01z524 not between", value1, value2, "e01z524");
            return (Criteria) this;
        }

        public Criteria andE01z527IsNull() {
            addCriterion("e01z527 is null");
            return (Criteria) this;
        }

        public Criteria andE01z527IsNotNull() {
            addCriterion("e01z527 is not null");
            return (Criteria) this;
        }

        public Criteria andE01z527EqualTo(String value) {
            addCriterion("e01z527 =", value, "e01z527");
            return (Criteria) this;
        }

        public Criteria andE01z527NotEqualTo(String value) {
            addCriterion("e01z527 <>", value, "e01z527");
            return (Criteria) this;
        }

        public Criteria andE01z527GreaterThan(String value) {
            addCriterion("e01z527 >", value, "e01z527");
            return (Criteria) this;
        }

        public Criteria andE01z527GreaterThanOrEqualTo(String value) {
            addCriterion("e01z527 >=", value, "e01z527");
            return (Criteria) this;
        }

        public Criteria andE01z527LessThan(String value) {
            addCriterion("e01z527 <", value, "e01z527");
            return (Criteria) this;
        }

        public Criteria andE01z527LessThanOrEqualTo(String value) {
            addCriterion("e01z527 <=", value, "e01z527");
            return (Criteria) this;
        }

        public Criteria andE01z527Like(String value) {
            addCriterion("e01z527 like", value, "e01z527");
            return (Criteria) this;
        }

        public Criteria andE01z527NotLike(String value) {
            addCriterion("e01z527 not like", value, "e01z527");
            return (Criteria) this;
        }

        public Criteria andE01z527In(List<String> values) {
            addCriterion("e01z527 in", values, "e01z527");
            return (Criteria) this;
        }

        public Criteria andE01z527NotIn(List<String> values) {
            addCriterion("e01z527 not in", values, "e01z527");
            return (Criteria) this;
        }

        public Criteria andE01z527Between(String value1, String value2) {
            addCriterion("e01z527 between", value1, value2, "e01z527");
            return (Criteria) this;
        }

        public Criteria andE01z527NotBetween(String value1, String value2) {
            addCriterion("e01z527 not between", value1, value2, "e01z527");
            return (Criteria) this;
        }

        public Criteria andE01z531IsNull() {
            addCriterion("e01z531 is null");
            return (Criteria) this;
        }

        public Criteria andE01z531IsNotNull() {
            addCriterion("e01z531 is not null");
            return (Criteria) this;
        }

        public Criteria andE01z531EqualTo(String value) {
            addCriterion("e01z531 =", value, "e01z531");
            return (Criteria) this;
        }

        public Criteria andE01z531NotEqualTo(String value) {
            addCriterion("e01z531 <>", value, "e01z531");
            return (Criteria) this;
        }

        public Criteria andE01z531GreaterThan(String value) {
            addCriterion("e01z531 >", value, "e01z531");
            return (Criteria) this;
        }

        public Criteria andE01z531GreaterThanOrEqualTo(String value) {
            addCriterion("e01z531 >=", value, "e01z531");
            return (Criteria) this;
        }

        public Criteria andE01z531LessThan(String value) {
            addCriterion("e01z531 <", value, "e01z531");
            return (Criteria) this;
        }

        public Criteria andE01z531LessThanOrEqualTo(String value) {
            addCriterion("e01z531 <=", value, "e01z531");
            return (Criteria) this;
        }

        public Criteria andE01z531Like(String value) {
            addCriterion("e01z531 like", value, "e01z531");
            return (Criteria) this;
        }

        public Criteria andE01z531NotLike(String value) {
            addCriterion("e01z531 not like", value, "e01z531");
            return (Criteria) this;
        }

        public Criteria andE01z531In(List<String> values) {
            addCriterion("e01z531 in", values, "e01z531");
            return (Criteria) this;
        }

        public Criteria andE01z531NotIn(List<String> values) {
            addCriterion("e01z531 not in", values, "e01z531");
            return (Criteria) this;
        }

        public Criteria andE01z531Between(String value1, String value2) {
            addCriterion("e01z531 between", value1, value2, "e01z531");
            return (Criteria) this;
        }

        public Criteria andE01z531NotBetween(String value1, String value2) {
            addCriterion("e01z531 not between", value1, value2, "e01z531");
            return (Criteria) this;
        }

        public Criteria andE01z534IsNull() {
            addCriterion("e01z534 is null");
            return (Criteria) this;
        }

        public Criteria andE01z534IsNotNull() {
            addCriterion("e01z534 is not null");
            return (Criteria) this;
        }

        public Criteria andE01z534EqualTo(String value) {
            addCriterion("e01z534 =", value, "e01z534");
            return (Criteria) this;
        }

        public Criteria andE01z534NotEqualTo(String value) {
            addCriterion("e01z534 <>", value, "e01z534");
            return (Criteria) this;
        }

        public Criteria andE01z534GreaterThan(String value) {
            addCriterion("e01z534 >", value, "e01z534");
            return (Criteria) this;
        }

        public Criteria andE01z534GreaterThanOrEqualTo(String value) {
            addCriterion("e01z534 >=", value, "e01z534");
            return (Criteria) this;
        }

        public Criteria andE01z534LessThan(String value) {
            addCriterion("e01z534 <", value, "e01z534");
            return (Criteria) this;
        }

        public Criteria andE01z534LessThanOrEqualTo(String value) {
            addCriterion("e01z534 <=", value, "e01z534");
            return (Criteria) this;
        }

        public Criteria andE01z534Like(String value) {
            addCriterion("e01z534 like", value, "e01z534");
            return (Criteria) this;
        }

        public Criteria andE01z534NotLike(String value) {
            addCriterion("e01z534 not like", value, "e01z534");
            return (Criteria) this;
        }

        public Criteria andE01z534In(List<String> values) {
            addCriterion("e01z534 in", values, "e01z534");
            return (Criteria) this;
        }

        public Criteria andE01z534NotIn(List<String> values) {
            addCriterion("e01z534 not in", values, "e01z534");
            return (Criteria) this;
        }

        public Criteria andE01z534Between(String value1, String value2) {
            addCriterion("e01z534 between", value1, value2, "e01z534");
            return (Criteria) this;
        }

        public Criteria andE01z534NotBetween(String value1, String value2) {
            addCriterion("e01z534 not between", value1, value2, "e01z534");
            return (Criteria) this;
        }

        public Criteria andE01z537IsNull() {
            addCriterion("e01z537 is null");
            return (Criteria) this;
        }

        public Criteria andE01z537IsNotNull() {
            addCriterion("e01z537 is not null");
            return (Criteria) this;
        }

        public Criteria andE01z537EqualTo(String value) {
            addCriterion("e01z537 =", value, "e01z537");
            return (Criteria) this;
        }

        public Criteria andE01z537NotEqualTo(String value) {
            addCriterion("e01z537 <>", value, "e01z537");
            return (Criteria) this;
        }

        public Criteria andE01z537GreaterThan(String value) {
            addCriterion("e01z537 >", value, "e01z537");
            return (Criteria) this;
        }

        public Criteria andE01z537GreaterThanOrEqualTo(String value) {
            addCriterion("e01z537 >=", value, "e01z537");
            return (Criteria) this;
        }

        public Criteria andE01z537LessThan(String value) {
            addCriterion("e01z537 <", value, "e01z537");
            return (Criteria) this;
        }

        public Criteria andE01z537LessThanOrEqualTo(String value) {
            addCriterion("e01z537 <=", value, "e01z537");
            return (Criteria) this;
        }

        public Criteria andE01z537Like(String value) {
            addCriterion("e01z537 like", value, "e01z537");
            return (Criteria) this;
        }

        public Criteria andE01z537NotLike(String value) {
            addCriterion("e01z537 not like", value, "e01z537");
            return (Criteria) this;
        }

        public Criteria andE01z537In(List<String> values) {
            addCriterion("e01z537 in", values, "e01z537");
            return (Criteria) this;
        }

        public Criteria andE01z537NotIn(List<String> values) {
            addCriterion("e01z537 not in", values, "e01z537");
            return (Criteria) this;
        }

        public Criteria andE01z537Between(String value1, String value2) {
            addCriterion("e01z537 between", value1, value2, "e01z537");
            return (Criteria) this;
        }

        public Criteria andE01z537NotBetween(String value1, String value2) {
            addCriterion("e01z537 not between", value1, value2, "e01z537");
            return (Criteria) this;
        }

        public Criteria andE01z541IsNull() {
            addCriterion("e01z541 is null");
            return (Criteria) this;
        }

        public Criteria andE01z541IsNotNull() {
            addCriterion("e01z541 is not null");
            return (Criteria) this;
        }

        public Criteria andE01z541EqualTo(String value) {
            addCriterion("e01z541 =", value, "e01z541");
            return (Criteria) this;
        }

        public Criteria andE01z541NotEqualTo(String value) {
            addCriterion("e01z541 <>", value, "e01z541");
            return (Criteria) this;
        }

        public Criteria andE01z541GreaterThan(String value) {
            addCriterion("e01z541 >", value, "e01z541");
            return (Criteria) this;
        }

        public Criteria andE01z541GreaterThanOrEqualTo(String value) {
            addCriterion("e01z541 >=", value, "e01z541");
            return (Criteria) this;
        }

        public Criteria andE01z541LessThan(String value) {
            addCriterion("e01z541 <", value, "e01z541");
            return (Criteria) this;
        }

        public Criteria andE01z541LessThanOrEqualTo(String value) {
            addCriterion("e01z541 <=", value, "e01z541");
            return (Criteria) this;
        }

        public Criteria andE01z541Like(String value) {
            addCriterion("e01z541 like", value, "e01z541");
            return (Criteria) this;
        }

        public Criteria andE01z541NotLike(String value) {
            addCriterion("e01z541 not like", value, "e01z541");
            return (Criteria) this;
        }

        public Criteria andE01z541In(List<String> values) {
            addCriterion("e01z541 in", values, "e01z541");
            return (Criteria) this;
        }

        public Criteria andE01z541NotIn(List<String> values) {
            addCriterion("e01z541 not in", values, "e01z541");
            return (Criteria) this;
        }

        public Criteria andE01z541Between(String value1, String value2) {
            addCriterion("e01z541 between", value1, value2, "e01z541");
            return (Criteria) this;
        }

        public Criteria andE01z541NotBetween(String value1, String value2) {
            addCriterion("e01z541 not between", value1, value2, "e01z541");
            return (Criteria) this;
        }

        public Criteria andE01z544IsNull() {
            addCriterion("e01z544 is null");
            return (Criteria) this;
        }

        public Criteria andE01z544IsNotNull() {
            addCriterion("e01z544 is not null");
            return (Criteria) this;
        }

        public Criteria andE01z544EqualTo(String value) {
            addCriterion("e01z544 =", value, "e01z544");
            return (Criteria) this;
        }

        public Criteria andE01z544NotEqualTo(String value) {
            addCriterion("e01z544 <>", value, "e01z544");
            return (Criteria) this;
        }

        public Criteria andE01z544GreaterThan(String value) {
            addCriterion("e01z544 >", value, "e01z544");
            return (Criteria) this;
        }

        public Criteria andE01z544GreaterThanOrEqualTo(String value) {
            addCriterion("e01z544 >=", value, "e01z544");
            return (Criteria) this;
        }

        public Criteria andE01z544LessThan(String value) {
            addCriterion("e01z544 <", value, "e01z544");
            return (Criteria) this;
        }

        public Criteria andE01z544LessThanOrEqualTo(String value) {
            addCriterion("e01z544 <=", value, "e01z544");
            return (Criteria) this;
        }

        public Criteria andE01z544Like(String value) {
            addCriterion("e01z544 like", value, "e01z544");
            return (Criteria) this;
        }

        public Criteria andE01z544NotLike(String value) {
            addCriterion("e01z544 not like", value, "e01z544");
            return (Criteria) this;
        }

        public Criteria andE01z544In(List<String> values) {
            addCriterion("e01z544 in", values, "e01z544");
            return (Criteria) this;
        }

        public Criteria andE01z544NotIn(List<String> values) {
            addCriterion("e01z544 not in", values, "e01z544");
            return (Criteria) this;
        }

        public Criteria andE01z544Between(String value1, String value2) {
            addCriterion("e01z544 between", value1, value2, "e01z544");
            return (Criteria) this;
        }

        public Criteria andE01z544NotBetween(String value1, String value2) {
            addCriterion("e01z544 not between", value1, value2, "e01z544");
            return (Criteria) this;
        }

        public Criteria andDutyIsNull() {
            addCriterion("duty is null");
            return (Criteria) this;
        }

        public Criteria andDutyIsNotNull() {
            addCriterion("duty is not null");
            return (Criteria) this;
        }

        public Criteria andDutyEqualTo(String value) {
            addCriterion("duty =", value, "duty");
            return (Criteria) this;
        }

        public Criteria andDutyNotEqualTo(String value) {
            addCriterion("duty <>", value, "duty");
            return (Criteria) this;
        }

        public Criteria andDutyGreaterThan(String value) {
            addCriterion("duty >", value, "duty");
            return (Criteria) this;
        }

        public Criteria andDutyGreaterThanOrEqualTo(String value) {
            addCriterion("duty >=", value, "duty");
            return (Criteria) this;
        }

        public Criteria andDutyLessThan(String value) {
            addCriterion("duty <", value, "duty");
            return (Criteria) this;
        }

        public Criteria andDutyLessThanOrEqualTo(String value) {
            addCriterion("duty <=", value, "duty");
            return (Criteria) this;
        }

        public Criteria andDutyLike(String value) {
            addCriterion("duty like", value, "duty");
            return (Criteria) this;
        }

        public Criteria andDutyNotLike(String value) {
            addCriterion("duty not like", value, "duty");
            return (Criteria) this;
        }

        public Criteria andDutyIn(List<String> values) {
            addCriterion("duty in", values, "duty");
            return (Criteria) this;
        }

        public Criteria andDutyNotIn(List<String> values) {
            addCriterion("duty not in", values, "duty");
            return (Criteria) this;
        }

        public Criteria andDutyBetween(String value1, String value2) {
            addCriterion("duty between", value1, value2, "duty");
            return (Criteria) this;
        }

        public Criteria andDutyNotBetween(String value1, String value2) {
            addCriterion("duty not between", value1, value2, "duty");
            return (Criteria) this;
        }

        public Criteria andReasonIsNull() {
            addCriterion("reason is null");
            return (Criteria) this;
        }

        public Criteria andReasonIsNotNull() {
            addCriterion("reason is not null");
            return (Criteria) this;
        }

        public Criteria andReasonEqualTo(String value) {
            addCriterion("reason =", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonNotEqualTo(String value) {
            addCriterion("reason <>", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonGreaterThan(String value) {
            addCriterion("reason >", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonGreaterThanOrEqualTo(String value) {
            addCriterion("reason >=", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonLessThan(String value) {
            addCriterion("reason <", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonLessThanOrEqualTo(String value) {
            addCriterion("reason <=", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonLike(String value) {
            addCriterion("reason like", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonNotLike(String value) {
            addCriterion("reason not like", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonIn(List<String> values) {
            addCriterion("reason in", values, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonNotIn(List<String> values) {
            addCriterion("reason not in", values, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonBetween(String value1, String value2) {
            addCriterion("reason between", value1, value2, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonNotBetween(String value1, String value2) {
            addCriterion("reason not between", value1, value2, "reason");
            return (Criteria) this;
        }

        public Criteria andTransIdIsNull() {
            addCriterion("trans_id is null");
            return (Criteria) this;
        }

        public Criteria andTransIdIsNotNull() {
            addCriterion("trans_id is not null");
            return (Criteria) this;
        }

        public Criteria andTransIdEqualTo(String value) {
            addCriterion("trans_id =", value, "transId");
            return (Criteria) this;
        }

        public Criteria andTransIdNotEqualTo(String value) {
            addCriterion("trans_id <>", value, "transId");
            return (Criteria) this;
        }

        public Criteria andTransIdGreaterThan(String value) {
            addCriterion("trans_id >", value, "transId");
            return (Criteria) this;
        }

        public Criteria andTransIdGreaterThanOrEqualTo(String value) {
            addCriterion("trans_id >=", value, "transId");
            return (Criteria) this;
        }

        public Criteria andTransIdLessThan(String value) {
            addCriterion("trans_id <", value, "transId");
            return (Criteria) this;
        }

        public Criteria andTransIdLessThanOrEqualTo(String value) {
            addCriterion("trans_id <=", value, "transId");
            return (Criteria) this;
        }

        public Criteria andTransIdLike(String value) {
            addCriterion("trans_id like", value, "transId");
            return (Criteria) this;
        }

        public Criteria andTransIdNotLike(String value) {
            addCriterion("trans_id not like", value, "transId");
            return (Criteria) this;
        }

        public Criteria andTransIdIn(List<String> values) {
            addCriterion("trans_id in", values, "transId");
            return (Criteria) this;
        }

        public Criteria andTransIdNotIn(List<String> values) {
            addCriterion("trans_id not in", values, "transId");
            return (Criteria) this;
        }

        public Criteria andTransIdBetween(String value1, String value2) {
            addCriterion("trans_id between", value1, value2, "transId");
            return (Criteria) this;
        }

        public Criteria andTransIdNotBetween(String value1, String value2) {
            addCriterion("trans_id not between", value1, value2, "transId");
            return (Criteria) this;
        }

        public Criteria andScoreIsNull() {
            addCriterion("score is null");
            return (Criteria) this;
        }

        public Criteria andScoreIsNotNull() {
            addCriterion("score is not null");
            return (Criteria) this;
        }

        public Criteria andScoreEqualTo(Integer value) {
            addCriterion("score =", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreNotEqualTo(Integer value) {
            addCriterion("score <>", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreGreaterThan(Integer value) {
            addCriterion("score >", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreGreaterThanOrEqualTo(Integer value) {
            addCriterion("score >=", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreLessThan(Integer value) {
            addCriterion("score <", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreLessThanOrEqualTo(Integer value) {
            addCriterion("score <=", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreIn(List<Integer> values) {
            addCriterion("score in", values, "score");
            return (Criteria) this;
        }

        public Criteria andScoreNotIn(List<Integer> values) {
            addCriterion("score not in", values, "score");
            return (Criteria) this;
        }

        public Criteria andScoreBetween(Integer value1, Integer value2) {
            addCriterion("score between", value1, value2, "score");
            return (Criteria) this;
        }

        public Criteria andScoreNotBetween(Integer value1, Integer value2) {
            addCriterion("score not between", value1, value2, "score");
            return (Criteria) this;
        }

        public Criteria andProblemIsNull() {
            addCriterion("problem is null");
            return (Criteria) this;
        }

        public Criteria andProblemIsNotNull() {
            addCriterion("problem is not null");
            return (Criteria) this;
        }

        public Criteria andProblemEqualTo(String value) {
            addCriterion("problem =", value, "problem");
            return (Criteria) this;
        }

        public Criteria andProblemNotEqualTo(String value) {
            addCriterion("problem <>", value, "problem");
            return (Criteria) this;
        }

        public Criteria andProblemGreaterThan(String value) {
            addCriterion("problem >", value, "problem");
            return (Criteria) this;
        }

        public Criteria andProblemGreaterThanOrEqualTo(String value) {
            addCriterion("problem >=", value, "problem");
            return (Criteria) this;
        }

        public Criteria andProblemLessThan(String value) {
            addCriterion("problem <", value, "problem");
            return (Criteria) this;
        }

        public Criteria andProblemLessThanOrEqualTo(String value) {
            addCriterion("problem <=", value, "problem");
            return (Criteria) this;
        }

        public Criteria andProblemLike(String value) {
            addCriterion("problem like", value, "problem");
            return (Criteria) this;
        }

        public Criteria andProblemNotLike(String value) {
            addCriterion("problem not like", value, "problem");
            return (Criteria) this;
        }

        public Criteria andProblemIn(List<String> values) {
            addCriterion("problem in", values, "problem");
            return (Criteria) this;
        }

        public Criteria andProblemNotIn(List<String> values) {
            addCriterion("problem not in", values, "problem");
            return (Criteria) this;
        }

        public Criteria andProblemBetween(String value1, String value2) {
            addCriterion("problem between", value1, value2, "problem");
            return (Criteria) this;
        }

        public Criteria andProblemNotBetween(String value1, String value2) {
            addCriterion("problem not between", value1, value2, "problem");
            return (Criteria) this;
        }

        public Criteria andIsCopyIsNull() {
            addCriterion("is_copy is null");
            return (Criteria) this;
        }

        public Criteria andIsCopyIsNotNull() {
            addCriterion("is_copy is not null");
            return (Criteria) this;
        }

        public Criteria andIsCopyEqualTo(Integer value) {
            addCriterion("is_copy =", value, "isCopy");
            return (Criteria) this;
        }

        public Criteria andIsCopyNotEqualTo(Integer value) {
            addCriterion("is_copy <>", value, "isCopy");
            return (Criteria) this;
        }

        public Criteria andIsCopyGreaterThan(Integer value) {
            addCriterion("is_copy >", value, "isCopy");
            return (Criteria) this;
        }

        public Criteria andIsCopyGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_copy >=", value, "isCopy");
            return (Criteria) this;
        }

        public Criteria andIsCopyLessThan(Integer value) {
            addCriterion("is_copy <", value, "isCopy");
            return (Criteria) this;
        }

        public Criteria andIsCopyLessThanOrEqualTo(Integer value) {
            addCriterion("is_copy <=", value, "isCopy");
            return (Criteria) this;
        }

        public Criteria andIsCopyIn(List<Integer> values) {
            addCriterion("is_copy in", values, "isCopy");
            return (Criteria) this;
        }

        public Criteria andIsCopyNotIn(List<Integer> values) {
            addCriterion("is_copy not in", values, "isCopy");
            return (Criteria) this;
        }

        public Criteria andIsCopyBetween(Integer value1, Integer value2) {
            addCriterion("is_copy between", value1, value2, "isCopy");
            return (Criteria) this;
        }

        public Criteria andIsCopyNotBetween(Integer value1, Integer value2) {
            addCriterion("is_copy not between", value1, value2, "isCopy");
            return (Criteria) this;
        }

        public Criteria andCopySaveIsNull() {
            addCriterion("copy_save is null");
            return (Criteria) this;
        }

        public Criteria andCopySaveIsNotNull() {
            addCriterion("copy_save is not null");
            return (Criteria) this;
        }

        public Criteria andCopySaveEqualTo(String value) {
            addCriterion("copy_save =", value, "copySave");
            return (Criteria) this;
        }

        public Criteria andCopySaveNotEqualTo(String value) {
            addCriterion("copy_save <>", value, "copySave");
            return (Criteria) this;
        }

        public Criteria andCopySaveGreaterThan(String value) {
            addCriterion("copy_save >", value, "copySave");
            return (Criteria) this;
        }

        public Criteria andCopySaveGreaterThanOrEqualTo(String value) {
            addCriterion("copy_save >=", value, "copySave");
            return (Criteria) this;
        }

        public Criteria andCopySaveLessThan(String value) {
            addCriterion("copy_save <", value, "copySave");
            return (Criteria) this;
        }

        public Criteria andCopySaveLessThanOrEqualTo(String value) {
            addCriterion("copy_save <=", value, "copySave");
            return (Criteria) this;
        }

        public Criteria andCopySaveLike(String value) {
            addCriterion("copy_save like", value, "copySave");
            return (Criteria) this;
        }

        public Criteria andCopySaveNotLike(String value) {
            addCriterion("copy_save not like", value, "copySave");
            return (Criteria) this;
        }

        public Criteria andCopySaveIn(List<String> values) {
            addCriterion("copy_save in", values, "copySave");
            return (Criteria) this;
        }

        public Criteria andCopySaveNotIn(List<String> values) {
            addCriterion("copy_save not in", values, "copySave");
            return (Criteria) this;
        }

        public Criteria andCopySaveBetween(String value1, String value2) {
            addCriterion("copy_save between", value1, value2, "copySave");
            return (Criteria) this;
        }

        public Criteria andCopySaveNotBetween(String value1, String value2) {
            addCriterion("copy_save not between", value1, value2, "copySave");
            return (Criteria) this;
        }

        public Criteria andLackIsNull() {
            addCriterion("lack is null");
            return (Criteria) this;
        }

        public Criteria andLackIsNotNull() {
            addCriterion("lack is not null");
            return (Criteria) this;
        }

        public Criteria andLackEqualTo(String value) {
            addCriterion("lack =", value, "lack");
            return (Criteria) this;
        }

        public Criteria andLackNotEqualTo(String value) {
            addCriterion("lack <>", value, "lack");
            return (Criteria) this;
        }

        public Criteria andLackGreaterThan(String value) {
            addCriterion("lack >", value, "lack");
            return (Criteria) this;
        }

        public Criteria andLackGreaterThanOrEqualTo(String value) {
            addCriterion("lack >=", value, "lack");
            return (Criteria) this;
        }

        public Criteria andLackLessThan(String value) {
            addCriterion("lack <", value, "lack");
            return (Criteria) this;
        }

        public Criteria andLackLessThanOrEqualTo(String value) {
            addCriterion("lack <=", value, "lack");
            return (Criteria) this;
        }

        public Criteria andLackLike(String value) {
            addCriterion("lack like", value, "lack");
            return (Criteria) this;
        }

        public Criteria andLackNotLike(String value) {
            addCriterion("lack not like", value, "lack");
            return (Criteria) this;
        }

        public Criteria andLackIn(List<String> values) {
            addCriterion("lack in", values, "lack");
            return (Criteria) this;
        }

        public Criteria andLackNotIn(List<String> values) {
            addCriterion("lack not in", values, "lack");
            return (Criteria) this;
        }

        public Criteria andLackBetween(String value1, String value2) {
            addCriterion("lack between", value1, value2, "lack");
            return (Criteria) this;
        }

        public Criteria andLackNotBetween(String value1, String value2) {
            addCriterion("lack not between", value1, value2, "lack");
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

        public Criteria andCatalogEqualTo(Integer value) {
            addCriterion("`catalog` =", value, "catalog");
            return (Criteria) this;
        }

        public Criteria andCatalogNotEqualTo(Integer value) {
            addCriterion("`catalog` <>", value, "catalog");
            return (Criteria) this;
        }

        public Criteria andCatalogGreaterThan(Integer value) {
            addCriterion("`catalog` >", value, "catalog");
            return (Criteria) this;
        }

        public Criteria andCatalogGreaterThanOrEqualTo(Integer value) {
            addCriterion("`catalog` >=", value, "catalog");
            return (Criteria) this;
        }

        public Criteria andCatalogLessThan(Integer value) {
            addCriterion("`catalog` <", value, "catalog");
            return (Criteria) this;
        }

        public Criteria andCatalogLessThanOrEqualTo(Integer value) {
            addCriterion("`catalog` <=", value, "catalog");
            return (Criteria) this;
        }

        public Criteria andCatalogIn(List<Integer> values) {
            addCriterion("`catalog` in", values, "catalog");
            return (Criteria) this;
        }

        public Criteria andCatalogNotIn(List<Integer> values) {
            addCriterion("`catalog` not in", values, "catalog");
            return (Criteria) this;
        }

        public Criteria andCatalogBetween(Integer value1, Integer value2) {
            addCriterion("`catalog` between", value1, value2, "catalog");
            return (Criteria) this;
        }

        public Criteria andCatalogNotBetween(Integer value1, Integer value2) {
            addCriterion("`catalog` not between", value1, value2, "catalog");
            return (Criteria) this;
        }

        public Criteria andDeptIdIsNull() {
            addCriterion("dept_id is null");
            return (Criteria) this;
        }

        public Criteria andDeptIdIsNotNull() {
            addCriterion("dept_id is not null");
            return (Criteria) this;
        }

        public Criteria andDeptIdEqualTo(String value) {
            addCriterion("dept_id =", value, "deptId");
            return (Criteria) this;
        }

        public Criteria andDeptIdNotEqualTo(String value) {
            addCriterion("dept_id <>", value, "deptId");
            return (Criteria) this;
        }

        public Criteria andDeptIdGreaterThan(String value) {
            addCriterion("dept_id >", value, "deptId");
            return (Criteria) this;
        }

        public Criteria andDeptIdGreaterThanOrEqualTo(String value) {
            addCriterion("dept_id >=", value, "deptId");
            return (Criteria) this;
        }

        public Criteria andDeptIdLessThan(String value) {
            addCriterion("dept_id <", value, "deptId");
            return (Criteria) this;
        }

        public Criteria andDeptIdLessThanOrEqualTo(String value) {
            addCriterion("dept_id <=", value, "deptId");
            return (Criteria) this;
        }

        public Criteria andDeptIdLike(String value) {
            addCriterion("dept_id like", value, "deptId");
            return (Criteria) this;
        }

        public Criteria andDeptIdNotLike(String value) {
            addCriterion("dept_id not like", value, "deptId");
            return (Criteria) this;
        }

        public Criteria andDeptIdIn(List<String> values) {
            addCriterion("dept_id in", values, "deptId");
            return (Criteria) this;
        }

        public Criteria andDeptIdNotIn(List<String> values) {
            addCriterion("dept_id not in", values, "deptId");
            return (Criteria) this;
        }

        public Criteria andDeptIdBetween(String value1, String value2) {
            addCriterion("dept_id between", value1, value2, "deptId");
            return (Criteria) this;
        }

        public Criteria andDeptIdNotBetween(String value1, String value2) {
            addCriterion("dept_id not between", value1, value2, "deptId");
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
