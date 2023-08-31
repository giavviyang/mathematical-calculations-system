package com.fudian.project.ams.pojo;

import java.util.ArrayList;
import java.util.List;

public class TransferBillExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TransferBillExample() {
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

        public Criteria andTransBillIndexIsNull() {
            addCriterion("trans_bill_index is null");
            return (Criteria) this;
        }

        public Criteria andTransBillIndexIsNotNull() {
            addCriterion("trans_bill_index is not null");
            return (Criteria) this;
        }

        public Criteria andTransBillIndexEqualTo(Integer value) {
            addCriterion("trans_bill_index =", value, "transBillIndex");
            return (Criteria) this;
        }

        public Criteria andTransBillIndexNotEqualTo(Integer value) {
            addCriterion("trans_bill_index <>", value, "transBillIndex");
            return (Criteria) this;
        }

        public Criteria andTransBillIndexGreaterThan(Integer value) {
            addCriterion("trans_bill_index >", value, "transBillIndex");
            return (Criteria) this;
        }

        public Criteria andTransBillIndexGreaterThanOrEqualTo(Integer value) {
            addCriterion("trans_bill_index >=", value, "transBillIndex");
            return (Criteria) this;
        }

        public Criteria andTransBillIndexLessThan(Integer value) {
            addCriterion("trans_bill_index <", value, "transBillIndex");
            return (Criteria) this;
        }

        public Criteria andTransBillIndexLessThanOrEqualTo(Integer value) {
            addCriterion("trans_bill_index <=", value, "transBillIndex");
            return (Criteria) this;
        }

        public Criteria andTransBillIndexIn(List<Integer> values) {
            addCriterion("trans_bill_index in", values, "transBillIndex");
            return (Criteria) this;
        }

        public Criteria andTransBillIndexNotIn(List<Integer> values) {
            addCriterion("trans_bill_index not in", values, "transBillIndex");
            return (Criteria) this;
        }

        public Criteria andTransBillIndexBetween(Integer value1, Integer value2) {
            addCriterion("trans_bill_index between", value1, value2, "transBillIndex");
            return (Criteria) this;
        }

