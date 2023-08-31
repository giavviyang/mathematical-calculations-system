package com.fudian.project.ams.pojo;

import java.util.ArrayList;
import java.util.List;

public class E01z6Example {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public E01z6Example() {
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

        public Criteria andE01z601IsNull() {
            addCriterion("e01z601 is null");
            return (Criteria) this;
        }

        public Criteria andE01z601IsNotNull() {
            addCriterion("e01z601 is not null");
            return (Criteria) this;
        }

        public Criteria andE01z601EqualTo(String value) {
            addCriterion("e01z601 =", value, "e01z601");
            return (Criteria) this;
        }

        public Criteria andE01z601NotEqualTo(String value) {
            addCriterion("e01z601 <>", value, "e01z601");
            return (Criteria) this;
        }

        public Criteria andE01z601GreaterThan(String value) {
            addCriterion("e01z601 >", value, "e01z601");
            return (Criteria) this;
        }

        public Criteria andE01z601GreaterThanOrEqualTo(String value) {
            addCriterion("e01z601 >=", value, "e01z601");
            return (Criteria) this;
        }

        public Criteria andE01z601LessThan(String value) {
            addCriterion("e01z601 <", value, "e01z601");
            return (Criteria) this;
        }

        public Criteria andE01z601LessThanOrEqualTo(String value) {
            addCriterion("e01z601 <=", value, "e01z601");
            return (Criteria) this;
        }

        public Criteria andE01z601Like(String value) {
            addCriterion("e01z601 like", value, "e01z601");
            return (Criteria) this;
        }

        public Criteria andE01z601NotLike(String value) {
            addCriterion("e01z601 not like", value, "e01z601");
            return (Criteria) this;
        }

        public Criteria andE01z601In(List<String> values) {
            addCriterion("e01z601 in", values, "e01z601");
            return (Criteria) this;
        }

        public Criteria andE01z601NotIn(List<String> values) {
            addCriterion("e01z601 not in", values, "e01z601");
            return (Criteria) this;
        }

        public Criteria andE01z601Between(String value1, String value2) {
            addCriterion("e01z601 between", value1, value2, "e01z601");
            return (Criteria) this;
        }

        public Criteria andE01z601NotBetween(String value1, String value2) {
            addCriterion("e01z601 not between", value1, value2, "e01z601");
            return (Criteria) this;
        }

        public Criteria andE01z604IsNull() {
            addCriterion("e01z604 is null");
            return (Criteria) this;
        }

        public Criteria andE01z604IsNotNull() {
            addCriterion("e01z604 is not null");
            return (Criteria) this;
        }

        public Criteria andE01z604EqualTo(Integer value) {
            addCriterion("e01z604 =", value, "e01z604");
            return (Criteria) this;
        }

        public Criteria andE01z604NotEqualTo(Integer value) {
            addCriterion("e01z604 <>", value, "e01z604");
            return (Criteria) this;
        }

        public Criteria andE01z604GreaterThan(Integer value) {
            addCriterion("e01z604 >", value, "e01z604");
            return (Criteria) this;
        }

        public Criteria andE01z604GreaterThanOrEqualTo(Integer value) {
            addCriterion("e01z604 >=", value, "e01z604");
            return (Criteria) this;
        }

        public Criteria andE01z604LessThan(Integer value) {
            addCriterion("e01z604 <", value, "e01z604");
            return (Criteria) this;
        }

        public Criteria andE01z604LessThanOrEqualTo(Integer value) {
            addCriterion("e01z604 <=", value, "e01z604");
            return (Criteria) this;
        }

        public Criteria andE01z604In(List<Integer> values) {
            addCriterion("e01z604 in", values, "e01z604");
            return (Criteria) this;
        }

        public Criteria andE01z604NotIn(List<Integer> values) {
            addCriterion("e01z604 not in", values, "e01z604");
            return (Criteria) this;
        }

        public Criteria andE01z604Between(Integer value1, Integer value2) {
            addCriterion("e01z604 between", value1, value2, "e01z604");
            return (Criteria) this;
        }

        public Criteria andE01z604NotBetween(Integer value1, Integer value2) {
            addCriterion("e01z604 not between", value1, value2, "e01z604");
            return (Criteria) this;
        }

        public Criteria andE01z607IsNull() {
            addCriterion("e01z607 is null");
            return (Criteria) this;
        }

        public Criteria andE01z607IsNotNull() {
            addCriterion("e01z607 is not null");
            return (Criteria) this;
        }

        public Criteria andE01z607EqualTo(Integer value) {
            addCriterion("e01z607 =", value, "e01z607");
            return (Criteria) this;
        }

        public Criteria andE01z607NotEqualTo(Integer value) {
            addCriterion("e01z607 <>", value, "e01z607");
            return (Criteria) this;
        }

