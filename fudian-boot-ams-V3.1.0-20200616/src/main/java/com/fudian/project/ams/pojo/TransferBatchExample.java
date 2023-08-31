package com.fudian.project.ams.pojo;

import java.util.ArrayList;
import java.util.List;

/**
* @Author: WangYuZhuo
* @Date: 2020/1/2 15:48
* @Desc:
*/
public class TransferBatchExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TransferBatchExample() {
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

        public Criteria andE01z704aIsNull() {
            addCriterion("e01z704a is null");
            return (Criteria) this;
        }

        public Criteria andE01z704aIsNotNull() {
            addCriterion("e01z704a is not null");
            return (Criteria) this;
        }

        public Criteria andE01z704aEqualTo(String value) {
            addCriterion("e01z704a =", value, "e01z704a");
            return (Criteria) this;
        }

        public Criteria andE01z704aNotEqualTo(String value) {
            addCriterion("e01z704a <>", value, "e01z704a");
            return (Criteria) this;
        }

        public Criteria andE01z704aGreaterThan(String value) {
            addCriterion("e01z704a >", value, "e01z704a");
            return (Criteria) this;
        }

        public Criteria andE01z704aGreaterThanOrEqualTo(String value) {
            addCriterion("e01z704a >=", value, "e01z704a");
            return (Criteria) this;
        }

        public Criteria andE01z704aLessThan(String value) {
            addCriterion("e01z704a <", value, "e01z704a");
            return (Criteria) this;
        }

        public Criteria andE01z704aLessThanOrEqualTo(String value) {
            addCriterion("e01z704a <=", value, "e01z704a");
            return (Criteria) this;
        }

        public Criteria andE01z704aLike(String value) {
            addCriterion("e01z704a like", value, "e01z704a");
            return (Criteria) this;
        }

        public Criteria andE01z704aNotLike(String value) {
            addCriterion("e01z704a not like", value, "e01z704a");
            return (Criteria) this;
        }

        public Criteria andE01z704aIn(List<String> values) {
            addCriterion("e01z704a in", values, "e01z704a");
            return (Criteria) this;
        }

        public Criteria andE01z704aNotIn(List<String> values) {
            addCriterion("e01z704a not in", values, "e01z704a");
            return (Criteria) this;
        }

        public Criteria andE01z704aBetween(String value1, String value2) {
            addCriterion("e01z704a between", value1, value2, "e01z704a");
            return (Criteria) this;
        }

        public Criteria andE01z704aNotBetween(String value1, String value2) {
            addCriterion("e01z704a not between", value1, value2, "e01z704a");
            return (Criteria) this;
        }

        public Criteria andE01z704bIsNull() {
            addCriterion("e01z704b is null");
            return (Criteria) this;
        }

        public Criteria andE01z704bIsNotNull() {
            addCriterion("e01z704b is not null");
            return (Criteria) this;
        }

        public Criteria andE01z704bEqualTo(String value) {
            addCriterion("e01z704b =", value, "e01z704b");
            return (Criteria) this;
        }

        public Criteria andE01z704bNotEqualTo(String value) {
            addCriterion("e01z704b <>", value, "e01z704b");
            return (Criteria) this;
        }

        public Criteria andE01z704bGreaterThan(String value) {
            addCriterion("e01z704b >", value, "e01z704b");
            return (Criteria) this;
        }

        public Criteria andE01z704bGreaterThanOrEqualTo(String value) {
            addCriterion("e01z704b >=", value, "e01z704b");
            return (Criteria) this;
        }

        public Criteria andE01z704bLessThan(String value) {
            addCriterion("e01z704b <", value, "e01z704b");
            return (Criteria) this;
        }

        public Criteria andE01z704bLessThanOrEqualTo(String value) {
            addCriterion("e01z704b <=", value, "e01z704b");
            return (Criteria) this;
        }

        public Criteria andE01z704bLike(String value) {
            addCriterion("e01z704b like", value, "e01z704b");
            return (Criteria) this;
        }

