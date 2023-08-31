package com.fudian.project.ams.pojo;

import java.util.ArrayList;
import java.util.List;

public class E01z2Example {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public E01z2Example() {
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

        public Criteria andE01z201IsNull() {
            addCriterion("e01z201 is null");
            return (Criteria) this;
        }

        public Criteria andE01z201IsNotNull() {
            addCriterion("e01z201 is not null");
            return (Criteria) this;
        }

        public Criteria andE01z201EqualTo(String value) {
            addCriterion("e01z201 =", value, "e01z201");
            return (Criteria) this;
        }

        public Criteria andE01z201NotEqualTo(String value) {
            addCriterion("e01z201 <>", value, "e01z201");
            return (Criteria) this;
        }

        public Criteria andE01z201GreaterThan(String value) {
            addCriterion("e01z201 >", value, "e01z201");
            return (Criteria) this;
        }

        public Criteria andE01z201GreaterThanOrEqualTo(String value) {
            addCriterion("e01z201 >=", value, "e01z201");
            return (Criteria) this;
        }

        public Criteria andE01z201LessThan(String value) {
            addCriterion("e01z201 <", value, "e01z201");
            return (Criteria) this;
        }

        public Criteria andE01z201LessThanOrEqualTo(String value) {
            addCriterion("e01z201 <=", value, "e01z201");
            return (Criteria) this;
        }

        public Criteria andE01z201Like(String value) {
            addCriterion("e01z201 like", value, "e01z201");
            return (Criteria) this;
        }

        public Criteria andE01z201NotLike(String value) {
            addCriterion("e01z201 not like", value, "e01z201");
            return (Criteria) this;
        }

        public Criteria andE01z201In(List<String> values) {
            addCriterion("e01z201 in", values, "e01z201");
            return (Criteria) this;
        }

        public Criteria andE01z201NotIn(List<String> values) {
            addCriterion("e01z201 not in", values, "e01z201");
            return (Criteria) this;
        }

        public Criteria andE01z201Between(String value1, String value2) {
            addCriterion("e01z201 between", value1, value2, "e01z201");
            return (Criteria) this;
        }

        public Criteria andE01z201NotBetween(String value1, String value2) {
            addCriterion("e01z201 not between", value1, value2, "e01z201");
            return (Criteria) this;
        }

        public Criteria andE01z204aIsNull() {
            addCriterion("e01z204a is null");
            return (Criteria) this;
        }

        public Criteria andE01z204aIsNotNull() {
            addCriterion("e01z204a is not null");
            return (Criteria) this;
        }

        public Criteria andE01z204aEqualTo(String value) {
            addCriterion("e01z204a =", value, "e01z204a");
            return (Criteria) this;
        }

        public Criteria andE01z204aNotEqualTo(String value) {
            addCriterion("e01z204a <>", value, "e01z204a");
            return (Criteria) this;
        }

        public Criteria andE01z204aGreaterThan(String value) {
            addCriterion("e01z204a >", value, "e01z204a");
            return (Criteria) this;
        }

        public Criteria andE01z204aGreaterThanOrEqualTo(String value) {
            addCriterion("e01z204a >=", value, "e01z204a");
            return (Criteria) this;
        }

        public Criteria andE01z204aLessThan(String value) {
            addCriterion("e01z204a <", value, "e01z204a");
            return (Criteria) this;
        }

        public Criteria andE01z204aLessThanOrEqualTo(String value) {
            addCriterion("e01z204a <=", value, "e01z204a");
            return (Criteria) this;
        }

        public Criteria andE01z204aLike(String value) {
            addCriterion("e01z204a like", value, "e01z204a");
            return (Criteria) this;
        }

        public Criteria andE01z204aNotLike(String value) {
            addCriterion("e01z204a not like", value, "e01z204a");
            return (Criteria) this;
        }

        public Criteria andE01z204aIn(List<String> values) {
            addCriterion("e01z204a in", values, "e01z204a");
            return (Criteria) this;
        }

        public Criteria andE01z204aNotIn(List<String> values) {
            addCriterion("e01z204a not in", values, "e01z204a");
            return (Criteria) this;
        }

        public Criteria andE01z204aBetween(String value1, String value2) {
            addCriterion("e01z204a between", value1, value2, "e01z204a");
            return (Criteria) this;
        }

        public Criteria andE01z204aNotBetween(String value1, String value2) {
            addCriterion("e01z204a not between", value1, value2, "e01z204a");
            return (Criteria) this;
        }

