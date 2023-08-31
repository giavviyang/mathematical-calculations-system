package com.fudian.project.ams.pojo;

import java.util.ArrayList;
import java.util.List;

public class E01z7Example {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public E01z7Example() {
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

        public Criteria andSendIdIsNull() {
            addCriterion("send_id is null");
            return (Criteria) this;
        }

        public Criteria andSendIdIsNotNull() {
            addCriterion("send_id is not null");
            return (Criteria) this;
        }

        public Criteria andSendIdEqualTo(String value) {
            addCriterion("send_id =", value, "sendId");
            return (Criteria) this;
        }

        public Criteria andSendIdNotEqualTo(String value) {
            addCriterion("send_id <>", value, "sendId");
            return (Criteria) this;
        }

        public Criteria andSendIdGreaterThan(String value) {
            addCriterion("send_id >", value, "sendId");
            return (Criteria) this;
        }

        public Criteria andSendIdGreaterThanOrEqualTo(String value) {
            addCriterion("send_id >=", value, "sendId");
            return (Criteria) this;
        }

        public Criteria andSendIdLessThan(String value) {
            addCriterion("send_id <", value, "sendId");
            return (Criteria) this;
        }

        public Criteria andSendIdLessThanOrEqualTo(String value) {
            addCriterion("send_id <=", value, "sendId");
            return (Criteria) this;
        }

        public Criteria andSendIdLike(String value) {
            addCriterion("send_id like", value, "sendId");
            return (Criteria) this;
        }

        public Criteria andSendIdNotLike(String value) {
            addCriterion("send_id not like", value, "sendId");
            return (Criteria) this;
        }

        public Criteria andSendIdIn(List<String> values) {
            addCriterion("send_id in", values, "sendId");
            return (Criteria) this;
        }

        public Criteria andSendIdNotIn(List<String> values) {
            addCriterion("send_id not in", values, "sendId");
            return (Criteria) this;
        }

        public Criteria andSendIdBetween(String value1, String value2) {
            addCriterion("send_id between", value1, value2, "sendId");
            return (Criteria) this;
        }

        public Criteria andSendIdNotBetween(String value1, String value2) {
            addCriterion("send_id not between", value1, value2, "sendId");
            return (Criteria) this;
        }

        public Criteria andA01IdIsNull() {
            addCriterion("A01_ID is null");
            return (Criteria) this;
        }

        public Criteria andA01IdIsNotNull() {
            addCriterion("A01_ID is not null");
            return (Criteria) this;
        }

        public Criteria andA01IdEqualTo(String value) {
            addCriterion("A01_ID =", value, "a01Id");
            return (Criteria) this;
        }

        public Criteria andA01IdNotEqualTo(String value) {
            addCriterion("A01_ID <>", value, "a01Id");
            return (Criteria) this;
        }

        public Criteria andA01IdGreaterThan(String value) {
            addCriterion("A01_ID >", value, "a01Id");
            return (Criteria) this;
        }

        public Criteria andA01IdGreaterThanOrEqualTo(String value) {
            addCriterion("A01_ID >=", value, "a01Id");
            return (Criteria) this;
        }

        public Criteria andA01IdLessThan(String value) {
            addCriterion("A01_ID <", value, "a01Id");
            return (Criteria) this;
        }

        public Criteria andA01IdLessThanOrEqualTo(String value) {
            addCriterion("A01_ID <=", value, "a01Id");
            return (Criteria) this;
        }

        public Criteria andA01IdLike(String value) {
            addCriterion("A01_ID like", value, "a01Id");
            return (Criteria) this;
        }

        public Criteria andA01IdNotLike(String value) {
            addCriterion("A01_ID not like", value, "a01Id");
            return (Criteria) this;
        }

        public Criteria andA01IdIn(List<String> values) {
            addCriterion("A01_ID in", values, "a01Id");
            return (Criteria) this;
        }

        public Criteria andA01IdNotIn(List<String> values) {
            addCriterion("A01_ID not in", values, "a01Id");
            return (Criteria) this;
        }

        public Criteria andA01IdBetween(String value1, String value2) {
            addCriterion("A01_ID between", value1, value2, "a01Id");
            return (Criteria) this;
        }