        public Criteria andE01z704bNotLike(String value) {
            addCriterion("e01z704b not like", value, "e01z704b");
            return (Criteria) this;
        }

        public Criteria andE01z704bIn(List<String> values) {
            addCriterion("e01z704b in", values, "e01z704b");
            return (Criteria) this;
        }

        public Criteria andE01z704bNotIn(List<String> values) {
            addCriterion("e01z704b not in", values, "e01z704b");
            return (Criteria) this;
        }

        public Criteria andE01z704bBetween(String value1, String value2) {
            addCriterion("e01z704b between", value1, value2, "e01z704b");
            return (Criteria) this;
        }

        public Criteria andE01z704bNotBetween(String value1, String value2) {
            addCriterion("e01z704b not between", value1, value2, "e01z704b");
            return (Criteria) this;
        }

        public Criteria andE01z701IsNull() {
            addCriterion("e01z701 is null");
            return (Criteria) this;
        }

        public Criteria andE01z701IsNotNull() {
            addCriterion("e01z701 is not null");
            return (Criteria) this;
        }

        public Criteria andE01z701EqualTo(String value) {
            addCriterion("e01z701 =", value, "e01z701");
            return (Criteria) this;
        }

        public Criteria andE01z701NotEqualTo(String value) {
            addCriterion("e01z701 <>", value, "e01z701");
            return (Criteria) this;
        }

        public Criteria andE01z701GreaterThan(String value) {
            addCriterion("e01z701 >", value, "e01z701");
            return (Criteria) this;
        }

        public Criteria andE01z701GreaterThanOrEqualTo(String value) {
            addCriterion("e01z701 >=", value, "e01z701");
            return (Criteria) this;
        }

        public Criteria andE01z701LessThan(String value) {
            addCriterion("e01z701 <", value, "e01z701");
            return (Criteria) this;
        }

        public Criteria andE01z701LessThanOrEqualTo(String value) {
            addCriterion("e01z701 <=", value, "e01z701");
            return (Criteria) this;
        }

        public Criteria andE01z701Like(String value) {
            addCriterion("e01z701 like", value, "e01z701");
            return (Criteria) this;
        }

        public Criteria andE01z701NotLike(String value) {
            addCriterion("e01z701 not like", value, "e01z701");
            return (Criteria) this;
        }

        public Criteria andE01z701In(List<String> values) {
            addCriterion("e01z701 in", values, "e01z701");
            return (Criteria) this;
        }

        public Criteria andE01z701NotIn(List<String> values) {
            addCriterion("e01z701 not in", values, "e01z701");
            return (Criteria) this;
        }

        public Criteria andE01z701Between(String value1, String value2) {
            addCriterion("e01z701 between", value1, value2, "e01z701");
            return (Criteria) this;
        }

        public Criteria andE01z701NotBetween(String value1, String value2) {
            addCriterion("e01z701 not between", value1, value2, "e01z701");
            return (Criteria) this;
        }

        public Criteria andPersonNumIsNull() {
            addCriterion("person_num is null");
            return (Criteria) this;
        }

        public Criteria andPersonNumIsNotNull() {
            addCriterion("person_num is not null");
            return (Criteria) this;
        }

        public Criteria andPersonNumEqualTo(Integer value) {
            addCriterion("person_num =", value, "personNum");
            return (Criteria) this;
        }

        public Criteria andPersonNumNotEqualTo(Integer value) {
            addCriterion("person_num <>", value, "personNum");
            return (Criteria) this;
        }

        public Criteria andPersonNumGreaterThan(Integer value) {
            addCriterion("person_num >", value, "personNum");
            return (Criteria) this;
        }