        public Criteria andE01z607GreaterThan(Integer value) {
            addCriterion("e01z607 >", value, "e01z607");
            return (Criteria) this;
        }

        public Criteria andE01z607GreaterThanOrEqualTo(Integer value) {
            addCriterion("e01z607 >=", value, "e01z607");
            return (Criteria) this;
        }

        public Criteria andE01z607LessThan(Integer value) {
            addCriterion("e01z607 <", value, "e01z607");
            return (Criteria) this;
        }

        public Criteria andE01z607LessThanOrEqualTo(Integer value) {
            addCriterion("e01z607 <=", value, "e01z607");
            return (Criteria) this;
        }

        public Criteria andE01z607In(List<Integer> values) {
            addCriterion("e01z607 in", values, "e01z607");
            return (Criteria) this;
        }

        public Criteria andE01z607NotIn(List<Integer> values) {
            addCriterion("e01z607 not in", values, "e01z607");
            return (Criteria) this;
        }

        public Criteria andE01z607Between(Integer value1, Integer value2) {
            addCriterion("e01z607 between", value1, value2, "e01z607");
            return (Criteria) this;
        }

        public Criteria andE01z607NotBetween(Integer value1, Integer value2) {
            addCriterion("e01z607 not between", value1, value2, "e01z607");
            return (Criteria) this;
        }

        public Criteria andE01z611IsNull() {
            addCriterion("e01z611 is null");
            return (Criteria) this;
        }

        public Criteria andE01z611IsNotNull() {
            addCriterion("e01z611 is not null");
            return (Criteria) this;
        }

        public Criteria andE01z611EqualTo(String value) {
            addCriterion("e01z611 =", value, "e01z611");
            return (Criteria) this;
        }

        public Criteria andE01z611NotEqualTo(String value) {
            addCriterion("e01z611 <>", value, "e01z611");
            return (Criteria) this;
        }

        public Criteria andE01z611GreaterThan(String value) {
            addCriterion("e01z611 >", value, "e01z611");
            return (Criteria) this;
        }

        public Criteria andE01z611GreaterThanOrEqualTo(String value) {
            addCriterion("e01z611 >=", value, "e01z611");
            return (Criteria) this;
        }

        public Criteria andE01z611LessThan(String value) {
            addCriterion("e01z611 <", value, "e01z611");
            return (Criteria) this;
        }

        public Criteria andE01z611LessThanOrEqualTo(String value) {
            addCriterion("e01z611 <=", value, "e01z611");
            return (Criteria) this;
        }

        public Criteria andE01z611Like(String value) {
            addCriterion("e01z611 like", value, "e01z611");
            return (Criteria) this;
        }

        public Criteria andE01z611NotLike(String value) {
            addCriterion("e01z611 not like", value, "e01z611");
            return (Criteria) this;
        }

        public Criteria andE01z611In(List<String> values) {
            addCriterion("e01z611 in", values, "e01z611");
            return (Criteria) this;
        }

        public Criteria andE01z611NotIn(List<String> values) {
            addCriterion("e01z611 not in", values, "e01z611");
            return (Criteria) this;
        }

        public Criteria andE01z611Between(String value1, String value2) {
            addCriterion("e01z611 between", value1, value2, "e01z611");
            return (Criteria) this;
        }

        public Criteria andE01z611NotBetween(String value1, String value2) {
            addCriterion("e01z611 not between", value1, value2, "e01z611");
            return (Criteria) this;
        }

        public Criteria andE01z614IsNull() {
            addCriterion("e01z614 is null");
            return (Criteria) this;
        }

        public Criteria andE01z614IsNotNull() {
            addCriterion("e01z614 is not null");
            return (Criteria) this;
        }

        public Criteria andE01z614EqualTo(String value) {
            addCriterion("e01z614 =", value, "e01z614");
            return (Criteria) this;
        }

        public Criteria andE01z614NotEqualTo(String value) {
            addCriterion("e01z614 <>", value, "e01z614");
            return (Criteria) this;
        }

        public Criteria andE01z614GreaterThan(String value) {
            addCriterion("e01z614 >", value, "e01z614");
            return (Criteria) this;
        }

        public Criteria andE01z614GreaterThanOrEqualTo(String value) {
            addCriterion("e01z614 >=", value, "e01z614");
            return (Criteria) this;
        }

        public Criteria andE01z614LessThan(String value) {
            addCriterion("e01z614 <", value, "e01z614");
            return (Criteria) this;
        }

        public Criteria andE01z614LessThanOrEqualTo(String value) {
            addCriterion("e01z614 <=", value, "e01z614");
            return (Criteria) this;
        }

        public Criteria andE01z614Like(String value) {
            addCriterion("e01z614 like", value, "e01z614");
            return (Criteria) this;
        }