        public Criteria andA01IdNotBetween(String value1, String value2) {
            addCriterion("A01_ID not between", value1, value2, "a01Id");
            return (Criteria) this;
        }

        public Criteria andE01z701IsNull() {
            addCriterion("E01Z701 is null");
            return (Criteria) this;
        }

        public Criteria andE01z701IsNotNull() {
            addCriterion("E01Z701 is not null");
            return (Criteria) this;
        }

        public Criteria andE01z701EqualTo(String value) {
            addCriterion("E01Z701 =", value, "e01z701");
            return (Criteria) this;
        }

        public Criteria andE01z701NotEqualTo(String value) {
            addCriterion("E01Z701 <>", value, "e01z701");
            return (Criteria) this;
        }

        public Criteria andE01z701GreaterThan(String value) {
            addCriterion("E01Z701 >", value, "e01z701");
            return (Criteria) this;
        }

        public Criteria andE01z701GreaterThanOrEqualTo(String value) {
            addCriterion("E01Z701 >=", value, "e01z701");
            return (Criteria) this;
        }

        public Criteria andE01z701LessThan(String value) {
            addCriterion("E01Z701 <", value, "e01z701");
            return (Criteria) this;
        }

        public Criteria andE01z701LessThanOrEqualTo(String value) {
            addCriterion("E01Z701 <=", value, "e01z701");
            return (Criteria) this;
        }

        public Criteria andE01z701Like(String value) {
            addCriterion("E01Z701 like", value, "e01z701");
            return (Criteria) this;
        }

        public Criteria andE01z701NotLike(String value) {
            addCriterion("E01Z701 not like", value, "e01z701");
            return (Criteria) this;
        }

        public Criteria andE01z701In(List<String> values) {
            addCriterion("E01Z701 in", values, "e01z701");
            return (Criteria) this;
        }

        public Criteria andE01z701NotIn(List<String> values) {
            addCriterion("E01Z701 not in", values, "e01z701");
            return (Criteria) this;
        }

        public Criteria andE01z701Between(String value1, String value2) {
            addCriterion("E01Z701 between", value1, value2, "e01z701");
            return (Criteria) this;
        }

        public Criteria andE01z701NotBetween(String value1, String value2) {
            addCriterion("E01Z701 not between", value1, value2, "e01z701");
            return (Criteria) this;
        }

        public Criteria andE01z704aIsNull() {
            addCriterion("E01Z704A is null");
            return (Criteria) this;
        }

        public Criteria andE01z704aIsNotNull() {
            addCriterion("E01Z704A is not null");
            return (Criteria) this;
        }

        public Criteria andE01z704aEqualTo(String value) {
            addCriterion("E01Z704A =", value, "e01z704a");
            return (Criteria) this;
        }

        public Criteria andE01z704aNotEqualTo(String value) {
            addCriterion("E01Z704A <>", value, "e01z704a");
            return (Criteria) this;
        }

        public Criteria andE01z704aGreaterThan(String value) {
            addCriterion("E01Z704A >", value, "e01z704a");
            return (Criteria) this;
        }

        public Criteria andE01z704aGreaterThanOrEqualTo(String value) {
            addCriterion("E01Z704A >=", value, "e01z704a");
            return (Criteria) this;
        }

        public Criteria andE01z704aLessThan(String value) {
            addCriterion("E01Z704A <", value, "e01z704a");
            return (Criteria) this;
        }

        public Criteria andE01z704aLessThanOrEqualTo(String value) {
            addCriterion("E01Z704A <=", value, "e01z704a");
            return (Criteria) this;
        }

        public Criteria andE01z704aLike(String value) {
            addCriterion("E01Z704A like", value, "e01z704a");
            return (Criteria) this;
        }

        public Criteria andE01z704aNotLike(String value) {
            addCriterion("E01Z704A not like", value, "e01z704a");
            return (Criteria) this;
        }

        public Criteria andE01z704aIn(List<String> values) {
            addCriterion("E01Z704A in", values, "e01z704a");
            return (Criteria) this;
        }

        public Criteria andE01z704aNotIn(List<String> values) {
            addCriterion("E01Z704A not in", values, "e01z704a");
            return (Criteria) this;
        }

