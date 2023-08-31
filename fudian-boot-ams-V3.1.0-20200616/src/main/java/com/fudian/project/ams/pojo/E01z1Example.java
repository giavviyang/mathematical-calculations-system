package com.fudian.project.ams.pojo;

import java.util.ArrayList;
import java.util.List;

public class E01z1Example {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public E01z1Example() {
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

        public Criteria andE01z101IsNull() {
            addCriterion("e01z101 is null");
            return (Criteria) this;
        }

        public Criteria andE01z101IsNotNull() {
            addCriterion("e01z101 is not null");
            return (Criteria) this;
        }

        public Criteria andE01z101EqualTo(String value) {
            addCriterion("e01z101 =", value, "e01z101");
            return (Criteria) this;
        }

        public Criteria andE01z101NotEqualTo(String value) {
            addCriterion("e01z101 <>", value, "e01z101");
            return (Criteria) this;
        }

        public Criteria andE01z101GreaterThan(String value) {
            addCriterion("e01z101 >", value, "e01z101");
            return (Criteria) this;
        }

        public Criteria andE01z101GreaterThanOrEqualTo(String value) {
            addCriterion("e01z101 >=", value, "e01z101");
            return (Criteria) this;
        }

        public Criteria andE01z101LessThan(String value) {
            addCriterion("e01z101 <", value, "e01z101");
            return (Criteria) this;
        }

        public Criteria andE01z101LessThanOrEqualTo(String value) {
            addCriterion("e01z101 <=", value, "e01z101");
            return (Criteria) this;
        }

        public Criteria andE01z101Like(String value) {
            addCriterion("e01z101 like", value, "e01z101");
            return (Criteria) this;
        }

        public Criteria andE01z101NotLike(String value) {
            addCriterion("e01z101 not like", value, "e01z101");
            return (Criteria) this;
        }

        public Criteria andE01z101In(List<String> values) {
            addCriterion("e01z101 in", values, "e01z101");
            return (Criteria) this;
        }

        public Criteria andE01z101NotIn(List<String> values) {
            addCriterion("e01z101 not in", values, "e01z101");
            return (Criteria) this;
        }

        public Criteria andE01z101Between(String value1, String value2) {
            addCriterion("e01z101 between", value1, value2, "e01z101");
            return (Criteria) this;
        }

        public Criteria andE01z101NotBetween(String value1, String value2) {
            addCriterion("e01z101 not between", value1, value2, "e01z101");
            return (Criteria) this;
        }

        public Criteria andE01z104IsNull() {
            addCriterion("e01z104 is null");
            return (Criteria) this;
        }

        public Criteria andE01z104IsNotNull() {
            addCriterion("e01z104 is not null");
            return (Criteria) this;
        }

        public Criteria andE01z104EqualTo(Integer value) {
            addCriterion("e01z104 =", value, "e01z104");
            return (Criteria) this;
        }

        public Criteria andE01z104NotEqualTo(Integer value) {
            addCriterion("e01z104 <>", value, "e01z104");
            return (Criteria) this;
        }

        public Criteria andE01z104GreaterThan(Integer value) {
            addCriterion("e01z104 >", value, "e01z104");
            return (Criteria) this;
        }

        public Criteria andE01z104GreaterThanOrEqualTo(Integer value) {
            addCriterion("e01z104 >=", value, "e01z104");
            return (Criteria) this;
        }

        public Criteria andE01z104LessThan(Integer value) {
            addCriterion("e01z104 <", value, "e01z104");
            return (Criteria) this;
        }

        public Criteria andE01z104LessThanOrEqualTo(Integer value) {
            addCriterion("e01z104 <=", value, "e01z104");
            return (Criteria) this;
        }

        public Criteria andE01z104In(List<Integer> values) {
            addCriterion("e01z104 in", values, "e01z104");
            return (Criteria) this;
        }

        public Criteria andE01z104NotIn(List<Integer> values) {
            addCriterion("e01z104 not in", values, "e01z104");
            return (Criteria) this;
        }

        public Criteria andE01z104Between(Integer value1, Integer value2) {
            addCriterion("e01z104 between", value1, value2, "e01z104");
            return (Criteria) this;
        }

        public Criteria andE01z104NotBetween(Integer value1, Integer value2) {
            addCriterion("e01z104 not between", value1, value2, "e01z104");
            return (Criteria) this;
        }

        public Criteria andE01z107IsNull() {
            addCriterion("e01z107 is null");
            return (Criteria) this;
        }

        public Criteria andE01z107IsNotNull() {
            addCriterion("e01z107 is not null");
            return (Criteria) this;
        }