        public Criteria andE01z614NotLike(String value) {
            addCriterion("e01z614 not like", value, "e01z614");
            return (Criteria) this;
        }

        public Criteria andE01z614In(List<String> values) {
            addCriterion("e01z614 in", values, "e01z614");
            return (Criteria) this;
        }

        public Criteria andE01z614NotIn(List<String> values) {
            addCriterion("e01z614 not in", values, "e01z614");
            return (Criteria) this;
        }

        public Criteria andE01z614Between(String value1, String value2) {
            addCriterion("e01z614 between", value1, value2, "e01z614");
            return (Criteria) this;
        }

        public Criteria andE01z614NotBetween(String value1, String value2) {
            addCriterion("e01z614 not between", value1, value2, "e01z614");
            return (Criteria) this;
        }

        public Criteria andCheckerIsNull() {
            addCriterion("checker is null");
            return (Criteria) this;
        }

        public Criteria andCheckerIsNotNull() {
            addCriterion("checker is not null");
            return (Criteria) this;
        }

        public Criteria andCheckerEqualTo(String value) {
            addCriterion("checker =", value, "checker");
            return (Criteria) this;
        }

        public Criteria andCheckerNotEqualTo(String value) {
            addCriterion("checker <>", value, "checker");
            return (Criteria) this;
        }

        public Criteria andCheckerGreaterThan(String value) {
            addCriterion("checker >", value, "checker");
            return (Criteria) this;
        }

        public Criteria andCheckerGreaterThanOrEqualTo(String value) {
            addCriterion("checker >=", value, "checker");
            return (Criteria) this;
        }

        public Criteria andCheckerLessThan(String value) {
            addCriterion("checker <", value, "checker");
            return (Criteria) this;
        }

        public Criteria andCheckerLessThanOrEqualTo(String value) {
            addCriterion("checker <=", value, "checker");
            return (Criteria) this;
        }

        public Criteria andCheckerLike(String value) {
            addCriterion("checker like", value, "checker");
            return (Criteria) this;
        }

        public Criteria andCheckerNotLike(String value) {
            addCriterion("checker not like", value, "checker");
            return (Criteria) this;
        }

        public Criteria andCheckerIn(List<String> values) {
            addCriterion("checker in", values, "checker");
            return (Criteria) this;
        }

        public Criteria andCheckerNotIn(List<String> values) {
            addCriterion("checker not in", values, "checker");
            return (Criteria) this;
        }

        public Criteria andCheckerBetween(String value1, String value2) {
            addCriterion("checker between", value1, value2, "checker");
            return (Criteria) this;
        }

        public Criteria andCheckerNotBetween(String value1, String value2) {
            addCriterion("checker not between", value1, value2, "checker");
            return (Criteria) this;
        }

        public Criteria andE01z617IsNull() {
            addCriterion("e01z617 is null");
            return (Criteria) this;
        }

        public Criteria andE01z617IsNotNull() {
            addCriterion("e01z617 is not null");
            return (Criteria) this;
        }

        public Criteria andE01z617EqualTo(String value) {
            addCriterion("e01z617 =", value, "e01z617");
            return (Criteria) this;
        }

        public Criteria andE01z617NotEqualTo(String value) {
            addCriterion("e01z617 <>", value, "e01z617");
            return (Criteria) this;
        }

        public Criteria andE01z617GreaterThan(String value) {
            addCriterion("e01z617 >", value, "e01z617");
            return (Criteria) this;
        }

        public Criteria andE01z617GreaterThanOrEqualTo(String value) {
            addCriterion("e01z617 >=", value, "e01z617");
            return (Criteria) this;
        }

        public Criteria andE01z617LessThan(String value) {
            addCriterion("e01z617 <", value, "e01z617");
            return (Criteria) this;
        }

        public Criteria andE01z617LessThanOrEqualTo(String value) {
            addCriterion("e01z617 <=", value, "e01z617");
            return (Criteria) this;
        }

        public Criteria andE01z617Like(String value) {
            addCriterion("e01z617 like", value, "e01z617");
            return (Criteria) this;
        }

        public Criteria andE01z617NotLike(String value) {
            addCriterion("e01z617 not like", value, "e01z617");
            return (Criteria) this;
        }

        public Criteria andE01z617In(List<String> values) {
            addCriterion("e01z617 in", values, "e01z617");
            return (Criteria) this;
        }

        public Criteria andE01z617NotIn(List<String> values) {
            addCriterion("e01z617 not in", values, "e01z617");
            return (Criteria) this;
        }

        public Criteria andE01z617Between(String value1, String value2) {
            addCriterion("e01z617 between", value1, value2, "e01z617");
            return (Criteria) this;
        }

        public Criteria andE01z617NotBetween(String value1, String value2) {
            addCriterion("e01z617 not between", value1, value2, "e01z617");
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
