package com.ylsh.survey.pojo;

import java.util.ArrayList;
import java.util.List;

public class TbQuestionExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TbQuestionExample() {
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

        public Criteria andQuestionDescIsNull() {
            addCriterion("question_desc is null");
            return (Criteria) this;
        }

        public Criteria andQuestionDescIsNotNull() {
            addCriterion("question_desc is not null");
            return (Criteria) this;
        }

        public Criteria andQuestionDescEqualTo(String value) {
            addCriterion("question_desc =", value, "questionDesc");
            return (Criteria) this;
        }

        public Criteria andQuestionDescNotEqualTo(String value) {
            addCriterion("question_desc <>", value, "questionDesc");
            return (Criteria) this;
        }

        public Criteria andQuestionDescGreaterThan(String value) {
            addCriterion("question_desc >", value, "questionDesc");
            return (Criteria) this;
        }

        public Criteria andQuestionDescGreaterThanOrEqualTo(String value) {
            addCriterion("question_desc >=", value, "questionDesc");
            return (Criteria) this;
        }

        public Criteria andQuestionDescLessThan(String value) {
            addCriterion("question_desc <", value, "questionDesc");
            return (Criteria) this;
        }

        public Criteria andQuestionDescLessThanOrEqualTo(String value) {
            addCriterion("question_desc <=", value, "questionDesc");
            return (Criteria) this;
        }

        public Criteria andQuestionDescLike(String value) {
            addCriterion("question_desc like", value, "questionDesc");
            return (Criteria) this;
        }

        public Criteria andQuestionDescNotLike(String value) {
            addCriterion("question_desc not like", value, "questionDesc");
            return (Criteria) this;
        }

        public Criteria andQuestionDescIn(List<String> values) {
            addCriterion("question_desc in", values, "questionDesc");
            return (Criteria) this;
        }

        public Criteria andQuestionDescNotIn(List<String> values) {
            addCriterion("question_desc not in", values, "questionDesc");
            return (Criteria) this;
        }

        public Criteria andQuestionDescBetween(String value1, String value2) {
            addCriterion("question_desc between", value1, value2, "questionDesc");
            return (Criteria) this;
        }

        public Criteria andQuestionDescNotBetween(String value1, String value2) {
            addCriterion("question_desc not between", value1, value2, "questionDesc");
            return (Criteria) this;
        }

        public Criteria andNaireIdIsNull() {
            addCriterion("naire_id is null");
            return (Criteria) this;
        }

        public Criteria andNaireIdIsNotNull() {
            addCriterion("naire_id is not null");
            return (Criteria) this;
        }

        public Criteria andNaireIdEqualTo(Long value) {
            addCriterion("naire_id =", value, "naireId");
            return (Criteria) this;
        }

        public Criteria andNaireIdNotEqualTo(Long value) {
            addCriterion("naire_id <>", value, "naireId");
            return (Criteria) this;
        }

        public Criteria andNaireIdGreaterThan(Long value) {
            addCriterion("naire_id >", value, "naireId");
            return (Criteria) this;
        }

        public Criteria andNaireIdGreaterThanOrEqualTo(Long value) {
            addCriterion("naire_id >=", value, "naireId");
            return (Criteria) this;
        }

        public Criteria andNaireIdLessThan(Long value) {
            addCriterion("naire_id <", value, "naireId");
            return (Criteria) this;
        }

        public Criteria andNaireIdLessThanOrEqualTo(Long value) {
            addCriterion("naire_id <=", value, "naireId");
            return (Criteria) this;
        }

        public Criteria andNaireIdIn(List<Long> values) {
            addCriterion("naire_id in", values, "naireId");
            return (Criteria) this;
        }

        public Criteria andNaireIdNotIn(List<Long> values) {
            addCriterion("naire_id not in", values, "naireId");
            return (Criteria) this;
        }

        public Criteria andNaireIdBetween(Long value1, Long value2) {
            addCriterion("naire_id between", value1, value2, "naireId");
            return (Criteria) this;
        }

        public Criteria andNaireIdNotBetween(Long value1, Long value2) {
            addCriterion("naire_id not between", value1, value2, "naireId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdIsNull() {
            addCriterion("category_id is null");
            return (Criteria) this;
        }

        public Criteria andCategoryIdIsNotNull() {
            addCriterion("category_id is not null");
            return (Criteria) this;
        }

        public Criteria andCategoryIdEqualTo(Byte value) {
            addCriterion("category_id =", value, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdNotEqualTo(Byte value) {
            addCriterion("category_id <>", value, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdGreaterThan(Byte value) {
            addCriterion("category_id >", value, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdGreaterThanOrEqualTo(Byte value) {
            addCriterion("category_id >=", value, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdLessThan(Byte value) {
            addCriterion("category_id <", value, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdLessThanOrEqualTo(Byte value) {
            addCriterion("category_id <=", value, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdIn(List<Byte> values) {
            addCriterion("category_id in", values, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdNotIn(List<Byte> values) {
            addCriterion("category_id not in", values, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdBetween(Byte value1, Byte value2) {
            addCriterion("category_id between", value1, value2, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdNotBetween(Byte value1, Byte value2) {
            addCriterion("category_id not between", value1, value2, "categoryId");
            return (Criteria) this;
        }

        public Criteria andOrderNumberIsNull() {
            addCriterion("order_number is null");
            return (Criteria) this;
        }

        public Criteria andOrderNumberIsNotNull() {
            addCriterion("order_number is not null");
            return (Criteria) this;
        }

        public Criteria andOrderNumberEqualTo(Integer value) {
            addCriterion("order_number =", value, "orderNumber");
            return (Criteria) this;
        }

        public Criteria andOrderNumberNotEqualTo(Integer value) {
            addCriterion("order_number <>", value, "orderNumber");
            return (Criteria) this;
        }

        public Criteria andOrderNumberGreaterThan(Integer value) {
            addCriterion("order_number >", value, "orderNumber");
            return (Criteria) this;
        }

        public Criteria andOrderNumberGreaterThanOrEqualTo(Integer value) {
            addCriterion("order_number >=", value, "orderNumber");
            return (Criteria) this;
        }

        public Criteria andOrderNumberLessThan(Integer value) {
            addCriterion("order_number <", value, "orderNumber");
            return (Criteria) this;
        }

        public Criteria andOrderNumberLessThanOrEqualTo(Integer value) {
            addCriterion("order_number <=", value, "orderNumber");
            return (Criteria) this;
        }

        public Criteria andOrderNumberIn(List<Integer> values) {
            addCriterion("order_number in", values, "orderNumber");
            return (Criteria) this;
        }

        public Criteria andOrderNumberNotIn(List<Integer> values) {
            addCriterion("order_number not in", values, "orderNumber");
            return (Criteria) this;
        }

        public Criteria andOrderNumberBetween(Integer value1, Integer value2) {
            addCriterion("order_number between", value1, value2, "orderNumber");
            return (Criteria) this;
        }

        public Criteria andOrderNumberNotBetween(Integer value1, Integer value2) {
            addCriterion("order_number not between", value1, value2, "orderNumber");
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