        public Criteria andE01z107EqualTo(String value) {
            addCriterion("e01z107 =", value, "e01z107");
            return (Criteria) this;
        }

        public Criteria andE01z107NotEqualTo(String value) {
            addCriterion("e01z107 <>", value, "e01z107");
            return (Criteria) this;
        }

        public Criteria andE01z107GreaterThan(String value) {
            addCriterion("e01z107 >", value, "e01z107");
            return (Criteria) this;
        }

        public Criteria andE01z107GreaterThanOrEqualTo(String value) {
            addCriterion("e01z107 >=", value, "e01z107");
            return (Criteria) this;
        }

        public Criteria andE01z107LessThan(String value) {
            addCriterion("e01z107 <", value, "e01z107");
            return (Criteria) this;
        }

        public Criteria andE01z107LessThanOrEqualTo(String value) {
            addCriterion("e01z107 <=", value, "e01z107");
            return (Criteria) this;
        }

        public Criteria andE01z107Like(String value) {
            addCriterion("e01z107 like", value, "e01z107");
            return (Criteria) this;
        }

        public Criteria andE01z107NotLike(String value) {
            addCriterion("e01z107 not like", value, "e01z107");
            return (Criteria) this;
        }

        public Criteria andE01z107In(List<String> values) {
            addCriterion("e01z107 in", values, "e01z107");
            return (Criteria) this;
        }

        public Criteria andE01z107NotIn(List<String> values) {
            addCriterion("e01z107 not in", values, "e01z107");
            return (Criteria) this;
        }

        public Criteria andE01z107Between(String value1, String value2) {
            addCriterion("e01z107 between", value1, value2, "e01z107");
            return (Criteria) this;
        }

        public Criteria andE01z107NotBetween(String value1, String value2) {
            addCriterion("e01z107 not between", value1, value2, "e01z107");
            return (Criteria) this;
        }

        public Criteria andE01z111aIsNull() {
            addCriterion("e01z111a is null");
            return (Criteria) this;
        }

        public Criteria andE01z111aIsNotNull() {
            addCriterion("e01z111a is not null");
            return (Criteria) this;
        }

        public Criteria andE01z111aEqualTo(String value) {
            addCriterion("e01z111a =", value, "e01z111a");
            return (Criteria) this;
        }

        public Criteria andE01z111aNotEqualTo(String value) {
            addCriterion("e01z111a <>", value, "e01z111a");
            return (Criteria) this;
        }

        public Criteria andE01z111aGreaterThan(String value) {
            addCriterion("e01z111a >", value, "e01z111a");
            return (Criteria) this;
        }

        public Criteria andE01z111aGreaterThanOrEqualTo(String value) {
            addCriterion("e01z111a >=", value, "e01z111a");
            return (Criteria) this;
        }

        public Criteria andE01z111aLessThan(String value) {
            addCriterion("e01z111a <", value, "e01z111a");
            return (Criteria) this;
        }

        public Criteria andE01z111aLessThanOrEqualTo(String value) {
            addCriterion("e01z111a <=", value, "e01z111a");
            return (Criteria) this;
        }

        public Criteria andE01z111aLike(String value) {
            addCriterion("e01z111a like", value, "e01z111a");
            return (Criteria) this;
        }

        public Criteria andE01z111aNotLike(String value) {
            addCriterion("e01z111a not like", value, "e01z111a");
            return (Criteria) this;
        }

        public Criteria andE01z111aIn(List<String> values) {
            addCriterion("e01z111a in", values, "e01z111a");
            return (Criteria) this;
        }

        public Criteria andE01z111aNotIn(List<String> values) {
            addCriterion("e01z111a not in", values, "e01z111a");
            return (Criteria) this;
        }

        public Criteria andE01z111aBetween(String value1, String value2) {
            addCriterion("e01z111a between", value1, value2, "e01z111a");
            return (Criteria) this;
        }

        public Criteria andE01z111aNotBetween(String value1, String value2) {
            addCriterion("e01z111a not between", value1, value2, "e01z111a");
            return (Criteria) this;
        }

        public Criteria andE01z111bIsNull() {
            addCriterion("e01z111b is null");
            return (Criteria) this;
        }

        public Criteria andE01z111bIsNotNull() {
            addCriterion("e01z111b is not null");
            return (Criteria) this;
        }

        public Criteria andE01z111bEqualTo(String value) {
            addCriterion("e01z111b =", value, "e01z111b");
            return (Criteria) this;
        }

        public Criteria andE01z111bNotEqualTo(String value) {
            addCriterion("e01z111b <>", value, "e01z111b");
            return (Criteria) this;
        }