        public Criteria andE01z704aBetween(String value1, String value2) {
            addCriterion("E01Z704A between", value1, value2, "e01z704a");
            return (Criteria) this;
        }

        public Criteria andE01z704aNotBetween(String value1, String value2) {
            addCriterion("E01Z704A not between", value1, value2, "e01z704a");
            return (Criteria) this;
        }

        public Criteria andE01z704bIsNull() {
            addCriterion("E01Z704B is null");
            return (Criteria) this;
        }

        public Criteria andE01z704bIsNotNull() {
            addCriterion("E01Z704B is not null");
            return (Criteria) this;
        }

        public Criteria andE01z704bEqualTo(String value) {
            addCriterion("E01Z704B =", value, "e01z704b");
            return (Criteria) this;
        }

        public Criteria andE01z704bNotEqualTo(String value) {
            addCriterion("E01Z704B <>", value, "e01z704b");
            return (Criteria) this;
        }

        public Criteria andE01z704bGreaterThan(String value) {
            addCriterion("E01Z704B >", value, "e01z704b");
            return (Criteria) this;
        }

        public Criteria andE01z704bGreaterThanOrEqualTo(String value) {
            addCriterion("E01Z704B >=", value, "e01z704b");
            return (Criteria) this;
        }

        public Criteria andE01z704bLessThan(String value) {
            addCriterion("E01Z704B <", value, "e01z704b");
            return (Criteria) this;
        }

        public Criteria andE01z704bLessThanOrEqualTo(String value) {
            addCriterion("E01Z704B <=", value, "e01z704b");
            return (Criteria) this;
        }

        public Criteria andE01z704bLike(String value) {
            addCriterion("E01Z704B like", value, "e01z704b");
            return (Criteria) this;
        }

        public Criteria andE01z704bNotLike(String value) {
            addCriterion("E01Z704B not like", value, "e01z704b");
            return (Criteria) this;
        }

        public Criteria andE01z704bIn(List<String> values) {
            addCriterion("E01Z704B in", values, "e01z704b");
            return (Criteria) this;
        }

        public Criteria andE01z704bNotIn(List<String> values) {
            addCriterion("E01Z704B not in", values, "e01z704b");
            return (Criteria) this;
        }

        public Criteria andE01z704bBetween(String value1, String value2) {
            addCriterion("E01Z704B between", value1, value2, "e01z704b");
            return (Criteria) this;
        }

        public Criteria andE01z704bNotBetween(String value1, String value2) {
            addCriterion("E01Z704B not between", value1, value2, "e01z704b");
            return (Criteria) this;
        }

        public Criteria andE01z707IsNull() {
            addCriterion("E01Z707 is null");
            return (Criteria) this;
        }

        public Criteria andE01z707IsNotNull() {
            addCriterion("E01Z707 is not null");
            return (Criteria) this;
        }

        public Criteria andE01z707EqualTo(String value) {
            addCriterion("E01Z707 =", value, "e01z707");
            return (Criteria) this;
        }

        public Criteria andE01z707NotEqualTo(String value) {
            addCriterion("E01Z707 <>", value, "e01z707");
            return (Criteria) this;
        }

        public Criteria andE01z707GreaterThan(String value) {
            addCriterion("E01Z707 >", value, "e01z707");
            return (Criteria) this;
        }

        public Criteria andE01z707GreaterThanOrEqualTo(String value) {
            addCriterion("E01Z707 >=", value, "e01z707");
            return (Criteria) this;
        }

        public Criteria andE01z707LessThan(String value) {
            addCriterion("E01Z707 <", value, "e01z707");
            return (Criteria) this;
        }

        public Criteria andE01z707LessThanOrEqualTo(String value) {
            addCriterion("E01Z707 <=", value, "e01z707");
            return (Criteria) this;
        }

        public Criteria andE01z707Like(String value) {
            addCriterion("E01Z707 like", value, "e01z707");
            return (Criteria) this;
        }

        public Criteria andE01z707NotLike(String value) {
            addCriterion("E01Z707 not like", value, "e01z707");
            return (Criteria) this;
        }

        public Criteria andE01z707In(List<String> values) {
            addCriterion("E01Z707 in", values, "e01z707");
            return (Criteria) this;
        }