        public Criteria andE01z204bIsNull() {
            addCriterion("e01z204b is null");
            return (Criteria) this;
        }

        public Criteria andE01z204bIsNotNull() {
            addCriterion("e01z204b is not null");
            return (Criteria) this;
        }

        public Criteria andE01z204bEqualTo(String value) {
            addCriterion("e01z204b =", value, "e01z204b");
            return (Criteria) this;
        }

        public Criteria andE01z204bNotEqualTo(String value) {
            addCriterion("e01z204b <>", value, "e01z204b");
            return (Criteria) this;
        }

        public Criteria andE01z204bGreaterThan(String value) {
            addCriterion("e01z204b >", value, "e01z204b");
            return (Criteria) this;
        }

        public Criteria andE01z204bGreaterThanOrEqualTo(String value) {
            addCriterion("e01z204b >=", value, "e01z204b");
            return (Criteria) this;
        }

        public Criteria andE01z204bLessThan(String value) {
            addCriterion("e01z204b <", value, "e01z204b");
            return (Criteria) this;
        }

        public Criteria andE01z204bLessThanOrEqualTo(String value) {
            addCriterion("e01z204b <=", value, "e01z204b");
            return (Criteria) this;
        }

        public Criteria andE01z204bLike(String value) {
            addCriterion("e01z204b like", value, "e01z204b");
            return (Criteria) this;
        }

        public Criteria andE01z204bNotLike(String value) {
            addCriterion("e01z204b not like", value, "e01z204b");
            return (Criteria) this;
        }

        public Criteria andE01z204bIn(List<String> values) {
            addCriterion("e01z204b in", values, "e01z204b");
            return (Criteria) this;
        }

        public Criteria andE01z204bNotIn(List<String> values) {
            addCriterion("e01z204b not in", values, "e01z204b");
            return (Criteria) this;
        }

        public Criteria andE01z204bBetween(String value1, String value2) {
            addCriterion("e01z204b between", value1, value2, "e01z204b");
            return (Criteria) this;
        }

        public Criteria andE01z204bNotBetween(String value1, String value2) {
            addCriterion("e01z204b not between", value1, value2, "e01z204b");
            return (Criteria) this;
        }

        public Criteria andE01z207IsNull() {
            addCriterion("e01z207 is null");
            return (Criteria) this;
        }

        public Criteria andE01z207IsNotNull() {
            addCriterion("e01z207 is not null");
            return (Criteria) this;
        }

        public Criteria andE01z207EqualTo(String value) {
            addCriterion("e01z207 =", value, "e01z207");
            return (Criteria) this;
        }

        public Criteria andE01z207NotEqualTo(String value) {
            addCriterion("e01z207 <>", value, "e01z207");
            return (Criteria) this;
        }

        public Criteria andE01z207GreaterThan(String value) {
            addCriterion("e01z207 >", value, "e01z207");
            return (Criteria) this;
        }

        public Criteria andE01z207GreaterThanOrEqualTo(String value) {
            addCriterion("e01z207 >=", value, "e01z207");
            return (Criteria) this;
        }

        public Criteria andE01z207LessThan(String value) {
            addCriterion("e01z207 <", value, "e01z207");
            return (Criteria) this;
        }

        public Criteria andE01z207LessThanOrEqualTo(String value) {
            addCriterion("e01z207 <=", value, "e01z207");
            return (Criteria) this;
        }

        public Criteria andE01z207Like(String value) {
            addCriterion("e01z207 like", value, "e01z207");
            return (Criteria) this;
        }

        public Criteria andE01z207NotLike(String value) {
            addCriterion("e01z207 not like", value, "e01z207");
            return (Criteria) this;
        }

        public Criteria andE01z207In(List<String> values) {
            addCriterion("e01z207 in", values, "e01z207");
            return (Criteria) this;
        }

        public Criteria andE01z207NotIn(List<String> values) {
            addCriterion("e01z207 not in", values, "e01z207");
            return (Criteria) this;
        }

        public Criteria andE01z207Between(String value1, String value2) {
            addCriterion("e01z207 between", value1, value2, "e01z207");
            return (Criteria) this;
        }

        public Criteria andE01z207NotBetween(String value1, String value2) {
            addCriterion("e01z207 not between", value1, value2, "e01z207");
            return (Criteria) this;
        }

        public Criteria andE01z211IsNull() {
            addCriterion("e01z211 is null");
            return (Criteria) this;
        }

