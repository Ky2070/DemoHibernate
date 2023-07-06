/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.nqk.demohibernate;

import com.nqk.repository.CategoryRepository;
import com.nqk.repository.ProductRepository;
import com.nqk.repository.StatsRepository;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import org.hibernate.Session;

/**
 *
 * @author Kiet
 */
public class DemoHibernate {

    public static void main(String[] args) throws ParseException {
        try (Session session = HibernateUtils.getFACTORY().openSession()) {
//           Query q = session.createQuery("FROM Category");
//           List<Category> cates = q.getResultList();
//           
//           cates.forEach(c -> System.out.printf("%d - %s\n", c.getId(), c.getName()));
//          
            StatsRepository sR = new StatsRepository();
//            sR.countProductByCate().forEach(o -> System.out.printf("%d - %s - %d\n", o[0], o[1], o[2]));
            Map<String, String> params = new HashMap<>();
//            params.put("kw", "Iphone");
//            params.put("fromDate","2020-02-07");
//            params.put("toDate","2020-02-07");
            params.put("quarter", "4");
            params.put("year", "2020");
            sR.statsRevenue(params).forEach(o -> System.out.printf("%d - %s - %d\n", o[0], o[1], o[2]));

//            CategoryRepository cR = new CategoryRepository();
//            cR.getCategory().forEach(c -> System.out.printf("%d - %s\n", c.getId(), c.getName()));
//            cR.updateCategory(0, "").forEach(cates -> System.out.printf("%d - %s\n", cates.getId(),cates.getName()));
//            
//            Map<String, String> params = new HashMap<>();
//            params.put("kw", "Iphone");
//            params.put("kw", "Galaxy");
//            params.put("fromPrice", "20000000");
//              params.put("toPrice", "30000000");
//              params.put("cateId", "1");
//            ProductRepository pR = new ProductRepository();
//            pR.getProducts(null).forEach(p -> System.out.printf("%d - %s - %.1f\n", p.getId(), p.getName(), p.getPrice()));
            //Dùng HQL để in ra danh sách sản phẩm
//            Query q = session.createQuery("FROM Product", Product.class);
//            List<Product> products = q.getResultList();
//            products.forEach(p -> System.out.printf("%d - Name: %s\nDescription:%s\nImage:%s\nCreateDate:%s\n"
//                    ,p.getId(),p.getName(),p.getDescription(),p.getImage(),p.getCreatedDate()));
            //Dùng Criteria Query API
//            CriteriaBuilder builder = session.getCriteriaBuilder();
//            CriteriaQuery<Product> cq = builder.createQuery(Product.class);
//            Root<Product> root = cq.from(Product.class);
//            cq.select(root);
//            List<Product> products = session.createQuery(cq).getResultList();
//            products.forEach(p -> System.out.printf("%d - %s\n", p.getId(), p.getName()));
//              Product p = session.get(Product.class, 2);
//              System.out.println(p.getName());
//                Category c = session.get(Category.class, 1);
//                c.getProductSet().forEach(p->System.out.printf("%d-%s\n", p.getId(),p.getName()));
            session.close();

        }
    }
}