        public Criteria andE01z707NotIn(List<String> values) {
            addCriterion("E01Z707 not in", values, "e01z707");
            return (Criteria) this;
        }

        public Criteria andE01z707Between(String value1, String value2) {
            addCriterion("E01Z707 between", value1, value2, "e01z707");
            return (Criteria) this;
        }

        public Criteria andE01z707NotBetween(String value1, String value2) {
            addCriterion("E01Z707 not between", value1, value2, "e01z707");
            return (Criteria) this;
        }

        public Criteria andE01z711IsNull() {
            addCriterion("E01Z711 is null");
            return (Criteria) this;
        }

        public Criteria andE01z711IsNotNull() {
            addCriterion("E01Z711 is not null");
            return (Criteria) this;
        }

        public Criteria andE01z711EqualTo(Integer value) {
            addCriterion("E01Z711 =", value, "e01z711");
            return (Criteria) this;
        }

        public Criteria andE01z711NotEqualTo(Integer value) {
            addCriterion("E01Z711 <>", value, "e01z711");
            return (Criteria) this;
        }

        public Criteria andE01z711GreaterThan(Integer value) {
            addCriterion("E01Z711 >", value, "e01z711");
            return (Criteria) this;
        }

        public Criteria andE01z711GreaterThanOrEqualTo(Integer value) {
            addCriterion("E01Z711 >=", value, "e01z711");
            return (Criteria) this;
        }

        public Criteria andE01z711LessThan(Integer value) {
            addCriterion("E01Z711 <", value, "e01z711");
            return (Criteria) this;
        }

        public Criteria andE01z711LessThanOrEqualTo(Integer value) {
            addCriterion("E01Z711 <=", value, "e01z711");
            return (Criteria) this;
        }

        public Criteria andE01z711In(List<Integer> values) {
            addCriterion("E01Z711 in", values, "e01z711");
            return (Criteria) this;
        }

        public Criteria andE01z711NotIn(List<Integer> values) {
            addCriterion("E01Z711 not in", values, "e01z711");
            return (Criteria) this;
        }

        public Criteria andE01z711Between(Integer value1, Integer value2) {
            addCriterion("E01Z711 between", value1, value2, "e01z711");
            return (Criteria) this;
        }

        public Criteria andE01z711NotBetween(Integer value1, Integer value2) {
            addCriterion("E01Z711 not between", value1, value2, "e01z711");
            return (Criteria) this;
        }

        public Criteria andE01z714IsNull() {
            addCriterion("E01Z714 is null");
            return (Criteria) this;
        }

        public Criteria andE01z714IsNotNull() {
            addCriterion("E01Z714 is not null");
            return (Criteria) this;
        }

        public Criteria andE01z714EqualTo(Integer value) {
            addCriterion("E01Z714 =", value, "e01z714");
            return (Criteria) this;
        }

        public Criteria andE01z714NotEqualTo(Integer value) {
            addCriterion("E01Z714 <>", value, "e01z714");
            return (Criteria) this;
        }

        public Criteria andE01z714GreaterThan(Integer value) {
            addCriterion("E01Z714 >", value, "e01z714");
            return (Criteria) this;
        }

        public Criteria andE01z714GreaterThanOrEqualTo(Integer value) {
            addCriterion("E01Z714 >=", value, "e01z714");
            return (Criteria) this;
        }

        public Criteria andE01z714LessThan(Integer value) {
            addCriterion("E01Z714 <", value, "e01z714");
            return (Criteria) this;
        }

        public Criteria andE01z714LessThanOrEqualTo(Integer value) {
            addCriterion("E01Z714 <=", value, "e01z714");
            return (Criteria) this;
        }

        public Criteria andE01z714In(List<Integer> values) {
            addCriterion("E01Z714 in", values, "e01z714");
            return (Criteria) this;
        }

        public Criteria andE01z714NotIn(List<Integer> values) {
            addCriterion("E01Z714 not in", values, "e01z714");
            return (Criteria) this;
        }

        public Criteria andE01z714Between(Integer value1, Integer value2) {
            addCriterion("E01Z714 between", value1, value2, "e01z714");
            return (Criteria) this;
        }

        public Criteria andE01z714NotBetween(Integer value1, Integer value2) {
            addCriterion("E01Z714 not between", value1, value2, "e01z714");
            return (Criteria) this;
        }

