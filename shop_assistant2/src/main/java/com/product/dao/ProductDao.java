package com.product.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.product.data.Product;

@Repository
public class ProductDao implements IProductDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<Product> listProduct() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		List<Product> list;
		try {
			list = session.createQuery("from Product").list();
		} finally {
			session.close();
		}
		return list;
	}

	@Override
	public void addProduct(Product product) {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.openSession();

		session.save(product);
		session.close();
	}

	@Override
	public void removeProduct(Product product) {
		// TODO Auto-generated method stub

	}

	public void deleteProduct(Integer id) {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.openSession();
		Query query = session.createQuery("delete Product where productId = :id");
		query.setParameter("id", id);
		query.executeUpdate();
		session.close();
	}

}