        public Criteria andE01z211IsNotNull() {
            addCriterion("e01z211 is not null");
            return (Criteria) this;
        }

        public Criteria andE01z211EqualTo(String value) {
            addCriterion("e01z211 =", value, "e01z211");
            return (Criteria) this;
        }

        public Criteria andE01z211NotEqualTo(String value) {
            addCriterion("e01z211 <>", value, "e01z211");
            return (Criteria) this;
        }

        public Criteria andE01z211GreaterThan(String value) {
            addCriterion("e01z211 >", value, "e01z211");
            return (Criteria) this;
        }

        public Criteria andE01z211GreaterThanOrEqualTo(String value) {
            addCriterion("e01z211 >=", value, "e01z211");
            return (Criteria) this;
        }

        public Criteria andE01z211LessThan(String value) {
            addCriterion("e01z211 <", value, "e01z211");
            return (Criteria) this;
        }

        public Criteria andE01z211LessThanOrEqualTo(String value) {
            addCriterion("e01z211 <=", value, "e01z211");
            return (Criteria) this;
        }

        public Criteria andE01z211Like(String value) {
            addCriterion("e01z211 like", value, "e01z211");
            return (Criteria) this;
        }

        public Criteria andE01z211NotLike(String value) {
            addCriterion("e01z211 not like", value, "e01z211");
            return (Criteria) this;
        }

        public Criteria andE01z211In(List<String> values) {
            addCriterion("e01z211 in", values, "e01z211");
            return (Criteria) this;
        }

        public Criteria andE01z211NotIn(List<String> values) {
            addCriterion("e01z211 not in", values, "e01z211");
            return (Criteria) this;
        }

        public Criteria andE01z211Between(String value1, String value2) {
            addCriterion("e01z211 between", value1, value2, "e01z211");
            return (Criteria) this;
        }

        public Criteria andE01z211NotBetween(String value1, String value2) {
            addCriterion("e01z211 not between", value1, value2, "e01z211");
            return (Criteria) this;
        }

        public Criteria andE01z214IsNull() {
            addCriterion("e01z214 is null");
            return (Criteria) this;
        }

        public Criteria andE01z214IsNotNull() {
            addCriterion("e01z214 is not null");
            return (Criteria) this;
        }

        public Criteria andE01z214EqualTo(String value) {
            addCriterion("e01z214 =", value, "e01z214");
            return (Criteria) this;
        }

        public Criteria andE01z214NotEqualTo(String value) {
            addCriterion("e01z214 <>", value, "e01z214");
            return (Criteria) this;
        }

        public Criteria andE01z214GreaterThan(String value) {
            addCriterion("e01z214 >", value, "e01z214");
            return (Criteria) this;
        }

        public Criteria andE01z214GreaterThanOrEqualTo(String value) {
            addCriterion("e01z214 >=", value, "e01z214");
            return (Criteria) this;
        }

        public Criteria andE01z214LessThan(String value) {
            addCriterion("e01z214 <", value, "e01z214");
            return (Criteria) this;
        }

        public Criteria andE01z214LessThanOrEqualTo(String value) {
            addCriterion("e01z214 <=", value, "e01z214");
            return (Criteria) this;
        }

        public Criteria andE01z214Like(String value) {
            addCriterion("e01z214 like", value, "e01z214");
            return (Criteria) this;
        }

        public Criteria andE01z214NotLike(String value) {
            addCriterion("e01z214 not like", value, "e01z214");
            return (Criteria) this;
        }

        public Criteria andE01z214In(List<String> values) {
            addCriterion("e01z214 in", values, "e01z214");
            return (Criteria) this;
        }

        public Criteria andE01z214NotIn(List<String> values) {
            addCriterion("e01z214 not in", values, "e01z214");
            return (Criteria) this;
        }

        public Criteria andE01z214Between(String value1, String value2) {
            addCriterion("e01z214 between", value1, value2, "e01z214");
            return (Criteria) this;
        }

        public Criteria andE01z214NotBetween(String value1, String value2) {
            addCriterion("e01z214 not between", value1, value2, "e01z214");
            return (Criteria) this;
        }

        public Criteria andE01z217IsNull() {
            addCriterion("e01z217 is null");
            return (Criteria) this;
        }

        public Criteria andE01z217IsNotNull() {
            addCriterion("e01z217 is not null");
            return (Criteria) this;
        }

        public Criteria andE01z217EqualTo(String value) {
            addCriterion("e01z217 =", value, "e01z217");
            return (Criteria) this;
        }