        public Criteria andE01z717IsNull() {
            addCriterion("E01Z717 is null");
            return (Criteria) this;
        }

        public Criteria andE01z717IsNotNull() {
            addCriterion("E01Z717 is not null");
            return (Criteria) this;
        }

        public Criteria andE01z717EqualTo(String value) {
            addCriterion("E01Z717 =", value, "e01z717");
            return (Criteria) this;
        }

        public Criteria andE01z717NotEqualTo(String value) {
            addCriterion("E01Z717 <>", value, "e01z717");
            return (Criteria) this;
        }

        public Criteria andE01z717GreaterThan(String value) {
            addCriterion("E01Z717 >", value, "e01z717");
            return (Criteria) this;
        }

        public Criteria andE01z717GreaterThanOrEqualTo(String value) {
            addCriterion("E01Z717 >=", value, "e01z717");
            return (Criteria) this;
        }

        public Criteria andE01z717LessThan(String value) {
            addCriterion("E01Z717 <", value, "e01z717");
            return (Criteria) this;
        }

        public Criteria andE01z717LessThanOrEqualTo(String value) {
            addCriterion("E01Z717 <=", value, "e01z717");
            return (Criteria) this;
        }

        public Criteria andE01z717Like(String value) {
            addCriterion("E01Z717 like", value, "e01z717");
            return (Criteria) this;
        }

        public Criteria andE01z717NotLike(String value) {
            addCriterion("E01Z717 not like", value, "e01z717");
            return (Criteria) this;
        }

        public Criteria andE01z717In(List<String> values) {
            addCriterion("E01Z717 in", values, "e01z717");
            return (Criteria) this;
        }

        public Criteria andE01z717NotIn(List<String> values) {
            addCriterion("E01Z717 not in", values, "e01z717");
            return (Criteria) this;
        }

        public Criteria andE01z717Between(String value1, String value2) {
            addCriterion("E01Z717 between", value1, value2, "e01z717");
            return (Criteria) this;
        }

        public Criteria andE01z717NotBetween(String value1, String value2) {
            addCriterion("E01Z717 not between", value1, value2, "e01z717");
            return (Criteria) this;
        }

        public Criteria andE01z721IsNull() {
            addCriterion("E01Z721 is null");
            return (Criteria) this;
        }

        public Criteria andE01z721IsNotNull() {
            addCriterion("E01Z721 is not null");
            return (Criteria) this;
        }

        public Criteria andE01z721EqualTo(String value) {
            addCriterion("E01Z721 =", value, "e01z721");
            return (Criteria) this;
        }

        public Criteria andE01z721NotEqualTo(String value) {
            addCriterion("E01Z721 <>", value, "e01z721");
            return (Criteria) this;
        }

        public Criteria andE01z721GreaterThan(String value) {
            addCriterion("E01Z721 >", value, "e01z721");
            return (Criteria) this;
        }

        public Criteria andE01z721GreaterThanOrEqualTo(String value) {
            addCriterion("E01Z721 >=", value, "e01z721");
            return (Criteria) this;
        }

        public Criteria andE01z721LessThan(String value) {
            addCriterion("E01Z721 <", value, "e01z721");
            return (Criteria) this;
        }

        public Criteria andE01z721LessThanOrEqualTo(String value) {
            addCriterion("E01Z721 <=", value, "e01z721");
            return (Criteria) this;
        }

        public Criteria andE01z721Like(String value) {
            addCriterion("E01Z721 like", value, "e01z721");
            return (Criteria) this;
        }

        public Criteria andE01z721NotLike(String value) {
            addCriterion("E01Z721 not like", value, "e01z721");
            return (Criteria) this;
        }

        public Criteria andE01z721In(List<String> values) {
            addCriterion("E01Z721 in", values, "e01z721");
            return (Criteria) this;
        }

        public Criteria andE01z721NotIn(List<String> values) {
            addCriterion("E01Z721 not in", values, "e01z721");
            return (Criteria) this;
        }

        public Criteria andE01z721Between(String value1, String value2) {
            addCriterion("E01Z721 between", value1, value2, "e01z721");
            return (Criteria) this;
        }