        public Criteria andTransBillIndexNotBetween(Integer value1, Integer value2) {
            addCriterion("trans_bill_index not between", value1, value2, "transBillIndex");
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

        public Criteria andOrgNameIsNull() {
            addCriterion("org_name is null");
            return (Criteria) this;
        }

        public Criteria andOrgNameIsNotNull() {
            addCriterion("org_name is not null");
            return (Criteria) this;
        }

        public Criteria andOrgNameEqualTo(String value) {
            addCriterion("org_name =", value, "orgName");
            return (Criteria) this;
        }

        public Criteria andOrgNameNotEqualTo(String value) {
            addCriterion("org_name <>", value, "orgName");
            return (Criteria) this;
        }

        public Criteria andOrgNameGreaterThan(String value) {
            addCriterion("org_name >", value, "orgName");
            return (Criteria) this;
        }

        public Criteria andOrgNameGreaterThanOrEqualTo(String value) {
            addCriterion("org_name >=", value, "orgName");
            return (Criteria) this;
        }

        public Criteria andOrgNameLessThan(String value) {
            addCriterion("org_name <", value, "orgName");
            return (Criteria) this;
        }

        public Criteria andOrgNameLessThanOrEqualTo(String value) {
            addCriterion("org_name <=", value, "orgName");
            return (Criteria) this;
        }

        public Criteria andOrgNameLike(String value) {
            addCriterion("org_name like", value, "orgName");
            return (Criteria) this;
        }

        public Criteria andOrgNameNotLike(String value) {
            addCriterion("org_name not like", value, "orgName");
            return (Criteria) this;
        }

        public Criteria andOrgNameIn(List<String> values) {
            addCriterion("org_name in", values, "orgName");
            return (Criteria) this;
        }

        public Criteria andOrgNameNotIn(List<String> values) {
            addCriterion("org_name not in", values, "orgName");
            return (Criteria) this;
        }

        public Criteria andOrgNameBetween(String value1, String value2) {
            addCriterion("org_name between", value1, value2, "orgName");
            return (Criteria) this;
        }

        public Criteria andOrgNameNotBetween(String value1, String value2) {
            addCriterion("org_name not between", value1, value2, "orgName");
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

        public Criteria andE01z721IsNull() {
            addCriterion("e01z721 is null");
            return (Criteria) this;
        }

        public Criteria andE01z721IsNotNull() {
            addCriterion("e01z721 is not null");
            return (Criteria) this;
        }

        public Criteria andE01z721EqualTo(String value) {
            addCriterion("e01z721 =", value, "e01z721");
            return (Criteria) this;
        }

        public Criteria andE01z721NotEqualTo(String value) {
            addCriterion("e01z721 <>", value, "e01z721");
            return (Criteria) this;
        }

        public Criteria andE01z721GreaterThan(String value) {
            addCriterion("e01z721 >", value, "e01z721");
            return (Criteria) this;
        }

        public Criteria andE01z721GreaterThanOrEqualTo(String value) {
            addCriterion("e01z721 >=", value, "e01z721");
            return (Criteria) this;
        }

        public Criteria andE01z721LessThan(String value) {
            addCriterion("e01z721 <", value, "e01z721");
            return (Criteria) this;
        }

        public Criteria andE01z721LessThanOrEqualTo(String value) {
            addCriterion("e01z721 <=", value, "e01z721");
            return (Criteria) this;
        }

        public Criteria andE01z721Like(String value) {
            addCriterion("e01z721 like", value, "e01z721");
            return (Criteria) this;
        }

        public Criteria andE01z721NotLike(String value) {
            addCriterion("e01z721 not like", value, "e01z721");
            return (Criteria) this;
        }

        public Criteria andE01z721In(List<String> values) {
            addCriterion("e01z721 in", values, "e01z721");
            return (Criteria) this;
        }

        public Criteria andE01z721NotIn(List<String> values) {
            addCriterion("e01z721 not in", values, "e01z721");
            return (Criteria) this;
        }

        public Criteria andE01z721Between(String value1, String value2) {
            addCriterion("e01z721 between", value1, value2, "e01z721");
            return (Criteria) this;
        }

        public Criteria andE01z721NotBetween(String value1, String value2) {
            addCriterion("e01z721 not between", value1, value2, "e01z721");
            return (Criteria) this;
        }

        public Criteria andE01z711IsNull() {
            addCriterion("e01z711 is null");
            return (Criteria) this;
        }

        public Criteria andE01z711IsNotNull() {
            addCriterion("e01z711 is not null");
            return (Criteria) this;
        }

        public Criteria andE01z711EqualTo(Integer value) {
            addCriterion("e01z711 =", value, "e01z711");
            return (Criteria) this;
        }

        public Criteria andE01z711NotEqualTo(Integer value) {
            addCriterion("e01z711 <>", value, "e01z711");
            return (Criteria) this;
        }

        public Criteria andE01z711GreaterThan(Integer value) {
            addCriterion("e01z711 >", value, "e01z711");
            return (Criteria) this;
        }

        public Criteria andE01z711GreaterThanOrEqualTo(Integer value) {
            addCriterion("e01z711 >=", value, "e01z711");
            return (Criteria) this;
        }

        public Criteria andE01z711LessThan(Integer value) {
            addCriterion("e01z711 <", value, "e01z711");
            return (Criteria) this;
        }

        public Criteria andE01z711LessThanOrEqualTo(Integer value) {
            addCriterion("e01z711 <=", value, "e01z711");
            return (Criteria) this;
        }

        public Criteria andE01z711In(List<Integer> values) {
            addCriterion("e01z711 in", values, "e01z711");
            return (Criteria) this;
        }

        public Criteria andE01z711NotIn(List<Integer> values) {
            addCriterion("e01z711 not in", values, "e01z711");
            return (Criteria) this;
        }

        public Criteria andE01z711Between(Integer value1, Integer value2) {
            addCriterion("e01z711 between", value1, value2, "e01z711");
            return (Criteria) this;
        }

        public Criteria andE01z711NotBetween(Integer value1, Integer value2) {
            addCriterion("e01z711 not between", value1, value2, "e01z711");
            return (Criteria) this;
        }

        public Criteria andE01z714IsNull() {
            addCriterion("e01z714 is null");
            return (Criteria) this;
        }

        public Criteria andE01z714IsNotNull() {
            addCriterion("e01z714 is not null");
            return (Criteria) this;
        }

        public Criteria andE01z714EqualTo(Integer value) {
            addCriterion("e01z714 =", value, "e01z714");
            return (Criteria) this;
        }

        public Criteria andE01z714NotEqualTo(Integer value) {
            addCriterion("e01z714 <>", value, "e01z714");
            return (Criteria) this;
        }

        public Criteria andE01z714GreaterThan(Integer value) {
            addCriterion("e01z714 >", value, "e01z714");
            return (Criteria) this;
        }

        public Criteria andE01z714GreaterThanOrEqualTo(Integer value) {
            addCriterion("e01z714 >=", value, "e01z714");
            return (Criteria) this;
        }

        public Criteria andE01z714LessThan(Integer value) {
            addCriterion("e01z714 <", value, "e01z714");
            return (Criteria) this;
        }

        public Criteria andE01z714LessThanOrEqualTo(Integer value) {
            addCriterion("e01z714 <=", value, "e01z714");
            return (Criteria) this;
        }

        public Criteria andE01z714In(List<Integer> values) {
            addCriterion("e01z714 in", values, "e01z714");
            return (Criteria) this;
        }

        public Criteria andE01z714NotIn(List<Integer> values) {
            addCriterion("e01z714 not in", values, "e01z714");
            return (Criteria) this;
        }

        public Criteria andE01z714Between(Integer value1, Integer value2) {
            addCriterion("e01z714 between", value1, value2, "e01z714");
            return (Criteria) this;
        }

        public Criteria andE01z714NotBetween(Integer value1, Integer value2) {
            addCriterion("e01z714 not between", value1, value2, "e01z714");
            return (Criteria) this;
        }

        public Criteria andMatNumIsNull() {
            addCriterion("mat_num is null");
            return (Criteria) this;
        }

        public Criteria andMatNumIsNotNull() {
            addCriterion("mat_num is not null");
            return (Criteria) this;
        }

        public Criteria andMatNumEqualTo(Integer value) {
            addCriterion("mat_num =", value, "matNum");
            return (Criteria) this;
        }

        public Criteria andMatNumNotEqualTo(Integer value) {
            addCriterion("mat_num <>", value, "matNum");
            return (Criteria) this;
        }

        public Criteria andMatNumGreaterThan(Integer value) {
            addCriterion("mat_num >", value, "matNum");
            return (Criteria) this;
        }

        public Criteria andMatNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("mat_num >=", value, "matNum");
            return (Criteria) this;
        }

        public Criteria andMatNumLessThan(Integer value) {
            addCriterion("mat_num <", value, "matNum");
            return (Criteria) this;
        }

        public Criteria andMatNumLessThanOrEqualTo(Integer value) {
            addCriterion("mat_num <=", value, "matNum");
            return (Criteria) this;
        }

        public Criteria andMatNumIn(List<Integer> values) {
            addCriterion("mat_num in", values, "matNum");
            return (Criteria) this;
        }

        public Criteria andMatNumNotIn(List<Integer> values) {
            addCriterion("mat_num not in", values, "matNum");
            return (Criteria) this;
        }

        public Criteria andMatNumBetween(Integer value1, Integer value2) {
            addCriterion("mat_num between", value1, value2, "matNum");
            return (Criteria) this;
        }

        public Criteria andMatNumNotBetween(Integer value1, Integer value2) {
            addCriterion("mat_num not between", value1, value2, "matNum");
            return (Criteria) this;
        }

        public Criteria andE01z731IsNull() {
            addCriterion("e01z731 is null");
            return (Criteria) this;
        }

        public Criteria andE01z731IsNotNull() {
            addCriterion("e01z731 is not null");
            return (Criteria) this;
        }

        public Criteria andE01z731EqualTo(String value) {
            addCriterion("e01z731 =", value, "e01z731");
            return (Criteria) this;
        }

        public Criteria andE01z731NotEqualTo(String value) {
            addCriterion("e01z731 <>", value, "e01z731");
            return (Criteria) this;
        }

        public Criteria andE01z731GreaterThan(String value) {
            addCriterion("e01z731 >", value, "e01z731");
            return (Criteria) this;
        }

        public Criteria andE01z731GreaterThanOrEqualTo(String value) {
            addCriterion("e01z731 >=", value, "e01z731");
            return (Criteria) this;
        }

        public Criteria andE01z731LessThan(String value) {
            addCriterion("e01z731 <", value, "e01z731");
            return (Criteria) this;
        }

        public Criteria andE01z731LessThanOrEqualTo(String value) {
            addCriterion("e01z731 <=", value, "e01z731");
            return (Criteria) this;
        }

        public Criteria andE01z731Like(String value) {
            addCriterion("e01z731 like", value, "e01z731");
            return (Criteria) this;
        }

        public Criteria andE01z731NotLike(String value) {
            addCriterion("e01z731 not like", value, "e01z731");
            return (Criteria) this;
        }

        public Criteria andE01z731In(List<String> values) {
            addCriterion("e01z731 in", values, "e01z731");
            return (Criteria) this;
        }

        public Criteria andE01z731NotIn(List<String> values) {
            addCriterion("e01z731 not in", values, "e01z731");
            return (Criteria) this;
        }

        public Criteria andE01z731Between(String value1, String value2) {
            addCriterion("e01z731 between", value1, value2, "e01z731");
            return (Criteria) this;
        }

        public Criteria andE01z731NotBetween(String value1, String value2) {
            addCriterion("e01z731 not between", value1, value2, "e01z731");
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
