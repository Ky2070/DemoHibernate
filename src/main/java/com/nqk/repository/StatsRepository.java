/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nqk.repository;

import com.nqk.demohibernate.HibernateUtils;
import com.nqk.pojo.Category;
import com.nqk.pojo.OrderDetail;
import com.nqk.pojo.Product;
import com.nqk.pojo.SaleOrder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author Kiet
 */
public class StatsRepository {
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    public List<Object[]> countProductByCate(){
        try(Session session = HibernateUtils.getFACTORY().openSession()){
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Object[]> cquery = builder.createQuery(Object[].class);
            Root rP = cquery.from(Product.class);
            Root rC = cquery.from(Category.class);
            cquery.where(builder.equal(rP.get("categoryId"), rC.get("id")));
            
            cquery.multiselect(rC.get("id"),rC.get("name"), builder.count(rP.get("id")));
            
            cquery.groupBy(rC.get("id"));
            Query query = session.createQuery(cquery);
            
             return query.getResultList();
        }
       
    }
    
    public List<Object[]> statsRevenue(Map<String, String> params) throws ParseException{
        try(Session session = HibernateUtils.getFACTORY().openSession()){
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);
            
            Root rP = query.from(Product.class);
            Root rOd = query.from(OrderDetail.class);
            Root rSo = query.from(SaleOrder.class);
            
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(builder.equal(rOd.get("productId"), rP.get("id")));
            predicates.add(builder.equal(rOd.get("orderId"), rSo.get("id")));
            
            if(params != null){
                String kw = params.get("kw");
                if(kw != null && !kw.isEmpty())
                    predicates.add(builder.like(rP.get("name"), String.format("%%%s%%", kw)));
                String fromDate = params.get("fromDate");
                if(fromDate !=null && !fromDate.isEmpty())
                    predicates.add(builder.greaterThanOrEqualTo(rSo.get("createdDate"), dateFormat.parse(fromDate)));
                
                String toDate = params.get("toDate");
                if(toDate !=null && !toDate.isEmpty())
                    predicates.add(builder.lessThanOrEqualTo(rSo.get("createdDate"), dateFormat.parse(toDate)));
                
                String quarter = params.get("quarter");
                if(quarter != null && !quarter.isEmpty()){
                    String year = params.get("year");
                    if(year != null && !year.isEmpty()){
                        predicates.add(builder.equal(builder.function("YEAR", Integer.class, rSo.get("createdDate")), Integer.parseInt(year)));
                        predicates.add(builder.equal(builder.function("QUARTER", Integer.class, rSo.get("createdDate")), Integer.parseInt(quarter)));
                        
                    }
                        
                }
                    
                
            }
            query.where(predicates.toArray(Predicate[]::new));
            query.multiselect(rP.get("id"),rP.get("name"),
                    builder.sum(builder.prod(rOd.get("unitPrice"), rOd.get("num"))));
            query.groupBy(rP.get("id"), rP.get("name"));
            
            Query q = session.createQuery(query);
            return q.getResultList();
       
        }
    }
}