        public Criteria andE01z217NotEqualTo(String value) {
            addCriterion("e01z217 <>", value, "e01z217");
            return (Criteria) this;
        }

        public Criteria andE01z217GreaterThan(String value) {
            addCriterion("e01z217 >", value, "e01z217");
            return (Criteria) this;
        }

        public Criteria andE01z217GreaterThanOrEqualTo(String value) {
            addCriterion("e01z217 >=", value, "e01z217");
            return (Criteria) this;
        }

        public Criteria andE01z217LessThan(String value) {
            addCriterion("e01z217 <", value, "e01z217");
            return (Criteria) this;
        }

        public Criteria andE01z217LessThanOrEqualTo(String value) {
            addCriterion("e01z217 <=", value, "e01z217");
            return (Criteria) this;
        }

        public Criteria andE01z217Like(String value) {
            addCriterion("e01z217 like", value, "e01z217");
            return (Criteria) this;
        }

        public Criteria andE01z217NotLike(String value) {
            addCriterion("e01z217 not like", value, "e01z217");
            return (Criteria) this;
        }

        public Criteria andE01z217In(List<String> values) {
            addCriterion("e01z217 in", values, "e01z217");
            return (Criteria) this;
        }

        public Criteria andE01z217NotIn(List<String> values) {
            addCriterion("e01z217 not in", values, "e01z217");
            return (Criteria) this;
        }

        public Criteria andE01z217Between(String value1, String value2) {
            addCriterion("e01z217 between", value1, value2, "e01z217");
            return (Criteria) this;
        }

        public Criteria andE01z217NotBetween(String value1, String value2) {
            addCriterion("e01z217 not between", value1, value2, "e01z217");
            return (Criteria) this;
        }

        public Criteria andE01z221aIsNull() {
            addCriterion("e01z221a is null");
            return (Criteria) this;
        }

        public Criteria andE01z221aIsNotNull() {
            addCriterion("e01z221a is not null");
            return (Criteria) this;
        }

        public Criteria andE01z221aEqualTo(String value) {
            addCriterion("e01z221a =", value, "e01z221a");
            return (Criteria) this;
        }

        public Criteria andE01z221aNotEqualTo(String value) {
            addCriterion("e01z221a <>", value, "e01z221a");
            return (Criteria) this;
        }

        public Criteria andE01z221aGreaterThan(String value) {
            addCriterion("e01z221a >", value, "e01z221a");
            return (Criteria) this;
        }

        public Criteria andE01z221aGreaterThanOrEqualTo(String value) {
            addCriterion("e01z221a >=", value, "e01z221a");
            return (Criteria) this;
        }

        public Criteria andE01z221aLessThan(String value) {
            addCriterion("e01z221a <", value, "e01z221a");
            return (Criteria) this;
        }

        public Criteria andE01z221aLessThanOrEqualTo(String value) {
            addCriterion("e01z221a <=", value, "e01z221a");
            return (Criteria) this;
        }

        public Criteria andE01z221aLike(String value) {
            addCriterion("e01z221a like", value, "e01z221a");
            return (Criteria) this;
        }

        public Criteria andE01z221aNotLike(String value) {
            addCriterion("e01z221a not like", value, "e01z221a");
            return (Criteria) this;
        }

        public Criteria andE01z221aIn(List<String> values) {
            addCriterion("e01z221a in", values, "e01z221a");
            return (Criteria) this;
        }

        public Criteria andE01z221aNotIn(List<String> values) {
            addCriterion("e01z221a not in", values, "e01z221a");
            return (Criteria) this;
        }

        public Criteria andE01z221aBetween(String value1, String value2) {
            addCriterion("e01z221a between", value1, value2, "e01z221a");
            return (Criteria) this;
        }

        public Criteria andE01z221aNotBetween(String value1, String value2) {
            addCriterion("e01z221a not between", value1, value2, "e01z221a");
            return (Criteria) this;
        }

        public Criteria andE01z221bIsNull() {
            addCriterion("e01z221b is null");
            return (Criteria) this;
        }

        public Criteria andE01z221bIsNotNull() {
            addCriterion("e01z221b is not null");
            return (Criteria) this;
        }

        public Criteria andE01z221bEqualTo(String value) {
            addCriterion("e01z221b =", value, "e01z221b");
            return (Criteria) this;
        }

        public Criteria andE01z221bNotEqualTo(String value) {
            addCriterion("e01z221b <>", value, "e01z221b");
            return (Criteria) this;
        }

