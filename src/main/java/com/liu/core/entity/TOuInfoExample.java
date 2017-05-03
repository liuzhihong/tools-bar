package com.liu.core.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TOuInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TOuInfoExample() {
        oredCriteria = new ArrayList<Criteria>();
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
            criteria = new ArrayList<Criterion>();
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

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andOuIdIsNull() {
            addCriterion("ou_id is null");
            return (Criteria) this;
        }

        public Criteria andOuIdIsNotNull() {
            addCriterion("ou_id is not null");
            return (Criteria) this;
        }

        public Criteria andOuIdEqualTo(Integer value) {
            addCriterion("ou_id =", value, "ouId");
            return (Criteria) this;
        }

        public Criteria andOuIdNotEqualTo(Integer value) {
            addCriterion("ou_id <>", value, "ouId");
            return (Criteria) this;
        }

        public Criteria andOuIdGreaterThan(Integer value) {
            addCriterion("ou_id >", value, "ouId");
            return (Criteria) this;
        }

        public Criteria andOuIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("ou_id >=", value, "ouId");
            return (Criteria) this;
        }

        public Criteria andOuIdLessThan(Integer value) {
            addCriterion("ou_id <", value, "ouId");
            return (Criteria) this;
        }

        public Criteria andOuIdLessThanOrEqualTo(Integer value) {
            addCriterion("ou_id <=", value, "ouId");
            return (Criteria) this;
        }

        public Criteria andOuIdIn(List<Integer> values) {
            addCriterion("ou_id in", values, "ouId");
            return (Criteria) this;
        }

        public Criteria andOuIdNotIn(List<Integer> values) {
            addCriterion("ou_id not in", values, "ouId");
            return (Criteria) this;
        }

        public Criteria andOuIdBetween(Integer value1, Integer value2) {
            addCriterion("ou_id between", value1, value2, "ouId");
            return (Criteria) this;
        }

        public Criteria andOuIdNotBetween(Integer value1, Integer value2) {
            addCriterion("ou_id not between", value1, value2, "ouId");
            return (Criteria) this;
        }

        public Criteria andOuNameIsNull() {
            addCriterion("ou_name is null");
            return (Criteria) this;
        }

        public Criteria andOuNameIsNotNull() {
            addCriterion("ou_name is not null");
            return (Criteria) this;
        }

        public Criteria andOuNameEqualTo(String value) {
            addCriterion("ou_name =", value, "ouName");
            return (Criteria) this;
        }

        public Criteria andOuNameNotEqualTo(String value) {
            addCriterion("ou_name <>", value, "ouName");
            return (Criteria) this;
        }

        public Criteria andOuNameGreaterThan(String value) {
            addCriterion("ou_name >", value, "ouName");
            return (Criteria) this;
        }

        public Criteria andOuNameGreaterThanOrEqualTo(String value) {
            addCriterion("ou_name >=", value, "ouName");
            return (Criteria) this;
        }

        public Criteria andOuNameLessThan(String value) {
            addCriterion("ou_name <", value, "ouName");
            return (Criteria) this;
        }

        public Criteria andOuNameLessThanOrEqualTo(String value) {
            addCriterion("ou_name <=", value, "ouName");
            return (Criteria) this;
        }

        public Criteria andOuNameLike(String value) {
            addCriterion("ou_name like", value, "ouName");
            return (Criteria) this;
        }

        public Criteria andOuNameNotLike(String value) {
            addCriterion("ou_name not like", value, "ouName");
            return (Criteria) this;
        }

        public Criteria andOuNameIn(List<String> values) {
            addCriterion("ou_name in", values, "ouName");
            return (Criteria) this;
        }

        public Criteria andOuNameNotIn(List<String> values) {
            addCriterion("ou_name not in", values, "ouName");
            return (Criteria) this;
        }

        public Criteria andOuNameBetween(String value1, String value2) {
            addCriterion("ou_name between", value1, value2, "ouName");
            return (Criteria) this;
        }

        public Criteria andOuNameNotBetween(String value1, String value2) {
            addCriterion("ou_name not between", value1, value2, "ouName");
            return (Criteria) this;
        }

        public Criteria andOuAddressIsNull() {
            addCriterion("ou_address is null");
            return (Criteria) this;
        }

        public Criteria andOuAddressIsNotNull() {
            addCriterion("ou_address is not null");
            return (Criteria) this;
        }

        public Criteria andOuAddressEqualTo(String value) {
            addCriterion("ou_address =", value, "ouAddress");
            return (Criteria) this;
        }

        public Criteria andOuAddressNotEqualTo(String value) {
            addCriterion("ou_address <>", value, "ouAddress");
            return (Criteria) this;
        }

        public Criteria andOuAddressGreaterThan(String value) {
            addCriterion("ou_address >", value, "ouAddress");
            return (Criteria) this;
        }

        public Criteria andOuAddressGreaterThanOrEqualTo(String value) {
            addCriterion("ou_address >=", value, "ouAddress");
            return (Criteria) this;
        }

        public Criteria andOuAddressLessThan(String value) {
            addCriterion("ou_address <", value, "ouAddress");
            return (Criteria) this;
        }

        public Criteria andOuAddressLessThanOrEqualTo(String value) {
            addCriterion("ou_address <=", value, "ouAddress");
            return (Criteria) this;
        }

        public Criteria andOuAddressLike(String value) {
            addCriterion("ou_address like", value, "ouAddress");
            return (Criteria) this;
        }

        public Criteria andOuAddressNotLike(String value) {
            addCriterion("ou_address not like", value, "ouAddress");
            return (Criteria) this;
        }

        public Criteria andOuAddressIn(List<String> values) {
            addCriterion("ou_address in", values, "ouAddress");
            return (Criteria) this;
        }

        public Criteria andOuAddressNotIn(List<String> values) {
            addCriterion("ou_address not in", values, "ouAddress");
            return (Criteria) this;
        }

        public Criteria andOuAddressBetween(String value1, String value2) {
            addCriterion("ou_address between", value1, value2, "ouAddress");
            return (Criteria) this;
        }

        public Criteria andOuAddressNotBetween(String value1, String value2) {
            addCriterion("ou_address not between", value1, value2, "ouAddress");
            return (Criteria) this;
        }

        public Criteria andCretaeTimeIsNull() {
            addCriterion("cretae_time is null");
            return (Criteria) this;
        }

        public Criteria andCretaeTimeIsNotNull() {
            addCriterion("cretae_time is not null");
            return (Criteria) this;
        }

        public Criteria andCretaeTimeEqualTo(Date value) {
            addCriterion("cretae_time =", value, "cretaeTime");
            return (Criteria) this;
        }

        public Criteria andCretaeTimeNotEqualTo(Date value) {
            addCriterion("cretae_time <>", value, "cretaeTime");
            return (Criteria) this;
        }

        public Criteria andCretaeTimeGreaterThan(Date value) {
            addCriterion("cretae_time >", value, "cretaeTime");
            return (Criteria) this;
        }

        public Criteria andCretaeTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("cretae_time >=", value, "cretaeTime");
            return (Criteria) this;
        }

        public Criteria andCretaeTimeLessThan(Date value) {
            addCriterion("cretae_time <", value, "cretaeTime");
            return (Criteria) this;
        }

        public Criteria andCretaeTimeLessThanOrEqualTo(Date value) {
            addCriterion("cretae_time <=", value, "cretaeTime");
            return (Criteria) this;
        }

        public Criteria andCretaeTimeIn(List<Date> values) {
            addCriterion("cretae_time in", values, "cretaeTime");
            return (Criteria) this;
        }

        public Criteria andCretaeTimeNotIn(List<Date> values) {
            addCriterion("cretae_time not in", values, "cretaeTime");
            return (Criteria) this;
        }

        public Criteria andCretaeTimeBetween(Date value1, Date value2) {
            addCriterion("cretae_time between", value1, value2, "cretaeTime");
            return (Criteria) this;
        }

        public Criteria andCretaeTimeNotBetween(Date value1, Date value2) {
            addCriterion("cretae_time not between", value1, value2, "cretaeTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
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