        public Criteria andE01z111bGreaterThan(String value) {
            addCriterion("e01z111b >", value, "e01z111b");
            return (Criteria) this;
        }

        public Criteria andE01z111bGreaterThanOrEqualTo(String value) {
            addCriterion("e01z111b >=", value, "e01z111b");
            return (Criteria) this;
        }

        public Criteria andE01z111bLessThan(String value) {
            addCriterion("e01z111b <", value, "e01z111b");
            return (Criteria) this;
        }

        public Criteria andE01z111bLessThanOrEqualTo(String value) {
            addCriterion("e01z111b <=", value, "e01z111b");
            return (Criteria) this;
        }

        public Criteria andE01z111bLike(String value) {
            addCriterion("e01z111b like", value, "e01z111b");
            return (Criteria) this;
        }

        public Criteria andE01z111bNotLike(String value) {
            addCriterion("e01z111b not like", value, "e01z111b");
            return (Criteria) this;
        }

        public Criteria andE01z111bIn(List<String> values) {
            addCriterion("e01z111b in", values, "e01z111b");
            return (Criteria) this;
        }

        public Criteria andE01z111bNotIn(List<String> values) {
            addCriterion("e01z111b not in", values, "e01z111b");
            return (Criteria) this;
        }

        public Criteria andE01z111bBetween(String value1, String value2) {
            addCriterion("e01z111b between", value1, value2, "e01z111b");
            return (Criteria) this;
        }

        public Criteria andE01z111bNotBetween(String value1, String value2) {
            addCriterion("e01z111b not between", value1, value2, "e01z111b");
            return (Criteria) this;
        }

        public Criteria andE01z114IsNull() {
            addCriterion("e01z114 is null");
            return (Criteria) this;
        }

        public Criteria andE01z114IsNotNull() {
            addCriterion("e01z114 is not null");
            return (Criteria) this;
        }

        public Criteria andE01z114EqualTo(Integer value) {
            addCriterion("e01z114 =", value, "e01z114");
            return (Criteria) this;
        }

        public Criteria andE01z114NotEqualTo(Integer value) {
            addCriterion("e01z114 <>", value, "e01z114");
            return (Criteria) this;
        }

        public Criteria andE01z114GreaterThan(Integer value) {
            addCriterion("e01z114 >", value, "e01z114");
            return (Criteria) this;
        }

        public Criteria andE01z114GreaterThanOrEqualTo(Integer value) {
            addCriterion("e01z114 >=", value, "e01z114");
            return (Criteria) this;
        }

        public Criteria andE01z114LessThan(Integer value) {
            addCriterion("e01z114 <", value, "e01z114");
            return (Criteria) this;
        }

        public Criteria andE01z114LessThanOrEqualTo(Integer value) {
            addCriterion("e01z114 <=", value, "e01z114");
            return (Criteria) this;
        }

        public Criteria andE01z114In(List<Integer> values) {
            addCriterion("e01z114 in", values, "e01z114");
            return (Criteria) this;
        }

        public Criteria andE01z114NotIn(List<Integer> values) {
            addCriterion("e01z114 not in", values, "e01z114");
            return (Criteria) this;
        }

        public Criteria andE01z114Between(Integer value1, Integer value2) {
            addCriterion("e01z114 between", value1, value2, "e01z114");
            return (Criteria) this;
        }

        public Criteria andE01z114NotBetween(Integer value1, Integer value2) {
            addCriterion("e01z114 not between", value1, value2, "e01z114");
            return (Criteria) this;
        }

        public Criteria andE01z117IsNull() {
            addCriterion("e01z117 is null");
            return (Criteria) this;
        }

        public Criteria andE01z117IsNotNull() {
            addCriterion("e01z117 is not null");
            return (Criteria) this;
        }

        public Criteria andE01z117EqualTo(String value) {
            addCriterion("e01z117 =", value, "e01z117");
            return (Criteria) this;
        }

        public Criteria andE01z117NotEqualTo(String value) {
            addCriterion("e01z117 <>", value, "e01z117");
            return (Criteria) this;
        }

        public Criteria andE01z117GreaterThan(String value) {
            addCriterion("e01z117 >", value, "e01z117");
            return (Criteria) this;
        }

        public Criteria andE01z117GreaterThanOrEqualTo(String value) {
            addCriterion("e01z117 >=", value, "e01z117");
            return (Criteria) this;
        }

        public Criteria andE01z117LessThan(String value) {
            addCriterion("e01z117 <", value, "e01z117");
            return (Criteria) this;
        }