        public Criteria andE01z221bGreaterThan(String value) {
            addCriterion("e01z221b >", value, "e01z221b");
            return (Criteria) this;
        }

        public Criteria andE01z221bGreaterThanOrEqualTo(String value) {
            addCriterion("e01z221b >=", value, "e01z221b");
            return (Criteria) this;
        }

        public Criteria andE01z221bLessThan(String value) {
            addCriterion("e01z221b <", value, "e01z221b");
            return (Criteria) this;
        }

        public Criteria andE01z221bLessThanOrEqualTo(String value) {
            addCriterion("e01z221b <=", value, "e01z221b");
            return (Criteria) this;
        }

        public Criteria andE01z221bLike(String value) {
            addCriterion("e01z221b like", value, "e01z221b");
            return (Criteria) this;
        }

        public Criteria andE01z221bNotLike(String value) {
            addCriterion("e01z221b not like", value, "e01z221b");
            return (Criteria) this;
        }

        public Criteria andE01z221bIn(List<String> values) {
            addCriterion("e01z221b in", values, "e01z221b");
            return (Criteria) this;
        }

        public Criteria andE01z221bNotIn(List<String> values) {
            addCriterion("e01z221b not in", values, "e01z221b");
            return (Criteria) this;
        }

        public Criteria andE01z221bBetween(String value1, String value2) {
            addCriterion("e01z221b between", value1, value2, "e01z221b");
            return (Criteria) this;
        }

        public Criteria andE01z221bNotBetween(String value1, String value2) {
            addCriterion("e01z221b not between", value1, value2, "e01z221b");
            return (Criteria) this;
        }

        public Criteria andE01z224IsNull() {
            addCriterion("e01z224 is null");
            return (Criteria) this;
        }

        public Criteria andE01z224IsNotNull() {
            addCriterion("e01z224 is not null");
            return (Criteria) this;
        }

        public Criteria andE01z224EqualTo(Integer value) {
            addCriterion("e01z224 =", value, "e01z224");
            return (Criteria) this;
        }

        public Criteria andE01z224NotEqualTo(Integer value) {
            addCriterion("e01z224 <>", value, "e01z224");
            return (Criteria) this;
        }

        public Criteria andE01z224GreaterThan(Integer value) {
            addCriterion("e01z224 >", value, "e01z224");
            return (Criteria) this;
        }

        public Criteria andE01z224GreaterThanOrEqualTo(Integer value) {
            addCriterion("e01z224 >=", value, "e01z224");
            return (Criteria) this;
        }

        public Criteria andE01z224LessThan(Integer value) {
            addCriterion("e01z224 <", value, "e01z224");
            return (Criteria) this;
        }

        public Criteria andE01z224LessThanOrEqualTo(Integer value) {
            addCriterion("e01z224 <=", value, "e01z224");
            return (Criteria) this;
        }

        public Criteria andE01z224In(List<Integer> values) {
            addCriterion("e01z224 in", values, "e01z224");
            return (Criteria) this;
        }

        public Criteria andE01z224NotIn(List<Integer> values) {
            addCriterion("e01z224 not in", values, "e01z224");
            return (Criteria) this;
        }

        public Criteria andE01z224Between(Integer value1, Integer value2) {
            addCriterion("e01z224 between", value1, value2, "e01z224");
            return (Criteria) this;
        }

        public Criteria andE01z224NotBetween(Integer value1, Integer value2) {
            addCriterion("e01z224 not between", value1, value2, "e01z224");
            return (Criteria) this;
        }

        public Criteria andE01z227IsNull() {
            addCriterion("e01z227 is null");
            return (Criteria) this;
        }

        public Criteria andE01z227IsNotNull() {
            addCriterion("e01z227 is not null");
            return (Criteria) this;
        }

        public Criteria andE01z227EqualTo(String value) {
            addCriterion("e01z227 =", value, "e01z227");
            return (Criteria) this;
        }

        public Criteria andE01z227NotEqualTo(String value) {
            addCriterion("e01z227 <>", value, "e01z227");
            return (Criteria) this;
        }

        public Criteria andE01z227GreaterThan(String value) {
            addCriterion("e01z227 >", value, "e01z227");
            return (Criteria) this;
        }

        public Criteria andE01z227GreaterThanOrEqualTo(String value) {
            addCriterion("e01z227 >=", value, "e01z227");
            return (Criteria) this;
        }

        public Criteria andE01z227LessThan(String value) {
            addCriterion("e01z227 <", value, "e01z227");
            return (Criteria) this;
        }

