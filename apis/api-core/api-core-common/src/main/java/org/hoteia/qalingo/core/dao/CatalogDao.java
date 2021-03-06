/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.hoteia.qalingo.core.domain.CatalogMaster;
import org.hoteia.qalingo.core.domain.CatalogVirtual;
import org.hoteia.qalingo.core.fetchplan.FetchPlan;
import org.hoteia.qalingo.core.fetchplan.common.FetchPlanGraphCommon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository("catalogDao")
public class CatalogDao extends AbstractGenericDao {

	private final Logger logger = LoggerFactory.getLogger(getClass());

    // MASTER CATALOG

    public CatalogMaster getMasterCatalogById(final Long masterCatalogId, Object... params) {
        Criteria criteria = createDefaultCriteria(CatalogMaster.class);

        FetchPlan fetchPlan = handleSpecificFetchMode(criteria, params);
        
        criteria.add(Restrictions.eq("id", masterCatalogId));
        
        CatalogMaster catalogMaster = (CatalogMaster) criteria.uniqueResult();
        if(catalogMaster != null){
            catalogMaster.setFetchPlan(fetchPlan);
        }
        return catalogMaster;
	}
    
    public CatalogMaster getMasterCatalogByCode(final String masterCatalogCode, Object... params) {
        Criteria criteria = createDefaultCriteria(CatalogMaster.class);
        
        FetchPlan fetchPlan = handleSpecificFetchMode(criteria, params);

        criteria.add(Restrictions.eq("code", handleCodeValue(masterCatalogCode)));

        CatalogMaster catalog = (CatalogMaster) criteria.uniqueResult();
        if(catalog != null){
            catalog.setFetchPlan(fetchPlan);
        }
        return catalog;
    }
    
    public List<CatalogMaster> findAllCatalogMasters(Object... params) {
        Criteria criteria = createDefaultCriteria(CatalogMaster.class);
        
        handleSpecificFetchMode(criteria, params);
        
        criteria.addOrder(Order.asc("id"));

        @SuppressWarnings("unchecked")
        List<CatalogMaster> catalogMasters = criteria.list();
        return catalogMasters;
    }
    
    public CatalogMaster saveOrUpdateCatalogMaster(final CatalogMaster catalogMaster) {
        if(catalogMaster.getDateCreate() == null){
            catalogMaster.setDateCreate(new Date());
        }
        catalogMaster.setDateUpdate(new Date());
        if (catalogMaster.getId() != null) {
            if(em.contains(catalogMaster)){
                em.refresh(catalogMaster);
            }
            CatalogMaster mergedCatalogMaster = em.merge(catalogMaster);
            em.flush();
            return mergedCatalogMaster;
        } else {
            em.persist(catalogMaster);
            return catalogMaster;
        }
    }
    
    public void deleteCatalogMaster(final CatalogMaster catalogMaster) {
        em.remove(em.contains(catalogMaster) ? catalogMaster : em.merge(catalogMaster));
    }
    
    // VIRTUAL CATALOG

    public CatalogVirtual getVirtualCatalogById(final Long virtualCatalogId, Object... params) {
        Criteria criteria = createDefaultCriteria(CatalogVirtual.class);
        
        FetchPlan fetchPlan = handleSpecificFetchMode(criteria, params);
        
        criteria.add(Restrictions.eq("id", virtualCatalogId));
        
        CatalogVirtual catalogVirtual = (CatalogVirtual) criteria.uniqueResult();
        if(catalogVirtual != null){
            catalogVirtual.setFetchPlan(fetchPlan);
        }
        return catalogVirtual;
    }
    
	public CatalogVirtual getVirtualCatalogByMarketAreaId(final Long marketAreaId, Object... params) {
        Criteria criteria = createDefaultCriteria(CatalogVirtual.class);
        
        FetchPlan fetchPlan = handleSpecificFetchMode(criteria, params);
        
        criteria.setFetchMode("catalogMaster", FetchMode.JOIN);
        criteria.createAlias("marketArea", "marketArea", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq("marketArea.id", marketAreaId));

        CatalogVirtual catalogVirtual = (CatalogVirtual) criteria.uniqueResult();
        if(catalogVirtual != null){
            catalogVirtual.setFetchPlan(fetchPlan);
        }
		return catalogVirtual;
	}
	
    public CatalogVirtual getVirtualCatalogByCode(final String virtualCatalogCode, Object... params) {
        Criteria criteria = createDefaultCriteria(CatalogVirtual.class);
        
        FetchPlan fetchPlan = handleSpecificFetchMode(criteria, params);

        criteria.add(Restrictions.eq("code", virtualCatalogCode));

        CatalogVirtual catalog = (CatalogVirtual) criteria.uniqueResult();
        if(catalog != null){
            catalog.setFetchPlan(fetchPlan);
        }
        return catalog;
    }
    
    public List<CatalogVirtual> findAllCatalogVirtuals(Object... params) {
        Criteria criteria = createDefaultCriteria(CatalogVirtual.class);
        
        handleSpecificFetchMode(criteria, params);
        
        criteria.addOrder(Order.asc("id"));

        @SuppressWarnings("unchecked")
        List<CatalogVirtual> catalogVirtuals = criteria.list();
        return catalogVirtuals;
    }
    
    public CatalogVirtual saveOrUpdateCatalogVirtual(final CatalogVirtual catalogVirtual) {
        if(catalogVirtual.getDateCreate() == null){
            catalogVirtual.setDateCreate(new Date());
        }
        catalogVirtual.setDateUpdate(new Date());
        if (catalogVirtual.getId() != null) {
            if(em.contains(catalogVirtual)){
                em.refresh(catalogVirtual);
            }
            CatalogVirtual mergedCatalogVirtual = em.merge(catalogVirtual);
            em.flush();
            return mergedCatalogVirtual;
        } else {
            em.persist(catalogVirtual);
            return catalogVirtual;
        }
    }
    
    public void deleteCatalogVirtual(final CatalogVirtual catalogVirtual) {
        em.remove(em.contains(catalogVirtual) ? catalogVirtual : em.merge(catalogVirtual));
    }
	
    @Override
    protected FetchPlan handleSpecificFetchMode(Criteria criteria, Object... params) {
        if (params != null && params.length > 0) {
            return super.handleSpecificFetchMode(criteria, params);
        } else {
            return super.handleSpecificFetchMode(criteria, FetchPlanGraphCommon.defaultCatalogFetchPlan());
        }
    }
}