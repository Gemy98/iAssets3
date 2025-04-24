/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.persistence.EntityGraph
 *  javax.persistence.EntityManager
 *  javax.persistence.NoResultException
 *  javax.persistence.PersistenceContext
 *  javax.persistence.Query
 *  javax.persistence.TemporalType
 *  javax.persistence.TypedQuery
 *  org.hibernate.Hibernate
 *  org.hibernate.proxy.HibernateProxy
 *  org.springframework.stereotype.Repository
 *  org.springframework.transaction.annotation.Transactional
 */
package com.iassets.common.DB;

import com.iassets.common.entity.AbstractLocalizedEntity;
import com.iassets.common.entity.CommonSite;
import com.iassets.common.util.AppUtil;
import com.iassets.common.util.Common;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class DBManager {
    @PersistenceContext(unitName="iassets")
    private EntityManager entityManager;

    public <T> List<T> executeNamedQuery(String namedQueryName, Map<String, Object> params, Class<T> c, String graphName) {
        TypedQuery query = this.entityManager.createNamedQuery(namedQueryName, c);
        this.assignQueryNamedParameters((Query)query, params);
        this.assignGraphNameToQuery((Query)query, graphName);
        return query.getResultList();
    }

    public <T> T getJPQLSingleResultFromNamedQuery(String namedQueryName, Map<String, Object> params, Class<T> c, String graphName) {
        try {
            TypedQuery query = this.entityManager.createNamedQuery(namedQueryName, c);
            this.assignQueryNamedParameters((Query)query, params);
            this.assignGraphNameToQuery((Query)query, graphName);
            return (T)query.getSingleResult();
        }
        catch (NoResultException e) {
            return null;
        }
    }

    public <T> List<T> executeNamedQuery(String namedQueryName, Map<String, Object> params, Class<T> c) {
        return this.executeNamedQuery(namedQueryName, params, c, null);
    }

    public <T> List<T> executeNamedQuery(String namedQueryName, Class<T> c) {
        return this.executeNamedQuery(namedQueryName, null, c, null);
    }

    public int executeUpdateNamedQuery(String namedQueryName, Map<String, Object> params) {
        Query query = this.entityManager.createNamedQuery(namedQueryName);
        this.assignQueryNamedParameters(query, params);
        return query.executeUpdate();
    }

    public int executeUpdateNamedQuery(String namedQueryName) {
        return this.executeUpdateNamedQuery(namedQueryName, null);
    }

    public List executeNativeQueryForSearch(String sqlString, Class c, Map<Integer, Object> params) {
        Query query = c == null ? this.entityManager.createNativeQuery(sqlString) : this.entityManager.createNativeQuery(sqlString, c);
        this.setQueryPositionalParameters(query, params);
        Common.log(query.toString());
        return query.getResultList();
    }

    public List executeNativeQueryForSearch(String sqlString, Map<Integer, Object> params) {
        return this.executeNativeQueryForSearch(sqlString, null, params);
    }

    public int executeNativeQueryForUpdate(String sqlString, Map<Integer, Object> params) {
        Query query = this.entityManager.createNativeQuery(sqlString);
        this.setQueryPositionalParameters(query, params);
        Common.log(query.toString());
        return query.executeUpdate();
    }

    public <T> T getJPQLSingleResult(String jpqlStmt, Map<Integer, Object> params, Class<T> c, String graphName) {
        return this.executeRetriveJPQLWithPositionalParameters(jpqlStmt, params, c, graphName).get(0);
    }

    public <T> T getJPQLSingleResult(String jpqlStmt, Map<Integer, Object> params, Class<T> c) {
        return this.executeRetriveJPQLWithPositionalParameters(jpqlStmt, params, c, null).get(0);
    }

    public <T> List<T> executeRetriveJPQLWithPositionalParameters(String jpqlStmt, Map<Integer, Object> params, Class<T> c, String graphName) {
        TypedQuery query = this.entityManager.createQuery(jpqlStmt, c);
        this.setQueryPositionalParameters((Query)query, params);
        this.assignGraphNameToQuery((Query)query, graphName);
        return query.getResultList();
    }

    public <T> List<T> queryJPQL(String jpqlStmt, Map<Integer, Object> params, Class<T> c) {
        return this.executeRetriveJPQLWithPositionalParameters(jpqlStmt, params, c, null);
    }

    public <T> T executeRetriveSingleResultJPQLWithNamedParameters(String jpqlStmt, Map<String, Object> params, Class<T> c) {
        return this.executeRetriveSingleResultJPQLWithNamedParameters(jpqlStmt, params, c, null);
    }

    public <T> T executeRetriveSingleResultJPQLWithNamedParameters(String jpqlStmt, Map<String, Object> params, Class<T> c, String graphName) {
        TypedQuery query = this.entityManager.createQuery(jpqlStmt, c);
        this.setQueryNamedParameters((Query)query, params);
        this.assignGraphNameToQuery((Query)query, graphName);
        return (T)query.getSingleResult();
    }

    public <T> List<T> executeRetriveJPQLWithNamedParameters(String jpqlStmt, Map<String, Object> params, Class<T> c, String graphName) {
        TypedQuery query = this.entityManager.createQuery(jpqlStmt, c);
        this.setQueryNamedParameters((Query)query, params);
        this.assignGraphNameToQuery((Query)query, graphName);
        return query.getResultList();
    }

    public <T> List<T> executeRetriveJPQLWithNamedParameters(String jpqlStmt, Map<String, Object> params, Class<T> c) {
        return this.executeRetriveJPQLWithNamedParameters(jpqlStmt, params, c, null);
    }

    public int updateJPQL(String jpqlStmt, Map<Integer, Object> params) {
        Query query = this.entityManager.createQuery(jpqlStmt);
        this.setQueryPositionalParameters(query, params);
        return query.executeUpdate();
    }

    protected void assignQueryNamedParameters(Query query, Map<String, Object> params) {
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                if (params.get(key) instanceof Date) {
                    query.setParameter(key, (Date)params.get(key), TemporalType.DATE);
                    continue;
                }
                query.setParameter(key, params.get(key));
            }
        }
    }

    protected void setQueryPositionalParameters(Query query, Map<Integer, Object> params) {
        if (params != null && !params.isEmpty()) {
            for (Integer key : params.keySet()) {
                query.setParameter(key.intValue(), params.get(key));
            }
        }
    }

    protected void setQueryNamedParameters(Query query, Map<String, Object> params) {
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                Object value = entry.getValue();
                if (value == null) continue;
                query.setParameter(entry.getKey(), value);
            }
        }
    }

    protected void assignGraphNameToQuery(Query query, String graphName) {
        if (graphName != null && !graphName.isEmpty()) {
            query.setHint("javax.persistence.loadgraph", (Object)this.entityManager.getEntityGraph(graphName));
        }
    }

    public List queryByRange(String jpqlStmt, int firstResult, int maxResults) {
        Query query = this.entityManager.createQuery(jpqlStmt);
        if (firstResult > 0) {
            query = query.setFirstResult(firstResult);
        }
        if (maxResults > 0) {
            query = query.setMaxResults(maxResults);
        }
        return query.getResultList();
    }

    public List findAll(Class c) {
        return this.entityManager.createNamedQuery(c.getSimpleName() + ".findAll").getResultList();
    }

    public <T extends AbstractLocalizedEntity> List<T> findAll(Class<T> c, String langCode) {
        return AppUtil.sortLocalizedEntityList(this.entityManager.createNamedQuery(c.getSimpleName() + ".findAll").getResultList(), langCode);
    }

    public <T> T findById(int id, Class<T> c, String graphName) {
        HashMap<String, EntityGraph> hints = null;
        if (id == 0) {
            return null;
        }
        if (graphName != null && !graphName.isEmpty()) {
            hints = new HashMap<String, EntityGraph>();
            hints.put("javax.persistence.loadgraph", this.entityManager.getEntityGraph(graphName));
        }
        return (T)this.entityManager.find(c, (Object)id);
    }

    public <T> T findById(int id, Class<T> c) {
        return this.findById(id, c, null);
    }

    public <T> T findById(Object id, Class<T> c) {
        if (id == null) {
            return null;
        }
        return (T)this.entityManager.find(c, id);
    }

    public <T> T getReference(int id, Class<T> c) {
        if (id == 0) {
            return null;
        }
        return (T)this.entityManager.getReference(c, (Object)id);
    }

    public <T> T persistEntity(T entity) {
        this.entityManager.persist(entity);
        return entity;
    }

    public <T> T mergeEntity(T entity) {
        entity = this.entityManager.merge(entity);
        return entity;
    }

    public void deleteEntity(Object entity) {
        this.entityManager.remove(this.entityManager.merge(entity));
        entity = null;
    }

    protected <T> T initializeAndUnproxy(T entity) {
        if (entity == null) {
            throw new NullPointerException("Entity to be initialized is null");
        }
        Hibernate.initialize(entity);
        if (entity instanceof HibernateProxy) {
            entity = (T) ((HibernateProxy)entity).getHibernateLazyInitializer().getImplementation();
        }
        return entity;
    }

    public void destory() {
    }

    public static void main(String[] args) {
        System.out.println(AbstractLocalizedEntity.class.isAssignableFrom(CommonSite.class));
        System.out.println(CommonSite.class.isInstance(AbstractLocalizedEntity.class));
    }
}