        public Criteria andE01z227LessThanOrEqualTo(String value) {
            addCriterion("e01z227 <=", value, "e01z227");
            return (Criteria) this;
        }

        public Criteria andE01z227Like(String value) {
            addCriterion("e01z227 like", value, "e01z227");
            return (Criteria) this;
        }

        public Criteria andE01z227NotLike(String value) {
            addCriterion("e01z227 not like", value, "e01z227");
            return (Criteria) this;
        }

        public Criteria andE01z227In(List<String> values) {
            addCriterion("e01z227 in", values, "e01z227");
            return (Criteria) this;
        }

        public Criteria andE01z227NotIn(List<String> values) {
            addCriterion("e01z227 not in", values, "e01z227");
            return (Criteria) this;
        }

        public Criteria andE01z227Between(String value1, String value2) {
            addCriterion("e01z227 between", value1, value2, "e01z227");
            return (Criteria) this;
        }

        public Criteria andE01z227NotBetween(String value1, String value2) {
            addCriterion("e01z227 not between", value1, value2, "e01z227");
            return (Criteria) this;
        }

        public Criteria andE01z231IsNull() {
            addCriterion("e01z231 is null");
            return (Criteria) this;
        }

        public Criteria andE01z231IsNotNull() {
            addCriterion("e01z231 is not null");
            return (Criteria) this;
        }

        public Criteria andE01z231EqualTo(String value) {
            addCriterion("e01z231 =", value, "e01z231");
            return (Criteria) this;
        }

        public Criteria andE01z231NotEqualTo(String value) {
            addCriterion("e01z231 <>", value, "e01z231");
            return (Criteria) this;
        }

        public Criteria andE01z231GreaterThan(String value) {
            addCriterion("e01z231 >", value, "e01z231");
            return (Criteria) this;
        }

        public Criteria andE01z231GreaterThanOrEqualTo(String value) {
            addCriterion("e01z231 >=", value, "e01z231");
            return (Criteria) this;
        }

        public Criteria andE01z231LessThan(String value) {
            addCriterion("e01z231 <", value, "e01z231");
            return (Criteria) this;
        }

        public Criteria andE01z231LessThanOrEqualTo(String value) {
            addCriterion("e01z231 <=", value, "e01z231");
            return (Criteria) this;
        }

        public Criteria andE01z231Like(String value) {
            addCriterion("e01z231 like", value, "e01z231");
            return (Criteria) this;
        }

        public Criteria andE01z231NotLike(String value) {
            addCriterion("e01z231 not like", value, "e01z231");
            return (Criteria) this;
        }

        public Criteria andE01z231In(List<String> values) {
            addCriterion("e01z231 in", values, "e01z231");
            return (Criteria) this;
        }

        public Criteria andE01z231NotIn(List<String> values) {
            addCriterion("e01z231 not in", values, "e01z231");
            return (Criteria) this;
        }

        public Criteria andE01z231Between(String value1, String value2) {
            addCriterion("e01z231 between", value1, value2, "e01z231");
            return (Criteria) this;
        }

        public Criteria andE01z231NotBetween(String value1, String value2) {
            addCriterion("e01z231 not between", value1, value2, "e01z231");
            return (Criteria) this;
        }

        public Criteria andE01z234IsNull() {
            addCriterion("e01z234 is null");
            return (Criteria) this;
        }

        public Criteria andE01z234IsNotNull() {
            addCriterion("e01z234 is not null");
            return (Criteria) this;
        }

        public Criteria andE01z234EqualTo(Integer value) {
            addCriterion("e01z234 =", value, "e01z234");
            return (Criteria) this;
        }

        public Criteria andE01z234NotEqualTo(Integer value) {
            addCriterion("e01z234 <>", value, "e01z234");
            return (Criteria) this;
        }

        public Criteria andE01z234GreaterThan(Integer value) {
            addCriterion("e01z234 >", value, "e01z234");
            return (Criteria) this;
        }

        public Criteria andE01z234GreaterThanOrEqualTo(Integer value) {
            addCriterion("e01z234 >=", value, "e01z234");
            return (Criteria) this;
        }

        public Criteria andE01z234LessThan(Integer value) {
            addCriterion("e01z234 <", value, "e01z234");
            return (Criteria) this;
        }

        public Criteria andE01z234LessThanOrEqualTo(Integer value) {
            addCriterion("e01z234 <=", value, "e01z234");
            return (Criteria) this;
        }