        public Criteria andPersonNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("person_num >=", value, "personNum");
            return (Criteria) this;
        }

        public Criteria andPersonNumLessThan(Integer value) {
            addCriterion("person_num <", value, "personNum");
            return (Criteria) this;
        }

        public Criteria andPersonNumLessThanOrEqualTo(Integer value) {
            addCriterion("person_num <=", value, "personNum");
            return (Criteria) this;
        }

        public Criteria andPersonNumIn(List<Integer> values) {
            addCriterion("person_num in", values, "personNum");
            return (Criteria) this;
        }

        public Criteria andPersonNumNotIn(List<Integer> values) {
            addCriterion("person_num not in", values, "personNum");
            return (Criteria) this;
        }

        public Criteria andPersonNumBetween(Integer value1, Integer value2) {
            addCriterion("person_num between", value1, value2, "personNum");
            return (Criteria) this;
        }

        public Criteria andPersonNumNotBetween(Integer value1, Integer value2) {
            addCriterion("person_num not between", value1, value2, "personNum");
            return (Criteria) this;
        }

        public Criteria andE01z717IsNull() {
            addCriterion("e01z717 is null");
            return (Criteria) this;
        }

        public Criteria andE01z717IsNotNull() {
            addCriterion("e01z717 is not null");
            return (Criteria) this;
        }

        public Criteria andE01z717EqualTo(String value) {
            addCriterion("e01z717 =", value, "e01z717");
            return (Criteria) this;
        }

        public Criteria andE01z717NotEqualTo(String value) {
            addCriterion("e01z717 <>", value, "e01z717");
            return (Criteria) this;
        }

        public Criteria andE01z717GreaterThan(String value) {
            addCriterion("e01z717 >", value, "e01z717");
            return (Criteria) this;
        }

        public Criteria andE01z717GreaterThanOrEqualTo(String value) {
            addCriterion("e01z717 >=", value, "e01z717");
            return (Criteria) this;
        }

        public Criteria andE01z717LessThan(String value) {
            addCriterion("e01z717 <", value, "e01z717");
            return (Criteria) this;
        }

        public Criteria andE01z717LessThanOrEqualTo(String value) {
            addCriterion("e01z717 <=", value, "e01z717");
            return (Criteria) this;
        }

        public Criteria andE01z717Like(String value) {
            addCriterion("e01z717 like", value, "e01z717");
            return (Criteria) this;
        }

        public Criteria andE01z717NotLike(String value) {
            addCriterion("e01z717 not like", value, "e01z717");
            return (Criteria) this;
        }

        public Criteria andE01z717In(List<String> values) {
            addCriterion("e01z717 in", values, "e01z717");
            return (Criteria) this;
        }

        public Criteria andE01z717NotIn(List<String> values) {
            addCriterion("e01z717 not in", values, "e01z717");
            return (Criteria) this;
        }

        public Criteria andE01z717Between(String value1, String value2) {
            addCriterion("e01z717 between", value1, value2, "e01z717");
            return (Criteria) this;
        }

        public Criteria andE01z717NotBetween(String value1, String value2) {
            addCriterion("e01z717 not between", value1, value2, "e01z717");
            return (Criteria) this;
        }

        public Criteria andE01z724IsNull() {
            addCriterion("e01z724 is null");
            return (Criteria) this;
        }

        public Criteria andE01z724IsNotNull() {
            addCriterion("e01z724 is not null");
            return (Criteria) this;
        }

        public Criteria andE01z724EqualTo(String value) {
            addCriterion("e01z724 =", value, "e01z724");
            return (Criteria) this;
        }

        public Criteria andE01z724NotEqualTo(String value) {
            addCriterion("e01z724 <>", value, "e01z724");
            return (Criteria) this;
        }

        public Criteria andE01z724GreaterThan(String value) {
            addCriterion("e01z724 >", value, "e01z724");
            return (Criteria) this;
        }

        public Criteria andE01z724GreaterThanOrEqualTo(String value) {
            addCriterion("e01z724 >=", value, "e01z724");
            return (Criteria) this;
        }

        public Criteria andE01z724LessThan(String value) {
            addCriterion("e01z724 <", value, "e01z724");
            return (Criteria) this;
        }

        public Criteria andE01z724LessThanOrEqualTo(String value) {
            addCriterion("e01z724 <=", value, "e01z724");
            return (Criteria) this;
        }

        public Criteria andE01z724Like(String value) {
            addCriterion("e01z724 like", value, "e01z724");
            return (Criteria) this;
        }

        public Criteria andE01z724NotLike(String value) {
            addCriterion("e01z724 not like", value, "e01z724");
            return (Criteria) this;
        }

        public Criteria andE01z724In(List<String> values) {
            addCriterion("e01z724 in", values, "e01z724");
            return (Criteria) this;
        }

        public Criteria andE01z724NotIn(List<String> values) {
            addCriterion("e01z724 not in", values, "e01z724");
            return (Criteria) this;
        }

        public Criteria andE01z724Between(String value1, String value2) {
            addCriterion("e01z724 between", value1, value2, "e01z724");
            return (Criteria) this;
        }

        public Criteria andE01z724NotBetween(String value1, String value2) {
            addCriterion("e01z724 not between", value1, value2, "e01z724");
            return (Criteria) this;
        }

        public Criteria andE01z727IsNull() {
            addCriterion("e01z727 is null");
            return (Criteria) this;
        }

        public Criteria andE01z727IsNotNull() {
            addCriterion("e01z727 is not null");
            return (Criteria) this;
        }

        public Criteria andE01z727EqualTo(String value) {
            addCriterion("e01z727 =", value, "e01z727");
            return (Criteria) this;
        }

        public Criteria andE01z727NotEqualTo(String value) {
            addCriterion("e01z727 <>", value, "e01z727");
            return (Criteria) this;
        }

        public Criteria andE01z727GreaterThan(String value) {
            addCriterion("e01z727 >", value, "e01z727");
            return (Criteria) this;
        }

        public Criteria andE01z727GreaterThanOrEqualTo(String value) {
            addCriterion("e01z727 >=", value, "e01z727");
            return (Criteria) this;
        }

        public Criteria andE01z727LessThan(String value) {
            addCriterion("e01z727 <", value, "e01z727");
            return (Criteria) this;
        }

        public Criteria andE01z727LessThanOrEqualTo(String value) {
            addCriterion("e01z727 <=", value, "e01z727");
            return (Criteria) this;
        }

        public Criteria andE01z727Like(String value) {
            addCriterion("e01z727 like", value, "e01z727");
            return (Criteria) this;
        }

        public Criteria andE01z727NotLike(String value) {
            addCriterion("e01z727 not like", value, "e01z727");
            return (Criteria) this;
        }

        public Criteria andE01z727In(List<String> values) {
            addCriterion("e01z727 in", values, "e01z727");
            return (Criteria) this;
        }

        public Criteria andE01z727NotIn(List<String> values) {
            addCriterion("e01z727 not in", values, "e01z727");
            return (Criteria) this;
        }

        public Criteria andE01z727Between(String value1, String value2) {
            addCriterion("e01z727 between", value1, value2, "e01z727");
            return (Criteria) this;
        }

        public Criteria andE01z727NotBetween(String value1, String value2) {
            addCriterion("e01z727 not between", value1, value2, "e01z727");
            return (Criteria) this;
        }

        public Criteria andRamarkIsNull() {
            addCriterion("ramark is null");
            return (Criteria) this;
        }

        public Criteria andRamarkIsNotNull() {
            addCriterion("ramark is not null");
            return (Criteria) this;
        }

        public Criteria andRamarkEqualTo(String value) {
            addCriterion("ramark =", value, "ramark");
            return (Criteria) this;
        }

        public Criteria andRamarkNotEqualTo(String value) {
            addCriterion("ramark <>", value, "ramark");
            return (Criteria) this;
        }

        public Criteria andRamarkGreaterThan(String value) {
            addCriterion("ramark >", value, "ramark");
            return (Criteria) this;
        }

        public Criteria andRamarkGreaterThanOrEqualTo(String value) {
            addCriterion("ramark >=", value, "ramark");
            return (Criteria) this;
        }

        public Criteria andRamarkLessThan(String value) {
            addCriterion("ramark <", value, "ramark");
            return (Criteria) this;
        }

        public Criteria andRamarkLessThanOrEqualTo(String value) {
            addCriterion("ramark <=", value, "ramark");
            return (Criteria) this;
        }

        public Criteria andRamarkLike(String value) {
            addCriterion("ramark like", value, "ramark");
            return (Criteria) this;
        }

        public Criteria andRamarkNotLike(String value) {
            addCriterion("ramark not like", value, "ramark");
            return (Criteria) this;
        }

        public Criteria andRamarkIn(List<String> values) {
            addCriterion("ramark in", values, "ramark");
            return (Criteria) this;
        }

        public Criteria andRamarkNotIn(List<String> values) {
            addCriterion("ramark not in", values, "ramark");
            return (Criteria) this;
        }

        public Criteria andRamarkBetween(String value1, String value2) {
            addCriterion("ramark between", value1, value2, "ramark");
            return (Criteria) this;
        }

        public Criteria andRamarkNotBetween(String value1, String value2) {
            addCriterion("ramark not between", value1, value2, "ramark");
            return (Criteria) this;
        }

        public Criteria andWfiIdIsNull() {
            addCriterion("wfi_id is null");
            return (Criteria) this;
        }

        public Criteria andWfiIdIsNotNull() {
            addCriterion("wfi_id is not null");
            return (Criteria) this;
        }

        public Criteria andWfiIdEqualTo(String value) {
            addCriterion("wfi_id =", value, "wfiId");
            return (Criteria) this;
        }

        public Criteria andWfiIdNotEqualTo(String value) {
            addCriterion("wfi_id <>", value, "wfiId");
            return (Criteria) this;
        }

        public Criteria andWfiIdGreaterThan(String value) {
            addCriterion("wfi_id >", value, "wfiId");
            return (Criteria) this;
        }

        public Criteria andWfiIdGreaterThanOrEqualTo(String value) {
            addCriterion("wfi_id >=", value, "wfiId");
            return (Criteria) this;
        }

        public Criteria andWfiIdLessThan(String value) {
            addCriterion("wfi_id <", value, "wfiId");
            return (Criteria) this;
        }

        public Criteria andWfiIdLessThanOrEqualTo(String value) {
            addCriterion("wfi_id <=", value, "wfiId");
            return (Criteria) this;
        }

        public Criteria andWfiIdLike(String value) {
            addCriterion("wfi_id like", value, "wfiId");
            return (Criteria) this;
        }

        public Criteria andWfiIdNotLike(String value) {
            addCriterion("wfi_id not like", value, "wfiId");
            return (Criteria) this;
        }

        public Criteria andWfiIdIn(List<String> values) {
            addCriterion("wfi_id in", values, "wfiId");
            return (Criteria) this;
        }

        public Criteria andWfiIdNotIn(List<String> values) {
            addCriterion("wfi_id not in", values, "wfiId");
            return (Criteria) this;
        }

        public Criteria andWfiIdBetween(String value1, String value2) {
            addCriterion("wfi_id between", value1, value2, "wfiId");
            return (Criteria) this;
        }

        public Criteria andWfiIdNotBetween(String value1, String value2) {
            addCriterion("wfi_id not between", value1, value2, "wfiId");
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

        public Criteria andFlagEqualTo(Integer value) {
            addCriterion("flag =", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagNotEqualTo(Integer value) {
            addCriterion("flag <>", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagGreaterThan(Integer value) {
            addCriterion("flag >", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("flag >=", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagLessThan(Integer value) {
            addCriterion("flag <", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagLessThanOrEqualTo(Integer value) {
            addCriterion("flag <=", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagIn(List<Integer> values) {
            addCriterion("flag in", values, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagNotIn(List<Integer> values) {
            addCriterion("flag not in", values, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagBetween(Integer value1, Integer value2) {
            addCriterion("flag between", value1, value2, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("flag not between", value1, value2, "flag");
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