        public Criteria andE01z721NotBetween(String value1, String value2) {
            addCriterion("E01Z721 not between", value1, value2, "e01z721");
            return (Criteria) this;
        }

        public Criteria andE01z724IsNull() {
            addCriterion("E01Z724 is null");
            return (Criteria) this;
        }

        public Criteria andE01z724IsNotNull() {
            addCriterion("E01Z724 is not null");
            return (Criteria) this;
        }

        public Criteria andE01z724EqualTo(String value) {
            addCriterion("E01Z724 =", value, "e01z724");
            return (Criteria) this;
        }

        public Criteria andE01z724NotEqualTo(String value) {
            addCriterion("E01Z724 <>", value, "e01z724");
            return (Criteria) this;
        }

        public Criteria andE01z724GreaterThan(String value) {
            addCriterion("E01Z724 >", value, "e01z724");
            return (Criteria) this;
        }

        public Criteria andE01z724GreaterThanOrEqualTo(String value) {
            addCriterion("E01Z724 >=", value, "e01z724");
            return (Criteria) this;
        }

        public Criteria andE01z724LessThan(String value) {
            addCriterion("E01Z724 <", value, "e01z724");
            return (Criteria) this;
        }

        public Criteria andE01z724LessThanOrEqualTo(String value) {
            addCriterion("E01Z724 <=", value, "e01z724");
            return (Criteria) this;
        }

        public Criteria andE01z724Like(String value) {
            addCriterion("E01Z724 like", value, "e01z724");
            return (Criteria) this;
        }

        public Criteria andE01z724NotLike(String value) {
            addCriterion("E01Z724 not like", value, "e01z724");
            return (Criteria) this;
        }

        public Criteria andE01z724In(List<String> values) {
            addCriterion("E01Z724 in", values, "e01z724");
            return (Criteria) this;
        }

        public Criteria andE01z724NotIn(List<String> values) {
            addCriterion("E01Z724 not in", values, "e01z724");
            return (Criteria) this;
        }

        public Criteria andE01z724Between(String value1, String value2) {
            addCriterion("E01Z724 between", value1, value2, "e01z724");
            return (Criteria) this;
        }

        public Criteria andE01z724NotBetween(String value1, String value2) {
            addCriterion("E01Z724 not between", value1, value2, "e01z724");
            return (Criteria) this;
        }

        public Criteria andE01z727IsNull() {
            addCriterion("E01Z727 is null");
            return (Criteria) this;
        }

        public Criteria andE01z727IsNotNull() {
            addCriterion("E01Z727 is not null");
            return (Criteria) this;
        }

        public Criteria andE01z727EqualTo(String value) {
            addCriterion("E01Z727 =", value, "e01z727");
            return (Criteria) this;
        }

        public Criteria andE01z727NotEqualTo(String value) {
            addCriterion("E01Z727 <>", value, "e01z727");
            return (Criteria) this;
        }

        public Criteria andE01z727GreaterThan(String value) {
            addCriterion("E01Z727 >", value, "e01z727");
            return (Criteria) this;
        }

        public Criteria andE01z727GreaterThanOrEqualTo(String value) {
            addCriterion("E01Z727 >=", value, "e01z727");
            return (Criteria) this;
        }

        public Criteria andE01z727LessThan(String value) {
            addCriterion("E01Z727 <", value, "e01z727");
            return (Criteria) this;
        }

        public Criteria andE01z727LessThanOrEqualTo(String value) {
            addCriterion("E01Z727 <=", value, "e01z727");
            return (Criteria) this;
        }

        public Criteria andE01z727Like(String value) {
            addCriterion("E01Z727 like", value, "e01z727");
            return (Criteria) this;
        }

        public Criteria andE01z727NotLike(String value) {
            addCriterion("E01Z727 not like", value, "e01z727");
            return (Criteria) this;
        }

        public Criteria andE01z727In(List<String> values) {
            addCriterion("E01Z727 in", values, "e01z727");
            return (Criteria) this;
        }

        public Criteria andE01z727NotIn(List<String> values) {
            addCriterion("E01Z727 not in", values, "e01z727");
            return (Criteria) this;
        }

        public Criteria andE01z727Between(String value1, String value2) {
            addCriterion("E01Z727 between", value1, value2, "e01z727");
            return (Criteria) this;
        }