        public Criteria andE01z234In(List<Integer> values) {
            addCriterion("e01z234 in", values, "e01z234");
            return (Criteria) this;
        }

        public Criteria andE01z234NotIn(List<Integer> values) {
            addCriterion("e01z234 not in", values, "e01z234");
            return (Criteria) this;
        }

        public Criteria andE01z234Between(Integer value1, Integer value2) {
            addCriterion("e01z234 between", value1, value2, "e01z234");
            return (Criteria) this;
        }

        public Criteria andE01z234NotBetween(Integer value1, Integer value2) {
            addCriterion("e01z234 not between", value1, value2, "e01z234");
            return (Criteria) this;
        }

        public Criteria andE01z237IsNull() {
            addCriterion("e01z237 is null");
            return (Criteria) this;
        }

        public Criteria andE01z237IsNotNull() {
            addCriterion("e01z237 is not null");
            return (Criteria) this;
        }

        public Criteria andE01z237EqualTo(String value) {
            addCriterion("e01z237 =", value, "e01z237");
            return (Criteria) this;
        }

        public Criteria andE01z237NotEqualTo(String value) {
            addCriterion("e01z237 <>", value, "e01z237");
            return (Criteria) this;
        }

        public Criteria andE01z237GreaterThan(String value) {
            addCriterion("e01z237 >", value, "e01z237");
            return (Criteria) this;
        }

        public Criteria andE01z237GreaterThanOrEqualTo(String value) {
            addCriterion("e01z237 >=", value, "e01z237");
            return (Criteria) this;
        }

        public Criteria andE01z237LessThan(String value) {
            addCriterion("e01z237 <", value, "e01z237");
            return (Criteria) this;
        }

        public Criteria andE01z237LessThanOrEqualTo(String value) {
            addCriterion("e01z237 <=", value, "e01z237");
            return (Criteria) this;
        }

        public Criteria andE01z237Like(String value) {
            addCriterion("e01z237 like", value, "e01z237");
            return (Criteria) this;
        }

        public Criteria andE01z237NotLike(String value) {
            addCriterion("e01z237 not like", value, "e01z237");
            return (Criteria) this;
        }

        public Criteria andE01z237In(List<String> values) {
            addCriterion("e01z237 in", values, "e01z237");
            return (Criteria) this;
        }

        public Criteria andE01z237NotIn(List<String> values) {
            addCriterion("e01z237 not in", values, "e01z237");
            return (Criteria) this;
        }

        public Criteria andE01z237Between(String value1, String value2) {
            addCriterion("e01z237 between", value1, value2, "e01z237");
            return (Criteria) this;
        }

        public Criteria andE01z237NotBetween(String value1, String value2) {
            addCriterion("e01z237 not between", value1, value2, "e01z237");
            return (Criteria) this;
        }

        public Criteria andE01z241IsNull() {
            addCriterion("e01z241 is null");
            return (Criteria) this;
        }

        public Criteria andE01z241IsNotNull() {
            addCriterion("e01z241 is not null");
            return (Criteria) this;
        }

        public Criteria andE01z241EqualTo(String value) {
            addCriterion("e01z241 =", value, "e01z241");
            return (Criteria) this;
        }

        public Criteria andE01z241NotEqualTo(String value) {
            addCriterion("e01z241 <>", value, "e01z241");
            return (Criteria) this;
        }

        public Criteria andE01z241GreaterThan(String value) {
            addCriterion("e01z241 >", value, "e01z241");
            return (Criteria) this;
        }

        public Criteria andE01z241GreaterThanOrEqualTo(String value) {
            addCriterion("e01z241 >=", value, "e01z241");
            return (Criteria) this;
        }

        public Criteria andE01z241LessThan(String value) {
            addCriterion("e01z241 <", value, "e01z241");
            return (Criteria) this;
        }

        public Criteria andE01z241LessThanOrEqualTo(String value) {
            addCriterion("e01z241 <=", value, "e01z241");
            return (Criteria) this;
        }

        public Criteria andE01z241Like(String value) {
            addCriterion("e01z241 like", value, "e01z241");
            return (Criteria) this;
        }

        public Criteria andE01z241NotLike(String value) {
            addCriterion("e01z241 not like", value, "e01z241");
            return (Criteria) this;
        }

        public Criteria andE01z241In(List<String> values) {
            addCriterion("e01z241 in", values, "e01z241");
            return (Criteria) this;
        }