        public Criteria andE01z117LessThanOrEqualTo(String value) {
            addCriterion("e01z117 <=", value, "e01z117");
            return (Criteria) this;
        }

        public Criteria andE01z117Like(String value) {
            addCriterion("e01z117 like", value, "e01z117");
            return (Criteria) this;
        }

        public Criteria andE01z117NotLike(String value) {
            addCriterion("e01z117 not like", value, "e01z117");
            return (Criteria) this;
        }

        public Criteria andE01z117In(List<String> values) {
            addCriterion("e01z117 in", values, "e01z117");
            return (Criteria) this;
        }

        public Criteria andE01z117NotIn(List<String> values) {
            addCriterion("e01z117 not in", values, "e01z117");
            return (Criteria) this;
        }

        public Criteria andE01z117Between(String value1, String value2) {
            addCriterion("e01z117 between", value1, value2, "e01z117");
            return (Criteria) this;
        }

        public Criteria andE01z117NotBetween(String value1, String value2) {
            addCriterion("e01z117 not between", value1, value2, "e01z117");
            return (Criteria) this;
        }

        public Criteria andE01z121IsNull() {
            addCriterion("e01z121 is null");
            return (Criteria) this;
        }

        public Criteria andE01z121IsNotNull() {
            addCriterion("e01z121 is not null");
            return (Criteria) this;
        }

        public Criteria andE01z121EqualTo(String value) {
            addCriterion("e01z121 =", value, "e01z121");
            return (Criteria) this;
        }

        public Criteria andE01z121NotEqualTo(String value) {
            addCriterion("e01z121 <>", value, "e01z121");
            return (Criteria) this;
        }

        public Criteria andE01z121GreaterThan(String value) {
            addCriterion("e01z121 >", value, "e01z121");
            return (Criteria) this;
        }

        public Criteria andE01z121GreaterThanOrEqualTo(String value) {
            addCriterion("e01z121 >=", value, "e01z121");
            return (Criteria) this;
        }

        public Criteria andE01z121LessThan(String value) {
            addCriterion("e01z121 <", value, "e01z121");
            return (Criteria) this;
        }

        public Criteria andE01z121LessThanOrEqualTo(String value) {
            addCriterion("e01z121 <=", value, "e01z121");
            return (Criteria) this;
        }

        public Criteria andE01z121Like(String value) {
            addCriterion("e01z121 like", value, "e01z121");
            return (Criteria) this;
        }

        public Criteria andE01z121NotLike(String value) {
            addCriterion("e01z121 not like", value, "e01z121");
            return (Criteria) this;
        }

        public Criteria andE01z121In(List<String> values) {
            addCriterion("e01z121 in", values, "e01z121");
            return (Criteria) this;
        }

        public Criteria andE01z121NotIn(List<String> values) {
            addCriterion("e01z121 not in", values, "e01z121");
            return (Criteria) this;
        }

        public Criteria andE01z121Between(String value1, String value2) {
            addCriterion("e01z121 between", value1, value2, "e01z121");
            return (Criteria) this;
        }

        public Criteria andE01z121NotBetween(String value1, String value2) {
            addCriterion("e01z121 not between", value1, value2, "e01z121");
            return (Criteria) this;
        }

        public Criteria andE01z124IsNull() {
            addCriterion("e01z124 is null");
            return (Criteria) this;
        }

        public Criteria andE01z124IsNotNull() {
            addCriterion("e01z124 is not null");
            return (Criteria) this;
        }

        public Criteria andE01z124EqualTo(Integer value) {
            addCriterion("e01z124 =", value, "e01z124");
            return (Criteria) this;
        }

        public Criteria andE01z124NotEqualTo(Integer value) {
            addCriterion("e01z124 <>", value, "e01z124");
            return (Criteria) this;
        }

        public Criteria andE01z124GreaterThan(Integer value) {
            addCriterion("e01z124 >", value, "e01z124");
            return (Criteria) this;
        }

        public Criteria andE01z124GreaterThanOrEqualTo(Integer value) {
            addCriterion("e01z124 >=", value, "e01z124");
            return (Criteria) this;
        }

        public Criteria andE01z124LessThan(Integer value) {
            addCriterion("e01z124 <", value, "e01z124");
            return (Criteria) this;
        }

        public Criteria andE01z124LessThanOrEqualTo(Integer value) {
            addCriterion("e01z124 <=", value, "e01z124");
            return (Criteria) this;
        }

        public Criteria andE01z124In(List<Integer> values) {
            addCriterion("e01z124 in", values, "e01z124");
            return (Criteria) this;
        }

        public Criteria andE01z124NotIn(List<Integer> values) {
            addCriterion("e01z124 not in", values, "e01z124");
            return (Criteria) this;
        }

