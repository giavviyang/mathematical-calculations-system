package com.fudian.project.ams.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class E01z9Example {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public E01z9Example() {
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

        public Criteria andCardIdIsNull() {
            addCriterion("card_id is null");
            return (Criteria) this;
        }

        public Criteria andCardIdIsNotNull() {
            addCriterion("card_id is not null");
            return (Criteria) this;
        }

        public Criteria andCardIdEqualTo(String value) {
            addCriterion("card_id =", value, "cardId");
            return (Criteria) this;
        }

        public Criteria andCardIdNotEqualTo(String value) {
            addCriterion("card_id <>", value, "cardId");
            return (Criteria) this;
        }

        public Criteria andCardIdGreaterThan(String value) {
            addCriterion("card_id >", value, "cardId");
            return (Criteria) this;
        }

        public Criteria andCardIdGreaterThanOrEqualTo(String value) {
            addCriterion("card_id >=", value, "cardId");
            return (Criteria) this;
        }

        public Criteria andCardIdLessThan(String value) {
            addCriterion("card_id <", value, "cardId");
            return (Criteria) this;
        }

        public Criteria andCardIdLessThanOrEqualTo(String value) {
            addCriterion("card_id <=", value, "cardId");
            return (Criteria) this;
        }

        public Criteria andCardIdLike(String value) {
            addCriterion("card_id like", value, "cardId");
            return (Criteria) this;
        }

        public Criteria andCardIdNotLike(String value) {
            addCriterion("card_id not like", value, "cardId");
            return (Criteria) this;
        }

        public Criteria andCardIdIn(List<String> values) {
            addCriterion("card_id in", values, "cardId");
            return (Criteria) this;
        }

        public Criteria andCardIdNotIn(List<String> values) {
            addCriterion("card_id not in", values, "cardId");
            return (Criteria) this;
        }

        public Criteria andCardIdBetween(String value1, String value2) {
            addCriterion("card_id between", value1, value2, "cardId");
            return (Criteria) this;
        }

        public Criteria andCardIdNotBetween(String value1, String value2) {
            addCriterion("card_id not between", value1, value2, "cardId");
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

        public Criteria andWorkflowIsNull() {
            addCriterion("workflow is null");
            return (Criteria) this;
        }

        public Criteria andWorkflowIsNotNull() {
            addCriterion("workflow is not null");
            return (Criteria) this;
        }

        public Criteria andWorkflowEqualTo(String value) {
            addCriterion("workflow =", value, "workflow");
            return (Criteria) this;
        }

        public Criteria andWorkflowNotEqualTo(String value) {
            addCriterion("workflow <>", value, "workflow");
            return (Criteria) this;
        }

        public Criteria andWorkflowGreaterThan(String value) {
            addCriterion("workflow >", value, "workflow");
            return (Criteria) this;
        }

        public Criteria andWorkflowGreaterThanOrEqualTo(String value) {
            addCriterion("workflow >=", value, "workflow");
            return (Criteria) this;
        }

        public Criteria andWorkflowLessThan(String value) {
            addCriterion("workflow <", value, "workflow");
            return (Criteria) this;
        }

        public Criteria andWorkflowLessThanOrEqualTo(String value) {
            addCriterion("workflow <=", value, "workflow");
            return (Criteria) this;
        }

        public Criteria andWorkflowLike(String value) {
            addCriterion("workflow like", value, "workflow");
            return (Criteria) this;
        }

        public Criteria andWorkflowNotLike(String value) {
            addCriterion("workflow not like", value, "workflow");
            return (Criteria) this;
        }

        public Criteria andWorkflowIn(List<String> values) {
            addCriterion("workflow in", values, "workflow");
            return (Criteria) this;
        }

        public Criteria andWorkflowNotIn(List<String> values) {
            addCriterion("workflow not in", values, "workflow");
            return (Criteria) this;
        }

        public Criteria andWorkflowBetween(String value1, String value2) {
            addCriterion("workflow between", value1, value2, "workflow");
            return (Criteria) this;
        }

        public Criteria andWorkflowNotBetween(String value1, String value2) {
            addCriterion("workflow not between", value1, value2, "workflow");
            return (Criteria) this;
        }

        public Criteria andWorkflowIdIsNull() {
            addCriterion("workflow_id is null");
            return (Criteria) this;
        }

        public Criteria andWorkflowIdIsNotNull() {
            addCriterion("workflow_id is not null");
            return (Criteria) this;
        }

        public Criteria andWorkflowIdEqualTo(String value) {
            addCriterion("workflow_id =", value, "workflowId");
            return (Criteria) this;
        }

        public Criteria andWorkflowIdNotEqualTo(String value) {
            addCriterion("workflow_id <>", value, "workflowId");
            return (Criteria) this;
        }

        public Criteria andWorkflowIdGreaterThan(String value) {
            addCriterion("workflow_id >", value, "workflowId");
            return (Criteria) this;
        }

        public Criteria andWorkflowIdGreaterThanOrEqualTo(String value) {
            addCriterion("workflow_id >=", value, "workflowId");
            return (Criteria) this;
        }

        public Criteria andWorkflowIdLessThan(String value) {
            addCriterion("workflow_id <", value, "workflowId");
            return (Criteria) this;
        }

        public Criteria andWorkflowIdLessThanOrEqualTo(String value) {
            addCriterion("workflow_id <=", value, "workflowId");
            return (Criteria) this;
        }

        public Criteria andWorkflowIdLike(String value) {
            addCriterion("workflow_id like", value, "workflowId");
            return (Criteria) this;
        }

        public Criteria andWorkflowIdNotLike(String value) {
            addCriterion("workflow_id not like", value, "workflowId");
            return (Criteria) this;
        }

        public Criteria andWorkflowIdIn(List<String> values) {
            addCriterion("workflow_id in", values, "workflowId");
            return (Criteria) this;
        }

        public Criteria andWorkflowIdNotIn(List<String> values) {
            addCriterion("workflow_id not in", values, "workflowId");
            return (Criteria) this;
        }

        public Criteria andWorkflowIdBetween(String value1, String value2) {
            addCriterion("workflow_id between", value1, value2, "workflowId");
            return (Criteria) this;
        }

        public Criteria andWorkflowIdNotBetween(String value1, String value2) {
            addCriterion("workflow_id not between", value1, value2, "workflowId");
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

        public Criteria andPolitcyIsNull() {
            addCriterion("politcy is null");
            return (Criteria) this;
        }

        public Criteria andPolitcyIsNotNull() {
            addCriterion("politcy is not null");
            return (Criteria) this;
        }

        public Criteria andPolitcyEqualTo(String value) {
            addCriterion("politcy =", value, "politcy");
            return (Criteria) this;
        }

        public Criteria andPolitcyNotEqualTo(String value) {
            addCriterion("politcy <>", value, "politcy");
            return (Criteria) this;
        }

        public Criteria andPolitcyGreaterThan(String value) {
            addCriterion("politcy >", value, "politcy");
            return (Criteria) this;
        }

        public Criteria andPolitcyGreaterThanOrEqualTo(String value) {
            addCriterion("politcy >=", value, "politcy");
            return (Criteria) this;
        }

        public Criteria andPolitcyLessThan(String value) {
            addCriterion("politcy <", value, "politcy");
            return (Criteria) this;
        }

        public Criteria andPolitcyLessThanOrEqualTo(String value) {
            addCriterion("politcy <=", value, "politcy");
            return (Criteria) this;
        }

        public Criteria andPolitcyLike(String value) {
            addCriterion("politcy like", value, "politcy");
            return (Criteria) this;
        }

        public Criteria andPolitcyNotLike(String value) {
            addCriterion("politcy not like", value, "politcy");
            return (Criteria) this;
        }

        public Criteria andPolitcyIn(List<String> values) {
            addCriterion("politcy in", values, "politcy");
            return (Criteria) this;
        }

        public Criteria andPolitcyNotIn(List<String> values) {
            addCriterion("politcy not in", values, "politcy");
            return (Criteria) this;
        }

        public Criteria andPolitcyBetween(String value1, String value2) {
            addCriterion("politcy between", value1, value2, "politcy");
            return (Criteria) this;
        }

        public Criteria andPolitcyNotBetween(String value1, String value2) {
            addCriterion("politcy not between", value1, value2, "politcy");
            return (Criteria) this;
        }

        public Criteria andE01z801IsNull() {
            addCriterion("e01z801 is null");
            return (Criteria) this;
        }

        public Criteria andE01z801IsNotNull() {
            addCriterion("e01z801 is not null");
            return (Criteria) this;
        }

        public Criteria andE01z801EqualTo(String value) {
            addCriterion("e01z801 =", value, "e01z801");
            return (Criteria) this;
        }

        public Criteria andE01z801NotEqualTo(String value) {
            addCriterion("e01z801 <>", value, "e01z801");
            return (Criteria) this;
        }

        public Criteria andE01z801GreaterThan(String value) {
            addCriterion("e01z801 >", value, "e01z801");
            return (Criteria) this;
        }

        public Criteria andE01z801GreaterThanOrEqualTo(String value) {
            addCriterion("e01z801 >=", value, "e01z801");
            return (Criteria) this;
        }

        public Criteria andE01z801LessThan(String value) {
            addCriterion("e01z801 <", value, "e01z801");
            return (Criteria) this;
        }

        public Criteria andE01z801LessThanOrEqualTo(String value) {
            addCriterion("e01z801 <=", value, "e01z801");
            return (Criteria) this;
        }

        public Criteria andE01z801Like(String value) {
            addCriterion("e01z801 like", value, "e01z801");
            return (Criteria) this;
        }

        public Criteria andE01z801NotLike(String value) {
            addCriterion("e01z801 not like", value, "e01z801");
            return (Criteria) this;
        }

        public Criteria andE01z801In(List<String> values) {
            addCriterion("e01z801 in", values, "e01z801");
            return (Criteria) this;
        }

        public Criteria andE01z801NotIn(List<String> values) {
            addCriterion("e01z801 not in", values, "e01z801");
            return (Criteria) this;
        }

        public Criteria andE01z801Between(String value1, String value2) {
            addCriterion("e01z801 between", value1, value2, "e01z801");
            return (Criteria) this;
        }

        public Criteria andE01z801NotBetween(String value1, String value2) {
            addCriterion("e01z801 not between", value1, value2, "e01z801");
            return (Criteria) this;
        }

        public Criteria andE01z804IsNull() {
            addCriterion("e01z804 is null");
            return (Criteria) this;
        }

        public Criteria andE01z804IsNotNull() {
            addCriterion("e01z804 is not null");
            return (Criteria) this;
        }

        public Criteria andE01z804EqualTo(Date value) {
            addCriterion("e01z804 =", value, "e01z804");
            return (Criteria) this;
        }

        public Criteria andE01z804NotEqualTo(Date value) {
            addCriterion("e01z804 <>", value, "e01z804");
            return (Criteria) this;
        }

        public Criteria andE01z804GreaterThan(Date value) {
            addCriterion("e01z804 >", value, "e01z804");
            return (Criteria) this;
        }

        public Criteria andE01z804GreaterThanOrEqualTo(Date value) {
            addCriterion("e01z804 >=", value, "e01z804");
            return (Criteria) this;
        }

        public Criteria andE01z804LessThan(Date value) {
            addCriterion("e01z804 <", value, "e01z804");
            return (Criteria) this;
        }

        public Criteria andE01z804LessThanOrEqualTo(Date value) {
            addCriterion("e01z804 <=", value, "e01z804");
            return (Criteria) this;
        }

        public Criteria andE01z804In(List<Date> values) {
            addCriterion("e01z804 in", values, "e01z804");
            return (Criteria) this;
        }

        public Criteria andE01z804NotIn(List<Date> values) {
            addCriterion("e01z804 not in", values, "e01z804");
            return (Criteria) this;
        }

        public Criteria andE01z804Between(Date value1, Date value2) {
            addCriterion("e01z804 between", value1, value2, "e01z804");
            return (Criteria) this;
        }

        public Criteria andE01z804NotBetween(Date value1, Date value2) {
            addCriterion("e01z804 not between", value1, value2, "e01z804");
            return (Criteria) this;
        }

        public Criteria andE01z805IsNull() {
            addCriterion("e01z805 is null");
            return (Criteria) this;
        }

        public Criteria andE01z805IsNotNull() {
            addCriterion("e01z805 is not null");
            return (Criteria) this;
        }

        public Criteria andE01z805EqualTo(Date value) {
            addCriterion("e01z805 =", value, "e01z805");
            return (Criteria) this;
        }

        public Criteria andE01z805NotEqualTo(Date value) {
            addCriterion("e01z805 <>", value, "e01z805");
            return (Criteria) this;
        }

        public Criteria andE01z805GreaterThan(Date value) {
            addCriterion("e01z805 >", value, "e01z805");
            return (Criteria) this;
        }

        public Criteria andE01z805GreaterThanOrEqualTo(Date value) {
            addCriterion("e01z805 >=", value, "e01z805");
            return (Criteria) this;
        }

        public Criteria andE01z805LessThan(Date value) {
            addCriterion("e01z805 <", value, "e01z805");
            return (Criteria) this;
        }

        public Criteria andE01z805LessThanOrEqualTo(Date value) {
            addCriterion("e01z805 <=", value, "e01z805");
            return (Criteria) this;
        }

        public Criteria andE01z805In(List<Date> values) {
            addCriterion("e01z805 in", values, "e01z805");
            return (Criteria) this;
        }

        public Criteria andE01z805NotIn(List<Date> values) {
            addCriterion("e01z805 not in", values, "e01z805");
            return (Criteria) this;
        }

        public Criteria andE01z805Between(Date value1, Date value2) {
            addCriterion("e01z805 between", value1, value2, "e01z805");
            return (Criteria) this;
        }

        public Criteria andE01z805NotBetween(Date value1, Date value2) {
            addCriterion("e01z805 not between", value1, value2, "e01z805");
            return (Criteria) this;
        }

        public Criteria andE01z806IsNull() {
            addCriterion("e01z806 is null");
            return (Criteria) this;
        }

        public Criteria andE01z806IsNotNull() {
            addCriterion("e01z806 is not null");
            return (Criteria) this;
        }

        public Criteria andE01z806EqualTo(Date value) {
            addCriterion("e01z806 =", value, "e01z806");
            return (Criteria) this;
        }

        public Criteria andE01z806NotEqualTo(Date value) {
            addCriterion("e01z806 <>", value, "e01z806");
            return (Criteria) this;
        }

        public Criteria andE01z806GreaterThan(Date value) {
            addCriterion("e01z806 >", value, "e01z806");
            return (Criteria) this;
        }

        public Criteria andE01z806GreaterThanOrEqualTo(Date value) {
            addCriterion("e01z806 >=", value, "e01z806");
            return (Criteria) this;
        }

        public Criteria andE01z806LessThan(Date value) {
            addCriterion("e01z806 <", value, "e01z806");
            return (Criteria) this;
        }

        public Criteria andE01z806LessThanOrEqualTo(Date value) {
            addCriterion("e01z806 <=", value, "e01z806");
            return (Criteria) this;
        }

        public Criteria andE01z806In(List<Date> values) {
            addCriterion("e01z806 in", values, "e01z806");
            return (Criteria) this;
        }

        public Criteria andE01z806NotIn(List<Date> values) {
            addCriterion("e01z806 not in", values, "e01z806");
            return (Criteria) this;
        }

        public Criteria andE01z806Between(Date value1, Date value2) {
            addCriterion("e01z806 between", value1, value2, "e01z806");
            return (Criteria) this;
        }

        public Criteria andE01z806NotBetween(Date value1, Date value2) {
            addCriterion("e01z806 not between", value1, value2, "e01z806");
            return (Criteria) this;
        }

        public Criteria andE01z803IsNull() {
            addCriterion("e01z803 is null");
            return (Criteria) this;
        }

        public Criteria andE01z803IsNotNull() {
            addCriterion("e01z803 is not null");
            return (Criteria) this;
        }

        public Criteria andE01z803EqualTo(String value) {
            addCriterion("e01z803 =", value, "e01z803");
            return (Criteria) this;
        }

        public Criteria andE01z803NotEqualTo(String value) {
            addCriterion("e01z803 <>", value, "e01z803");
            return (Criteria) this;
        }

        public Criteria andE01z803GreaterThan(String value) {
            addCriterion("e01z803 >", value, "e01z803");
            return (Criteria) this;
        }

        public Criteria andE01z803GreaterThanOrEqualTo(String value) {
            addCriterion("e01z803 >=", value, "e01z803");
            return (Criteria) this;
        }

        public Criteria andE01z803LessThan(String value) {
            addCriterion("e01z803 <", value, "e01z803");
            return (Criteria) this;
        }

        public Criteria andE01z803LessThanOrEqualTo(String value) {
            addCriterion("e01z803 <=", value, "e01z803");
            return (Criteria) this;
        }

        public Criteria andE01z803Like(String value) {
            addCriterion("e01z803 like", value, "e01z803");
            return (Criteria) this;
        }

        public Criteria andE01z803NotLike(String value) {
            addCriterion("e01z803 not like", value, "e01z803");
            return (Criteria) this;
        }

        public Criteria andE01z803In(List<String> values) {
            addCriterion("e01z803 in", values, "e01z803");
            return (Criteria) this;
        }

        public Criteria andE01z803NotIn(List<String> values) {
            addCriterion("e01z803 not in", values, "e01z803");
            return (Criteria) this;
        }

        public Criteria andE01z803Between(String value1, String value2) {
            addCriterion("e01z803 between", value1, value2, "e01z803");
            return (Criteria) this;
        }

        public Criteria andE01z803NotBetween(String value1, String value2) {
            addCriterion("e01z803 not between", value1, value2, "e01z803");
            return (Criteria) this;
        }

        public Criteria andE01z817IsNull() {
            addCriterion("e01z817 is null");
            return (Criteria) this;
        }

        public Criteria andE01z817IsNotNull() {
            addCriterion("e01z817 is not null");
            return (Criteria) this;
        }

        public Criteria andE01z817EqualTo(String value) {
            addCriterion("e01z817 =", value, "e01z817");
            return (Criteria) this;
        }

        public Criteria andE01z817NotEqualTo(String value) {
            addCriterion("e01z817 <>", value, "e01z817");
            return (Criteria) this;
        }

        public Criteria andE01z817GreaterThan(String value) {
            addCriterion("e01z817 >", value, "e01z817");
            return (Criteria) this;
        }

        public Criteria andE01z817GreaterThanOrEqualTo(String value) {
            addCriterion("e01z817 >=", value, "e01z817");
            return (Criteria) this;
        }

        public Criteria andE01z817LessThan(String value) {
            addCriterion("e01z817 <", value, "e01z817");
            return (Criteria) this;
        }

        public Criteria andE01z817LessThanOrEqualTo(String value) {
            addCriterion("e01z817 <=", value, "e01z817");
            return (Criteria) this;
        }

        public Criteria andE01z817Like(String value) {
            addCriterion("e01z817 like", value, "e01z817");
            return (Criteria) this;
        }

        public Criteria andE01z817NotLike(String value) {
            addCriterion("e01z817 not like", value, "e01z817");
            return (Criteria) this;
        }

        public Criteria andE01z817In(List<String> values) {
            addCriterion("e01z817 in", values, "e01z817");
            return (Criteria) this;
        }

        public Criteria andE01z817NotIn(List<String> values) {
            addCriterion("e01z817 not in", values, "e01z817");
            return (Criteria) this;
        }

        public Criteria andE01z817Between(String value1, String value2) {
            addCriterion("e01z817 between", value1, value2, "e01z817");
            return (Criteria) this;
        }

        public Criteria andE01z817NotBetween(String value1, String value2) {
            addCriterion("e01z817 not between", value1, value2, "e01z817");
            return (Criteria) this;
        }

        public Criteria andE01z821IsNull() {
            addCriterion("e01z821 is null");
            return (Criteria) this;
        }

        public Criteria andE01z821IsNotNull() {
            addCriterion("e01z821 is not null");
            return (Criteria) this;
        }

        public Criteria andE01z821EqualTo(String value) {
            addCriterion("e01z821 =", value, "e01z821");
            return (Criteria) this;
        }

        public Criteria andE01z821NotEqualTo(String value) {
            addCriterion("e01z821 <>", value, "e01z821");
            return (Criteria) this;
        }

        public Criteria andE01z821GreaterThan(String value) {
            addCriterion("e01z821 >", value, "e01z821");
            return (Criteria) this;
        }

        public Criteria andE01z821GreaterThanOrEqualTo(String value) {
            addCriterion("e01z821 >=", value, "e01z821");
            return (Criteria) this;
        }

        public Criteria andE01z821LessThan(String value) {
            addCriterion("e01z821 <", value, "e01z821");
            return (Criteria) this;
        }

        public Criteria andE01z821LessThanOrEqualTo(String value) {
            addCriterion("e01z821 <=", value, "e01z821");
            return (Criteria) this;
        }

        public Criteria andE01z821Like(String value) {
            addCriterion("e01z821 like", value, "e01z821");
            return (Criteria) this;
        }

        public Criteria andE01z821NotLike(String value) {
            addCriterion("e01z821 not like", value, "e01z821");
            return (Criteria) this;
        }

        public Criteria andE01z821In(List<String> values) {
            addCriterion("e01z821 in", values, "e01z821");
            return (Criteria) this;
        }

        public Criteria andE01z821NotIn(List<String> values) {
            addCriterion("e01z821 not in", values, "e01z821");
            return (Criteria) this;
        }

        public Criteria andE01z821Between(String value1, String value2) {
            addCriterion("e01z821 between", value1, value2, "e01z821");
            return (Criteria) this;
        }

        public Criteria andE01z821NotBetween(String value1, String value2) {
            addCriterion("e01z821 not between", value1, value2, "e01z821");
            return (Criteria) this;
        }

        public Criteria andE01z824aIsNull() {
            addCriterion("e01z824a is null");
            return (Criteria) this;
        }

        public Criteria andE01z824aIsNotNull() {
            addCriterion("e01z824a is not null");
            return (Criteria) this;
        }

        public Criteria andE01z824aEqualTo(String value) {
            addCriterion("e01z824a =", value, "e01z824a");
            return (Criteria) this;
        }

        public Criteria andE01z824aNotEqualTo(String value) {
            addCriterion("e01z824a <>", value, "e01z824a");
            return (Criteria) this;
        }

        public Criteria andE01z824aGreaterThan(String value) {
            addCriterion("e01z824a >", value, "e01z824a");
            return (Criteria) this;
        }

        public Criteria andE01z824aGreaterThanOrEqualTo(String value) {
            addCriterion("e01z824a >=", value, "e01z824a");
            return (Criteria) this;
        }

        public Criteria andE01z824aLessThan(String value) {
            addCriterion("e01z824a <", value, "e01z824a");
            return (Criteria) this;
        }

        public Criteria andE01z824aLessThanOrEqualTo(String value) {
            addCriterion("e01z824a <=", value, "e01z824a");
            return (Criteria) this;
        }

        public Criteria andE01z824aLike(String value) {
            addCriterion("e01z824a like", value, "e01z824a");
            return (Criteria) this;
        }

        public Criteria andE01z824aNotLike(String value) {
            addCriterion("e01z824a not like", value, "e01z824a");
            return (Criteria) this;
        }

        public Criteria andE01z824aIn(List<String> values) {
            addCriterion("e01z824a in", values, "e01z824a");
            return (Criteria) this;
        }

        public Criteria andE01z824aNotIn(List<String> values) {
            addCriterion("e01z824a not in", values, "e01z824a");
            return (Criteria) this;
        }

        public Criteria andE01z824aBetween(String value1, String value2) {
            addCriterion("e01z824a between", value1, value2, "e01z824a");
            return (Criteria) this;
        }

        public Criteria andE01z824aNotBetween(String value1, String value2) {
            addCriterion("e01z824a not between", value1, value2, "e01z824a");
            return (Criteria) this;
        }

        public Criteria andE01z824bIsNull() {
            addCriterion("e01z824b is null");
            return (Criteria) this;
        }

        public Criteria andE01z824bIsNotNull() {
            addCriterion("e01z824b is not null");
            return (Criteria) this;
        }

        public Criteria andE01z824bEqualTo(String value) {
            addCriterion("e01z824b =", value, "e01z824b");
            return (Criteria) this;
        }

        public Criteria andE01z824bNotEqualTo(String value) {
            addCriterion("e01z824b <>", value, "e01z824b");
            return (Criteria) this;
        }

        public Criteria andE01z824bGreaterThan(String value) {
            addCriterion("e01z824b >", value, "e01z824b");
            return (Criteria) this;
        }

        public Criteria andE01z824bGreaterThanOrEqualTo(String value) {
            addCriterion("e01z824b >=", value, "e01z824b");
            return (Criteria) this;
        }

        public Criteria andE01z824bLessThan(String value) {
            addCriterion("e01z824b <", value, "e01z824b");
            return (Criteria) this;
        }

        public Criteria andE01z824bLessThanOrEqualTo(String value) {
            addCriterion("e01z824b <=", value, "e01z824b");
            return (Criteria) this;
        }

        public Criteria andE01z824bLike(String value) {
            addCriterion("e01z824b like", value, "e01z824b");
            return (Criteria) this;
        }

        public Criteria andE01z824bNotLike(String value) {
            addCriterion("e01z824b not like", value, "e01z824b");
            return (Criteria) this;
        }

        public Criteria andE01z824bIn(List<String> values) {
            addCriterion("e01z824b in", values, "e01z824b");
            return (Criteria) this;
        }

        public Criteria andE01z824bNotIn(List<String> values) {
            addCriterion("e01z824b not in", values, "e01z824b");
            return (Criteria) this;
        }

        public Criteria andE01z824bBetween(String value1, String value2) {
            addCriterion("e01z824b between", value1, value2, "e01z824b");
            return (Criteria) this;
        }

        public Criteria andE01z824bNotBetween(String value1, String value2) {
            addCriterion("e01z824b not between", value1, value2, "e01z824b");
            return (Criteria) this;
        }

        public Criteria andE01z831IsNull() {
            addCriterion("e01z831 is null");
            return (Criteria) this;
        }

        public Criteria andE01z831IsNotNull() {
            addCriterion("e01z831 is not null");
            return (Criteria) this;
        }

        public Criteria andE01z831EqualTo(String value) {
            addCriterion("e01z831 =", value, "e01z831");
            return (Criteria) this;
        }

        public Criteria andE01z831NotEqualTo(String value) {
            addCriterion("e01z831 <>", value, "e01z831");
            return (Criteria) this;
        }

        public Criteria andE01z831GreaterThan(String value) {
            addCriterion("e01z831 >", value, "e01z831");
            return (Criteria) this;
        }

        public Criteria andE01z831GreaterThanOrEqualTo(String value) {
            addCriterion("e01z831 >=", value, "e01z831");
            return (Criteria) this;
        }

        public Criteria andE01z831LessThan(String value) {
            addCriterion("e01z831 <", value, "e01z831");
            return (Criteria) this;
        }

        public Criteria andE01z831LessThanOrEqualTo(String value) {
            addCriterion("e01z831 <=", value, "e01z831");
            return (Criteria) this;
        }

        public Criteria andE01z831Like(String value) {
            addCriterion("e01z831 like", value, "e01z831");
            return (Criteria) this;
        }

        public Criteria andE01z831NotLike(String value) {
            addCriterion("e01z831 not like", value, "e01z831");
            return (Criteria) this;
        }

        public Criteria andE01z831In(List<String> values) {
            addCriterion("e01z831 in", values, "e01z831");
            return (Criteria) this;
        }

        public Criteria andE01z831NotIn(List<String> values) {
            addCriterion("e01z831 not in", values, "e01z831");
            return (Criteria) this;
        }

        public Criteria andE01z831Between(String value1, String value2) {
            addCriterion("e01z831 between", value1, value2, "e01z831");
            return (Criteria) this;
        }

        public Criteria andE01z831NotBetween(String value1, String value2) {
            addCriterion("e01z831 not between", value1, value2, "e01z831");
            return (Criteria) this;
        }

        public Criteria andE01z807IsNull() {
            addCriterion("e01z807 is null");
            return (Criteria) this;
        }

        public Criteria andE01z807IsNotNull() {
            addCriterion("e01z807 is not null");
            return (Criteria) this;
        }

        public Criteria andE01z807EqualTo(String value) {
            addCriterion("e01z807 =", value, "e01z807");
            return (Criteria) this;
        }

        public Criteria andE01z807NotEqualTo(String value) {
            addCriterion("e01z807 <>", value, "e01z807");
            return (Criteria) this;
        }

        public Criteria andE01z807GreaterThan(String value) {
            addCriterion("e01z807 >", value, "e01z807");
            return (Criteria) this;
        }

        public Criteria andE01z807GreaterThanOrEqualTo(String value) {
            addCriterion("e01z807 >=", value, "e01z807");
            return (Criteria) this;
        }

        public Criteria andE01z807LessThan(String value) {
            addCriterion("e01z807 <", value, "e01z807");
            return (Criteria) this;
        }

        public Criteria andE01z807LessThanOrEqualTo(String value) {
            addCriterion("e01z807 <=", value, "e01z807");
            return (Criteria) this;
        }

        public Criteria andE01z807Like(String value) {
            addCriterion("e01z807 like", value, "e01z807");
            return (Criteria) this;
        }

        public Criteria andE01z807NotLike(String value) {
            addCriterion("e01z807 not like", value, "e01z807");
            return (Criteria) this;
        }

        public Criteria andE01z807In(List<String> values) {
            addCriterion("e01z807 in", values, "e01z807");
            return (Criteria) this;
        }

        public Criteria andE01z807NotIn(List<String> values) {
            addCriterion("e01z807 not in", values, "e01z807");
            return (Criteria) this;
        }

        public Criteria andE01z807Between(String value1, String value2) {
            addCriterion("e01z807 between", value1, value2, "e01z807");
            return (Criteria) this;
        }

        public Criteria andE01z807NotBetween(String value1, String value2) {
            addCriterion("e01z807 not between", value1, value2, "e01z807");
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
