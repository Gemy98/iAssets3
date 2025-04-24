///*
// * Decompiled with CFR 0.152.
// *
// * Could not load the following classes:
// *  javax.persistence.EntityManager
// *  javax.persistence.Id
// *  javax.persistence.Query
// *  javax.persistence.TemporalType
// *  javax.persistence.TypedQuery
// *  org.apache.commons.lang.StringUtils
// */
//package com.iassets.common.util;
//
//import com.iassets.common.util.DynamicQueryBuilder;
//import com.iassets.common.util.SearchRequired;
//import java.lang.annotation.Annotation;
//import java.lang.reflect.Field;
//import java.sql.Timestamp;
//import java.util.Collection;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import javax.persistence.EntityManager;
//import javax.persistence.Id;
//import javax.persistence.Query;
//import javax.persistence.TemporalType;
//import javax.persistence.TypedQuery;
//import org.apache.commons.lang.StringUtils;
//
//public static class DynamicQueryBuilder.Builder<T> {
//    private DynamicQueryBuilder dqb;
//    private String queryObjChar = "";
//    private EntityManager em;
//    private T beanType;
//
//    public DynamicQueryBuilder.Builder(EntityManager em) {
//        this.em = em;
//        this.dqb = new DynamicQueryBuilder(null);
//    }
//
//    public DynamicQueryBuilder.Builder select(T beanType) {
//        if (beanType == null) {
//            throw new IllegalArgumentException("Bean should be passed.");
//        }
//        this.beanType = beanType;
//        this.queryObjChar = this.resolveName(beanType.getClass().getName()).substring(0, 1).toLowerCase();
//        this.append("SELECT " + this.queryObjChar + " FROM " + this.resolveName(beanType.getClass().getName()) + " " + this.queryObjChar);
//        this.queryObjChar = this.queryObjChar + ".";
//        return this;
//    }
//
//    public DynamicQueryBuilder.Builder withOperType(DynamicQueryBuilder.Operator.OperType operType) {
//        if (this.dqb.operType == null) {
//            this.dqb.operType = operType;
//        }
//        return this;
//    }
//
//    public DynamicQueryBuilder.Builder orderBy(String param, boolean desc) {
//        this.dqb.orderByParams.put(param, desc ? "DESC" : "ASC");
//        return this;
//    }
//
//    public DynamicQueryBuilder.Builder orderBy(String param) {
//        this.orderBy(param, false);
//        return this;
//    }
//
//    public DynamicQueryBuilder.Builder orderBy(Map<String, Boolean> orderByParams) {
//        if (orderByParams != null) {
//            for (String key : orderByParams.keySet()) {
//                this.orderBy(key, orderByParams.get(key));
//            }
//        }
//        return this;
//    }
//
//    public DynamicQueryBuilder.Builder withAdvancedParam(String param, DynamicQueryBuilder.Operator operator, Object extras) throws IllegalArgumentException {
//        this.isValidAdvancedParam(operator, extras);
//        HashMap<DynamicQueryBuilder.Operator, Object> others = new HashMap<DynamicQueryBuilder.Operator, Object>();
//        others.put(operator, extras);
//        this.dqb.advancedParams.put(param, others);
//        return this;
//    }
//
//    public DynamicQueryBuilder.Builder withAdvancedParam(String param, DynamicQueryBuilder.Operator operator) throws IllegalArgumentException {
//        this.withAdvancedParam(param, operator, null);
//        return this;
//    }
//
//    public DynamicQueryBuilder.Builder withAdvancedParams(Map<String, Map<DynamicQueryBuilder.Operator, Object>> advancedParams) {
//        if (advancedParams != null) {
//            this.dqb.advancedParams.putAll(advancedParams);
//        }
//        return this;
//    }
//
//    public DynamicQueryBuilder build() {
//        try {
//            this.createQueryAndParams();
//            this.resolveAdvancedParams();
//            this.resolveOrderByParams();
//            this.prepareQueryParams();
//        }
//        catch (IllegalAccessException iae) {
//            iae.printStackTrace();
//        }
//        return this.dqb;
//    }
//
//    private void append(String newVal) {
//        this.dqb.queryString.append(newVal);
//    }
//
//    private void put(String key, Object value) {
//        this.dqb.paramsAndValues.put(key, value);
//    }
//
//    private void createQueryAndParams() throws IllegalAccessException {
//        Map<Field, SearchRequired> fields = this.getSearchFields();
//        int i = 0;
//        if (fields.size() != 0) {
//            this.append(" WHERE ");
//        }
//        for (Field field : fields.keySet()) {
//            ++i;
//            SearchRequired srAnnotation = fields.get(field);
//            if (srAnnotation.isObject()) {
//                Field[] subFields = field.getType().getDeclaredFields();
//                String[] searchFields = srAnnotation.fields();
//                if (searchFields.length == 0) {
//                    this.append(this.queryObjChar + field.getName() + " = :" + field.getName());
//                    this.put(field.getName(), field.get(this.beanType));
//                    if (i == fields.keySet().size()) continue;
//                    this.append(this.dqb.operType.getOp());
//                    continue;
//                }
//                block1: for (String name : searchFields) {
//                    for (Field subField : subFields) {
//                        if (!subField.getName().equals(name)) continue;
//                        subField.setAccessible(true);
//                        if (!this.isContainingValue(subField.get(field.get(this.beanType)))) continue block1;
//                        String fullName = field.getName() + StringUtils.capitalize((String)subField.getName());
//                        this.append(this.queryObjChar + field.getName() + "." + subField.getName() + " = :" + fullName);
//                        this.put(fullName, subField.get(field.get(this.beanType)));
//                        if (i == fields.keySet().size()) continue block1;
//                        this.append(this.dqb.operType.getOp());
//                        continue block1;
//                    }
//                }
//                continue;
//            }
//            this.append(this.queryObjChar + field.getName() + " = :" + field.getName());
//            this.put(field.getName(), field.get(this.beanType));
//            if (i == fields.keySet().size()) continue;
//            this.append(this.dqb.operType.getOp());
//        }
//    }
//
//    private boolean isContainingValue(Object value) {
//        boolean containsValue = false;
//        if (value != null) {
//            containsValue = value instanceof String ? !((String)String.class.cast(value)).trim().isEmpty() : true;
//        }
//        return containsValue;
//    }
//
//    private String resolveName(String name) {
//        return StringUtils.substringAfterLast((String)name, (String)".");
//    }
//
//    private boolean isIdFieldContainsValue(Field[] subFields, Object type) throws IllegalAccessException {
//        boolean containsValue = true;
//        if (subFields != null) {
//            for (Field field : subFields) {
//                for (Annotation annotation : field.getDeclaredAnnotations()) {
//                    if (!(annotation instanceof Id)) continue;
//                    field.setAccessible(true);
//                    if (this.isContainingValue(field.get(type))) continue;
//                    containsValue = false;
//                }
//            }
//        }
//        return containsValue;
//    }
//
//    private Map<Field, SearchRequired> getSearchFields() throws IllegalAccessException {
//        HashMap<Field, SearchRequired> fields = new HashMap<Field, SearchRequired>();
//        for (Field field : this.beanType.getClass().getDeclaredFields()) {
//            for (Annotation annotation : field.getDeclaredAnnotations()) {
//                if (!(annotation instanceof SearchRequired)) continue;
//                SearchRequired srAnno = (SearchRequired)SearchRequired.class.cast(annotation);
//                field.setAccessible(true);
//                if (!this.isContainingValue(field.get(this.beanType))) continue;
//                if (srAnno.isObject()) {
//                    if (srAnno.fields().length == 0 && (srAnno.fields().length != 0 || !this.isIdFieldContainsValue(field.getType().getDeclaredFields(), field.get(this.beanType)))) continue;
//                    fields.put(field, srAnno);
//                    continue;
//                }
//                fields.put(field, srAnno);
//            }
//        }
//        return fields;
//    }
//
//    private void resolveAdvancedParams() {
//        if (this.dqb.advancedParams == null || this.dqb.advancedParams.isEmpty()) {
//            return;
//        }
//        for (String key : this.dqb.advancedParams.keySet()) {
//            if (!this.dqb.paramsAndValues.containsKey(key)) continue;
//            DynamicQueryBuilder.Operator oper = (DynamicQueryBuilder.Operator)((Object)((Map)this.dqb.advancedParams.get(key)).keySet().iterator().next());
//            Object extra = ((Map)this.dqb.advancedParams.get(key)).get((Object)oper);
//            this.handleAdvancedParamsOpers(key.trim(), oper, extra);
//        }
//    }
//
//    private void resolveOrderByParams() {
//        if (this.dqb.orderByParams == null || this.dqb.orderByParams.isEmpty()) {
//            return;
//        }
//        this.append(" ORDER BY ");
//        int i = this.dqb.orderByParams.keySet().size();
//        for (String key : this.dqb.orderByParams.keySet()) {
//            this.append(this.queryObjChar + key + " " + (String)this.dqb.orderByParams.get(key));
//            if (--i <= 0) continue;
//            this.append(", ");
//        }
//    }
//
//    private void handleAdvancedParamsOpers(String key, DynamicQueryBuilder.Operator operator, Object extras) {
//        int start = this.dqb.queryString.toString().indexOf(":" + key);
//        this.dqb.queryString.replace(start - 2, start, operator.getOp());
//        if (operator.equals((Object)DynamicQueryBuilder.Operator.IN)) {
//            this.dqb.paramsAndValues.put(key, extras);
//        } else if (operator.equals((Object)DynamicQueryBuilder.Operator.LIKE)) {
//            this.dqb.paramsAndValues.put(key, DynamicQueryBuilder.Operator.adjustLikeValue((String)this.dqb.paramsAndValues.get(key), (DynamicQueryBuilder.Operator.LikePattern)((Object)DynamicQueryBuilder.Operator.LikePattern.class.cast(extras))));
//        } else if (operator.equals((Object)DynamicQueryBuilder.Operator.BETWEEN)) {
//            int queryKeyPosition = this.dqb.queryString.toString().indexOf(key);
//            while (this.dqb.queryString.toString().charAt(queryKeyPosition) != this.queryObjChar.charAt(0)) {
//                --queryKeyPosition;
//            }
//            this.dqb.queryString.replace(queryKeyPosition - 1, queryKeyPosition, " (");
//            queryKeyPosition = this.dqb.queryString.toString().indexOf(":" + key);
//            this.dqb.queryString.replace(queryKeyPosition, queryKeyPosition + key.length() + 1, ":" + key + "From AND :" + key + "To)");
//            this.dqb.paramsAndValues.remove(key);
//            Collection betweenValues = (Collection)Collection.class.cast(extras);
//            this.dqb.paramsAndValues.put(key + "From", betweenValues.iterator().next());
//            this.dqb.paramsAndValues.put(key + "To", betweenValues.iterator().next());
//        }
//    }
//
//    private void prepareQueryParams() {
//        if (this.em == null) {
//            return;
//        }
//        TypedQuery query = this.em.createQuery(this.dqb.getQueryString(), this.beanType.getClass());
//        if (this.dqb.paramsAndValues != null || !this.dqb.paramsAndValues.isEmpty()) {
//            for (String key : this.dqb.paramsAndValues.keySet()) {
//                Object value = this.dqb.paramsAndValues.get(key);
//                if (value instanceof Timestamp) {
//                    query.setParameter(key, (Date)Timestamp.class.cast(value), TemporalType.TIMESTAMP);
//                    continue;
//                }
//                if (value instanceof Date) {
//                    query.setParameter(key, (Date)Date.class.cast(value), TemporalType.DATE);
//                    continue;
//                }
//                query.setParameter(key, value);
//            }
//        }
//        this.dqb.query = (Query)query;
//    }
//
//    private void isValidAdvancedParam(DynamicQueryBuilder.Operator operator, Object value) throws IllegalArgumentException {
//        switch (operator) {
//            case LIKE: {
//                if (value instanceof DynamicQueryBuilder.Operator.LikePattern) break;
//                throw new IllegalArgumentException("You should use one value of 'Operator.LikePattern' enum with 'LIKE' operator.");
//            }
//            case IN: {
//                if (value instanceof Collection) break;
//                throw new IllegalArgumentException("You should use 'Collection' object with 'IN' operator.");
//            }
//            case BETWEEN: {
//                if (value instanceof Collection) {
//                    Collection values = (Collection)Collection.class.cast(value);
//                    if (values.size() == 2) break;
//                    throw new IllegalArgumentException("The max values in 'Collection' object with 'BETWEEN' operator should be 2.");
//                }
//                throw new IllegalArgumentException("You should use 'Collection' object with 'BETWEEN' operator.");
//            }
//        }
//    }
//}