        public Criteria andE01z124Between(Integer value1, Integer value2) {
            addCriterion("e01z124 between", value1, value2, "e01z124");
            return (Criteria) this;
        }

        public Criteria andE01z124NotBetween(Integer value1, Integer value2) {
            addCriterion("e01z124 not between", value1, value2, "e01z124");
            return (Criteria) this;
        }

        public Criteria andE01z127IsNull() {
            addCriterion("e01z127 is null");
            return (Criteria) this;
        }

        public Criteria andE01z127IsNotNull() {
            addCriterion("e01z127 is not null");
            return (Criteria) this;
        }

        public Criteria andE01z127EqualTo(String value) {
            addCriterion("e01z127 =", value, "e01z127");
            return (Criteria) this;
        }

        public Criteria andE01z127NotEqualTo(String value) {
            addCriterion("e01z127 <>", value, "e01z127");
            return (Criteria) this;
        }

        public Criteria andE01z127GreaterThan(String value) {
            addCriterion("e01z127 >", value, "e01z127");
            return (Criteria) this;
        }

        public Criteria andE01z127GreaterThanOrEqualTo(String value) {
            addCriterion("e01z127 >=", value, "e01z127");
            return (Criteria) this;
        }

        public Criteria andE01z127LessThan(String value) {
            addCriterion("e01z127 <", value, "e01z127");
            return (Criteria) this;
        }

        public Criteria andE01z127LessThanOrEqualTo(String value) {
            addCriterion("e01z127 <=", value, "e01z127");
            return (Criteria) this;
        }

        public Criteria andE01z127Like(String value) {
            addCriterion("e01z127 like", value, "e01z127");
            return (Criteria) this;
        }

        public Criteria andE01z127NotLike(String value) {
            addCriterion("e01z127 not like", value, "e01z127");
            return (Criteria) this;
        }

        public Criteria andE01z127In(List<String> values) {
            addCriterion("e01z127 in", values, "e01z127");
            return (Criteria) this;
        }

        public Criteria andE01z127NotIn(List<String> values) {
            addCriterion("e01z127 not in", values, "e01z127");
            return (Criteria) this;
        }

        public Criteria andE01z127Between(String value1, String value2) {
            addCriterion("e01z127 between", value1, value2, "e01z127");
            return (Criteria) this;
        }

        public Criteria andE01z127NotBetween(String value1, String value2) {
            addCriterion("e01z127 not between", value1, value2, "e01z127");
            return (Criteria) this;
        }

        public Criteria andE01z131IsNull() {
            addCriterion("e01z131 is null");
            return (Criteria) this;
        }

        public Criteria andE01z131IsNotNull() {
            addCriterion("e01z131 is not null");
            return (Criteria) this;
        }

        public Criteria andE01z131EqualTo(String value) {
            addCriterion("e01z131 =", value, "e01z131");
            return (Criteria) this;
        }

        public Criteria andE01z131NotEqualTo(String value) {
            addCriterion("e01z131 <>", value, "e01z131");
            return (Criteria) this;
        }

        public Criteria andE01z131GreaterThan(String value) {
            addCriterion("e01z131 >", value, "e01z131");
            return (Criteria) this;
        }

        public Criteria andE01z131GreaterThanOrEqualTo(String value) {
            addCriterion("e01z131 >=", value, "e01z131");
            return (Criteria) this;
        }

        public Criteria andE01z131LessThan(String value) {
            addCriterion("e01z131 <", value, "e01z131");
            return (Criteria) this;
        }

        public Criteria andE01z131LessThanOrEqualTo(String value) {
            addCriterion("e01z131 <=", value, "e01z131");
            return (Criteria) this;
        }

        public Criteria andE01z131Like(String value) {
            addCriterion("e01z131 like", value, "e01z131");
            return (Criteria) this;
        }

        public Criteria andE01z131NotLike(String value) {
            addCriterion("e01z131 not like", value, "e01z131");
            return (Criteria) this;
        }

        public Criteria andE01z131In(List<String> values) {
            addCriterion("e01z131 in", values, "e01z131");
            return (Criteria) this;
        }

        public Criteria andE01z131NotIn(List<String> values) {
            addCriterion("e01z131 not in", values, "e01z131");
            return (Criteria) this;
        }

        public Criteria andE01z131Between(String value1, String value2) {
            addCriterion("e01z131 between", value1, value2, "e01z131");
            return (Criteria) this;
        }

        public Criteria andE01z131NotBetween(String value1, String value2) {
            addCriterion("e01z131 not between", value1, value2, "e01z131");
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