        public Criteria andE01z727NotBetween(String value1, String value2) {
            addCriterion("E01Z727 not between", value1, value2, "e01z727");
            return (Criteria) this;
        }

        public Criteria andE01z731IsNull() {
            addCriterion("E01Z731 is null");
            return (Criteria) this;
        }

        public Criteria andE01z731IsNotNull() {
            addCriterion("E01Z731 is not null");
            return (Criteria) this;
        }

        public Criteria andE01z731EqualTo(String value) {
            addCriterion("E01Z731 =", value, "e01z731");
            return (Criteria) this;
        }

        public Criteria andE01z731NotEqualTo(String value) {
            addCriterion("E01Z731 <>", value, "e01z731");
            return (Criteria) this;
        }

        public Criteria andE01z731GreaterThan(String value) {
            addCriterion("E01Z731 >", value, "e01z731");
            return (Criteria) this;
        }

        public Criteria andE01z731GreaterThanOrEqualTo(String value) {
            addCriterion("E01Z731 >=", value, "e01z731");
            return (Criteria) this;
        }

        public Criteria andE01z731LessThan(String value) {
            addCriterion("E01Z731 <", value, "e01z731");
            return (Criteria) this;
        }

        public Criteria andE01z731LessThanOrEqualTo(String value) {
            addCriterion("E01Z731 <=", value, "e01z731");
            return (Criteria) this;
        }

        public Criteria andE01z731Like(String value) {
            addCriterion("E01Z731 like", value, "e01z731");
            return (Criteria) this;
        }

        public Criteria andE01z731NotLike(String value) {
            addCriterion("E01Z731 not like", value, "e01z731");
            return (Criteria) this;
        }

        public Criteria andE01z731In(List<String> values) {
            addCriterion("E01Z731 in", values, "e01z731");
            return (Criteria) this;
        }

        public Criteria andE01z731NotIn(List<String> values) {
            addCriterion("E01Z731 not in", values, "e01z731");
            return (Criteria) this;
        }

        public Criteria andE01z731Between(String value1, String value2) {
            addCriterion("E01Z731 between", value1, value2, "e01z731");
            return (Criteria) this;
        }

        public Criteria andE01z731NotBetween(String value1, String value2) {
            addCriterion("E01Z731 not between", value1, value2, "e01z731");
            return (Criteria) this;
        }

        public Criteria andMatSumIsNull() {
            addCriterion("MAT_SUM is null");
            return (Criteria) this;
        }

        public Criteria andMatSumIsNotNull() {
            addCriterion("MAT_SUM is not null");
            return (Criteria) this;
        }

        public Criteria andMatSumEqualTo(Integer value) {
            addCriterion("MAT_SUM =", value, "matSum");
            return (Criteria) this;
        }

        public Criteria andMatSumNotEqualTo(Integer value) {
            addCriterion("MAT_SUM <>", value, "matSum");
            return (Criteria) this;
        }

        public Criteria andMatSumGreaterThan(Integer value) {
            addCriterion("MAT_SUM >", value, "matSum");
            return (Criteria) this;
        }

        public Criteria andMatSumGreaterThanOrEqualTo(Integer value) {
            addCriterion("MAT_SUM >=", value, "matSum");
            return (Criteria) this;
        }

        public Criteria andMatSumLessThan(Integer value) {
            addCriterion("MAT_SUM <", value, "matSum");
            return (Criteria) this;
        }

        public Criteria andMatSumLessThanOrEqualTo(Integer value) {
            addCriterion("MAT_SUM <=", value, "matSum");
            return (Criteria) this;
        }

        public Criteria andMatSumIn(List<Integer> values) {
            addCriterion("MAT_SUM in", values, "matSum");
            return (Criteria) this;
        }

        public Criteria andMatSumNotIn(List<Integer> values) {
            addCriterion("MAT_SUM not in", values, "matSum");
            return (Criteria) this;
        }

        public Criteria andMatSumBetween(Integer value1, Integer value2) {
            addCriterion("MAT_SUM between", value1, value2, "matSum");
            return (Criteria) this;
        }

        public Criteria andMatSumNotBetween(Integer value1, Integer value2) {
            addCriterion("MAT_SUM not between", value1, value2, "matSum");
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