        public Criteria andE01z241NotIn(List<String> values) {
            addCriterion("e01z241 not in", values, "e01z241");
            return (Criteria) this;
        }

        public Criteria andE01z241Between(String value1, String value2) {
            addCriterion("e01z241 between", value1, value2, "e01z241");
            return (Criteria) this;
        }

        public Criteria andE01z241NotBetween(String value1, String value2) {
            addCriterion("e01z241 not between", value1, value2, "e01z241");
            return (Criteria) this;
        }

        public Criteria andE01z244IsNull() {
            addCriterion("e01z244 is null");
            return (Criteria) this;
        }

        public Criteria andE01z244IsNotNull() {
            addCriterion("e01z244 is not null");
            return (Criteria) this;
        }

        public Criteria andE01z244EqualTo(String value) {
            addCriterion("e01z244 =", value, "e01z244");
            return (Criteria) this;
        }

        public Criteria andE01z244NotEqualTo(String value) {
            addCriterion("e01z244 <>", value, "e01z244");
            return (Criteria) this;
        }

        public Criteria andE01z244GreaterThan(String value) {
            addCriterion("e01z244 >", value, "e01z244");
            return (Criteria) this;
        }

        public Criteria andE01z244GreaterThanOrEqualTo(String value) {
            addCriterion("e01z244 >=", value, "e01z244");
            return (Criteria) this;
        }

        public Criteria andE01z244LessThan(String value) {
            addCriterion("e01z244 <", value, "e01z244");
            return (Criteria) this;
        }

        public Criteria andE01z244LessThanOrEqualTo(String value) {
            addCriterion("e01z244 <=", value, "e01z244");
            return (Criteria) this;
        }

        public Criteria andE01z244Like(String value) {
            addCriterion("e01z244 like", value, "e01z244");
            return (Criteria) this;
        }

        public Criteria andE01z244NotLike(String value) {
            addCriterion("e01z244 not like", value, "e01z244");
            return (Criteria) this;
        }

        public Criteria andE01z244In(List<String> values) {
            addCriterion("e01z244 in", values, "e01z244");
            return (Criteria) this;
        }

        public Criteria andE01z244NotIn(List<String> values) {
            addCriterion("e01z244 not in", values, "e01z244");
            return (Criteria) this;
        }

        public Criteria andE01z244Between(String value1, String value2) {
            addCriterion("e01z244 between", value1, value2, "e01z244");
            return (Criteria) this;
        }

        public Criteria andE01z244NotBetween(String value1, String value2) {
            addCriterion("e01z244 not between", value1, value2, "e01z244");
            return (Criteria) this;
        }

        public Criteria andArchiveIsNull() {
            addCriterion("archive is null");
            return (Criteria) this;
        }

        public Criteria andArchiveIsNotNull() {
            addCriterion("archive is not null");
            return (Criteria) this;
        }

        public Criteria andArchiveEqualTo(String value) {
            addCriterion("archive =", value, "archive");
            return (Criteria) this;
        }

        public Criteria andArchiveNotEqualTo(String value) {
            addCriterion("archive <>", value, "archive");
            return (Criteria) this;
        }

        public Criteria andArchiveGreaterThan(String value) {
            addCriterion("archive >", value, "archive");
            return (Criteria) this;
        }

        public Criteria andArchiveGreaterThanOrEqualTo(String value) {
            addCriterion("archive >=", value, "archive");
            return (Criteria) this;
        }

        public Criteria andArchiveLessThan(String value) {
            addCriterion("archive <", value, "archive");
            return (Criteria) this;
        }

        public Criteria andArchiveLessThanOrEqualTo(String value) {
            addCriterion("archive <=", value, "archive");
            return (Criteria) this;
        }

        public Criteria andArchiveLike(String value) {
            addCriterion("archive like", value, "archive");
            return (Criteria) this;
        }

        public Criteria andArchiveNotLike(String value) {
            addCriterion("archive not like", value, "archive");
            return (Criteria) this;
        }

        public Criteria andArchiveIn(List<String> values) {
            addCriterion("archive in", values, "archive");
            return (Criteria) this;
        }

        public Criteria andArchiveNotIn(List<String> values) {
            addCriterion("archive not in", values, "archive");
            return (Criteria) this;
        }

        public Criteria andArchiveBetween(String value1, String value2) {
            addCriterion("archive between", value1, value2, "archive");
            return (Criteria) this;
        }

        public Criteria andArchiveNotBetween(String value1, String value2) {
            addCriterion("archive not between", value1, value2, "archive");
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
