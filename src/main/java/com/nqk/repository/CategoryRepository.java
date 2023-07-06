/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nqk.repository;

import com.nqk.demohibernate.HibernateUtils;
import com.nqk.pojo.Category;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author Kiet
 */
public class CategoryRepository {
    
    public Set<Category> updateCategory(int categoryId, String newNameCategory) {
        try(Session session = HibernateUtils.getFACTORY().openSession()){
        
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaUpdate<Category> update = builder.createCriteriaUpdate(Category.class);
        Root<Category> rootCate = update.from(Category.class);
        
        update.set(rootCate.get("name"), newNameCategory);
        
        Predicate p1 = builder.equal(rootCate.get("id"), categoryId);
        update.where(p1);
        
        session.getTransaction().begin();
        session.createQuery(update).executeUpdate();
        session.getTransaction().commit();
        
        
        CriteriaQuery<Category> cq = builder.createQuery(Category.class);
        Root root = cq.from(Category.class);
        cq.select(root);
        
        cq.orderBy(builder.desc(root.get("id")));
        
        return new HashSet<>(session.createQuery(cq).getResultList());
            
        } 
    }
    
    public List<Category> getCategory(){
        try(Session session = HibernateUtils.getFACTORY().openSession()){
        
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Category> cq = builder.createQuery(Category.class);
        Root root = cq.from(Category.class);
        cq.select(root);
        cq.orderBy(builder.desc(root.get("id")));
        Query q = session.createQuery(cq);
        return q.getResultList();
        }
    }
}
